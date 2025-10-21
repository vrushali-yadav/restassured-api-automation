package userManagement;

import core.BaseTest;
import core.StatusCode;
import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import utils.JsonReader;
import utils.PropertyReader;
import utils.RetryListener;
import utils.SoftAssertionUtil;

import java.io.IOException;
import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GetUsersTest extends BaseTest {

    @Test(groups = "SmokeSuite")
    public void getUserData() {
        // Send a GET request and validate status code
        given()
                .header("x-api-key", "reqres-free-v1")
                .when().get("https://reqres.in/api/users?page=2")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void getUserDataWithSingleQueryParameter() {
        // Set base URI
        RestAssured.baseURI = "https://reqres.in/api";

        // Send GET request with single query parameter and save response in a variable
        Response response = given()
                .header("x-api-key", "reqres-free-v1")
                .queryParam("page", 2)
                .when()
                .get("/users");

        // Check that status code is 200 and response contains 6 users
        assertThat(response.getBody().toString(), not(isEmptyString()));
        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.getBody().jsonPath().get("data"), hasSize(6));
    }

    @Test
    public void getUserDataWithMultipleQueryParameters() {
        // Set base URI
        RestAssured.baseURI = "https://reqres.in/api";

        // Send GET request with 2 query parameters and save response in a variable
        Response response = given()
                .header("x-api-key", "reqres-free-v1")
                .queryParam("page", 2)
                .queryParam("per_page", 3)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Check that response contains 3 users
        assertThat(response.jsonPath().get("data"), hasSize(3));

        // Validate the data of first user in the list
        response.then().body("data[0].id", is(4));
        response.then().body("data[0].email", is("eve.holt@reqres.in"));
        response.then().body("data[0].first_name", is("Eve"));
        response.then().body("data[0].last_name", is("Holt"));
        response.then().body("data[0].avatar", is("https://reqres.in/img/faces/4-image.jpg"));
    }

    @Test
    public void testUserDataWithPathParameter() {
        // Set base URI
        RestAssured.baseURI = "https://reqres.in/api";

        // Send GET request with path parameter and check the response body
        given()
                .header("x-api-key", "reqres-free-v1")
                .pathParam("userId", 4)
                .when()
                .get("/users/{userId}")
                .then()
                .assertThat()
                .statusCode(200)
                .body(not(isEmptyString()))
                .body("data.id", equalTo(4))
                .body("data.email", equalTo("eve.holt@reqres.in"))
                .body("data.first_name", equalTo("Eve"))
                .body("data.last_name", equalTo("Holt"));
    }

    @Test
    public void testPrintResponseInConsole() {
        // Set base URI
        RestAssured.baseURI = "https://reqres.in/api";

        // Send GET request with path parameter and check the response body
        Response response = given()
                .header("x-api-key", "reqres-free-v1")
                .pathParam("userId", 3)
                .when()
                .get("/users/{userId}");

        // Check that status code is 200
        assertThat(response.getStatusCode(), is(200));

        // Print response in console
        System.out.println(response.body().asString());
    }

    @Test
    public void testToCreateUserUsingFormParameter() {
        // Send POST request to create a user with form parameter and check the response body
        Response response = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", "Scott")
                .formParam("job", "API Developer")
                .when()
                .post("https://httpbin.org/post")
                .then()
                //.log().all()
                .statusCode(200)
                .extract()
                .response();

//  The https://reqres.in/api/users endpoint expects JSON, not application/x-www-form-urlencoded.
//  So using .formParam() with application/x-www-form-urlencoded is incorrect for this API.
//  Due to this, its giving 415 error code
//        Response response = given()
//                .header("x-api-key", "reqres-free-v1")
//                .contentType("application/x-www-form-urlencoded")
//                .formParam("name", "Scott")
//                .formParam("job", "API Developer")
//                .when()
//                .post("https://reqres.in/api/users")
//                .then()
//                .assertThat()
//                .statusCode(201)
//                .extract()
//                .response();

        // Validate response body fields
        response.then().body("form.name", equalTo("Scott"));
        response.then().body("form.job", equalTo("API Developer"));
    }

    @Test
    public void testGetUsersDataWithHeader() {
        given()
                .header("x-api-key", "reqres-free-v1")
                .header("Content-Type", "application/json")
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200);
        System.out.println("testGetUsersDataWithHeader is successfully executed");
    }

    @Test
    public void testWithMultipleHeaders() {
        given()
                .header("Content-Type", "application/json")
                .header("x-api-key", "reqres-free-v1")
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200);
        System.out.println("testGetUsersDataWithMultipleHeaders is successfully executed");
    }

    @Test
    public void testMultipleHeadersWithMap() {
        // Create a Map to store multiple headers
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("x-api-key", "reqres-free-v1");
        headers.put("Authorization", "bearer ywtefdu13tx4fdub1t3ygdxuy3gnx1iuwdheni1u3y4gfuy1t3bx");

        // Send a GET request with headers and validate status code
        given()
                .headers(headers)
                .when()
                .get("https://reqres.in/api/users?page=1")
                .then()
                .statusCode(200);

        System.out.println("testMultipleHeadersWithMap is successfully executed");
    }

    @Test
    public void testValidateResponseHeaders() {
        given()
                .header("x-api-key", "reqres-free-v1")
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .assertThat()
                .statusCode(200)
                .header("Content-Type", equalTo("application/json; charset=utf-8"))
                .header("Cache-Control", equalTo("max-age=14400"))
                .header("Server", equalTo("cloudflare"));

        System.out.println("testValidateResponseHeaders successfully executed");
    }

    @Test
    public void testFetchHeadersUsingHeaderClass() {
        // Send a GET request, extract and store the response in a variable
        Response response = given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .extract()
                .response();

        // Fetch headers from response
        Headers headers = response.getHeaders();

        for (Header header : headers) {
            if (header.getName().equals("Server")) {
                System.out.println(header.getName() + " : " + header.getValue());
                System.out.println("testFetchHeadersUsingHeaderClass successfully executed");
            }
        }

    }

    @Test
    public void testCookies() {
        // Set base URI
        RestAssured.baseURI = "https://httpbin.org";

        // Create a map to store cookies
        Map<String, String> cookies = new HashMap<>();
        cookies.put("Cookie1", "Value1");
        cookies.put("Cookie2", "Value2");

        // Send a GET request with cookies and validate response code and cookies.
        given()
                .cookies(cookies)
                .when()
                .get("/cookies")
                .then()
                .statusCode(200)
                .body("cookies.Cookie1", equalTo("Value1"))
                .body("cookies.Cookie2", equalTo("Value2"));

        System.out.println("testCookies executed successfully");
    }

    @Test
    public void testWithCookieBuilder() {
        Cookie myCookie = new Cookie.Builder("myCookieName", "myCookieValue")
                .setComment("using cookie key and value")
                .build();

        given()
                .header("x-api-key", "reqres-free-v1")
                .cookie(myCookie)
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .assertThat()
                .statusCode(200)
                .body(not(isEmptyString()));

        System.out.println("testWithCookieBuilder executed successfully");
    }

    @Test
    public void validateDeleteStatusCode() {
        RestAssured.baseURI = "https://reqres.in";

        Response response = given()
                .header("x-api-key", "reqres-free-v1")
                .when()
                .delete("/api/users/2");

        assertEquals(response.getStatusCode(), StatusCode.NO_CONTENT.code);
        System.out.println("validateDeleteStatusCode executed successfully");
    }

    @Test(groups = "RegressionSuite")
    public void validateWithPropertiesFileData() {
        String serverAddress = PropertyReader.propertyReader("config.properties", "server.address");
        System.out.println(serverAddress);

        Response response = given()
                .header("x-api-key", "reqres-free-v1")
                .when()
                .get(serverAddress);

        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println("validateWithPropertiesFileData executed successfully");
    }

    @Test(groups = {"RegressionSuite", "SmokeSuite"})
    public void validateWithTestData_PropertiesFileData() throws IOException, ParseException {
        String baseURL = PropertyReader.propertyReader("config.properties", "base.url");
        String endpoint = JsonReader.getTestData("endpoint");
        String URL = baseURL + endpoint;
        System.out.println("server address is : " + URL);

        Response response = given()
                .header("x-api-key", "reqres-free-v1")
                .when()
                .get(URL);

        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println("validateWithTestData_PropertiesFileData executed successfully");
    }

    @Test
    public void softAssertion() {
        SoftAssert softAssertion = new SoftAssert();
        System.out.println("Soft Assertion");
        softAssertion.assertTrue(true, "");
        softAssertion.assertAll();
        System.out.println("softAssertion executed successfully");
    }

    @Test
    public void hardAssertion() {
        System.out.println("Hard Assertion");
        assertTrue(true);
        System.out.println("hardAssertion executed successfully");
    }

    @Test
    public void validateWithSoftAssertUtil() {
       // SoftAssertionUtil softAssertion = new SoftAssertionUtil();
        RestAssured.baseURI = "https://reqres.in/api";
        Response response = given()
                .queryParam("page", 2)
                .when()
                .get("/users");

        SoftAssertionUtil.assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code, "Status code is not 200");
        SoftAssertionUtil.assertAll();
        System.out.println("validateWithSoftAssertUtil executed successfully");
    }

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {"1", "John"},
                {"2", "Scott"},
                {"3", "Bob"},
                {"4", "Smith"}
        };
    }

    @Test(dataProvider = "testData")
    @Parameters({"id", "name"})
    public void testEndpoint(String id, String name) {
        String apiKey = PropertyReader.propertyReader("config.properties", "api.key");
        Response response = given()
                .queryParam("id", id)
                .queryParam("name", name)
                .header("x-api-key", apiKey)
                .when()
                .get("https://reqres.in/api/users");

        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
    }

    @Test
    public void testJsonArrayData() {
        System.out.println(JsonReader.getJsonArrayData("languages", 2));
        System.out.println(JsonReader.getJsonArrayData("technologies", 1));
        JSONArray jsonArray = JsonReader.getJsonArray("contact");

        // Approach 1
//        for(Object value : jsonArray) {
//            System.out.println(value);
//        }

        // Approach 2
        Iterator<String> iterator = jsonArray.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }

}
