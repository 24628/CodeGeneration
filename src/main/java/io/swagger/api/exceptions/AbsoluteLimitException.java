package io.swagger.api.exceptions;

public class AbsoluteLimitException extends RuntimeException {

    public AbsoluteLimitException(Long amount, Long balance, Long absoluteLimit) {
        super(String.format("The transaction amount EUR %.2d would set your balance of EUR %s below your absolute limit of EUR %d", amount, balance,absoluteLimit));
    }
}
