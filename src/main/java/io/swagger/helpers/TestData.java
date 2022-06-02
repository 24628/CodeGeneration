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
import org.springframework.stereotype.Component;


import java.util.Date;
import java.util.List;
import java.util.Random;

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
        String[] names = {"Bob", "Karel", "hans", "piet"};
        for (String user : names) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(user);
            userEntity.setName(user);
            userEntity.setEmail(user + "@example.com");
            userEntity.setPassword("$2a$12$PDMzF/Zq9t6M.guuRiN5pevmQtcaG6wMv9wWvZJaFwylap9FYb7Tu"); //password all the same password
            userEntity.setRole(Roles.values()[random.nextInt(Roles.values().length)]);
            userEntity.setTransaction_limit(0L);
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
        userEntity.setTransaction_limit(0L);
        userService.generateUsers(userEntity);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setBalance(0L);
        accountEntity.setType(AccountType.ATM);
        accountEntity.setAbsolute_limit(0L);
        accountEntity.setUser_uuid(userEntity.getUuid());
        accountEntity.setIBAN("NL01INHO0000000001");
        accountService.generateAccount(accountEntity);
    }

    private void generateAccount(UserEntity user) {
        AccountType[] accounttypes = new AccountType[]{AccountType.SAVING, AccountType.NORMAL};
        for (var account : accounttypes) {
            AccountEntity accountEntity = new AccountEntity();
            accountEntity.setBalance(0L);
            accountEntity.setType(account);
            accountEntity.setAbsolute_limit(0L);
            accountEntity.setUser_uuid(user.getUuid());
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
        Random rnd = new Random();
        for (UserEntity currentuser : users) {
            TransactionEntity transaction = new TransactionEntity();
            UserEntity randomuser = users.get(new Random().nextInt(users.size()));
            transaction.setAmount(random.nextInt((1000 - 100) + 1) + 10);
            transaction.setAccount_from(allaccounts.getOne(currentuser.getUuid()).getUuid()); // moet account entity pakken
            transaction.setDate(new Date());
            transaction.setUser_id(randomuser.getUuid());
            transaction.setAccount_to(randomuser.getUuid());
            if (currentuser == randomuser) {
                continue;
            }
            transactionService.generateTransaction(transaction);
        }

    }
}

