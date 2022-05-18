package io.swagger.api;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.swagger.annotations.Api;
import io.swagger.api.interfaces.RegisterApi;
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

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private RegisterService registerService;

    @org.springframework.beans.factory.annotation.Autowired
    public RegisterApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public String registerPost(@Parameter(in = ParameterIn.DEFAULT, description = "register a new account", required=true, schema=@Schema()) @Valid @RequestBody RegisterBody body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                String token = registerService.register(body.getEmail(), body.getUsername(), body.getPassword(), body.getDayLimit());
                ObjectMapper mapper = new ObjectMapper();
                String json = "";

                try {
                    json = mapper.writeValueAsString(new JwtTokenResponse(HttpStatus.CREATED, token));
                }
                catch (JsonGenerationException | JsonMappingException e) {
                    json = mapper.writeValueAsString(new JwtTokenResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Oopsie!"));
                    e.printStackTrace();
                }

                return json;
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return "test";
            }
        }
        return "oops!";
    }

}
