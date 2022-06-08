package steps;


import io.swagger.Swagger2SpringBoot;
import io.swagger.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = CucumberContextConfig.class)
@Slf4j
public class BaseStepDefinitions {

    @LocalServerPort
    private int port;

    @Value("${baseurl}")
    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl + port;
    }
}
