package io.swagger.service;

import io.swagger.api.exceptions.EntityNotFoundException;
import io.swagger.api.exceptions.ValidationException;
import io.swagger.enums.AccountType;
import io.swagger.enums.Roles;
import io.swagger.helpers.ValidateAtmHelper;
import io.swagger.model.Atm;
import io.swagger.model.Entity.AccountEntity;
import io.swagger.model.Entity.TransactionEntity;
import io.swagger.model.Entity.UserEntity;
import io.swagger.model.Transaction;
import io.swagger.model.TransactionAdvancedSearchRequest;
import io.swagger.repository.IAccountDTO;
import io.swagger.repository.ITransactionDTO;
import io.swagger.repository.IUserDTO;
import io.swagger.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    IUserDTO userDTO;

    @Autowired
    private IAccountDTO accountRepository;

    @Autowired
    Validator validator;
    public TransactionEntity addTransaction(Transaction body) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userService.findUserByName(userDetails.getUsername());

        AccountEntity accountFrom = accountRepository.getAccountByIBAN(body.getFrom());
        AccountEntity accountTo = accountRepository.getAccountByIBAN(body.getTo());

        if (accountFrom == null)
            throw new EntityNotFoundException("Account from ");

        if (accountTo == null)
            throw new EntityNotFoundException("Account to ");

        UserEntity userFrom = userService.getUserById(accountFrom.getUserId().toString());

        //amount to deposit has to be higher then 0
        if (body.getAmount() > 1)
            throw new ValidationException("Amount has to be higher then 0");

        //de account balance nadat de deposit er van afgaat mag niet lager zijn dan de absolute limit van het account
        if ((accountFrom.getBalance() - (long) body.getAmount()) > accountFrom.getAbsoluteLimit())
            throw new ValidationException("the account value will go below the absolute limit");

        //als left to transact 0 is dan zjn we over de limit van de day limit en mogen er geen transactie limits gemaakt worden
//        if((float) body.getAmount() > leftToTransact)
//            throw new ValidationException("over the daily transaction limit");

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

    public List<TransactionEntity> getTransactions() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userService.findUserByName(userDetails.getUsername());

        if (user.getRole().equals(Roles.CUSTOMER))
            return transactionRepository.getAllByAccountFrom(user.getUuid());

        return transactionRepository.findAll();
    }

    public Long withdrawMoney(Atm body) {
        ValidateAtmHelper res = validator.isAllowedToAtm(body);
        AccountEntity accountEntity = res.getAccountEntity();
        UserEntity userEntity = res.getUserEntity();
        AccountEntity atm = accountRepository.findByTypeIs(AccountType.ATM);
        UserEntity bank = userDTO.findUserEntityByRoleIs(Roles.BANK);


        if ((accountEntity.getBalance() - (long) body.getAmount()) > accountEntity.getAbsoluteLimit())
            throw new ValidationException("the account value will go below the absolute limit");
        //als left to transact 0 is dan zjn we over de limit van de day limit en mogen er geen transactie limits gemaakt worden

        //@todo make day limit check

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

    public Long depositMoney(Atm body) {
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

    public List<TransactionEntity> advanceSearch(TransactionAdvancedSearchRequest body) {
        validator.NeedsToBeEmployee();

        AccountEntity accountEntityFrom = accountRepository.getAccountByIBAN(body.getIbanFrom());
        AccountEntity accountEntityTo = accountRepository.getAccountByIBAN(body.getIbanTo());

        if(accountEntityTo == null && accountEntityFrom == null)
            return transactionRepository.findAllByAmountBetweenAndDateBetween(body.getLessThanTransAmount(), body.getGreaterThanTransAmount(), body.getDateBefore(), body.getDateAfter());

        if(accountEntityTo == null)
            return transactionRepository.findAllByAmountBetweenAndDateBetweenAndAccountFrom(body.getLessThanTransAmount(), body.getGreaterThanTransAmount(), body.getDateBefore(), body.getDateAfter(), accountEntityFrom.getUuid());

        if(accountEntityFrom == null)
            return transactionRepository.findAllByAmountBetweenAndDateBetweenAndAccountTo(body.getLessThanTransAmount(), body.getGreaterThanTransAmount(), body.getDateBefore(), body.getDateAfter(), accountEntityTo.getUuid());

        return transactionRepository.findAllByAmountBetweenAndDateBetweenAndAccountFromAndAccountTo(body.getLessThanTransAmount(), body.getGreaterThanTransAmount(), body.getDateBefore(), body.getDateAfter(), accountEntityFrom.getUuid(), accountEntityTo.getUuid());
    }
}
