package io.swagger.service;

import io.swagger.api.exceptions.EntityNotFoundException;
import io.swagger.api.exceptions.InvalidPermissionsException;
import io.swagger.api.exceptions.ValidationException;
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
        validator.CanCreateUser(body.getName(), body.getEmail(), body.getPassword(), body.getDayLimit(), body.getTransactionLimit(),body.getName());

        UserEntity userEntity = UserEntity.builder()
                .dayLimit(body.getDayLimit())
                .email(body.getEmail())
                .name(body.getName())
                .username(body.getUsername())
                .password(passwordEncoder.encode(body.getPassword()))
                .pinCode(body.getPinCode())
                .role(Roles.valueOf(body.getRole()))
                .transactionLimit(body.getTransactionLimit())
                .build();

        userDTO.save(userEntity);
        return userEntity;
    }

    public UserEntity findUserByName(String username) {
        return userDTO.findByUsername(username);
    }

    public List<UserEntity> getUsers(Integer limit,Integer offset) {
        return userDTO.findAllByRoleIsNot(Roles.BANK, new OffsetPageableUUID(limit,offset));
    }

    public UserEntity getUserById(String uuid) {
        UserEntity found = userDTO.getOne(UUID.fromString(uuid));

        if(found == null)
            throw new EntityNotFoundException("User with this uuid ");

        return found;
    }

    public UserEntity updateUser(String uuid, UserRequest body) {
        UserEntity userToEdit = userDTO.getOne(UUID.fromString(uuid));

        if(userToEdit == null)
            throw new EntityNotFoundException("User with this uuid ");

        if (1 > body.getTransactionLimit())
            throw new ValidationException("Transaction limit has to be higher then 0");

        userToEdit.setTransactionLimit(body.getTransactionLimit());
        userDTO.save(userToEdit);
        return userToEdit;
    }

    public UserEntity deleteUser(String uuid) {
        UserEntity user = userDTO.getOne(UUID.fromString(uuid));

        if(user == null)
            throw new EntityNotFoundException("User with this uuid ");

        user.setRole(Roles.DISABLED);

        userDTO.save(user);
        return user;
    }

    public void generateUsers(UserEntity u) { userDTO.save(u); }


    public List<UserEntity> getUsersWithNoAccount(Integer offset,Integer limit) {
        List<UserEntity> userEntityList = userDTO.findAllByRoleIs(Roles.CUSTOMER);
        ArrayList<UserEntity> foundUsers = new ArrayList<>();

        for (UserEntity user : userEntityList) {
            if(!accountDTO.findByUserIdIs(user.getUuid())){
                foundUsers.add(user);
            }

            if(foundUsers.size() > (limit +(offset * limit)))
                break;
        }

        return foundUsers.subList(foundUsers.size()-limit, foundUsers.size());
    }
}
