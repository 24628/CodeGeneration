package io.swagger.responses.transactions;

import io.swagger.model.Entity.TransactionEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

public class TransactionListResponse {
    public final HttpStatus code;
    public final List<TransactionEntity> transactionEntityList;

    public TransactionListResponse(HttpStatus code, List<TransactionEntity> transactionEntityList) {
        this.code = code;
        this.transactionEntityList = transactionEntityList;
    }
}
