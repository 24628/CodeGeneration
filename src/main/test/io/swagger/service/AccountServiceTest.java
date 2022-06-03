package io.swagger.service;

import io.swagger.Swagger2SpringBoot;
import io.swagger.api.RegisterApiController;
import io.swagger.enums.AccountType;
import io.swagger.enums.Roles;
import io.swagger.model.Account;
import io.swagger.model.Entity.AccountEntity;
import io.swagger.model.Entity.UserEntity;
import io.swagger.repository.IUserDTO;
import io.swagger.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Swagger2SpringBoot.class,AccountService.class}, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class AccountServiceTest {


    @MockBean
    public AccountService AccountService;

    @MockBean
    public UserService userService;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
    }




    @Test
    public void addAccount() {
        assertEquals(23,44);
    }

    @Test
    public void getAccounts() {
//        UserEntity userEntity = new UserEntity();
//        userEntity.setUsername("admin");
//        userEntity.setEmail("admin@example.com");
//        userEntity.setPassword("$2a$12$PDMzF/Zq9t6M.guuRiN5pevmQtcaG6wMv9wWvZJaFwylap9FYb7Tu"); //password
//        userEntity.setRole(Roles.BANK);
//        userEntity.setTransaction_limit(0L);
//
//        userService.generateUsers(userEntity);

        Account acc = new Account();
        acc.setType(AccountType.NORMAL.toString());
        acc.setAbsoluteLimit(0L);
        acc.setIBAN("NL01INHO0000000001");
        acc.setUserId("test");


        this.AccountService.addAccount(acc);

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