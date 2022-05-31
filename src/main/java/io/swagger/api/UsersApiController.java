package io.swagger.api;

import io.swagger.annotations.Api;
import io.swagger.api.interfaces.UsersApi;
import io.swagger.model.Entity.UserEntity;
import io.swagger.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.responses.AccountCreatedResponse;
import io.swagger.responses.UserCreatedResponse;
import io.swagger.responses.UserDeletedResponse;
import io.swagger.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
import java.io.ByteArrayOutputStream;
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
    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public String usersGet(@Parameter(in = ParameterIn.QUERY, description = "Limits the number of items on a page", schema = @Schema()) @Valid @RequestParam(value = "limit", required = false) Integer limit, @Parameter(in = ParameterIn.QUERY, description = "Specifies the page number of the artists to be displayed", schema = @Schema()) @Valid @RequestParam(value = "offset", required = false) Integer offset) {

        try {
            List<UserEntity> users = userService.getUsers();

            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            objectMapper.writeValue(out, users);
            final byte[] data = out.toByteArray();
            return new String(data);
        } catch (IOException e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return "test";
        }
    }

    public String usersIdDelete(@Parameter(in = ParameterIn.PATH, description = "Numeric ID of the user to get", required = true, schema = @Schema()) @PathVariable("id") String id) {

        try {
            userService.deleteUser(id);
            return objectMapper.writeValueAsString(new UserDeletedResponse(HttpStatus.ACCEPTED));
        } catch (IOException e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return "oops";
        }
    }

    public String usersIdGet(@Parameter(in = ParameterIn.PATH, description = "Numeric ID of the user", required = true, schema = @Schema()) @PathVariable("id") String id) {

        try {
            UserEntity users = userService.getUserById(id);

            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            objectMapper.writeValue(out, users);
            final byte[] data = out.toByteArray();
            return new String(data);
        } catch (IOException e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return "oops";
        }

    }

    public String usersIdPut(@Parameter(in = ParameterIn.PATH, description = "Numeric ID of the user to get", required = true, schema = @Schema()) @PathVariable("id") String id, @RequestBody User body) {

        try {
            userService.updateUser(id, body);
            return objectMapper.writeValueAsString(new UserDeletedResponse(HttpStatus.ACCEPTED));
        } catch (IOException e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return "oops";
        }
    }

    public String usersPost(@RequestBody User body) {

        try {
            userService.addUser(body);
            return this.objectMapper.writeValueAsString(new UserCreatedResponse(HttpStatus.CREATED));
        } catch (IOException e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return "error";
        }
    }

}
