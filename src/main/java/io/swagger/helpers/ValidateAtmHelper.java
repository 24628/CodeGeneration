package io.swagger.helpers;

import io.swagger.model.Entity.AccountEntity;
import io.swagger.model.Entity.UserEntity;

public class ValidateAtmHelper {
    private final UserEntity userEntity;
    private final AccountEntity accountEntity;

    public ValidateAtmHelper(UserEntity userEntity, AccountEntity accountEntity) {
        this.userEntity = userEntity;
        this.accountEntity = accountEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public AccountEntity getAccountEntity() {
        return accountEntity;
    }
}
