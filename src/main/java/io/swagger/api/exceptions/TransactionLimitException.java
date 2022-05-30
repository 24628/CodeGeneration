package io.swagger.api.exceptions;

public class TransactionLimitException extends RuntimeException {

    public TransactionLimitException(Long amount, Long transactionLimit) {
        super(String.format("The transaction amount EUR %.2d would exceed your transaction limit of EUR %d", amount, transactionLimit));
    }
}
