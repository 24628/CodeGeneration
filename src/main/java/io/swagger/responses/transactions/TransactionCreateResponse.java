package io.swagger.responses.transactions;

import org.springframework.http.HttpStatus;

public class TransactionCreateResponse {

    public final HttpStatus code;

    public TransactionCreateResponse(HttpStatus code) {
        this.code = code;
    }

    public String toString() {
        return String.format("{code:%s,{transaction completed}}", code.value());
    }
}
