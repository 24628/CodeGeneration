package io.swagger.service;

import io.swagger.enums.AccountType;
import io.swagger.enums.Roles;
import io.swagger.model.Entity.AccountEntity;
import io.swagger.model.Entity.TransactionEntity;
import io.swagger.model.Entity.UserEntity;
import io.swagger.model.Transaction;
import io.swagger.repository.IAccountDTO;
import io.swagger.repository.ITransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Timestamp;
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
    private IAccountDTO accountRepository;

    public void addTransaction(Transaction body) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userService.findUserByName(userDetails.getUsername());

        AccountEntity accountFrom = accountRepository.getAccountByIBAN(body.getFrom());
        AccountEntity accountTo = accountRepository.getAccountByIBAN(body.getTo());

        if (body.getAmount() > 1)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Amount has to be higher then 0");

        if ((float) body.getAmount() > accountFrom.getBalance())
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "No sufficient funds in the account");

        //if its an savings account make sure we can only go to the same users own normal account
        if (accountFrom.getType().equals(AccountType.SAVING)
            && accountFrom.getUser_uuid() != accountTo.getUser_uuid())
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Savings accounts can only make transactions to your own account");

        //if we go from a normal account to a savings account make sure it's the same user and not another user!
        if(accountFrom.getType().equals(AccountType.NORMAL)
            && accountTo.getType().equals(AccountType.SAVING)
            && accountFrom.getUser_uuid() != accountTo.getUser_uuid()
        ) throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Cannot make transactions from normal account to another users saving account");

        if(user.getRole().equals(Roles.CUSTOMER)
            && accountFrom.getUser_uuid() != user.getUuid())
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "This is not your own account!");

        //Create the transactions
        TransactionEntity transaction = new TransactionEntity();
        transaction.setAmount(body.getAmount());
        transaction.setDate(new Date());
        transaction.setAccount_from(UUID.fromString(body.getFrom()));
        transaction.setAccount_to(UUID.fromString(body.getTo()));
        transaction.setUser_id(user.getUuid());
        transactionRepository.save(transaction);

        //update balance from
        accountFrom.setBalance(accountFrom.getBalance() - body.getAmount());
        accountRepository.save(accountFrom);

        //update balance to
        accountTo.setBalance(accountTo.getBalance() + body.getAmount());
        accountRepository.save(accountTo);
    }

    public List<TransactionEntity> getTransactions() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userService.findUserByName(userDetails.getUsername());

//        if (user.getRole().equals(Roles.CUSTOMER))
//            return transactionRepository.getAllByAccount_from(user.getUuid());

        return transactionRepository.findAll();
    }
}
