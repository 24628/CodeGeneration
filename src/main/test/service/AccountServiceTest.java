package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.Swagger2SpringBoot;
import io.swagger.enums.AccountType;
import io.swagger.enums.Roles;
import io.swagger.jwt.JwtTokenProvider;
import io.swagger.model.Entity.AccountEntity;
import io.swagger.model.Entity.UserEntity;
import io.swagger.model.Request.AccountRequest;
import io.swagger.repository.IAccountDTO;
import io.swagger.repository.IUserDTO;
import io.swagger.service.AccountService;
import io.swagger.service.UserService;
import io.swagger.validator.Validator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Swagger2SpringBoot.class, AccountService.class}, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class AccountServiceTest {

    @MockBean
    public AccountService AccountService;

    @MockBean
    public UserService userService;

    @MockBean
    private IAccountDTO accountRepository;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private AuthenticationManager authenticationManagerBean;

    @MockBean
    private SecurityContextHolder securityContextHolder;

    @MockBean
    private ObjectMapper objectMapper;

    @MockBean
    private IUserDTO userDTO;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    Validator validator;
    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addAccount() {
        assertEquals(23,44);
    }

    @Test
    @WithMockUser(username = "johndoe", roles = "EMPLOYEE")
    @WithUserDetails("johndoe")
    public void getAccounts() {
        AccountRequest acc = new AccountRequest();
        acc.setType(AccountType.NORMAL.toString());
        acc.setAbsoluteLimit(0L);
        acc.setIBAN("NL01INHO0000000001");
        acc.setUserId("test");

        try {
            AccountEntity accountEntity = this.AccountService.addAccount(acc);
            System.out.println(accountEntity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(this.AccountService.getAccounts());

        assertEquals(1,this.AccountService.getAccounts().size());
    }

    @Test
    public void getAccountByIBAN() {
    }

    @Test
    public void getAccountByUserId() {
    }

    @Test
    public void updateAccountByIBAN() {
    }

    @Test
    public void generateAccount() {
    }
}