package hms.cpaas.simple.bmi.calculator.handler;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class SMSHandler {
    public Mono<ServerResponse> handleSMSMO(final String requestId, final ServerRequest request) {
        //todo: implement this method
        return ServerResponse.ok().build();
    }
}
