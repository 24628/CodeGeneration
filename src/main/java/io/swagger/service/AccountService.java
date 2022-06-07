package io.swagger.service;

import io.swagger.api.exceptions.*;
import io.swagger.enums.AccountType;
import io.swagger.enums.Roles;
import io.swagger.helpers.OffsetPageableUUID;
import io.swagger.model.Request.AccountRequest;
import io.swagger.model.Entity.AccountEntity;
import io.swagger.model.Entity.UserEntity;
import io.swagger.repository.IUserDTO;
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
    private IUserDTO userDTO;

    @Autowired
    Validator validator;

    //Check if the user has already a saving // normal account
    public AccountEntity addAccount(AccountRequest body) {
        List<AccountEntity>  accountEntityList = accountRepository.getAllByUuidIs(UUID.fromString(body.getUserId()));
        for (AccountEntity account: accountEntityList){
            if(account.getType().equals(AccountType.valueOf(body.getType()))){
                throw new EntityAlreadyExistException("they account of the type " + body.getType() + " already exist on this user");
            }
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
        account.setUserId(UUID.fromString(body.getUserId()));

        accountRepository.save(account);

        return account;
    }

   // alleen employee amg alle accounts opnemen
    public List<AccountEntity> getAccounts(Integer offset, Integer limit) {
        return accountRepository.getAllByTypeIsNot(AccountType.ATM, new OffsetPageableUUID(limit,offset));
    }


    public AccountEntity getAccountByIBAN(String IBAN) {
        return accountRepository.getAccountByIBAN(IBAN);
    }

    public List<AccountEntity> getAccountByUserId(UUID userid) {
        return accountRepository.getAllByUuidIs(userid);
    }

    public AccountEntity updateAccountByIBAN(AccountRequest body, String IBAN) {
        AccountEntity account = accountRepository.getAccountByIBAN(IBAN);

        if (1 > body.getAbsoluteLimit())
            throw new ValidationException("Absolute limit has to be higher then 0");

        if(AccountType.valueOf(body.getType()) == AccountType.ATM)
            throw new ValidationException("Can not have account type of ATM");

        account.setAbsoluteLimit(body.getAbsoluteLimit());
        account.setType(AccountType.valueOf(body.getType()));

        accountRepository.save(account);

        return account;
    }

    public void generateAccount(AccountEntity account) {
        accountRepository.save(account);
    }


    public AccountEntity findAccountByUserName(String name) {

        UserEntity userEntity = userDTO.findByUsername(name);

        if(userEntity == null)
            throw new UserNotFoundException(name);

        AccountEntity account = accountRepository.getAccountEntityByUserIdAndTypeIsNot(userEntity.getUuid(), AccountType.SAVING);

        if(account == null)
            throw new EntityNotFoundException("Account ");

        return account;
    }
}
