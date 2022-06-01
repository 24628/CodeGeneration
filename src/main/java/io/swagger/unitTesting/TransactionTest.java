package io.swagger.unitTesting;

import io.swagger.api.TransactionsApiController;
import io.swagger.model.Entity.TransactionEntity;
import io.swagger.model.Transaction;
import io.swagger.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@WebMvcTest(TransactionsApiController.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionTest {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

//    @Before
//    public void setup() {
//        mvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//    }
//
//
//    @WithMockUser(value = "spring")
    @Test
    public void checkIfTransactionAmountDoesNotExceedAccountBalance(){

        transactionService = new TransactionService();
        Transaction transaction = new Transaction();
        transaction.setAmount(-2);
        transactionService.addTransaction(transaction);
    }

    @Test
    public void checkIfAccountHasSufficientBalanceBeforeTransaction(){
        TransactionEntity transaction = new TransactionEntity();
        transaction.setAmount(200);
    }
}
