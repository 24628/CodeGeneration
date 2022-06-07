package io.swagger.service;

import io.swagger.api.exceptions.DayLimitReachedException;
import io.swagger.api.exceptions.EntityNotFoundException;
import io.swagger.api.exceptions.InvalidPermissionsException;
import io.swagger.api.exceptions.ValidationException;
import io.swagger.enums.AccountType;
import io.swagger.enums.Roles;
import io.swagger.helpers.OffsetPageableDate;
import io.swagger.helpers.ValidateAtmHelper;
import io.swagger.model.Request.AtmRequest;
import io.swagger.model.Entity.AccountEntity;
import io.swagger.model.Entity.TransactionEntity;
import io.swagger.model.Entity.UserEntity;
import io.swagger.model.Request.TransactionRequest;
import io.swagger.model.Request.TransactionAdvancedSearchRequest;
import io.swagger.repository.IAccountDTO;
import io.swagger.repository.ITransactionDTO;
import io.swagger.repository.IUserDTO;
import io.swagger.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private ITransactionDTO transactionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    ITransactionDTO transactionDTO;
    @Autowired
    IUserDTO userDTO;
    @Autowired
    private IAccountDTO accountRepository;
    @Autowired
    Validator validator;

    public TransactionEntity addTransaction(TransactionRequest body) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userService.findUserByName(userDetails.getUsername());

        AccountEntity accountFrom = accountRepository.getOne(UUID.fromString(body.getFrom()));
        AccountEntity accountTo = accountRepository.getOne(UUID.fromString(body.getTo()));

        System.out.println(accountFrom.getUuid());
        System.out.println(accountTo.getUuid());

        if (accountFrom == null)
            throw new EntityNotFoundException("Account from ");

        if (accountTo == null)
            throw new EntityNotFoundException("Account to ");

        UserEntity userFrom = userService.getUserById(accountFrom.getUserId().toString());

        //amount to deposit has to be higher then 0
        if (body.getAmount() < 1)
            throw new ValidationException("Amount has to be higher then 0");

        //de account balance nadat de deposit er van afgaat mag niet lager zijn dan de absolute limit van het account
        if ((accountFrom.getBalance() - (long) body.getAmount()) < accountFrom.getAbsoluteLimit())
            throw new ValidationException("the account value will go below the absolute limit");

        //als left to transact 0 is dan zjn we over de limit van de day limit en mogen er geen transactie limits gemaakt worden
        validator.CheckDayLimit(userFrom, (long)body.getAmount());

        // als de body hoger is dan de transactie limit dan gooien we een error
        if ((float) body.getAmount() > userFrom.getTransactionLimit())
            throw new ValidationException("over the transaction limit");

        //if its an savings account make sure we can only go to the same users own normal account
        if (accountFrom.getType().equals(AccountType.SAVING)
                && accountFrom.getUserId() != accountTo.getUserId())
            throw new ValidationException("Savings accounts can only make transactions to your own account");

        //if we go from a normal account to a savings account make sure it's the same user and not another user!
        if (accountFrom.getType().equals(AccountType.NORMAL)
                && accountTo.getType().equals(AccountType.SAVING)
                && accountFrom.getUserId() != accountTo.getUserId()
        ) throw new ValidationException("Cannot make transactions from normal account to another users saving account");

        if (user.getRole().equals(Roles.CUSTOMER)
                && accountFrom.getUserId() != user.getUuid())
            throw new ValidationException("This is not your own account!");

        //Create the transactions
        TransactionEntity transaction = new TransactionEntity();
        transaction.setAmount(body.getAmount());
        transaction.setDate(LocalDateTime.now());
        transaction.setAccountFrom(UUID.fromString(body.getFrom()));
        transaction.setAccountTo(UUID.fromString(body.getTo()));
        transaction.setUser_id(user.getUuid());
        transactionRepository.save(transaction);

        //update balance from
        accountFrom.setBalance(accountFrom.getBalance() - body.getAmount());
        accountRepository.save(accountFrom);

        //update balance to
        accountTo.setBalance(accountTo.getBalance() + body.getAmount());
        accountRepository.save(accountTo);

        return transaction;
    }

    public List<TransactionEntity> getTransactions(Integer offset,Integer limit) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userService.findUserByName(userDetails.getUsername());
        if (user.getRole().equals(Roles.BANK) || user.getRole().equals(Roles.EMPLOYEE))
            return transactionRepository.findAll(new OffsetPageableDate(limit,offset)).getContent();

        return transactionRepository.getAllByAccountFromOrAccountTo(user.getUuid(),new OffsetPageableDate(limit,offset));
    }
    public List<TransactionEntity> getTransactions() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userService.findUserByName(userDetails.getUsername());
        if (user.getRole().equals(Roles.BANK) || user.getRole().equals(Roles.EMPLOYEE))
            return transactionRepository.findAll();
        return transactionRepository.getAllByAccountFromOrAccountTo(user.getUuid());
    }

    public Long withdrawMoney(AtmRequest body) {
        ValidateAtmHelper res = validator.isAllowedToAtm(body);
        AccountEntity accountEntity = res.getAccountEntity();
        UserEntity userEntity = res.getUserEntity();
        AccountEntity atm = accountRepository.findByTypeIs(AccountType.ATM);
        UserEntity bank = userDTO.findUserEntityByRoleIs(Roles.BANK);


        if ((accountEntity.getBalance() - (long) body.getAmount()) < accountEntity.getAbsoluteLimit())
            throw new ValidationException("the account value will go below the absolute limit");
        //als left to transact 0 is dan zjn we over de limit van de day limit en mogen er geen transactie limits gemaakt worden

        validator.CheckDayLimit(userEntity, body.getAmount());

        // als de body hoger is dan de transactie limit dan gooien we een error
        if ((float) body.getAmount() > userEntity.getTransactionLimit())
            throw new ValidationException("over the transaction limit");

        TransactionEntity transaction = new TransactionEntity();
        transaction.setAmount(body.getAmount());
        transaction.setDate(LocalDateTime.now());
        transaction.setAccountFrom(accountEntity.getUuid());
        transaction.setAccountTo(atm.getUuid());
        transaction.setUser_id(bank.getUuid());
        transactionRepository.save(transaction);

        accountEntity.setBalance(accountEntity.getBalance() - body.getAmount());
        accountRepository.save(accountEntity);

        return body.getAmount();
    }



    public Long depositMoney(AtmRequest body) {
        ValidateAtmHelper res = validator.isAllowedToAtm(body);
        AccountEntity accountEntity = res.getAccountEntity();
        AccountEntity atm = accountRepository.findByTypeIs(AccountType.ATM);
        UserEntity bank = userDTO.findUserEntityByRoleIs(Roles.BANK);
        
        TransactionEntity transaction = new TransactionEntity();
        transaction.setAccountFrom(atm.getUuid());
        transaction.setAmount(body.getAmount());
        transaction.setDate(LocalDateTime.now());
        transaction.setAccountTo(accountEntity.getUuid());
        transaction.setUser_id(bank.getUuid());
        transactionRepository.save(transaction);

        accountEntity.setBalance(accountEntity.getBalance() + body.getAmount());
        accountRepository.save(accountEntity);

        return body.getAmount();
    }

    public void generateTransaction(TransactionEntity transaction) {
        transactionRepository.save(transaction);
    }

    public List<TransactionEntity> advanceSearch(Integer limit, Integer offset, Long lessThenTransAmount, Long greaterThanTransAmount, LocalDateTime dateBefore, LocalDateTime dateAfter, String ibanTo, String ibanFrom) {
        validator.NeedsToBeEmployee();

        AccountEntity accountEntityFrom = accountRepository.getAccountByIBAN(ibanFrom);
        AccountEntity accountEntityTo = accountRepository.getAccountByIBAN(ibanTo);

        if (accountEntityTo == null && accountEntityFrom == null)
            return transactionRepository.findAllByAmountBetweenAndDateBetween(lessThenTransAmount, greaterThanTransAmount, dateBefore, dateAfter,new OffsetPageableDate(limit,offset));

        if (accountEntityTo == null)
            return transactionRepository.findAllByAmountBetweenAndDateBetweenAndAccountFrom(lessThenTransAmount, greaterThanTransAmount, dateBefore, dateAfter, accountEntityFrom.getUuid(),new OffsetPageableDate(limit,offset));

        if (accountEntityFrom == null)
            return transactionRepository.findAllByAmountBetweenAndDateBetweenAndAccountTo(lessThenTransAmount, greaterThanTransAmount, dateBefore, dateAfter, accountEntityTo.getUuid(),new OffsetPageableDate(limit,offset));

        return transactionRepository.findAllByAmountBetweenAndDateBetweenAndAccountFromAndAccountTo(lessThenTransAmount, greaterThanTransAmount, dateBefore, dateAfter, accountEntityFrom.getUuid(), accountEntityTo.getUuid(),new OffsetPageableDate(limit,offset));
    }
}
