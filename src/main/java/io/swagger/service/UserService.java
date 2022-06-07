package io.swagger.service;

import io.swagger.api.exceptions.InvalidPermissionsException;
import io.swagger.enums.Roles;
import io.swagger.helpers.OffsetPageableUUID;
import io.swagger.model.Entity.UserEntity;
import io.swagger.model.Request.UserRequest;
import io.swagger.repository.IAccountDTO;
import io.swagger.repository.IUserDTO;
import io.swagger.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
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
    private IAccountDTO accountDTO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Validator validator;

    public UserEntity addUser(UserRequest body) {
        validator.CanCreateUser(body.getName(), body.getEmail(), body.getPassword(), body.getDayLimit(), 100L,body.getName());

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(body.getName());
        userEntity.setEmail(body.getEmail());
        userEntity.setTransactionLimit(body.getTransactionLimit());
        userEntity.setPassword(passwordEncoder.encode(body.getPassword()));
        userEntity.setRole(Roles.valueOf(body.getRole()));

        return userDTO.save(userEntity);
    }

    public UserEntity findUserByName(String username) {
        return userDTO.findByUsername(username);
    }

    public List<UserEntity> getUsers(Integer limit,Integer offset) throws InvalidPermissionsException {
        return userDTO.findAll(new OffsetPageableUUID(limit,offset)).getContent();
    }

    public UserEntity getUserById(String uuid) {
        return userDTO.getOne(UUID.fromString(uuid));
    }

    public UserEntity updateUser(String uuid, UserRequest body) throws InvalidPermissionsException{
        UserEntity userToEdit = userDTO.getOne(UUID.fromString(uuid));

        if(!userToEdit.getRole().equals(Roles.EMPLOYEE) && !userToEdit.getUuid().equals(UUID.fromString(uuid))) {
            throw new InvalidPermissionsException("Your only allowed to view your own account");
        }

        userToEdit.setTransactionLimit(body.getTransactionLimit());
        return userDTO.save(userToEdit);
    }

    public void deleteUser(String uuid) {
        UserEntity user = userDTO.getOne(UUID.fromString(uuid));

        user.setRole(Roles.DISABLED);

        userDTO.save(user);
    }

    public void generateUsers(UserEntity u) { userDTO.save(u); }


    public List<UserEntity> getUsersWithNoAccount(Integer offset,Integer limit) {
        List<UserEntity> userEntityList = userDTO.findAllByRoleIs(Roles.CUSTOMER, new OffsetPageableUUID(limit,offset));
        ArrayList<UserEntity> foundUsers = new ArrayList<>();

        for (UserEntity user : userEntityList) {
            if(!accountDTO.findByUserIdIs(user.getUuid())){
                foundUsers.add(user);
            }

            if(foundUsers.size() > 10)
                break;
        }

        return foundUsers;
    }
}
