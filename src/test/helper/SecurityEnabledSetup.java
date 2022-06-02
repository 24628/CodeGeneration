package helper;

import io.swagger.configuration.WebSecurityConfig;
import io.swagger.service.MyUserDetailsService;
import io.swagger.service.auth.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.context.WebApplicationContext;
import springfox.documentation.swagger.web.SecurityConfiguration;

@Import(SecurityConfiguration.class)
public abstract class SecurityEnabledSetup {

    @MockBean
    protected UserDetailsService userDetailsService;

    @MockBean
    protected WebSecurityConfig webSecurityConfig;

    @MockBean
    protected MyUserDetailsService myUserDetailsService;

    @Autowired
    WebApplicationContext context;
}
