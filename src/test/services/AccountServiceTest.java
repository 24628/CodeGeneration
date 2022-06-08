package services;

import io.swagger.Swagger2SpringBoot;
import io.swagger.api.exceptions.EntityAlreadyExistException;
import io.swagger.api.exceptions.EntityNotFoundException;
import io.swagger.api.exceptions.UserNotFoundException;
import io.swagger.api.exceptions.ValidationException;
import io.swagger.enums.AccountType;
import io.swagger.enums.Roles;
import io.swagger.helpers.OffsetPageableUUID;
import io.swagger.model.Entity.AccountEntity;
import io.swagger.model.Entity.UserEntity;
import io.swagger.model.Request.AccountRequest;
import io.swagger.repository.IAccountDTO;
import io.swagger.repository.IUserDTO;
import io.swagger.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Swagger2SpringBoot.class, AccountService.class}, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class AccountServiceTest {
    @Mock
    private IAccountDTO iAccountDTO;
    @Mock
    private IUserDTO iUserDTO;
    @InjectMocks
    private AccountService accountService;
    private AccountEntity accountEntity;
    private AccountEntity accountEntity2;
    private AccountEntity accountEntity3;
    private AccountRequest accountRequest;
    private AccountRequest accountRequest2;
    private AccountRequest accountRequestUpdate;
    private AccountRequest accountRequestUpdate1;
    private AccountRequest accountRequestUpdate2;
    private UserEntity userEntity;

    @Before
    public void setupMock() {
        userEntity = UserEntity.builder()
                .uuid(UUID.randomUUID())
                .dayLimit(2000L)
                .email("johndoe@example.com")
                .name("john")
                .username("johndoe")
                .password("password")
                .pinCode(1234)
                .role(Roles.EMPLOYEE)
                .transactionLimit(2000L)
                .build();

        accountEntity = AccountEntity.builder()
                .IBAN("testIban123")
                .uuid(UUID.randomUUID())
                .absoluteLimit(10L)
                .balance(1000L)
                .type(AccountType.NORMAL)
                .userId(userEntity.getUuid())
                .build();

        accountEntity2 = AccountEntity.builder()
                .IBAN("testIban123")
                .uuid(UUID.randomUUID())
                .absoluteLimit(-10L)
                .balance(1000L)
                .type(AccountType.NORMAL)
                .userId(userEntity.getUuid())
                .build();

        accountEntity3 = AccountEntity.builder()
                .IBAN("testIban123")
                .uuid(UUID.randomUUID())
                .absoluteLimit(10L)
                .balance(1000L)
                .type(AccountType.SAVING)
                .userId(userEntity.getUuid())
                .build();


        accountRequest = new AccountRequest();
        accountRequest.setIban(accountEntity.getIBAN());
        accountRequest.setAbsoluteLimit(accountEntity.getAbsoluteLimit());
        accountRequest.setType(String.valueOf(accountEntity.getType()));
        accountRequest.setUserId(String.valueOf(accountEntity.getUserId()));

        accountRequest2 = new AccountRequest();
        accountRequest2.setIban(accountEntity2.getIBAN());
        accountRequest2.setAbsoluteLimit(accountEntity2.getAbsoluteLimit());
        accountRequest2.setType(String.valueOf(accountEntity2.getType()));
        accountRequest2.setUserId(String.valueOf(accountEntity2.getUserId()));

        accountRequestUpdate = new AccountRequest();
        accountRequestUpdate.setIban(accountEntity2.getIBAN());
        accountRequestUpdate.setAbsoluteLimit(2000L);
        accountRequestUpdate.setType(String.valueOf(AccountType.SAVING));
        accountRequestUpdate.setUserId(String.valueOf(accountEntity2.getUserId()));

        accountRequestUpdate1 = new AccountRequest();
        accountRequestUpdate1.setIban(accountEntity2.getIBAN());
        accountRequestUpdate1.setAbsoluteLimit(-10L);
        accountRequestUpdate1.setType(String.valueOf(accountEntity2.getType()));
        accountRequestUpdate1.setUserId(String.valueOf(accountEntity2.getUserId()));

        accountRequestUpdate2 = new AccountRequest();
        accountRequestUpdate2.setIban(accountEntity2.getIBAN());
        accountRequestUpdate2.setAbsoluteLimit(accountEntity2.getAbsoluteLimit());
        accountRequestUpdate2.setType(String.valueOf(AccountType.ATM));
        accountRequestUpdate2.setUserId(String.valueOf(accountEntity2.getUserId()));
    }

    @Test
    public void CanSaveAnAccountObject() {
        given(iAccountDTO.getAccountByIBAN(accountEntity.getIBAN()))
                .willReturn(null);

        given(iAccountDTO.save(accountEntity)).willReturn(accountEntity);

        AccountEntity savedAccount = accountService.addAccount(accountRequest);

        assertNotNull(savedAccount);
    }

    @Test
    public void SaveAccountObjectShouldThrowValidationError(){
        given(iAccountDTO.getAccountByIBAN(accountEntity.getIBAN()))
                .willReturn(null);

        given(iAccountDTO.save(accountEntity2)).willReturn(accountEntity2);

        org.junit.jupiter.api.Assertions.assertThrows(ValidationException.class, () -> {
            accountService.addAccount(accountRequest2);
        });

        verify(iAccountDTO, never()).save(any(AccountEntity.class));
    }

    @Test
    public void ShouldThrowAccountEntityAlreadyExistCauseCanNotHaveMultipleNormalAccounts(){
        given(iAccountDTO.getAllByUserId(userEntity.getUuid()))
                .willReturn(List.of(accountEntity,accountEntity3));

        org.junit.jupiter.api.Assertions.assertThrows(EntityAlreadyExistException.class, () -> {
            accountService.addAccount(accountRequest2);
        });

        verify(iAccountDTO, never()).save(any(AccountEntity.class));
    }

    @Test
    public void ShouldGetAllAccounts(){
        Integer limit = 10;
        Integer offset = 0;
        given(iAccountDTO.getAllByTypeIsNot(AccountType.ATM, new OffsetPageableUUID(limit,offset)))
                .willReturn(List.of(accountEntity,accountEntity3));

        List<AccountEntity> accountEntityList = accountService.getAccounts(offset, limit);

        // then - verify the output
        assertNotNull(accountEntityList);
    }

    @Test
    public void getAccountByIBAN(){
        given(iAccountDTO.getAccountByIBAN(accountEntity.getIBAN()))
                .willReturn(accountEntity);

        AccountEntity acc = accountService.getAccountByIBAN(accountEntity.getIBAN());

        assertNotNull(acc);
    }

    @Test
    public void cannotFindAccountByIbanShouldReturnNull(){
        given(iAccountDTO.getAccountByIBAN(accountEntity.getIBAN()))
                .willReturn(null);

        AccountEntity acc = accountService.getAccountByIBAN("deze random string");

        assertNull(acc);
    }

    @Test
    public void updateAccountByIBAN(){
        given(iAccountDTO.getAccountByIBAN(accountEntity.getIBAN()))
                .willReturn(accountEntity);

        AccountEntity updatedAccount = accountService.updateAccountByIBAN(accountRequestUpdate, accountEntity.getIBAN());

        assertEquals(Optional.of(2000L), Optional.of(updatedAccount.getAbsoluteLimit()));
    }

    @Test
    public void updateAccountByIBANAbsoluteLimitHasToBeHigher(){
        given(iAccountDTO.getAccountByIBAN(accountEntity.getIBAN()))
                .willReturn(accountEntity);

        org.junit.jupiter.api.Assertions.assertThrows(ValidationException.class, () -> {
            accountService.updateAccountByIBAN(accountRequestUpdate1, accountEntity.getIBAN());
        });

        verify(iAccountDTO, never()).save(any(AccountEntity.class));
    }

    @Test
    public void updateAccountByIBANCanNotBeAtm(){
        given(iAccountDTO.getAccountByIBAN(accountEntity.getIBAN()))
                .willReturn(accountEntity);

        org.junit.jupiter.api.Assertions.assertThrows(ValidationException.class, () -> {
            accountService.updateAccountByIBAN(accountRequestUpdate2, accountEntity.getIBAN());
        });

        verify(iAccountDTO, never()).save(any(AccountEntity.class));
    }

    @Test
    public void findAccountByUserName(){
        given(iUserDTO.findByUsername(userEntity.getUsername()))
                .willReturn(userEntity);

        given(iAccountDTO.getAccountEntityByUserIdAndTypeIsNot(userEntity.getUuid(), AccountType.SAVING))
                .willReturn(accountEntity);

        AccountEntity account = accountService.findAccountByUserName(userEntity.getUsername());

        assertNotNull(account);
    }

    @Test
    public void findAccountByUserNameShouldThrowUserNotFound(){
        given(iUserDTO.findByUsername(userEntity.getUsername()))
                .willReturn(userEntity);

        given(iAccountDTO.getAccountEntityByUserIdAndTypeIsNot(userEntity.getUuid(), AccountType.SAVING))
                .willReturn(accountEntity);

        org.junit.jupiter.api.Assertions.assertThrows(UserNotFoundException.class, () -> {
            accountService.findAccountByUserName(userEntity.getName());
        });
    }

    @Test
    public void findAccountByUserNameShouldThrowEntityNotFound(){
        given(iUserDTO.findByUsername(userEntity.getUsername()))
                .willReturn(userEntity);

        given(iAccountDTO.getAccountEntityByUserIdAndTypeIsNot(userEntity.getUuid(), AccountType.SAVING))
                .willReturn(null);

        org.junit.jupiter.api.Assertions.assertThrows(EntityNotFoundException.class, () -> {
            accountService.findAccountByUserName(userEntity.getUsername());
        });
    }

}
