package dev.chaunm.infrastructure;

public class IDGenerator {
    private static long ID = 0;

    public static long generateID() {
        return ++ID;
    }
}
