package hms.cpaas.simple.bmi.calculator.handler;

import hms.cpaas.simple.bmi.calculator.api.USSDIndicationObject;
import hms.cpaas.simple.bmi.calculator.service.SystemRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class USSDHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(USSDHandler.class);

    private final SystemRequestHandler requestHandler;

    @Autowired
    public USSDHandler(SystemRequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public Mono<ServerResponse> handleUSSDMO(final String requestId, final ServerRequest request) {
        return request
                .bodyToMono(USSDIndicationObject.class)
                .doOnNext(req -> LOGGER.info("Received Ussd Mo Request [{}]", req))
                .flatMap(req -> requestHandler.handlePlatformRequest(requestId, req))
                .flatMap(req -> ServerResponse.ok().body(BodyInserters.fromObject(req)));
    }
}
