package io.swagger.api;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.swagger.annotations.Api;
import io.swagger.model.InlineResponse201;
import io.swagger.model.InlineResponse403;
import io.swagger.model.InlineResponse405;
import io.swagger.model.LoginBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.responses.HelperResponse;
import io.swagger.service.auth.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    public String loginPost(@Parameter(in = ParameterIn.DEFAULT, description = "logging in to an existing account", required=true, schema=@Schema()) @Valid @RequestBody LoginBody body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                String token = loginService.login(body.getUsername(), body.getPassword());
                ObjectMapper mapper = new ObjectMapper();
                String json = "";

                try {
                    json = mapper.writeValueAsString(new HelperResponse(HttpStatus.CREATED, token));
                }
                catch (JsonGenerationException | JsonMappingException e) {
                    json = mapper.writeValueAsString(new HelperResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Oopsie!"));
                    e.printStackTrace();
                }

                return json;
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return "test";
            }
        }
        return "oopsie";
    }

}
