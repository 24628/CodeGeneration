package io.swagger.service;

import io.swagger.enums.Roles;
import io.swagger.model.Entity.AccountEntity;
import io.swagger.model.Entity.UserEntity;
import io.swagger.model.User;
import io.swagger.repository.IUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private IUserDTO userRepository;

    public UserEntity addUser(UserEntity t) {
        return userRepository.save(t);
    }

    public UserEntity findUserByName(String username) {
        return userRepository.findByUsername(username);
    }

    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserById(String uuid) {
        return userRepository.getOne(UUID.fromString(uuid));
    }

    public void updateUser(String uuid, User body){

    }

    public void deleteUser(String uuid) {
        UserEntity user = userRepository.getOne(UUID.fromString(uuid));

        user.setRole(Roles.DISABLED);

        userRepository.save(user);
    }
}
