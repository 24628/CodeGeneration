package io.swagger.service;

import io.swagger.model.Entity.Account;
import io.swagger.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private IAccountRepository accountRepository;
    public Account addAccount(Account t) {
        return accountRepository.save(t);
    }

}
