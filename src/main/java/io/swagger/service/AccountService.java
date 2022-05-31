package io.swagger.service;

import io.swagger.api.exceptions.EntityAlreadyExistException;
import io.swagger.api.exceptions.InvalidPermissionsException;
import io.swagger.api.exceptions.ValidationException;
import io.swagger.enums.AccountType;
import io.swagger.enums.Roles;
import io.swagger.model.Account;
import io.swagger.model.Entity.AccountEntity;
import io.swagger.model.Entity.UserEntity;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import io.swagger.repository.IAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private IAccountDTO accountRepository;

    @Autowired
    private UserService userService;


    //Check if the user has already a saving // normal account
    public void addAccount(Account body) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userService.findUserByName(userDetails.getUsername());

        List<AccountEntity>  accountEntityList = accountRepository.getAllByUuidIs(UUID.fromString(body.getUserId()));

        for (AccountEntity account: accountEntityList){
            if(account.getType().equals(AccountType.valueOf(body.getType()))){
                //Throw error body.getType() -> aan geven welk type account niet aan gemaakt mag worden
                throw new EntityAlreadyExistException("they account of the type " + body.getType() + " already exist on this user");
            }
        }

        if (1 > body.getAbsoluteLimit())
            throw new ValidationException("Absolute limit has to be higher then 0");

        AccountEntity account = new AccountEntity();
        account.setType(AccountType.valueOf(body.getType()));
        account.setAbsolute_limit(body.getAbsoluteLimit());
        account.setBalance(0L); // Always start with 0 with transactions we can up that
        account.setIBAN(
                new Iban.Builder()
                        .countryCode(CountryCode.NL)
                        .buildRandom()
                        .toString()
        );

        if (user.getRole().equals(Roles.CUSTOMER))
            account.setUser_uuid(user.getUuid());

        if (user.getRole().equals(Roles.EMPLOYEE))
            account.setUser_uuid(UUID.fromString(body.getUserId()));

        accountRepository.save(account);
    }

    // haalt alle accounts op uit de database
    // Zorg dat alleen employee dit mag doen
    // Als de account role ATM heeft moet deze weg gefilterd worden
    // Stuurt een lijst terug van alle accounts
    public List<AccountEntity> getAccounts() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userService.findUserByName(userDetails.getUsername());

        if (!user.getRole().equals(Roles.EMPLOYEE)) {
            throw new InvalidPermissionsException("You dont have the correct permissions");
        }

        return accountRepository.getAllByTypeIsNot("ATM");
    }


    // get account after inserting the iban
    // Zorg dat je geen atm kan op halen
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

    public AccountEntity updateAccountByIBAN(Account body, String IBAN) {
        AccountEntity account = accountRepository.getAccountByIBAN(IBAN);

        if (1 > body.getAbsoluteLimit())
            throw new ValidationException("Absolute limit has to be higher then 0");

        account.setAbsolute_limit(body.getAbsoluteLimit());
        account.setType(AccountType.valueOf(body.getType()));

        accountRepository.save(account);

        return account;
    }

    public void generateAccount(AccountEntity account) {
        accountRepository.save(account);
    }


}
