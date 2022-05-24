package io.swagger.service;

import io.swagger.model.Entity.AccountEntity;
import io.swagger.repository.IAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private IAccountDTO accountRepository;
    public AccountEntity addAccount(AccountEntity t) {
        return accountRepository.save(t);
    }
    public List<AccountEntity> getAllUsersAndAccounts(){return accountRepository.getAllUsersAndAccounts();}
    public List<AccountEntity> getAccountByIban(String iban){ return accountRepository.getAccountByIban(iban);}


}
