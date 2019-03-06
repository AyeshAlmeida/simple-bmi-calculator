package hms.cpaas.simple.bmi.calculator.util;

import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.List;

public class RouterUtils {
    public static String getRequestId(ServerRequest request) {
        List<String> headers = request.headers().header("X-Request-Id");
        if (headers != null && headers.size() > 0) {
            return headers.get(0);
        } else {
            return "";
        }
    }
}
