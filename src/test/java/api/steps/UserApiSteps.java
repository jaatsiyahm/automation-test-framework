package api.steps;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class UserApiSteps {

    private static String baseUrl;
    private static String appId;
    private static String userId;
    private static Response response;
    private static String createdUserId;
    private static String lastUsedEmail;

    // ====== BACKGROUND ======

    @Given("API base URL {string}")
    public void setBaseUrl(String url) {
        baseUrl = url;
        RestAssured.baseURI = url;
    }

    @Given("API using app-id {string}")
    public void setAppId(String id) {
        appId = id;
    }

    // ====== GIVEN ======

    @Given("I have user ID {string}")
    public void setUserId(String id) {
        userId = id;
    }

    @Given("I have ID of last user that just created")
    public void useCreatedUserId() {
        userId = (createdUserId != null) ? createdUserId : "60d0fe4f5311236168a109ca";
    }

    @Given("I have new user info:")
    public void setNewUserData(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<String, String> data = rows.get(0);

        lastUsedEmail = "testuser." + System.currentTimeMillis() + "@example.com";

        String body = String.format(
                "{\"firstName\":\"%s\",\"lastName\":\"%s\",\"email\":\"%s\"}",
                data.get("firstName"),
                data.get("lastName"),
                lastUsedEmail
        );
        ApiContext.setRequestBody(body);
        System.out.println("Request body: " + body);
    }

    @Given("I have user with used email")
    public void setDuplicateUserData() {
        String email = (lastUsedEmail != null) ? lastUsedEmail : "duplicate@example.com";
        String body = String.format(
                "{\"firstName\":\"Duplicate\",\"lastName\":\"User\",\"email\":\"%s\"}", email
        );
        ApiContext.setRequestBody(body);
        System.out.println("Duplicate request body: " + body);
    }

    @Given("I have user with used email:")
    public void setUpdateData(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<String, String> data = rows.get(0);
        String body = String.format(
                "{\"firstName\":\"%s\",\"lastName\":\"%s\"}",
                data.get("firstName"),
                data.get("lastName")
        );
        ApiContext.setRequestBody(body);
        System.out.println("Update body: " + body);
    }

    // ====== WHEN ======

    @When("i run GET request to endpoint {string}")
    public void doGetRequest(String endpoint) {
        String url = baseUrl + endpoint.replace("{id}", userId != null ? userId : "");
        System.out.println("GET " + url);

        response = RestAssured.given()
                .header("app-id", appId)
                .header("Content-Type", "application/json")
                .get(url);

        System.out.println("Status: " + response.getStatusCode());
        System.out.println("Body: " + response.getBody().asString());
    }

    @When("i run POST request to endpoint {string}")
    public void doPostRequest(String endpoint) {
        String url = baseUrl + endpoint;
        System.out.println("POST " + url);
        System.out.println("Body: " + ApiContext.getRequestBody());

        response = RestAssured.given()
                .header("app-id", appId)
                .header("Content-Type", "application/json")
                .body(ApiContext.getRequestBody())
                .post(url);

        System.out.println("Status: " + response.getStatusCode());
        System.out.println("Body: " + response.getBody().asString());

        if (response.getStatusCode() == 200) {
            createdUserId = response.jsonPath().getString("id");
            lastUsedEmail = response.jsonPath().getString("email");
            System.out.println("Created ID: " + createdUserId);
        }
    }

    @When("I run PUT request to endpoint {string}")
    public void doPutRequest(String endpoint) {
        String url = baseUrl + endpoint.replace("{id}", userId != null ? userId : "");
        System.out.println("PUT " + url);

        response = RestAssured.given()
                .header("app-id", appId)
                .header("Content-Type", "application/json")
                .body(ApiContext.getRequestBody())
                .put(url);

        System.out.println("Status: " + response.getStatusCode());
        System.out.println("Body: " + response.getBody().asString());
    }

    @When("I run DELETE request to endpoint {string}")
    public void doDeleteRequest(String endpoint) {
        String url = baseUrl + endpoint.replace("{id}", userId != null ? userId : "");
        System.out.println("DELETE " + url);

        response = RestAssured.given()
                .header("app-id", appId)
                .delete(url);

        System.out.println("Status: " + response.getStatusCode());
        System.out.println("Body: " + response.getBody().asString());
    }

    // ====== THEN ======

    @Then("response status code should be {int}")
    public void validateStatusCode(int expected) {
        Assert.assertEquals(
                "Status code mismatched! Expected: " + expected + ", Actual: " + response.getStatusCode(),
                expected,
                response.getStatusCode()
        );
    }

    @Then("response body should contain field {string}")
    public void validateFieldExists(String field) {
        Object value = response.jsonPath().get(field);
        Assert.assertNotNull("Field '" + field + "' non exist!", value);
    }

    @Then("response body field {string} should filled with {string}")
    public void validateFieldValue(String field, String expected) {
        String actual = response.jsonPath().getString(field);
        Assert.assertEquals("Field '" + field + "' wrong!", expected, actual);
    }

    @Then("response body field {string} should be in array type")
    public void validateFieldIsArray(String field) {
        Object value = response.jsonPath().get(field);
        Assert.assertTrue("Field '" + field + "' not array!", value instanceof List);
    }
}