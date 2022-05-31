package io.swagger.validator;

import io.swagger.api.exceptions.EntityAlreadyExistException;
import io.swagger.api.exceptions.ValidationException;
import io.swagger.model.Entity.UserEntity;
import io.swagger.model.User;
import io.swagger.repository.IUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void CanCreateUser(String name, String email, String password, Long dayLimit, long transactionLimit){
        if(email.isEmpty() || name.isEmpty() || password.isEmpty())
            throw new ValidationException("Missing content");

        if(containsWhiteSpace(email) || containsWhiteSpace(name) || containsWhiteSpace(password))
            throw new ValidationException("No white Spaces!");

        UserEntity userExist = userDTO.findByUsername(name);
        if(userExist != null)
            throw new EntityAlreadyExistException("Username already exist in the database");

        if(dayLimit < 0)
            throw new ValidationException("day limit has to be positive");

        if(transactionLimit < 0)
            throw new ValidationException("transaction limit has to be positive");
    }
}
