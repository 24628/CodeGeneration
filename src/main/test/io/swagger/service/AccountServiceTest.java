package io.swagger.service;

import io.swagger.Swagger2SpringBoot;
import io.swagger.api.RegisterApiController;
import io.swagger.enums.Roles;
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
@AutoConfigureMockMvc
public class AccountServiceTest {

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        mockUserDetails();
    }

    @Test
    public void mockUserDetails() {

    }

    @Autowired
    private AccountService accountService;

    @Test
    public void addAccount() {
        UserDetails applicationUser = mock(UserDetails.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(applicationUser);
        IUserDTO userDTO = mock(IUserDTO.class);
        UserEntity userEntity = mock(UserEntity.class);
        when(userDTO.findByUsername(userEntity.getUsername())).thenReturn(userEntity);
        when(userEntity.getRole()).thenReturn(Roles.EMPLOYEE);
        accountService.getAccounts();
        assertEquals(23,44);
    }

    @Test
    public void getAccounts() {
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

    @org.junit.Test
    public void generateAccount() {
    }
}