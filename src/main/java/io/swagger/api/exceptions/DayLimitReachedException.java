package io.swagger.api.exceptions;

public class DayLimitReachedException extends RuntimeException {

    public DayLimitReachedException(Long dailyLimit) {
        super(String.format("This transaction would exceed your daily limit of EUR %d.", dailyLimit));
    }
}
