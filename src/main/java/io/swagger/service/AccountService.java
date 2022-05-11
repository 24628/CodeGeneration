package io.swagger.service;

import io.swagger.model.Entity.AccountEntity;
import io.swagger.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private IAccountRepository accountRepository;
    public AccountEntity addAccount(AccountEntity t) {
        return accountRepository.save(t);
    }

}
