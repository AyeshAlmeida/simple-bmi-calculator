package hms.cpaas.simple.bmi.calculator.service.ussd;

import hms.cpaas.simple.bmi.calculator.api.USSDSendConfirmationObject;
import hms.cpaas.simple.bmi.calculator.api.USSDSendRequestObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Service
public class USSDMtExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(USSDMtExecutor.class);

    @Value("${ideamart.base.url}")
    private String ideamartBaseUrl;
    @Value("${ideamart.ussd.mt.url.fragment}")
    private String ussdMtUrlFragment;

    private WebClient ideamartApiClient;

    @PostConstruct
    public void init() {
        this.ideamartApiClient = WebClient
                .builder()
                .clientConnector(new ReactorClientHttpConnector())
                .baseUrl(ideamartBaseUrl)
                .build();
    }

    public Mono<USSDSendConfirmationObject> sendRequest(USSDSendRequestObject mtRequest) {
        LOGGER.debug("Sending USSDConfirmRequest [{}] path [{}]", mtRequest, ussdMtUrlFragment);
        return ideamartApiClient
                .post()
                .uri(ussdMtUrlFragment)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(fromObject(mtRequest))
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(USSDSendConfirmationObject.class))
                .doOnSuccess(response -> {
                    LOGGER.info("USSDMtRequest response [{}]", response);
                })
                .doOnError(err -> {
                    LOGGER.error("Error while retrieving USSDMtRequest response ", err);
                })
                .onErrorResume(err -> createErrorResumeResponse(err, mtRequest));
    }

    private Mono<USSDSendConfirmationObject> createErrorResumeResponse(final Throwable e,
                                                                 final USSDSendRequestObject request) {
        LOGGER.error("Exception Occurred on server-response ", e);
        USSDSendConfirmationObject errorConfirmation = new USSDSendConfirmationObject();
        errorConfirmation.setRequestId(request.getSessionId());
        errorConfirmation.setVersion(request.getVersion());
        errorConfirmation.setTimeStamp(LocalDateTime.now().toString());
        errorConfirmation.setStatusCode("E14010");
        errorConfirmation.setStatusDetail("Error occurred while handling response");
        return Mono.just(errorConfirmation);
    }
}
