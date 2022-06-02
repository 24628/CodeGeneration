package io.swagger.service;

import io.swagger.api.exceptions.EntityAlreadyExistException;
import io.swagger.api.exceptions.InvalidPermissionsException;
import io.swagger.api.exceptions.ValidationException;
import io.swagger.enums.Roles;
import io.swagger.model.Entity.AccountEntity;
import io.swagger.model.Entity.DayLimitEntity;
import io.swagger.model.Entity.UserEntity;
import io.swagger.model.User;
import io.swagger.repository.IDayLimitDTO;
import io.swagger.repository.IUserDTO;
import io.swagger.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private IUserDTO userDTO;

    @Autowired
    private IDayLimitDTO dayLimitDTO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    Validator validator;

    public UserEntity addUser(User body) {
        validator.NeedsToBeEmployee();

        validator.CanCreateUser(body.getName(), body.getEmail(), body.getPassword(), body.getDayLimit(), 100L,body.getName());

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(body.getName());
        userEntity.setEmail(body.getEmail());
        userEntity.setTransactionLimit(body.getTransactionLimit());
        userEntity.setPassword(passwordEncoder.encode(body.getPassword()));
        userEntity.setRole(Roles.valueOf(body.getRole()));

        DayLimitEntity dayLimit = new DayLimitEntity();
        dayLimit.setActualLimit(body.getDayLimit());
        dayLimit.setCurrent(0L);
        dayLimit.setUserId(userEntity.getUuid());

        dayLimitDTO.save(dayLimit);
        return userDTO.save(userEntity);
    }

    public UserEntity findUserByName(String username) {

        return userDTO.findByUsername(username);
    }

    public List<UserEntity> getUsers() throws InvalidPermissionsException {
        validator.NeedsToBeEmployee();
        return userDTO.findAll();
    }

    public UserEntity getUserById(String uuid) {
        validator.NeedsToBeEmployee();

        return userDTO.getOne(UUID.fromString(uuid));
    }

    public UserEntity updateUser(String uuid, User body) throws InvalidPermissionsException{
        UserEntity userToEdit = userDTO.getOne(UUID.fromString(uuid));
        DayLimitEntity dayLimit = dayLimitDTO.getByUserId(userToEdit.getUuid());

        if(!userToEdit.getRole().equals(Roles.EMPLOYEE) && !userToEdit.getUuid().equals(UUID.fromString(uuid))) {
            throw new InvalidPermissionsException("Your only allowed to view your own account");
        }
        dayLimit.setActualLimit(body.getDayLimit());
        dayLimit.setCurrent(0L);
        dayLimitDTO.save(dayLimit);

        userToEdit.setTransactionLimit(body.getTransactionLimit());
        return userDTO.save(userToEdit);
    }

    public void deleteUser(String uuid) {

        validator.NeedsToBeEmployee();

        UserEntity user = userDTO.getOne(UUID.fromString(uuid));

        user.setRole(Roles.DISABLED);

        userDTO.save(user);
    }

    public void generateUsers(UserEntity u) { userDTO.save(u); }
}
