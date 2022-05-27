package io.swagger.api;

import io.swagger.annotations.Api;
import io.swagger.api.interfaces.TransactionsApi;
import io.swagger.model.Entity.AccountEntity;
import io.swagger.model.Entity.TransactionEntity;
import io.swagger.model.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.responses.AccountCreatedResponse;
import io.swagger.service.AccountService;
import io.swagger.service.TransactionService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-04-26T09:18:21.534Z[GMT]")
@RestController
@Api(tags = "Transactions")
public class TransactionsApiController implements TransactionsApi {

    private static final Logger log = LoggerFactory.getLogger(TransactionsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private TransactionService transactionService;

    @org.springframework.beans.factory.annotation.Autowired
    public TransactionsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public String transactionsGet(@Parameter(in = ParameterIn.QUERY, description = "Limits the number of items on a page", schema = @Schema()) @Valid @RequestParam(value = "limit", required = false) Integer limit, @Parameter(in = ParameterIn.QUERY, description = "Specifies the page number of the artists to be displayed", schema = @Schema()) @Valid @RequestParam(value = "offset", required = false) Integer offset) {

        try {
            List<TransactionEntity> transactions = transactionService.getTransactions();

            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            objectMapper.writeValue(out, transactions);
            final byte[] data = out.toByteArray();
            return new String(data);
        } catch (IOException e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return "OOps";
        }

    }

    public String transactionsPost(@RequestBody Transaction body) {
        try {
            transactionService.addTransaction(body);
            return this.objectMapper.writeValueAsString(new AccountCreatedResponse(HttpStatus.CREATED));
        } catch (IOException e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return "OOps";
        }

    }

}
