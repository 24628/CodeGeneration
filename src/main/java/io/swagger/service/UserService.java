package io.swagger.service;

import io.swagger.model.Entity.UserEntity;
import io.swagger.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    public UserEntity addUser(UserEntity t) {
        return userRepository.save(t);
    }
}
