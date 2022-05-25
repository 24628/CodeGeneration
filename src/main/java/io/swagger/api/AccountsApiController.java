package io.swagger.api;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.swagger.annotations.Api;
import io.swagger.api.interfaces.AccountsApi;
import io.swagger.enums.AccountType;
import io.swagger.enums.Roles;
import io.swagger.jwt.JwtTokenProvider;
import io.swagger.model.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.Entity.UserEntity;
import io.swagger.responses.AccountCreatedResponse;
import io.swagger.responses.JwtTokenResponse;
import io.swagger.service.AccountService;
import io.swagger.service.UserService;
import io.swagger.service.auth.LoginService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-04-26T09:18:21.534Z[GMT]")
@RestController
@Api(tags = "Accounts")
public class AccountsApiController implements AccountsApi {

    private static final Logger log = LoggerFactory.getLogger(AccountsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;



    @org.springframework.beans.factory.annotation.Autowired
    public AccountsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<Account>> accountsGet(@Parameter(in = ParameterIn.QUERY, description = "Limits the number of items on a page" ,schema=@Schema()) @Valid @RequestParam(value = "limit", required = false) Integer limit,@Parameter(in = ParameterIn.QUERY, description = "Specifies the page number of the artists to be displayed" ,schema=@Schema()) @Valid @RequestParam(value = "offset", required = false) Integer offset) {
        String accept = request.getHeader("Accept");
        System.out.println("Wow post accountGet");
        if (accept != null && accept.contains("application/json")) {
            try {
               // accountService.getAccountByIban(body)
                return new ResponseEntity<List<Account>>(objectMapper.readValue("[ {\n  \"IBAN\" : \"NL69INHO1234123412\",\n  \"user_id\" : 0,\n  \"absolute_limit\" : 6,\n  \"type\" : \"normal\"\n}, {\n  \"IBAN\" : \"NL69INHO1234123412\",\n  \"user_id\" : 0,\n  \"absolute_limit\" : 6,\n  \"type\" : \"normal\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Account>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Account>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Account> accountsIbanIbanGet(@Parameter(in = ParameterIn.PATH, description = "Gets the Iban of the user based on the input", required=true, schema=@Schema()) @PathVariable("iban") String iban) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Account>(objectMapper.readValue("{\n  \"IBAN\" : \"NL69INHO1234123412\",\n  \"user_id\" : 0,\n  \"absolute_limit\" : 6,\n  \"type\" : \"normal\"\n}", Account.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                System.out.println("Couldn't serialize response for content type application/json");
                return new ResponseEntity<Account>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Account>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Account> accountsIbanIbanPut(@Parameter(in = ParameterIn.PATH, description = "The iban of the user is taken", required=true, schema=@Schema()) @PathVariable("iban") String iban) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Account>(objectMapper.readValue("{\n  \"IBAN\" : \"NL69INHO1234123412\",\n  \"user_id\" : 0,\n  \"absolute_limit\" : 6,\n  \"type\" : \"normal\"\n}", Account.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Account>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Account>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Account> accountsIdIdGet(@Parameter(in = ParameterIn.PATH, description = "The unique id of the user is taken", required=true, schema=@Schema()) @PathVariable("id") Integer id) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Account>(objectMapper.readValue("{\n  \"IBAN\" : \"NL69INHO1234123412\",\n  \"user_id\" : 0,\n  \"absolute_limit\" : 6,\n  \"type\" : \"normal\"\n}", Account.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Account>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Account>(HttpStatus.NOT_IMPLEMENTED);
    }

    public String accountsPost(@Parameter(in = ParameterIn.DEFAULT,
            description = "This endpoint creates a new account that can be used to transfer and withdraw money.",
            required=true, schema=@Schema()) @RequestBody Account body) {

        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                accountService.addAccount(body);
                return this.objectMapper.writeValueAsString(new AccountCreatedResponse(HttpStatus.CREATED));
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return "error";
            }
        }

        return "oops";
    }

    public ResponseEntity<Account> accountsSearchGet(@Parameter(in = ParameterIn.QUERY, description = "The name of the user is searched with the submitted input. If the user existed the account is returned" ,schema=@Schema()) @Valid @RequestParam(value = "name", required = false) String name) {

        String accept = request.getHeader("Accept");
        String jwtToken = request.getHeader("Authorization").split("\\s+")[1];
        UserEntity user = userService.findUserByName(jwtTokenProvider.getUsername(jwtToken));
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Account>(objectMapper.readValue("{\n  \"IBAN\" : \"NL69INHO1234123412\",\n  \"user_id\" : 0,\n  \"absolute_limit\" : 6,\n  \"type\" : \"normal\"\n}", Account.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Account>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Account>(HttpStatus.NOT_IMPLEMENTED);
    }

}
