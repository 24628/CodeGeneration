package io.swagger.responses.account;

import io.swagger.model.Entity.AccountEntity;
import org.springframework.http.HttpStatus;

public class AccountSingleResponse {
    public final HttpStatus code;
    public final AccountEntity accountEntity;

    public AccountSingleResponse(HttpStatus code, AccountEntity accountEntity) {
        this.code = code;
        this.accountEntity = accountEntity;
    }
}
