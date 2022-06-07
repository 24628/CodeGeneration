package io.swagger.api;

import io.swagger.annotations.Api;
import io.swagger.api.interfaces.UsersApi;
import io.swagger.helpers.Authorized;
import io.swagger.model.Entity.UserEntity;
import io.swagger.model.Request.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.responses.user.UserDeletedResponse;
import io.swagger.responses.user.UserListResponse;
import io.swagger.responses.user.UserSingleResponse;
import io.swagger.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-04-26T09:18:21.534Z[GMT]")
@RestController
@Api(tags = "Users")

public class UsersApiController implements UsersApi {

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private UserService userService;

    @Autowired
    Authorized authorized;

    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<UserListResponse> usersGet(
            @Parameter(in = ParameterIn.QUERY, description = "Limits the number of items on a page", schema = @Schema())
            @Valid @RequestParam(value = "limit", required = false) Integer limit,
            @Parameter(in = ParameterIn.QUERY, description = "Specifies the page number of the artists to be displayed", schema = @Schema())
            @Valid @RequestParam(value = "offset", required = false) Integer offset) throws IOException {
        authorized.NeedsToBeEmployee();
        List<UserEntity> users = userService.getUsers(limit,offset);
        return ResponseEntity.ok(new UserListResponse(HttpStatus.CREATED, users));
    }

    public ResponseEntity<UserDeletedResponse> usersIdDelete(
            @Parameter(in = ParameterIn.PATH, description = "Numeric ID of the user to get", required = true, schema = @Schema())
            @PathVariable("id") String id) throws IOException {
        authorized.NeedsToBeEmployee();
        userService.deleteUser(id);
        return ResponseEntity.ok(new UserDeletedResponse(HttpStatus.OK));
    }

    public ResponseEntity<UserSingleResponse> usersIdGet(
            @Parameter(in = ParameterIn.PATH, description = "Numeric ID of the user", required = true, schema = @Schema())
            @PathVariable("id") String id) throws IOException {
        authorized.NeedsToBeEmployee();
        UserEntity user = userService.getUserById(id);
        return ResponseEntity.ok(new UserSingleResponse(HttpStatus.OK, user));
    }

    public ResponseEntity<UserSingleResponse> usersIdPut(
            @Parameter(in = ParameterIn.PATH, description = "Numeric ID of the user to get", required = true, schema = @Schema())
            @PathVariable("id") String id, @RequestBody UserRequest body) throws IOException {
        UserEntity user = userService.updateUser(id, body);
        return ResponseEntity.ok(new UserSingleResponse(HttpStatus.OK, user));
    }

    public ResponseEntity<UserSingleResponse> usersPost(@RequestBody UserRequest body) throws IOException {
        authorized.NeedsToBeEmployee();
        UserEntity user = userService.addUser(body);
        return ResponseEntity.ok(new UserSingleResponse(HttpStatus.CREATED, user));
    }

    @Override
    public ResponseEntity<UserListResponse> usersGetAllUserWithNoAccount(@Parameter(in = ParameterIn.QUERY, description = "Limits the number of items on a page", schema = @Schema()) @Valid @RequestParam(value = "limit", required = false) Integer limit, @Parameter(in = ParameterIn.QUERY, description = "Specifies the page number of the artists to be displayed", schema = @Schema()) @Valid @RequestParam(value = "offset", required = false) Integer offset) throws IOException {
        authorized.NeedsToBeEmployee();
        List<UserEntity> foundUsers = userService.getUsersWithNoAccount(offset,limit);
        return ResponseEntity.ok(new UserListResponse(HttpStatus.CREATED, foundUsers));
    }

}
