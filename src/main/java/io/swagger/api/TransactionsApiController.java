package io.swagger.api;

import io.swagger.annotations.Api;
import io.swagger.api.interfaces.TransactionsApi;
import io.swagger.model.Atm;
import io.swagger.model.Entity.TransactionEntity;
import io.swagger.model.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
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

    public ResponseEntity<List<TransactionListResponse>> transactionsGet(@Parameter(in = ParameterIn.QUERY, description = "Limits the number of items on a page", schema = @Schema()) @Valid @RequestParam(value = "limit", required = false) Integer limit, @Parameter(in = ParameterIn.QUERY, description = "Specifies the page number of the artists to be displayed", schema = @Schema()) @Valid @RequestParam(value = "offset", required = false) Integer offset) throws IOException {

        List<TransactionEntity> transactions = transactionService.getTransactions();

        return new ResponseEntity<List<TransactionListResponse>>(
                objectMapper.readValue(
                        objectMapper.writeValueAsString(
                                new TransactionListResponse(HttpStatus.OK, transactions)),
                        List.class),
                HttpStatus.OK
        );

    }

    public ResponseEntity<TransactionSingleResponse> transactionsPost(@RequestBody Transaction body) throws IOException {

        TransactionEntity transaction = transactionService.addTransaction(body);
        return new ResponseEntity<TransactionSingleResponse>(
                objectMapper.readValue(
                        objectMapper.writeValueAsString(
                                new TransactionSingleResponse(HttpStatus.CREATED, transaction)),
                        TransactionSingleResponse.class),
                HttpStatus.OK
        );

    }

    public ResponseEntity<TransactionAtmResponse> atmWithdraw(Atm body) throws IOException {

        Long amount = transactionService.withdrawMoney(body);
        return new ResponseEntity<TransactionAtmResponse>(
                objectMapper.readValue(
                        objectMapper.writeValueAsString(
                                new TransactionAtmResponse(HttpStatus.OK, amount)),
                        TransactionAtmResponse.class),
                HttpStatus.OK
        );
    }

    public ResponseEntity<TransactionAtmResponse> atmDeposit(Atm body) throws IOException {
        Long amount = transactionService.depositMoney(body);
        return new ResponseEntity<TransactionAtmResponse>(
                objectMapper.readValue(
                        objectMapper.writeValueAsString(
                                new TransactionAtmResponse(HttpStatus.OK, amount)),
                        TransactionAtmResponse.class),
                HttpStatus.OK
        );
    }

}
