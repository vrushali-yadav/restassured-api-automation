package userManagement;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

public class JSONPlaceholderApiTest {

    @Test(groups = {"SmokeSuite", "RegressionSuite"})
    public void validateGetResponseBody() {
        // Set API base URI
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Send GET request and validate response body data
        given()
                .when()
                .get("/todos/1")
                .then()
                .assertThat()
                .statusCode(200)
                .body(not(isEmptyString()))
                .body("title", equalTo("delectus aut autem"))
                .body("userId", equalTo(1));
    }

    @Test
    public void validateGetResponseBodyWithMatchers() {
        // Set base URI
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Send a GET request and extract response and save it in a variable
        Response response = given()
                .when().get("/users/2")
                .then()
                .extract()
                .response();

        // Validate response with different Hamcrest matchers
        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.getBody().asString(), not(isEmptyString()));
        response.then().body("email", containsString("@melissa.tv"));
    }

    @Test
    public void validateResponseWithHasItems() {
        // Set base URI
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Send GET request, extract a response and save it in a variable
        Response response = given()
                .when()
                .get("/posts")
                .then()
                .extract()
                .response();

        // Check that response contains the mentioned items using Hamcrest
        assertThat(response.jsonPath().getList("title"),
                hasItems("eum et est occaecati",
                        "qui est esse",
                        "magnam facilis autem"));
    }

    @Test
    public void validateResponseWithHasSize() {
        // Set base URI
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Send GET request, extract a response and save it in a variable
        Response response = given()
                .when()
                .get("/posts")
                .then()
                .extract()
                .response();

        // Check that response body contains specified size using Hamcrest
        assertThat(response.jsonPath().getList(""), hasSize(100));
    }

    @Test
    public void validateListContainsInSpecificOrder() {
        // Set base URI
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Send GET request, extract response and save it in a variable
        Response response = given()
                .when()
                .get("/comments?postId=1")
                .then()
                .extract()
                .response();

        // Check response body contains specified items in specified order
        List<String> expectedEmails = Arrays.asList("Eliseo@gardner.biz", "Jayne_Kuhic@sydney.com",
                "Nikita@garfield.biz", "Lew@alysha.tv", "Hayden@althea.biz");

        //System.out.println(response.jsonPath().get("email").toString());
        assertThat(response.jsonPath().getList("email"), contains(expectedEmails.toArray(new String[0])));
    }

    @Test
    public void validateListContainsInAnyOrder() {
        // Set base URI
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Send GET request, extract response and save it in a variable
        Response response = given()
                .when()
                .get("/comments?postId=1")
                .then()
                .extract()
                .response();

        // Check response body contains specified items in specified order
        List<String> expectedEmails = Arrays.asList("Nikita@garfield.biz", "Lew@alysha.tv",
                "Eliseo@gardner.biz", "Hayden@althea.biz", "Jayne_Kuhic@sydney.com");

        //System.out.println(response.jsonPath().get("email").toString());
        assertThat(response.jsonPath().getList("email"), containsInAnyOrder(expectedEmails.toArray(new String[0])));
    }
}
