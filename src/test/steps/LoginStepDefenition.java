import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

public class LoginStepDefenition {
    @Given("I have a valid user object")
    public void iHaveAValidUserObject() {
    }

    @When("I call the login endpoint")
    public void iCallTheLoginEndpoint() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<String>(mapper.writeValueAsString(
                dto),
                httpHeaders);
        response = restTemplate.postForEntity(getBaseUrl() + "/login",
                request, String.class);
    }

    @Then("I receive a status of {int}")
    public void iReceiveAStatusOf(int arg0) {
        Assertions.assertEquals(status, response.getStatusCodeValue());
    }

    @And("I get a JWT-token")
    public void iGetAJWTToken() {
        JSONObject jsonObject = new JSONObject(response.getBody());
        String token = jsonObject.getString("token");
        Assertions.assertTrue(token.startsWith("ey"));
    }

    @Given("I have an invalid user object")
    public void iHaveAnInvalidUserObject() {
        dto = new LoginDTO("user", "password");
    }
}
