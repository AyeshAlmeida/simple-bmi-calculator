package hms.cpaas.simple.bmi.calculator.util;

import java.text.DecimalFormat;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class IdCounter {
    private static short currentId = 0;
    private static final short UPPER_LIMIT = 8999;
    private static final Lock lock = new ReentrantLock(false);
    protected static final ThreadLocal<DecimalFormat> formatter = new ThreadLocal<DecimalFormat>() {
        protected DecimalFormat initialValue() {
            return new DecimalFormat("0000");
        }
    };

    public IdCounter() {
    }

    protected static String incrementAndGet() {
        lock.lock();

        String var0;
        try {
            if (currentId >= 8999) {
                currentId = 0;
            }

            ++currentId;
            var0 = ((DecimalFormat)formatter.get()).format((long)currentId);
        } finally {
            lock.unlock();
        }

        return var0;
    }

    /** @deprecated */
    @Deprecated
    static void reset() {
        lock.lock();

        try {
            currentId = 0;
        } finally {
            lock.unlock();
        }

    }
}
