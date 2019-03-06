package hms.cpaas.simple.bmi.calculator.service.sms;

import hms.cpaas.simple.bmi.calculator.api.SMSMTRequest;
import hms.cpaas.simple.bmi.calculator.api.SMSMTResponse;
import hms.cpaas.simple.bmi.calculator.service.ussd.USSDMtExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Service
public class SMSMtExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(USSDMtExecutor.class);

    @Value("${ideamart.base.url}")
    private String ideamartBaseUrl;
    @Value("${ideamart.sms.mt.url.fragment}")
    private String smsMtUrlFragment;

    private WebClient ideamartApiClient;

    @PostConstruct
    public void init() {
        this.ideamartApiClient = WebClient
                .builder()
                .clientConnector(new ReactorClientHttpConnector())
                .baseUrl(ideamartBaseUrl)
                .build();
    }

    public Mono<SMSMTResponse> sendRequest(SMSMTRequest mtRequest) {
        LOGGER.debug("Sending USSDConfirmRequest [{}] path [{}]", mtRequest, smsMtUrlFragment);
        return ideamartApiClient
                .post()
                .uri(smsMtUrlFragment)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(fromObject(mtRequest))
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(SMSMTResponse.class))
                .doOnSuccess(response -> {
                    LOGGER.info("SMSMTResponse response [{}]", response);
                })
                .doOnError(err -> {
                    LOGGER.error("Error while retrieving SMSMTRequest response ", err);
                })
                .onErrorResume(err -> createErrorResumeResponse(err, mtRequest));
    }

    private Mono<SMSMTResponse> createErrorResumeResponse(final Throwable e,
                                                          final SMSMTRequest request) {
        LOGGER.error("Exception Occurred on server-response ", e);
        SMSMTResponse errorConfirmation = new SMSMTResponse();
        errorConfirmation.setVersion(request.getVersion());
        errorConfirmation.setStatusCode("E14010");
        errorConfirmation.setStatusDetail("Error occurred while handling response");
        return Mono.just(errorConfirmation);
    }
}
