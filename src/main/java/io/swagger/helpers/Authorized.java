package io.swagger.helpers;

import io.swagger.api.exceptions.InvalidPermissionsException;
import io.swagger.enums.Roles;
import io.swagger.model.Entity.AccountEntity;
import io.swagger.model.Entity.UserEntity;
import io.swagger.repository.IAccountDTO;
import io.swagger.repository.IUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Authorized {

    @Autowired
    private IUserDTO userDTO;

    @Autowired
    private IAccountDTO accountDTO;

    public void NeedsToBeEmployee() throws InvalidPermissionsException {
        UserEntity user = getUser();

        if (!user.getRole().equals(Roles.EMPLOYEE))
            throw new InvalidPermissionsException("no permissions to access this");
    }

    public UserEntity CanOnlyEditOwnAccountByIBan(String iban) throws InvalidPermissionsException {
        UserEntity user = getUser();

        AccountEntity acc = accountDTO.getAccountByIBAN(iban);
        UserEntity u = userDTO.findUserEntityByUuid(acc.getUserId());
        if(!user.getRole().equals(Roles.EMPLOYEE) && !user.getUuid().equals(u.getUuid())) {
            throw new InvalidPermissionsException("Your only allowed to view your own account");
        }

        return user;
    }

    public void CanOnlyEditOwnAccount(UUID uuid) throws InvalidPermissionsException {
        UserEntity user = getUser();

        if(!user.getRole().equals(Roles.EMPLOYEE) && !user.getUuid().equals(uuid)) {
            throw new InvalidPermissionsException("Your only allowed to view your own account");
        }
    }

    private UserEntity getUser(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDTO.findByUsername(userDetails.getUsername());
    }
}
