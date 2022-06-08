package steps;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@CucumberContextConfiguration
@SpringBootTest
public class SpringIntegrationTest {

    @LocalServerPort
    private int port;
    // executeGet implementation

    private String base = "http://localhost:";

    public String getBaseUrl () {
        return base + port;
    }
}
