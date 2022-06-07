package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.api.interfaces.TransactionsApi;
import io.swagger.helpers.Authorized;
import io.swagger.model.Entity.TransactionEntity;
import io.swagger.model.Entity.UserEntity;
import io.swagger.model.Request.AtmRequest;
import io.swagger.model.Request.TransactionRequest;
import io.swagger.responses.transactions.TransactionAtmResponse;
import io.swagger.responses.transactions.TransactionListResponse;
import io.swagger.responses.transactions.TransactionSingleResponse;
import io.swagger.service.TransactionService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-04-26T09:18:21.534Z[GMT]")
@RestController
@Api(tags = "Transactions")
public class TransactionsApiController implements TransactionsApi {

    private static final Logger log = LoggerFactory.getLogger(TransactionsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    Authorized authorized;
    @org.springframework.beans.factory.annotation.Autowired
    public TransactionsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<TransactionListResponse> transactionsGet(
            @Parameter(in = ParameterIn.PATH, description = "IBAN for transactions to get", required = false, schema = @Schema())
            @PathVariable("iban") String iban,
            @Parameter(in = ParameterIn.QUERY, description = "Limits the number of items on a page", schema = @Schema())
            @Valid @RequestParam(value = "limit", required = false) Integer limit,
            @Parameter(in = ParameterIn.QUERY, description = "Specifies the page number of the artists to be displayed", schema = @Schema())
            @Valid @RequestParam(value = "offset", required = false) Integer offset) throws IOException {
        List<TransactionEntity> transactions = transactionService.getTransactions(iban,offset,limit);
        return ResponseEntity.ok(new TransactionListResponse(HttpStatus.OK, transactions));
    }

    public ResponseEntity<TransactionSingleResponse> transactionsPost(@RequestBody TransactionRequest body) throws IOException {
        UserEntity user =  authorized.CanOnlyEditOwnAccount(UUID.fromString(body.getFrom()));
        TransactionEntity transaction = transactionService.addTransaction(body, user);
        return ResponseEntity.ok(new TransactionSingleResponse(HttpStatus.OK, transaction));
    }

    public ResponseEntity<TransactionAtmResponse> atmWithdraw(AtmRequest body) throws IOException {
        Long amount = transactionService.withdrawMoney(body);
        return ResponseEntity.ok(new TransactionAtmResponse(HttpStatus.OK, amount));
    }

    public ResponseEntity<TransactionAtmResponse> atmDeposit(AtmRequest body) throws IOException {
        Long amount = transactionService.depositMoney(body);
        return ResponseEntity.ok(new TransactionAtmResponse(HttpStatus.OK, amount));
    }

    public ResponseEntity<TransactionListResponse> transactionsGetAdvancedSearch(
            Integer limit,
            Integer offset,
            Long lessThenTransAmount,
            Long greaterThanTransAmount,
            LocalDateTime dateBefore,
            LocalDateTime dateAfter,
            String ibanTo,
            String ibanFrom
    ) throws IOException {
        authorized.NeedsToBeEmployee();
        List<TransactionEntity> transactions = transactionService.advanceSearch(limit, offset, lessThenTransAmount, greaterThanTransAmount, dateBefore, dateAfter, ibanTo, ibanFrom);
        return ResponseEntity.ok(new TransactionListResponse(HttpStatus.OK, transactions));
    }

}
