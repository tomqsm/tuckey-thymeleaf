package biz.letsweb.tuckey.thymeleaf.jmx;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author toks
 */
public class MyCounter {

    private static AtomicInteger counter = new AtomicInteger(0);

    public static int incrementAndGet() {
        return counter.incrementAndGet();
    }

    public static int getCurrentCount() {
        return counter.get();
    }

    public static void resetCounter() {
        resetCounter(0);
    }

    public static void resetCounter(int start) {
        counter = new AtomicInteger(start);
    }
}
