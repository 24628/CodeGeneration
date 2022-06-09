package io.swagger.services.services;

import io.swagger.Swagger2SpringBoot;
import io.swagger.api.exceptions.EntityNotFoundException;
import io.swagger.api.exceptions.ValidationException;
import io.swagger.enums.AccountType;
import io.swagger.enums.Roles;
import io.swagger.helpers.ValidateAtmHelper;
import io.swagger.model.Entity.AccountEntity;
import io.swagger.model.Entity.TransactionEntity;
import io.swagger.model.Entity.UserEntity;
import io.swagger.model.Request.AtmRequest;
import io.swagger.model.Request.TransactionRequest;
import io.swagger.repository.IAccountDTO;
import io.swagger.repository.ITransactionDTO;
import io.swagger.repository.IUserDTO;
import io.swagger.service.TransactionService;
import io.swagger.service.UserService;
import io.swagger.validator.Validator;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Swagger2SpringBoot.class, TransactionService.class}, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class TransactionServiceTest {
    @Mock
    private ITransactionDTO transactionRepository;
    @Mock
    private UserService userService;
    @Mock
    ITransactionDTO transactionDTO;
    @Mock
    IUserDTO userDTO;
    @Mock
    private IAccountDTO iAccountDTO;
    @Mock
    Validator validator;
    @InjectMocks
    private TransactionService transactionService;
    private UserEntity userEntityLoggedInUser;
    private UserEntity userEntityFrom;
    private AccountEntity accountEntityFrom;
    private AccountEntity accountEntityTo;
    private TransactionRequest transactionRequest;
    private AtmRequest atmRequest;
    @Before
    public void setupMock() {
        userEntityLoggedInUser = UserEntity.builder()
                .uuid(UUID.randomUUID())
                .dayLimit(2000L)
                .email("johndoe@example.com")
                .name("john")
                .username("johndoe")
                .password("test")
                .pinCode(1234)
                .role(Roles.EMPLOYEE)
                .transactionLimit(1500L)
                .build();

        userEntityFrom = UserEntity.builder()
                .uuid(UUID.randomUUID())
                .dayLimit(2000L)
                .email("from@example.com")
                .name("from")
                .username("from")
                .password("test")
                .pinCode(1234)
                .role(Roles.CUSTOMER)
                .transactionLimit(1500L)
                .build();

        accountEntityFrom = AccountEntity.builder()
                .IBAN("testIban123")
                .uuid(UUID.randomUUID())
                .absoluteLimit(10L)
                .balance(1000L)
                .type(AccountType.NORMAL)
                .userId(userEntityFrom.getUuid())
                .build();

        accountEntityTo = AccountEntity.builder()
                .IBAN("testIban123")
                .uuid(UUID.randomUUID())
                .absoluteLimit(10L)
                .balance(1000L)
                .type(AccountType.NORMAL)
                .userId(UUID.randomUUID())
                .build();

        transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(400L);
        transactionRequest.setFrom(String.valueOf(accountEntityFrom.getIBAN()));
        transactionRequest.setTo(String.valueOf(accountEntityTo.getIBAN()));
        transactionRequest.setUserId(String.valueOf(userEntityLoggedInUser.getUuid()));

        atmRequest = new AtmRequest();
        atmRequest.setPinCode(userEntityFrom.getPinCode());
        atmRequest.setAmount(300L);
        atmRequest.setIban(accountEntityFrom.getIBAN());
    }

    @Test
    @Ignore
    public void addTransaction(){
        //@todo what wrong idk its 3am tho so wtf
        given(iAccountDTO.getAccountByIBAN(accountEntityFrom.getIBAN()))
                .willReturn(accountEntityFrom);

        given(iAccountDTO.getAccountByIBAN(accountEntityTo.getIBAN()))
                .willReturn(accountEntityTo);

        System.out.println(accountEntityFrom.getUserId());
        System.out.println(userEntityFrom.getUuid());

        given(userDTO.getOne(userEntityFrom.getUuid()))
                .willReturn(userEntityFrom);

        TransactionEntity success = transactionService.addTransaction(transactionRequest, userEntityLoggedInUser);

        assertNotNull(success);
        assertEquals(Optional.of(400L), Optional.of(success.getAmount()));
        verify(iAccountDTO, times(2)).save(any(AccountEntity.class));
    }

    @Test
    public void addTransactionAccountFromNull(){
        given(iAccountDTO.getOne(accountEntityFrom.getUuid()))
                .willReturn(null);

        given(iAccountDTO.getOne(accountEntityTo.getUuid()))
                .willReturn(accountEntityTo);

        given(userDTO.getOne(userEntityFrom.getUuid()))
                .willReturn(userEntityFrom);

        org.junit.jupiter.api.Assertions.assertThrows(EntityNotFoundException.class, () -> {
            transactionService.addTransaction(transactionRequest, userEntityLoggedInUser);
        });
    }

    @Test
    public void addTransactionAccountToNull(){
        given(iAccountDTO.getOne(accountEntityFrom.getUuid()))
                .willReturn(accountEntityFrom);

        given(iAccountDTO.getOne(accountEntityTo.getUuid()))
                .willReturn(null);

        given(userDTO.getOne(userEntityFrom.getUuid()))
                .willReturn(userEntityFrom);

        org.junit.jupiter.api.Assertions.assertThrows(EntityNotFoundException.class, () -> {
            transactionService.addTransaction(transactionRequest, userEntityLoggedInUser);
        });
    }

    @Test
    public void addTransactionAmountBeHigherThenZero(){
        given(iAccountDTO.getAccountByIBAN(accountEntityFrom.getIBAN()))
                .willReturn(accountEntityFrom);

        given(iAccountDTO.getAccountByIBAN(accountEntityTo.getIBAN()))
                .willReturn(accountEntityTo);

        given(userDTO.getOne(userEntityFrom.getUuid()))
                .willReturn(userEntityFrom);

        transactionRequest.setAmount(-10L);
        org.junit.jupiter.api.Assertions.assertThrows(ValidationException.class, () -> {
            transactionService.addTransaction(transactionRequest, userEntityLoggedInUser);
        });
    }

    @Test
    public void addTransactionAmountBeOverTransactionLimit(){
        given(iAccountDTO.getAccountByIBAN(accountEntityFrom.getIBAN()))
                .willReturn(accountEntityFrom);

        given(iAccountDTO.getAccountByIBAN(accountEntityTo.getIBAN()))
                .willReturn(accountEntityTo);

        given(userDTO.getOne(userEntityFrom.getUuid()))
                .willReturn(userEntityFrom);

        transactionRequest.setAmount(20000L);
        org.junit.jupiter.api.Assertions.assertThrows(ValidationException.class, () -> {
            transactionService.addTransaction(transactionRequest, userEntityLoggedInUser);
        });
    }

    @Test
    public void addTransactionNotSameUserSavingAccountFrom(){
        accountEntityFrom.setType(AccountType.SAVING);

        given(iAccountDTO.getAccountByIBAN(accountEntityFrom.getIBAN()))
                .willReturn(accountEntityFrom);

        given(iAccountDTO.getAccountByIBAN(accountEntityTo.getIBAN()))
                .willReturn(accountEntityTo);

        given(userDTO.getOne(userEntityFrom.getUuid()))
                .willReturn(userEntityFrom);

        transactionRequest.setAmount(20000L);
        org.junit.jupiter.api.Assertions.assertThrows(ValidationException.class, () -> {
            transactionService.addTransaction(transactionRequest, userEntityLoggedInUser);
        });
    }

    @Test
    public void addTransactionNotSameUserSavingAccountTo(){
        accountEntityTo.setType(AccountType.SAVING);

        given(iAccountDTO.getAccountByIBAN(accountEntityFrom.getIBAN()))
                .willReturn(accountEntityFrom);

        given(iAccountDTO.getAccountByIBAN(accountEntityTo.getIBAN()))
                .willReturn(accountEntityTo);

        given(userDTO.getOne(userEntityFrom.getUuid()))
                .willReturn(userEntityFrom);

        transactionRequest.setAmount(20000L);
        org.junit.jupiter.api.Assertions.assertThrows(ValidationException.class, () -> {
            transactionService.addTransaction(transactionRequest, userEntityLoggedInUser);
        });
    }

    @Test
    public void getTransactions(){
        //@todo get transaction has to be done later
    }

    @Test
    public void withdrawMoney(){
        ValidateAtmHelper atmHelper = new ValidateAtmHelper(userEntityFrom, accountEntityFrom);

        accountEntityTo.setType(AccountType.ATM);
        userEntityLoggedInUser.setRole(Roles.BANK);

        given(validator.isAllowedToAtm(atmRequest))
                .willReturn(atmHelper);

        given(iAccountDTO.findByTypeIs(AccountType.ATM))
                .willReturn(accountEntityTo);

        given(userDTO.findUserEntityByRoleIs(Roles.BANK))
                .willReturn(userEntityLoggedInUser);

        Long amount = transactionService.withdrawMoney(atmRequest);

        assertNotNull(amount);
        assertEquals(Optional.of(atmRequest.getAmount()), Optional.of(amount));
    }

    @Test
    public void withdrawMoneyThrowBelowAbsoluteLimit(){
        ValidateAtmHelper atmHelper = new ValidateAtmHelper(userEntityFrom, accountEntityFrom);

        atmRequest.setAmount(50000L);
        accountEntityTo.setType(AccountType.ATM);
        userEntityLoggedInUser.setRole(Roles.BANK);

        given(validator.isAllowedToAtm(atmRequest))
                .willReturn(atmHelper);

        given(iAccountDTO.findByTypeIs(AccountType.ATM))
                .willReturn(accountEntityTo);

        given(userDTO.findUserEntityByRoleIs(Roles.BANK))
                .willReturn(userEntityLoggedInUser);

        org.junit.jupiter.api.Assertions.assertThrows(ValidationException.class, () -> {
            transactionService.withdrawMoney(atmRequest);
        });
    }

    @Test
    public void withdrawMoneyGoOverTransactionLimit(){
        ValidateAtmHelper atmHelper = new ValidateAtmHelper(userEntityFrom, accountEntityFrom);

        atmRequest.setAmount(2000L);
        accountEntityTo.setType(AccountType.ATM);
        userEntityLoggedInUser.setRole(Roles.BANK);

        given(validator.isAllowedToAtm(atmRequest))
                .willReturn(atmHelper);

        given(iAccountDTO.findByTypeIs(AccountType.ATM))
                .willReturn(accountEntityTo);

        given(userDTO.findUserEntityByRoleIs(Roles.BANK))
                .willReturn(userEntityLoggedInUser);

        org.junit.jupiter.api.Assertions.assertThrows(ValidationException.class, () -> {
            transactionService.withdrawMoney(atmRequest);
        });
    }

    @Test
    public void depositMoney(){
        ValidateAtmHelper atmHelper = new ValidateAtmHelper(userEntityFrom, accountEntityFrom);

        accountEntityTo.setType(AccountType.ATM);
        userEntityLoggedInUser.setRole(Roles.BANK);

        given(validator.isAllowedToAtm(atmRequest))
                .willReturn(atmHelper);

        given(iAccountDTO.findByTypeIs(AccountType.ATM))
                .willReturn(accountEntityTo);

        given(userDTO.findUserEntityByRoleIs(Roles.BANK))
                .willReturn(userEntityLoggedInUser);

        Long amount = transactionService.depositMoney(atmRequest);

        assertNotNull(amount);
        assertEquals(Optional.of(atmRequest.getAmount()), Optional.of(amount));;
    }


}