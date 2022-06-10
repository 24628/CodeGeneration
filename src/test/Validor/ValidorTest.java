//package Validor;
//
//import io.swagger.Swagger2SpringBoot;
//import io.swagger.api.exceptions.*;
//import io.swagger.enums.Roles;
//import io.swagger.model.Entity.AccountEntity;
//import io.swagger.model.Entity.UserEntity;
//import io.swagger.model.Request.RegisterRequest;
//import io.swagger.repository.IUserDTO;
//import io.swagger.service.AccountService;
//import io.swagger.service.auth.RegisterService;
//import io.swagger.validator.Validator;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.UUID;
//
//import static org.junit.Assert.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.BDDMockito.willReturn;
//import static org.mockito.Mockito.verify;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {Swagger2SpringBoot.class, AccountService.class}, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//public class ValidorTest {
//
//    @Mock
//    private IUserDTO iUserDTO;
//
//    @InjectMocks
//    private Validator validator;
//    private AccountService accountService;
//    private RegisterService registerService;
//    private RegisterRequest registerRequest;
//
//    private AccountEntity accountEntity;
//    private UserEntity userEntity;
//
//
//    @Before
//    public void setupMock() {
//        userEntity = UserEntity.builder()
//                .uuid(UUID.randomUUID())
//                .dayLimit(2000L)
//                .email("")
//                .name("john")
//                .username("johndoe")
//                .password("password")
//                .pinCode(1234)
//                .role(Roles.EMPLOYEE)
//                .transactionLimit(2000L)
//                .build();
//
//        given(iUserDTO.save(userEntity)).willReturn(userEntity);
//
//        accountEntity = AccountEntity.builder()
//                .uuid(UUID.randomUUID())
//                .IBAN("NL01INHO0000000001")
//                .userId(userEntity.getUuid())
//                .build();
//
//        given(iUserDTO.save(userEntity)).willReturn(userEntity);
//
//
//        registerRequest = new RegisterRequest();
//        registerRequest.setEmail(userEntity.getEmail());
//        registerRequest.setName(userEntity.getName());
//        registerRequest.setDayLimit(userEntity.getDayLimit());
//        registerRequest.setUsername(userEntity.getUsername());
//
//    }
//
//
//    @Test
//    public void ContainsWhiteSpaceShouldReturnTrue() {
//
//        org.junit.jupiter.api.Assertions.assertThrows(ValidationException.class, () -> {
//            validator.CanCreateUser("John doe ", "Johndoe@email.com", "password", 50L, 20L, "johndoe");
//        });
//    }
//
//
//    @Test
//    public void CanCreateUserShouldThrowValidationErrorMissingContent() {
//
//        org.junit.jupiter.api.Assertions.assertThrows(ValidationException.class, () -> {
//            validator.CanCreateUser("", "Johndoe@email.com", "password", 50L, 20L, "johndoe");
//        });
//    }
//
//    @Test
//    public void ShouldThrowUsernameAlreadyExitstIntheDatabaseError() {
//
//        given(iUserDTO.findByUsername(userEntity.getUsername()))
//                .willReturn(userEntity);
//
//        org.junit.jupiter.api.Assertions.assertThrows(EntityAlreadyExistException.class, () -> {
//            validator.CanCreateUser("johndoe", "Johndoe@email.com", "password", 50L, 20L, "johndoe");
//        });
//    }
//
//    @Test
//    public void ShouldThrowValidationExceptionDayLimitHasToBePositive() {
//        given(iUserDTO.findByUsername(userEntity.getUsername()))
//                .willReturn(userEntity);
//
//        org.junit.jupiter.api.Assertions.assertThrows(DayLimitReachedException.class, () -> {
//            validator.CheckDayLimit(userEntity,"NL01INHO0000000001", 5000L);
//        });
//    }
//
//    @Test
//    public void ShouldThrowTransActionlimitHasToBePositive() {
//        given(iUserDTO.findByUsername(userEntity.getUsername()))
//                .willReturn(userEntity);
//
//        org.junit.jupiter.api.Assertions.assertThrows(DayLimitReachedException.class, () -> {
//            validator.CheckDayLimit(userEntity,"NL01INHO0000000001", 5000L);
//        });
//
//
//    }
//
//    @Test
//    public void cannotFindAccountByIbanShouldReturnNull() {
//
//    }
//
//    @Test
//    public void CantFindUserEntityShouldThrowValidationExceptionError() {
//
//    }
//
//    @Test
//    public void CantFindAccountEntityShouldThrowValidationException() {
//
//    }
//
//
//
//    @Test
//    public void ShouldThrowDaylimitReachedException() {
//
//    }
//
//}
//
