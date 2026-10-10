package dev.chaunm.protocol;

import java.util.concurrent.atomic.AtomicLong;

public class IDGenerator {
    private static final AtomicLong ID = new AtomicLong();

    public static long generateID() {
        return ID.incrementAndGet();
    }
}
