package io.swagger.configuration;

import io.swagger.enums.AccountType;
import io.swagger.enums.Roles;
import io.swagger.model.Entity.AccountEntity;
import io.swagger.model.Entity.DayLimitEntity;
import io.swagger.model.Entity.UserEntity;
import io.swagger.service.AccountService;
import io.swagger.service.DayLimitService;
import io.swagger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.*;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-04-26T09:18:21.534Z[GMT]")
@Configuration
public class SwaggerUiConfiguration implements WebMvcConfigurer {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private DayLimitService dayLimitService;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        generateData();

        registry.
            addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/swagger-ui/").setViewName("forward:/swagger-ui/index.html");
    }

    private void generateData(){
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("admin");
        userEntity.setEmail("admin@example.com");
        userEntity.setPassword("$2a$12$PDMzF/Zq9t6M.guuRiN5pevmQtcaG6wMv9wWvZJaFwylap9FYb7Tu"); //password
        userEntity.setRole(Roles.BANK);
        userEntity.setTransactionLimit(0L);

        userService.generateUsers(userEntity);

        DayLimitEntity dayLimit = new DayLimitEntity();
        dayLimit.setUserId(userEntity.getUuid());
        dayLimit.setActualLimit(2000L);
        dayLimit.setCurrent(0L);

        dayLimitService.save(dayLimit);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setBalance(0L);
        accountEntity.setType(AccountType.ATM);
        accountEntity.setAbsoluteLimit(0L);
        accountEntity.setUserId(userEntity.getUuid());
        accountEntity.setIBAN("NL01INHO0000000001");

        accountService.generateAccount(accountEntity);
    }
}
