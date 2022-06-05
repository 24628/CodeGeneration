package io.swagger.responses.user;

import io.swagger.model.Entity.AccountEntity;
import io.swagger.model.Entity.UserEntity;
import org.springframework.http.HttpStatus;

public class UserSingleResponse {
    public final HttpStatus code;
    public final UserEntity userEntity;

    public UserSingleResponse(HttpStatus code, UserEntity userEntity) {
        this.code = code;
        this.userEntity = userEntity;
    }
}
