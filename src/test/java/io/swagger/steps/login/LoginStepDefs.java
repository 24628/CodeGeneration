package io.swagger.steps.login;

import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.swagger.model.Request.LoginRequest;
import io.swagger.steps.BaseStepDefinitions;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberContextConfiguration
public class LoginStepDefs extends BaseStepDefinitions {

    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    private ResponseEntity<String> response;
    private LoginRequest loginRequest;

    @Given("I have a valid user object")
    public void iHaveAValidUserObject() {
        loginRequest = new LoginRequest();
        loginRequest.setUsername("jan");
        loginRequest.setPassword("password");
    }

    @When("I call the login endpoint")
    public void iCallTheLoginEndpoint() throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<String>(mapper.writeValueAsString(
                loginRequest),
                httpHeaders);
        response = restTemplate.postForEntity(getBaseUrl() + "/login",
                request, String.class);
    }

    @Then("I receive a status of {int}")
    public void iReceiveAStatusOf(int status) {
        Assertions.assertEquals(status, response.getStatusCodeValue());
    }

    @And("I get a JWT-token")
    public void iGetAJWTToken() throws JSONException {
        JSONObject jsonObject = new JSONObject(response.getBody());
        String token = jsonObject.getString("token");
        Assertions.assertTrue(token.startsWith("ey"));
    }
}
