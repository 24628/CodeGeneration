package io.swagger.validator;

import io.swagger.api.exceptions.EntityAlreadyExistException;
import io.swagger.api.exceptions.InvalidPermissionsException;
import io.swagger.api.exceptions.ValidationException;
import io.swagger.enums.Roles;
import io.swagger.model.Entity.UserEntity;
import io.swagger.model.User;
import io.swagger.repository.IUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class Validator {

    @Autowired
    IUserDTO userDTO;

    public boolean containsWhiteSpace(final String testCode){
        if(testCode != null){
            for(int i = 0; i < testCode.length(); i++){
                if(Character.isWhitespace(testCode.charAt(i))){
                    return true;
                }
            }
        }
        return false;
    }

    public void CanCreateUser(String username, String email, String password, Long dayLimit, long transactionLimit, String name){
        if(email.isEmpty() || name.isEmpty() || password.isEmpty() || username.isEmpty())
            throw new ValidationException("Missing content");

        if(containsWhiteSpace(email) || containsWhiteSpace(username) || containsWhiteSpace(password))
            throw new ValidationException("No white Spaces!");

        UserEntity userExist = userDTO.findByUsername(username);
        if(userExist != null)
            throw new EntityAlreadyExistException("Username already exist in the database");

        if(dayLimit < 0)
            throw new ValidationException("day limit has to be positive");

        if(transactionLimit < 0)
            throw new ValidationException("transaction limit has to be positive");
    }

    public void NeedsToBeEmployee(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userDTO.findByUsername(userDetails.getUsername());

        if(!user.getRole().equals(Roles.EMPLOYEE))
            throw new InvalidPermissionsException("no permissions to access this");
    }
}
