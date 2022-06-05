package io.swagger.api;

import io.swagger.annotations.Api;
import io.swagger.api.interfaces.LoginApi;
import io.swagger.helpers.AuthResult;
import io.swagger.model.Request.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.responses.auth.JwtTokenResponse;
import io.swagger.service.auth.LoginService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-04-26T09:18:21.534Z[GMT]")
@RestController
@Api(tags = "Auth")
public class LoginApiController implements LoginApi {

    private static final Logger log = LoggerFactory.getLogger(LoginApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private LoginService loginService;

    @org.springframework.beans.factory.annotation.Autowired
    public LoginApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<JwtTokenResponse> loginPost(@Parameter(in = ParameterIn.DEFAULT, description = "logging in to an existing account", required=true, schema=@Schema()) @Valid @RequestBody LoginRequest body) throws IOException {
        AuthResult result = loginService.login(body.getUsername(), body.getPassword());
        return ResponseEntity.ok(new JwtTokenResponse(HttpStatus.CREATED, result.getToken(), result.getUser()));
    }
}
