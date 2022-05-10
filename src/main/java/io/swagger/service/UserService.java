package io.swagger.service;

import io.swagger.model.Entity.User;
import io.swagger.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    public User addUser(User t) {
        return userRepository.save(t);
    }
}
