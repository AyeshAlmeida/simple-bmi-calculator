package hms.cpaas.simple.bmi.calculator.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class IdGenerator {
    private static volatile String nodeId = "10";
    protected static final ThreadLocal<SimpleDateFormat> formatter = new ThreadLocal<SimpleDateFormat>() {
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyMMddHHmmss");
        }
    };

    public IdGenerator() {
    }

    public static String generateId() {
        Date date = new Date();
        StringBuilder sb = new StringBuilder(20);
        sb.append(nodeId);
        sb.append(((SimpleDateFormat)formatter.get()).format(date));
        sb.append(IdCounter.incrementAndGet());
        return sb.toString();
    }

    private static void setNodeId(String nodeId) {
        nodeId = nodeId;
    }
}
