package steps;

import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java8.En;
import io.cucumber.java8.PendingException;
import io.swagger.model.Request.LoginRequest;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import steps.SpringIntegrationTest;

import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MyStepdefs  extends SpringIntegrationTest implements En {

    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    private ResponseEntity<String> response;
    private LoginRequest loginRequest;

    public MyStepdefs() {

        Given("^I have a valid user object$", () -> {
            loginRequest = new LoginRequest();
            loginRequest.setUsername("jan");
            loginRequest.setPassword("password");
            throw new PendingException();
        });
        When("^I call the login endpoint$", () -> {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Type", "application/json");

            HttpEntity<String> request = new HttpEntity<String>(mapper.writeValueAsString(
                    loginRequest),
                    httpHeaders);
            response = restTemplate.postForEntity(getBaseUrl() + "/login",
                    request, String.class);
        });
        Then("^I receive a status of (\\d+)$", (Integer status) -> {
            Assertions.assertEquals(Optional.ofNullable(status), Optional.of(response.getStatusCodeValue()));
        });

        And("^I get a JWT-token$", () -> {
            JSONObject jsonObject = new JSONObject(response.getBody());
            String token = jsonObject.getString("token");
            Assertions.assertTrue(token.startsWith("ey"));
        });
    }
}
