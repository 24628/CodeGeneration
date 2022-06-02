package io.swagger.responses.user;

import io.swagger.model.Entity.AccountEntity;
import io.swagger.model.Entity.UserEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

public class UserListResponse {

    public final HttpStatus code;
    public final List<UserEntity> userEntities;

    public UserListResponse(HttpStatus code, List<UserEntity> userEntities) {
        this.code = code;
        this.userEntities = userEntities;
    }
}
