package io.swagger.service;

import io.swagger.model.Entity.UserEntity;
import io.swagger.repository.IUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService  {

    @Autowired
    private IUserDTO userRepository;

    public UserEntity addUser(UserEntity t) {
        return userRepository.save(t);
    }

    public UserEntity findUserByName(String username) {
        return userRepository.findByUsername(username);
    }
}
