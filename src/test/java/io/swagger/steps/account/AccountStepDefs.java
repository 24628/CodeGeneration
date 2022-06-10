package io.swagger.steps.account;

import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.swagger.model.Request.LoginRequest;
import io.swagger.steps.BaseStepDefinitions;
import io.swagger.steps.helper.CustomError;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;


public class AccountStepDefs extends BaseStepDefinitions {

    private LoginRequest loginRequest;
    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    private ResponseEntity<String> response;

    private String getAccountsToken;
    private String invalidJwtToken;
    private ResponseEntity<String> getAccountsResponse;
    private ResponseEntity<String> invalidAccountResponseForbidden;

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
    private ResponseEntity<String> callPostHttpHeaders(String token, String url) throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Authorization", "Bearer " + token);

        return new TestRestTemplate().exchange(
                getBaseUrl() + url, HttpMethod.POST, new HttpEntity<Object>(httpHeaders),
                String.class);
    }
    private ResponseEntity<String> callPutHttpHeaders(String token, String url) throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Authorization", "Bearer " + token);

        return new TestRestTemplate().exchange(
                getBaseUrl() + url, HttpMethod.PUT, new HttpEntity<Object>(httpHeaders),
                String.class);
    }



    @Given("I have a valid jwt token to get accounts by iban and permissions")
    public void iHaveAValidUserObject2() throws JSONException, JsonProcessingException {
        getAccountsToken = getJwtToken();
        Assertions.assertTrue(getJwtToken().startsWith("ey"));
    }

    @When("I call the account/IBAN/{IBAN} get endpoint")
    public void iCallTheAccountGetByIbanEndpoint() throws JsonProcessingException {
        getAccountsResponse = callGetHttpHeaders(getAccountsToken, "/accounts/iban/NL01INHO0000000001");
    }

    @And("I get an list of account back (saving or normal)")
    public void iGetAListOfAccountsBackByIban() throws JSONException {
        JSONObject jsonObject = new JSONObject(getAccountsResponse.getBody());
        String accountEntityList = jsonObject.getString("accountEntityList");
        System.out.println(accountEntityList);

        Assertions.assertTrue(accountEntityList.contains("iban"));
        Assertions.assertTrue(accountEntityList.contains("uuid"));
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
        System.out.println("test123");
        System.out.println(result.getMessage());
        Assertions.assertEquals("Token not authorised",result.getMessage());
    }

    @Given("I have a valid jwt token to create an account")
    public void iHaveAValidJwtTokenToCreateAnAccount() {
    }

    @When("I call the account post endpoint")
    public void iCallTheAccountPostEndpoint() {
    }

    @And("I will receive mine created account")
    public void iWillReceiveMineCreatedAccount() {
    }

    @Given("I have a valid jwt token to get accounts by IBAN and permissions")
    public void iHaveAValidJwtTokenToGetAccountsByIBANAndPermissions() {
    }

    @When("I call the account IBAN IBAN put endpoint")
    public void iCallTheAccountIBANIBANPutEndpoint() {
    }

    @And("I change the account data of the IBAN")
    public void iChangeTheAccountDataOfTheIBAN() {
    }

    @Given("I have a valid jwt token to get accounts by userId and permissions")
    public void iHaveAValidJwtTokenToGetAccountsByUserIdAndPermissions() {
    }

    @When("I call the account userid get endpoint")
    public void iCallTheAccountUseridGetEndpoint() {
    }

    @And("I get an list of account back saving or normal by userId")
    public void iGetAnListOfAccountBackSavingOrNormalByUserId() {
    }

    @When("I call the account IBAN IBAN get endpoint")
    public void iCallTheAccountIBANIBANGetEndpoint() {
    }

    @And("I get a single account using the IBAN")
    public void iGetASingleAccountUsingTheIBAN() {
    }
}
