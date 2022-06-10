package io.swagger.steps.account;

import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.swagger.enums.AccountType;
import io.swagger.enums.Roles;
import io.swagger.model.Entity.AccountEntity;
import io.swagger.model.Entity.UserEntity;
import io.swagger.model.Request.AccountRequest;
import io.swagger.model.Request.LoginRequest;
import io.swagger.model.Responses.UserLoginEntity;
import io.swagger.repository.IAccountDTO;
import io.swagger.service.UserService;
import io.swagger.steps.BaseStepDefinitions;
import io.swagger.steps.helper.CustomError;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.UUID;


public class AccountStepDefs extends BaseStepDefinitions {

    private LoginRequest loginRequest;
    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    private ResponseEntity<String> response;

    private String getAccountsToken;
    private String invalidJwtToken;
    private ResponseEntity<String> getAccountsResponse;
    private ResponseEntity<String> invalidAccountResponseForbidden;
    private String userId;

    private String getJwtToken() throws JSONException, JsonProcessingException {
        loginRequest = new LoginRequest();
        loginRequest.setUsername("employee");
        loginRequest.setPassword("password");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<String>(mapper.writeValueAsString(
                loginRequest),
                httpHeaders);
        response = restTemplate.postForEntity(getBaseUrl() + "/login",
                request, String.class);

        JSONObject jsonObject = new JSONObject(response.getBody());
        userId = new JSONObject(jsonObject.getString("userEntity")).getString("userId");
        return jsonObject.getString("token");
    }

    private ResponseEntity<String> callGetHttpHeaders(String token, String url) throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Authorization", "Bearer " + token);

