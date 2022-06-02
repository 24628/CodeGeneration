package io.swagger.responses.transactions;

import org.springframework.http.HttpStatus;

public class TransactionAtmResponse {
    public final HttpStatus code;
    public final Long amount;

    public TransactionAtmResponse(HttpStatus code, Long amount) {
        this.code = code;
        this.amount = amount;
    }
}
