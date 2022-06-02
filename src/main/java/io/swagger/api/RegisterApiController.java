package io.swagger.api;

import io.swagger.annotations.Api;
import io.swagger.api.exceptions.SerializeException;
import io.swagger.api.interfaces.RegisterApi;
import io.swagger.helpers.AuthResult;
import io.swagger.responses.JwtTokenResponse;
import io.swagger.model.RegisterBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.service.auth.RegisterService;
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
public class RegisterApiController implements RegisterApi {

    private static final Logger log = LoggerFactory.getLogger(RegisterApiController.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final HttpServletRequest request;

    @Autowired
    private RegisterService registerService;

    @org.springframework.beans.factory.annotation.Autowired
    public RegisterApiController(HttpServletRequest request) {
        this.request = request;
    }

    public ResponseEntity<JwtTokenResponse> registerPost(@Parameter(in = ParameterIn.DEFAULT, description = "register a new account", required = true, schema = @Schema()) @Valid @RequestBody RegisterBody body) {

        try {
            AuthResult result = registerService.register(body);

            return new ResponseEntity<JwtTokenResponse>(
                objectMapper.readValue(
                    objectMapper.writeValueAsString(
                        new JwtTokenResponse(HttpStatus.CREATED, result.getToken(), result.getUser())),
                    JwtTokenResponse.class),
                HttpStatus.CREATED
            );

        } catch (IOException e) {
            log.error("Couldn't serialize response for content type application/json", e);
            throw new SerializeException();
        }
    }
}
