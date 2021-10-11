package com.travix.medusa.busyflights.exception.enums;

public enum MessageCodes implements IMessageCode {
    OK(1000),
    CANCEL(1001),
    YES(1002),
    NO(1003),
    APPROVE(1004),
    ACCEPT(1005),
    DECLINE(1006),
    SUCCESS(1007),
    INFO(1008),
    WARNING(1009),
    ERROR(1010),
    BUSY_FLIGHTS_NOT_FOUND_EXCEPTION(1500);

    private final int value;

    MessageCodes(int value) {
        this.value = value;
    }

    @Override
    public int getMessageCode() {
        return value;
    }

}
