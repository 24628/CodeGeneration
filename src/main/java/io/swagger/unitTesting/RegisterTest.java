package io.swagger.unitTesting;


import io.swagger.api.RegisterApiController;
import io.swagger.api.interfaces.RegisterApi;
import io.swagger.model.Account;
import io.swagger.model.RegisterBody;
import io.swagger.service.auth.RegisterService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest

@AutoConfigureMockMvc
public class RegisterTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RegisterApi registerApi;

    @BeforeEach
    public void init() throws Exception {
//        testUser = createMockUser();
//        testAccount = createMockAccount(testUser,testIban1, BigDecimal.valueOf(1000), AccountType.CURRENT);
//        testDestinationAccount = createMockAccount(testUser,testIban2, BigDecimal.valueOf(1000), AccountType.SAVINGS);
//
//        //        Login as test employee
//        MvcResult result = mvc.perform(post("/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"email_address\": \"testuser@example.com\", \"password\": \"secret\"}"))
//                .andExpect(status().isOk()
//                ).andReturn();
//        String contentAsString = result.getResponse().getContentAsString();
//
//        JwtResponse loginResponse = objectMapper.readValue(contentAsString, JwtResponse.class);
//
////        Store token
//        this.jwtToken = loginResponse.getToken();
    }

    @Test
    public void RegisterUserShouldReturnJwtToken() {
        RegisterBody registerBody = new RegisterBody();
        registerBody.setUsername("obamacare");
        registerBody.setEmail("obamacare@hotmail.com");
        registerBody.setPassword("password");
        registerBody.setDayLimit(20L);
        registerApiController.registerPost(registerBody);
    }
}
