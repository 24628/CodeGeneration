package io.swagger.helpers;

import io.swagger.enums.AccountType;
import io.swagger.enums.Roles;
import io.swagger.model.Entity.AccountEntity;
import io.swagger.model.Entity.TransactionEntity;
import io.swagger.model.Entity.UserEntity;
import io.swagger.repository.IAccountDTO;
import io.swagger.repository.IUserDTO;
import io.swagger.service.TransactionService;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import io.swagger.service.AccountService;
import io.swagger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class TestData {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private IUserDTO allusers;
    @Autowired
    private IAccountDTO allaccounts;
    private Random random;

  public TestData(){
      random = new Random();
  }

    public void Generate(){

        generateUsers();
        generateTransactions();
        CreateBank();
    }

    private void generateUsers() {
        Roles [] roles = new Roles[]{Roles.EMPLOYEE,Roles.CUSTOMER,Roles.DISABLED};
        String[] names = {"bob", "karel", "hans", "piet"};
        for (String user : names) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(user);
            userEntity.setName(user);
            userEntity.setEmail(user + "@example.com");
            userEntity.setPassword("$2a$12$PDMzF/Zq9t6M.guuRiN5pevmQtcaG6wMv9wWvZJaFwylap9FYb7Tu"); //password all the same password
            userEntity.setRole(Roles.values()[random.nextInt(roles.length)]);
            userEntity.setTransactionLimit(200L);
            userEntity.setDayLimit(500L);
            userEntity.setPinCode(1234);
            userService.generateUsers(userEntity);
            generateAccount(userEntity);
        }
    }

    private void CreateBank(){
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("admin");
        userEntity.setEmail("admin@example.com");
        userEntity.setPassword("$2a$12$PDMzF/Zq9t6M.guuRiN5pevmQtcaG6wMv9wWvZJaFwylap9FYb7Tu"); //password
        userEntity.setRole(Roles.BANK);
        userEntity.setTransactionLimit(0L);
        userService.generateUsers(userEntity);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setBalance(0L);
        accountEntity.setType(AccountType.ATM);
        accountEntity.setAbsoluteLimit(0L);
        accountEntity.setUuid(userEntity.getUuid());
        accountEntity.setIBAN("NL01INHO0000000001");
        accountService.generateAccount(accountEntity);
    }

    private void generateAccount(UserEntity user) {
        AccountType[] accounttypes = new AccountType[]{AccountType.SAVING, AccountType.NORMAL};
        for (var account : accounttypes) {
            AccountEntity accountEntity = new AccountEntity();
            accountEntity.setBalance(1000L);
            accountEntity.setType(account);
            accountEntity.setAbsoluteLimit(0L);
            accountEntity.setUuid(user.getUuid());
            accountEntity.setUserId(user.getUuid());
            accountEntity.setAbsoluteLimit(0L);
            accountEntity.setIBAN(new Iban.Builder()
                    .countryCode(CountryCode.NL)
                    .bankCode("INHO")
                    .buildRandom()
                    .toString());
            accountService.generateAccount(accountEntity);
        }
    }

    private void generateTransactions() {

        List<UserEntity> users = allusers.findAll();
        for (UserEntity currentuser : users) {
            UUID currentuuid = currentuser.getUuid();
            TransactionEntity transaction = new TransactionEntity();

            UserEntity randomuser = users.get(new Random().nextInt(users.size()));

            String toAccount = allaccounts.getAccountEntityByUserIdAndTypeIsNot(randomuser.getUuid(),AccountType.SAVING).getIBAN();

            transaction.setAmount(random.nextInt((1000 - 100) + 1) + 10);

            transaction.setAccountFrom(
                    allaccounts.getAccountEntityByUserIdAndTypeIsNot(currentuuid,AccountType.SAVING).getIBAN()
            ); // moet account entity pakken
            transaction.setDate(LocalDateTime.now());
            transaction.setUser_id(randomuser.getUuid());

            transaction.setAccountTo(toAccount);

            if (currentuser == randomuser) {
                continue;
            }
            transactionService.generateTransaction(transaction);
        }

    }
}

