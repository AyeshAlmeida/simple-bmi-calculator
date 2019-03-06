package hms.cpaas.simple.bmi.calculator.service;

import hms.cpaas.simple.bmi.calculator.api.*;
import hms.cpaas.simple.bmi.calculator.service.sms.SMSMtExecutor;
import hms.cpaas.simple.bmi.calculator.service.ussd.USSDMtExecutor;
import hms.cpaas.simple.bmi.calculator.util.AppUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class SystemRequestHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemRequestHandler.class);

    private static final String EXIT_SERVICE_CODE = "000";
    private static final String BACK_SERVICE_CODE = "999";
    private static final String USSD_OP_MO_INIT = "mo-init";
    private static final String USSD_OP_MT_CONT = "mt-cont";
    private static final String USSD_OP_MT_FIN = "mt-fin";

    @Value("${ideamart.application.password}")
    private String applicationPassword;

    @Value("${menu.level.ONE}")
    private String menuContentLevelOne;
    @Value("${menu.level.TWO}")
    private String menuContentLevelTwo;
    @Value("${menu.level.THREE}")
    private String menuContentLevelThree;

    @Value("${menu.exit.msg}")
    private String menuExitMsg;

    private Map<String, SessionData> sessionDetails = new HashMap<>();

    @Autowired
    private USSDMtExecutor ussdContentSender;
    @Autowired
    private SMSMtExecutor smsContentSender;
    @Autowired
    private BMIService bmiService;

    public Mono<USSDReceiveResponseObject> handlePlatformRequest(String requestId, USSDIndicationObject indication) {
        USSDSendRequestObject ussdContentToBeUpdated;
        if (indication.getUssdOperation().equalsIgnoreCase(USSD_OP_MO_INIT)) {
            initialSessionDetailUpdate(indication);
            String initUssdContent = getUssdContentByCurrentLevel(0);
            ussdContentToBeUpdated = getRequest(indication, initUssdContent, USSD_OP_MT_CONT);
        } else {
            SessionData sessionData = sessionDetails.get(indication.getSessionId());

            if (indication.getMessage().equalsIgnoreCase(EXIT_SERVICE_CODE)) {
                sessionDetails.remove(indication.getSessionId());
                ussdContentToBeUpdated = getRequest(indication, menuExitMsg, USSD_OP_MT_FIN);
            } else {
                int currentUssdLevel;
                if (indication.getMessage().equalsIgnoreCase(BACK_SERVICE_CODE)) {
                    currentUssdLevel = sessionData.getLevel() - 1;
                } else {
                    currentUssdLevel = sessionData.getLevel() + 1;
                }
                updateSessionByCurrentLevel(currentUssdLevel, indication, sessionData);
                ussdContentToBeUpdated = getRequest(indication, getUssdContentByCurrentLevel(currentUssdLevel), USSD_OP_MT_CONT);
            }
        }

        ussdContentSender.sendRequest(ussdContentToBeUpdated).subscribe();

        LOGGER.info("SessionDetails [{}]", sessionDetails);

        USSDReceiveResponseObject response = new USSDReceiveResponseObject();
        response.setStatusCode("S1000");
        response.setStatusDetail("Successfully handled the request");
        return Mono.just(response);
    }

    private void updateSessionByCurrentLevel(int level, USSDIndicationObject indication, SessionData sessionData) {
        if (level == 0) {
            return;
        } else if (level == 1) {
            sessionData.setWeight(Float.parseFloat(indication.getMessage()));
        } else {
            sessionData.setHeight(Float.parseFloat(indication.getMessage()));
        }

        sessionData.setLevel(level);

        sessionDetails.remove(indication.getSessionId());
        sessionDetails.put(indication.getSessionId(), sessionData);

        sendSMSToUser(sessionData, indication);
    }

    private void sendSMSToUser(SessionData data, USSDIndicationObject indication) {
        if (data.getLevel() == 2) {
            SMSMTRequest request = new SMSMTRequest();
            request.setApplicationId(indication.getApplicationId());
            request.setDestinationAddresses(Arrays.asList(indication.getSourceAddress()));
            request.setVersion(indication.getVersion());
            request.setPassword(applicationPassword);

            String smsContent = bmiService
                    .getBmiBasedSmsContent(AppUtil.calculateBmi(data.getWeight(), data.getHeight()));

            request.setMessage(smsContent);

            smsContentSender.sendRequest(request).subscribe();
        }
    }

    private String getUssdContentByCurrentLevel(int level) {
        if (level == 0) {
            return menuContentLevelOne;
        } else if (level == 1) {
            return menuContentLevelTwo;
        } else {
            return menuContentLevelThree;
        }
    }

    private void initialSessionDetailUpdate(USSDIndicationObject indication) {
        SessionData sessionData = new SessionData();
        sessionData.setSessionId(indication.getSessionId());
        sessionData.setSourceAddress(indication.getSourceAddress());
        sessionData.setLevel(0);
        sessionDetails.put(indication.getSessionId(), sessionData);
    }

    private USSDSendRequestObject getRequest(USSDIndicationObject indication, String content, String operation) {
        USSDSendRequestObject request = new USSDSendRequestObject();
        request.setVersion(indication.getVersion());
        request.setApplicationId(indication.getApplicationId());
        request.setDestinationAddress(indication.getSourceAddress());
        request.setEncoding(indication.getEncoding());
        request.setPassword(applicationPassword);
        request.setMessage(content);
        request.setUssdOperation(operation);
        request.setSessionId(indication.getSessionId());
        return request;
    }
}