        return new TestRestTemplate().exchange(
                getBaseUrl() + url, HttpMethod.GET, new HttpEntity<Object>(httpHeaders),
                String.class);
    }

    @Given("I have a valid jwt token to get accounts and permissions")
    public void iHaveAValidUserObject() throws JSONException, JsonProcessingException {
        getAccountsToken = getJwtToken();
        Assertions.assertTrue(getJwtToken().startsWith("ey"));
    }

    @When("I call the account get endpoint")
    public void iCallTheAccountGetEndpoint() throws JsonProcessingException {
        getAccountsResponse = callGetHttpHeaders(getAccountsToken, "/accounts");
    }

    @Then("I receive a status of success of {int}")
    public void iReceiveAStatusOfSuccessOf(int status) {
        Assertions.assertEquals(status, getAccountsResponse.getStatusCodeValue());
    }

    @And("I get a list of accounts back")
    public void iGetAListOfAccountsBack() throws JSONException {
        JSONObject jsonObject = new JSONObject(getAccountsResponse.getBody());
        String accountEntityList = jsonObject.getString("accountEntityList");
        Assertions.assertTrue(accountEntityList.contains("iban"));
        Assertions.assertTrue(accountEntityList.contains("uuid"));
    }

    @Given("I dont have valid jwt token")
    public void iDontHaveValidJwtToken() {
        invalidJwtToken = "somerandombsthatsclearlynotatoken";
    }

    @When("When I call an endpoint with permissions")
    public void whenICallAnEndpointWithPermissions() throws JsonProcessingException {
        invalidAccountResponseForbidden = callGetHttpHeaders(invalidJwtToken, "/accounts");
    }

    @Then("I get a message forbidden to access")
    public void iGetAMessageForbiddenToAccess() throws JsonProcessingException {
        CustomError result = mapper.readValue(invalidAccountResponseForbidden.getBody(),
                CustomError.class);
        Assertions.assertEquals("Token not authorised",result.getMessage());
    }

    private String validJwtToken;
    private ResponseEntity<String> createAccountResponse;
    private AccountRequest accountRequestCreate;
    private AccountEntity createdAccountEntity;
    @Given("I have a valid jwt token to create new account")
    public void iHaveAValidJwtTokenToCreateNewAccount() throws JSONException, JsonProcessingException {
        validJwtToken = getJwtToken();
        Assertions.assertTrue(getJwtToken().startsWith("ey"));
    }

    @When("I call the account post endpoint")
    public void iCallTheAccountPostEndpoint() throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Authorization", "Bearer " + validJwtToken);

        String json = "{\n" +
                "  \"absoluteLimit\": 500,\n" +
                "  \"type\": \"NORMAL\",\n" +
                "  \"user_id\":\""+userId+"\"\n" +
                "}";
        HttpEntity<String> request = new HttpEntity<String>(json, httpHeaders);
        createAccountResponse = restTemplate.postForEntity(getBaseUrl() + "/accounts",
                request, String.class);
    }

    @Then("I receive a status of success of by new account {int}")
    public void iReceiveAStatusOfSuccessOfByNewAccount(int status) {
        Assertions.assertEquals(status, createAccountResponse.getStatusCodeValue());
    }

    @And("I will receive mine created account")
    public void iWillReceiveMineCreatedAccount() throws JsonProcessingException, JSONException {
        JSONObject jsonObject = new JSONObject(createAccountResponse.getBody());

        createdAccountEntity = mapper.readValue(jsonObject.getString("accountEntity"), AccountEntity.class);
        Assertions.assertEquals(createdAccountEntity.getUserId(), UUID.fromString(userId));
    }

    @Given("I have a valid jwt token to update account")
    public void iHaveAValidJwtTokenToUpdateAccount() throws JSONException, JsonProcessingException {
        validJwtToken = getJwtToken();
        Assertions.assertTrue(getJwtToken().startsWith("ey"));
    }

    @When("I call the account IBAN IBAN put endpoint")
    public void iCallTheAccountIBANIBANPutEndpoint() throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Authorization", "Bearer " + validJwtToken);

        String json = "{\n" +
                "  \"absoluteLimit\": 1000,\n" +
                "  \"type\": \"SAVING\",\n" +
                "}";

        HttpEntity<String> requestUpdate = new HttpEntity<>(json, httpHeaders);
        restTemplate.exchange(getBaseUrl() + "/accounts/IBAN/NL01INHO0000000002", HttpMethod.PUT, requestUpdate, Void.class);
    }

    @Then("Check the get endpoint if its updated")
    public void checkTheGetEndpointIfItsUpdated() throws JsonProcessingException, JSONException {
        ResponseEntity<String> res = callGetHttpHeaders(validJwtToken, "/accounts/IBAN/NL01INHO0000000002");
        JSONObject jsonObject = new JSONObject(res.getBody());

        AccountEntity AccE = mapper.readValue(jsonObject.getString("accountEntity"), AccountEntity.class);
        Assertions.assertEquals(AccE.getType(), AccountType.SAVING);
    }

    private  ResponseEntity<String> resGetByUserId;
    @Given("I have a valid jwt token to get a list of account by userid")
    public void iHaveAValidJwtTokenToGetAListOfAccountByUserid() throws JSONException, JsonProcessingException {
        validJwtToken = getJwtToken();
        Assertions.assertTrue(getJwtToken().startsWith("ey"));
    }
    @When("I call the account userid get endpoint")
    public void iCallTheAccountUseridGetEndpoint() throws JsonProcessingException {
        resGetByUserId = callGetHttpHeaders(validJwtToken, "/accounts/IBAN/" + userId);
    }

    @Then("I receive a status of success of by userid {int}")
    public void iReceiveAStatusOfSuccessOfByUserid(int status) {
        Assertions.assertEquals(status, resGetByUserId.getStatusCodeValue());
    }

    @And("I get an list of account back saving or normal by userId")
    public void iGetAnListOfAccountBackSavingOrNormalByUserId() throws JSONException {
        JSONObject jsonObject = new JSONObject(resGetByUserId.getBody());
        Assertions.assertTrue(jsonObject.toString().contains("accountEntity"));
    }

    private  ResponseEntity<String> resGetByUserIBAN;
    @Given("I have a valid jwt token get a single account based on iban")
    public void iHaveAValidJwtTokenGetASingleAccountBasedOnIban() throws JSONException, JsonProcessingException {
        validJwtToken = getJwtToken();
        Assertions.assertTrue(getJwtToken().startsWith("ey"));
    }

    @When("I call the account IBAN IBAN get endpoint")
    public void iCallTheAccountIBANIBANGetEndpoint() throws JsonProcessingException {
        resGetByUserIBAN = callGetHttpHeaders(validJwtToken, "/accounts/IBAN/NL01INHO0000000002");
    }

    @Then("I receive a status of success of by iban {int}")
    public void iReceiveAStatusOfSuccessOfByIban(int status) {
        Assertions.assertEquals(status, resGetByUserIBAN.getStatusCodeValue());
    }

    @And("I get a single account using the IBAN")
    public void iGetASingleAccountUsingTheIBAN() throws JSONException, JsonProcessingException {
        JSONObject jsonObject = new JSONObject(resGetByUserIBAN.getBody());
        createdAccountEntity = mapper.readValue(jsonObject.getString("accountEntity"), AccountEntity.class);
        Assertions.assertEquals(createdAccountEntity.getUserId(), UUID.fromString(userId));
    }
}
