package io.swagger.responses.account;

import io.swagger.model.Entity.AccountEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

public class AccountListResponse {
    public final HttpStatus code;
    public final List<AccountEntity> accountEntityList;

    public AccountListResponse(HttpStatus code, List<AccountEntity> accountEntityList) {
        this.code = code;
        this.accountEntityList = accountEntityList;
    }
}
