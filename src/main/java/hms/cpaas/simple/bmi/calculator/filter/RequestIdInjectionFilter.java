package hms.cpaas.simple.bmi.calculator.filter;

import hms.cpaas.simple.bmi.calculator.util.IdGenerator;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class RequestIdInjectionFilter implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    @Override
    public Mono<ServerResponse> filter(ServerRequest serverRequest,
                                       HandlerFunction<ServerResponse> handlerFunction) {

        String requestId = IdGenerator.generateId();

        HeaderMapRequestWrapper wrapper = new HeaderMapRequestWrapper(serverRequest);
        wrapper.addHeader("X-Request-Id", requestId);
        wrapper.addHeader("X-Received-At", String.valueOf(System.currentTimeMillis()));

        MDC.put("trxId", requestId);
        try {
            return handlerFunction.handle(wrapper);
        } finally {
            MDC.clear();
        }

    }
}
