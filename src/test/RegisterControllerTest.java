import com.fasterxml.jackson.databind.ObjectMapper;
import helper.SecurityEnabledSetup;
import io.swagger.api.RegisterApiController;
import io.swagger.enums.Roles;
import io.swagger.model.Entity.UserEntity;
import io.swagger.model.User;
import io.swagger.service.auth.RegisterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@SpringBootTest(classes = {RegisterApiController.class, ServletWebServerFactoryAutoConfiguration.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
public class RegisterControllerTest extends SecurityEnabledSetup {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegisterService registerService;

    @MockBean
    private ObjectMapper objectMapper;

    UserEntity user1 = new UserEntity(Roles.CUSTOMER, "johndoe", "john", "john@example.com", "$2a$12$PDMzF/Zq9t6M.guuRiN5pevmQtcaG6wMv9wWvZJaFwylap9FYb7Tu", 2000L);
    User user2 = new User("johndoe", "john", "johndoe@example.com", "password", 5000L);

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void registerUserWithSuccess() throws Exception {

        String json = "{\"username\":\"johndoe\",\"name\":\"john\",\"email\":\"johndoe@example.com\",\"password\":\"password\",\"dayLimit\":5000,}";

//        String content = mapper.writeValueAsString(user2);
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.userEntity.name", is("johndoe")));

    }
}
