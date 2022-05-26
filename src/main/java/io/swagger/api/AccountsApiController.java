package io.swagger.api;

import io.swagger.annotations.Api;
import io.swagger.api.interfaces.AccountsApi;
import io.swagger.jwt.JwtTokenProvider;
import io.swagger.model.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.Entity.AccountEntity;
import io.swagger.responses.AccountCreatedResponse;
import io.swagger.service.AccountService;
import io.swagger.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
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

    public String accountsGet(@Parameter(in = ParameterIn.QUERY, description = "Limits the number of items on a page", schema = @Schema()) @Valid @RequestParam(value = "limit", required = false) Integer limit,
                              @Parameter(in = ParameterIn.QUERY, description = "Specifies the page number of the artists to be displayed", schema = @Schema()) @Valid
                              @RequestParam(value = "offset", required = false) Integer offset) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                System.out.println("het komt hier");
                List<AccountEntity> accounts = accountService.getAccounts();

                final ByteArrayOutputStream out = new ByteArrayOutputStream();
                objectMapper.writeValue(out, accounts);

                final byte[] data = out.toByteArray();
                return new String(data);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return "OOps";
            }
        }

        return "OOps";
    }

    public String accountsIbanIbanGet(@Parameter(in = ParameterIn.PATH, description = "Gets the Iban of the user based on the input", required = true, schema = @Schema()) @PathVariable("IBAN") String IBAN) {

        try {
            AccountEntity found = accountService.getAccountByIBAN(IBAN);

            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            objectMapper.writeValue(out, found);

            final byte[] data = out.toByteArray();
            return new String(data);
        } catch (IOException e) {
            log.error("Couldn't serialize response for content type application/json", e);
            System.out.println("Couldn't serialize response for content type application/json");
            return "OOps";

        }

    }

    public String accountsIbanIbanPut(
            @Parameter(in = ParameterIn.PATH, description = "The iban of the user is taken",
                    required = true,
                    schema = @Schema()) @PathVariable("IBAN") String IBAN,
            @RequestBody Account body
    ) {
        try {
            AccountEntity acc = accountService.updateAccountByIBAN(body, IBAN);
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            objectMapper.writeValue(out, acc);
            final byte[] data = out.toByteArray();
            return new String(data);
        } catch (IOException e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return "LOL";
        }

    }

    public String accountsIdIdGet(@Parameter(in = ParameterIn.PATH,
            description = "The unique id of the user is taken",
            required = true,
            schema = @Schema()) @PathVariable("id") String id) {

        try {
            List<AccountEntity> acc = accountService.getAccountByUserId(UUID.fromString(id));
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            objectMapper.writeValue(out, acc);

            final byte[] data = out.toByteArray();
            return new String(data);
        } catch (IOException e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return "LOL";

        }
    }

    public String accountsPost(@Parameter(in = ParameterIn.DEFAULT,
            description = "This endpoint creates a new account that can be used to transfer and withdraw money.",
            required = true, schema = @Schema()) @RequestBody Account body) {

        try {
            accountService.addAccount(body);
            return this.objectMapper.writeValueAsString(new AccountCreatedResponse(HttpStatus.CREATED));
        } catch (IOException e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return "error";
        }
    }

    public ResponseEntity<Account> accountsSearchGet(@Parameter(in = ParameterIn.QUERY, description = "The name of the user is searched with the submitted input. If the user existed the account is returned", schema = @Schema()) @Valid @RequestParam(value = "name", required = false) String name) {

        try {
            return new ResponseEntity<Account>(objectMapper.readValue("{\n  \"IBAN\" : \"NL69INHO1234123412\",\n  \"user_id\" : 0,\n  \"absolute_limit\" : 6,\n  \"type\" : \"normal\"\n}", Account.class), HttpStatus.NOT_IMPLEMENTED);
        } catch (IOException e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<Account>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
