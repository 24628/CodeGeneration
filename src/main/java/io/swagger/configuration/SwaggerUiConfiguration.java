package io.swagger.configuration;

import io.swagger.enums.AccountType;
import io.swagger.enums.Roles;
import io.swagger.helpers.TestData;
import io.swagger.model.Entity.AccountEntity;
import io.swagger.model.Entity.DayLimitEntity;
import io.swagger.model.Entity.TransactionEntity;
import io.swagger.model.Entity.UserEntity;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.repository.IAccountDTO;
import io.swagger.repository.IUserDTO;
import io.swagger.service.AccountService;
import io.swagger.service.DayLimitService;
import io.swagger.service.TransactionService;
import io.swagger.service.UserService;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
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
    private TestData testData;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        generateData();
        testData.Generate();
        registry.
                addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/swagger-ui/").setViewName("forward:/swagger-ui/index.html");
    }

    private void generateData() {

//        DayLimitEntity dayLimit = new DayLimitEntity();
//        dayLimit.setUserId(userEntity.getUuid());
//        dayLimit.setActualLimit(2000L);
//        dayLimit.setCurrent(0L);
//        dayLimitService.save(dayLimit);

    }
}
