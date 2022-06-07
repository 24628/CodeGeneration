package io.swagger.validator;

import io.swagger.api.exceptions.DayLimitReachedException;
import io.swagger.api.exceptions.EntityAlreadyExistException;
import io.swagger.api.exceptions.InvalidPermissionsException;
import io.swagger.api.exceptions.ValidationException;
import io.swagger.enums.Roles;
import io.swagger.helpers.ValidateAtmHelper;
import io.swagger.model.Entity.TransactionEntity;
import io.swagger.model.Request.AtmRequest;
import io.swagger.model.Entity.AccountEntity;
import io.swagger.model.Entity.UserEntity;
import io.swagger.repository.IAccountDTO;
import io.swagger.repository.ITransactionDTO;
import io.swagger.repository.IUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Component
public class Validator {

    @Autowired
    IUserDTO userDTO;

    @Autowired
    private IAccountDTO accountRepository;

    @Autowired
    ITransactionDTO transactionDTO;



    public boolean containsWhiteSpace(final String testCode) {
        if (testCode != null) {
            for (int i = 0; i < testCode.length(); i++) {
                if (Character.isWhitespace(testCode.charAt(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    public void CanCreateUser(String username, String email, String password, Long dayLimit, long transactionLimit, String name) throws ValidationException, EntityAlreadyExistException {
        if (email.isEmpty() || name.isEmpty() || password.isEmpty() || username.isEmpty())
            throw new ValidationException("Missing content");

        if (containsWhiteSpace(email) || containsWhiteSpace(username) || containsWhiteSpace(password))
            throw new ValidationException("No white Spaces!");

        UserEntity userExist = userDTO.findByUsername(username);
        if (userExist != null)
            throw new EntityAlreadyExistException("Username already exist in the database");

        if (dayLimit < 0)
            throw new ValidationException("day limit has to be positive");

        if (transactionLimit < 0)
            throw new ValidationException("transaction limit has to be positive");
    }

    public void NeedsToBeEmployee() throws InvalidPermissionsException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userDTO.findByUsername(userDetails.getUsername());

        if (!user.getRole().equals(Roles.EMPLOYEE))
            throw new InvalidPermissionsException("no permissions to access this");
    }

    public ValidateAtmHelper isAllowedToAtm(AtmRequest body){

        AccountEntity accountEntity = accountRepository.getAccountByIBAN(body.getIban());
        UserEntity userEntity = userDTO.findUserEntityByUuid(accountEntity.getUserId());

        if (userEntity == null)
            throw new ValidationException("invalid pincode");

        if (accountEntity == null)
            throw new ValidationException("invalid pincode");

        if (!userEntity.getPinCode().equals( body.getPinCode()))
            throw new ValidationException("invalid pincode entered");

        return new ValidateAtmHelper(userEntity, accountEntity);
    }

    public void CheckDayLimit(UserEntity user, Long amount) {
        long limit = 0;
        List<TransactionEntity> transactions = transactionDTO.getAllByAccountFromAndDate(
                user.getUuid(),
                LocalDateTime.from(LocalDate.now(ZoneId.of("Europe/Paris")).atStartOfDay(ZoneId.of("Europe/Paris")))
        );
        for (TransactionEntity transaction : transactions) {
            limit += transaction.getAmount();
        }

        Long dayLimit = user.getDayLimit() - limit;
        if (amount > dayLimit) {
            throw new DayLimitReachedException(dayLimit);
        }
    }
}
