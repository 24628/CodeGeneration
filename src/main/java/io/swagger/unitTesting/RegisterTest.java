package io.swagger.unitTesting;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.api.RegisterApiController;
import io.swagger.model.Entity.UserEntity;
import static org.assertj.core.api.Assertions.assertThat;
import io.swagger.model.RegisterBody;
import io.swagger.responses.JwtTokenResponse;
import io.swagger.service.auth.RegisterService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.platform.runner.JUnitPlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class RegisterTest {

    @InjectMocks
    RegisterApiController registerApiController;

    @Mock
    RegisterService registerService;

    @Test
    public void testRegister()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(registerService.register(any(RegisterBody.class)));//.thenReturn(true);

        RegisterBody body = new RegisterBody("username", "name", "email@email.com", "password", 2000L);
        ResponseEntity<JwtTokenResponse> responseEntity = registerApiController.registerPost(body);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
//        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/1");
    }

}