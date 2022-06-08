package Validor;

import io.swagger.Swagger2SpringBoot;
import io.swagger.api.exceptions.*;
import io.swagger.enums.AccountType;
import io.swagger.enums.Roles;
import io.swagger.helpers.OffsetPageableUUID;
import io.swagger.model.Entity.AccountEntity;
import io.swagger.model.Entity.UserEntity;
import io.swagger.model.Request.AccountRequest;
import io.swagger.model.Request.RegisterRequest;
import io.swagger.repository.IAccountDTO;
import io.swagger.repository.IUserDTO;
import io.swagger.service.AccountService;
import io.swagger.service.auth.RegisterService;
import io.swagger.validator.Validator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Swagger2SpringBoot.class, AccountService.class}, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ValidorTest {


    @Mock
    private IUserDTO iUserDTO;

    @InjectMocks
    private Validator validator;
    private AccountService accountService;
    private RegisterService registerService;
    private RegisterRequest registerRequest;
    private RegisterRequest registerRequest2;
    private RegisterRequest registerRequest3;

    private UserEntity userEntity;


    @Before
    public void setupMock() {
        userEntity = UserEntity.builder()
                .uuid(UUID.randomUUID())
                .dayLimit(2000L)
                .email("")
                .name("john")
                .username("johndoe")
                .password("password")
                .pinCode(1234)
                .role(Roles.EMPLOYEE)
                .transactionLimit(2000L)
                .build();

        given(iUserDTO.save(userEntity)).willReturn(userEntity);


        registerRequest = new RegisterRequest();
        registerRequest.setEmail(userEntity.getEmail());
        registerRequest.setName(userEntity.getName());
        registerRequest.setDayLimit(userEntity.getDayLimit());
        registerRequest.setUsername(userEntity.getUsername());

    }

    // werkt
    @Test
    public void ContainsWhiteSpaceShouldReturnTrue() {

        org.junit.jupiter.api.Assertions.assertThrows(ValidationException.class, () -> {
            validator.CanCreateUser("", "Johndoe@email.com", "password", 50L, 20L, "johndoe");
        });
    }

    // werkt
    @Test
    public void CanCreateUserShouldThrowValidationErrorMissingContent() {

        org.junit.jupiter.api.Assertions.assertThrows(ValidationException.class, () -> {
            validator.CanCreateUser("John doe", "Johndoe@email.com", "password", 50L, 20L, "johndoe");
        });
    }

    @Test // done
    public void ShouldThrowUsernameAlreadyExitstIntheDatabaseError() {

        given(iUserDTO.findByUsername(userEntity.getUsername()))
                .willReturn(userEntity);

        org.junit.jupiter.api.Assertions.assertThrows(ValidationException.class, () -> {
            validator.CanCreateUser("Johndoe", "Johndoe@email.com", "password", 50L, 20L, "johndoe");
        });
    }

    @Test
    public void ShouldThrowValidationExceptionDayLimitHasToBePositive() {
//        given(iUserDTO.findByUsername(userEntity.getUsername()))
//                .willReturn(userEntity);
//        System.out.println(userEntity.getUsername());
//        org.junit.jupiter.api.Assertions.assertThrows(DayLimitReachedException.class, () -> {
//            validator.CheckDayLimit(userEntity, 5000L);
//        });
    }

    @Test
    public void ShouldThrowTransActionlimitHasToBePositive() {
//        given(iAccountDTO.getAccountByIBAN(accountEntity.getIBAN()))
//                .willReturn(accountEntity);
//
//        AccountEntity acc = accountService.getAccountByIBAN(accountEntity.getIBAN());
//
//        assertNotNull(acc);
    }

    @Test
    public void cannotFindAccountByIbanShouldReturnNull() {
//        given(iAccountDTO.getAccountByIBAN(accountEntity.getIBAN()))
//                .willReturn(null);
//
//        AccountEntity acc = accountService.getAccountByIBAN("deze random string");
//
//        assertNull(acc);
    }

    @Test
    public void CantFindUserEntityShouldThrowValidationExceptionError() {
//        given(iAccountDTO.getAccountByIBAN(accountEntity.getIBAN()))
//                .willReturn(accountEntity);
//
//        AccountEntity updatedAccount = accountService.updateAccountByIBAN(accountRequestUpdate, accountEntity.getIBAN());
//
//        assertEquals(Optional.of(2000L), Optional.of(updatedAccount.getAbsoluteLimit()));
    }

    @Test
    public void CantFindAccountEntityShouldThrowValidationException() {
//        given(iAccountDTO.getAccountByIBAN(accountEntity.getIBAN()))
//                .willReturn(accountEntity);
//
//        org.junit.jupiter.api.Assertions.assertThrows(ValidationException.class, () -> {
//            accountService.updateAccountByIBAN(accountRequestUpdate1, accountEntity.getIBAN());
//        });
//
//        verify(iAccountDTO, never()).save(any(AccountEntity.class));
    }

//    @Test
//    public void PincodeIncorrectShouldThrowIncorrectPincodeException() {
//        given(iUserDTO.findUserEntitiesByPinCode(userEntity4.getPinCode()))
//                .willReturn(userEntity4);
//
//        Assertions.assertEquals(userEntity4.getPinCode(), registerRequest3);
//    }


    @Test
    public void ShouldThrowDaylimitReachedException() {
//        given(iUserDTO.findByUsername(userEntity.getUsername()))
//                .willReturn(userEntity);
//
//        given(iAccountDTO.getAccountEntityByUserIdAndTypeIsNot(userEntity.getUuid(), AccountType.SAVING))
//                .willReturn(accountEntity);
//
//        AccountEntity account = accountService.findAccountByUserName(userEntity.getUsername());
//
//        assertNotNull(account);
    }

}

