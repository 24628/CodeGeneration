package io.swagger.service;

import io.swagger.api.exceptions.*;
import io.swagger.enums.AccountType;
import io.swagger.enums.Roles;
import io.swagger.helpers.OffsetPageableUUID;
import io.swagger.model.Request.AccountRequest;
import io.swagger.model.Entity.AccountEntity;
import io.swagger.model.Entity.UserEntity;
import io.swagger.validator.Validator;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import io.swagger.repository.IAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private IAccountDTO accountRepository;

    @Autowired
    private UserService userService;

    @Autowired
    Validator validator;

    //Check if the user has already a saving // normal account
    public AccountEntity addAccount(AccountRequest body) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userService.findUserByName(userDetails.getUsername());

        List<AccountEntity>  accountEntityList = accountRepository.getAllByUuidIs(UUID.fromString(body.getUserId()));
        for (AccountEntity account: accountEntityList){
            if(account.getType().equals(AccountType.valueOf(body.getType()))){
                throw new EntityAlreadyExistException("they account of the type " + body.getType() + " already exist on this user");
            }
        }

        if(!user.getRole().equals(Roles.EMPLOYEE) && !user.getUuid().equals(UUID.fromString(body.getUserId()))) {
            throw new InvalidPermissionsException("Your only allowed to view your own account");
        }

        if (1 > body.getAbsoluteLimit())
            throw new ValidationException("Absolute limit has to be higher then 0");

        AccountEntity account = new AccountEntity();
        account.setType(AccountType.valueOf(body.getType()));
        account.setAbsoluteLimit(body.getAbsoluteLimit());
        account.setBalance(0L); // Always start with 0 with transactions we can up that
        account.setIBAN(
                new Iban.Builder()
                        .countryCode(CountryCode.NL)
                        .bankCode("INHO")
                        .buildRandom()
                        .toString()
        );

        if (user.getRole().equals(Roles.CUSTOMER))
            account.setUserId(user.getUuid());

        if (user.getRole().equals(Roles.EMPLOYEE))
            account.setUserId(UUID.fromString(body.getUserId()));

        accountRepository.save(account);

        return account;
    }

   // alleen employee amg alle accounts opnemen
    public List<AccountEntity> getAccounts(Integer offset, Integer limit) {
        validator.NeedsToBeEmployee();
        return accountRepository.getAllByTypeIsNot(AccountType.ATM, new OffsetPageableUUID(limit,offset));
    }


    public AccountEntity getAccountByIBAN(String IBAN) {
        return accountRepository.getAccountByIBAN(IBAN);
    }

    public List<AccountEntity> getAccountByUserId(UUID userid) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userService.findUserByName(userDetails.getUsername());

        if(!user.getRole().equals(Roles.EMPLOYEE) && !user.getUuid().equals(userid)) {
            throw new InvalidPermissionsException("Your only allowed to view your own account");
        }

        return accountRepository.getAllByUuidIs(userid);
    }

    public AccountEntity updateAccountByIBAN(AccountRequest body, String IBAN) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userService.findUserByName(userDetails.getUsername());

        if(!user.getRole().equals(Roles.EMPLOYEE) && !user.getUuid().equals(UUID.fromString(body.getUserId()))) {
            throw new InvalidPermissionsException("Your only allowed to view your own account");
        }

        AccountEntity account = accountRepository.getAccountByIBAN(IBAN);

        if (1 > body.getAbsoluteLimit())
            throw new ValidationException("Absolute limit has to be higher then 0");

        account.setAbsoluteLimit(body.getAbsoluteLimit());
        account.setType(AccountType.valueOf(body.getType()));

        accountRepository.save(account);

        return account;
    }

    public void generateAccount(AccountEntity account) {
        accountRepository.save(account);
    }


    public AccountEntity findAccountByUserName(String name) {

        UserEntity userEntity = userService.findUserByName(name);

        if(userEntity == null)
            throw new UserNotFoundException(name);

        AccountEntity account = accountRepository.getAccountEntityByUserIdAndTypeIsNot(userEntity.getUuid(), AccountType.SAVING);

        if(account == null)
            throw new EntityNotFoundException("Account ");

        return account;
    }
}
