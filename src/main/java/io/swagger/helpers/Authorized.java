package io.swagger.helpers;

import io.swagger.api.exceptions.InvalidPermissionsException;
import io.swagger.enums.Roles;
import io.swagger.model.Entity.UserEntity;
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

    public void NeedsToBeEmployee() throws InvalidPermissionsException {
        UserEntity user = getUser();

        if (!user.getRole().equals(Roles.EMPLOYEE))
            throw new InvalidPermissionsException("no permissions to access this");
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
