package io.swagger.responses.transactions;

import io.swagger.model.Entity.TransactionEntity;
import org.springframework.http.HttpStatus;

public class TransactionSingleResponse {

    public final HttpStatus code;
    public final TransactionEntity transactionEntity;

    public TransactionSingleResponse(HttpStatus code, TransactionEntity transactionEntity) {
        this.code = code;
        this.transactionEntity = transactionEntity;
    }
}
