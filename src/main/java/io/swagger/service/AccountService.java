package io.swagger.service;

import io.swagger.enums.AccountType;
import io.swagger.enums.Roles;
import io.swagger.model.Account;
import io.swagger.model.Entity.AccountEntity;
import io.swagger.model.Entity.UserEntity;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import io.swagger.repository.IAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private IAccountDTO accountRepository;

    @Autowired
    private UserService userService;

    public void addAccount(Account body) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userService.findUserByName(userDetails.getUsername());

        AccountEntity account = new AccountEntity();
        account.setType(AccountType.valueOf(body.getType()));
        account.setAbsolute_limit((long)body.getAbsoluteLimit());
        account.setIBAN(
            new Iban.Builder()
                .countryCode(CountryCode.NL)
                .buildRandom()
                .toString()
        );

        if(user.getRole().equals(Roles.CUSTOMER))
            account.setUser_uuid(user.getUuid());

        if(user.getRole().equals(Roles.EMPLOYEE))
            account.setUser_uuid(UUID.fromString(body.getUserId()));

        accountRepository.save(account);
    }
    // haalt alle accounts op uit de database
    public List<AccountEntity> getAccounts(){
        return accountRepository.findAll();
    }
    // get account after inserting the iban
    public AccountEntity getAccountByIBAN(String IBAN){
       return accountRepository.getAccountByIBAN(IBAN);
    }
    // todo account can be updated
    public void updateAccountByIBAN(String IBAN){
         accountRepository.updateAccountByIBAN(IBAN);
    }



}
