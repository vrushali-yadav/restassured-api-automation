package userManagement;

import core.StatusCode;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojo.BookStoreRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ApiChainingTest {

    // Step 1 - generate auth token using username and password.
    // Step 2 -  Get Books - No Auth is required for this.
    // Step 3 - Add a book - with Auth
    // The token we had saved in the variable before from response in Step 1,
    // we will be passing in the headers for each of the succeeding request
    // Step 4 - Delete a book - with Auth
    // Step 5 - Get User

    private final String contentType = "application/json";
    private final String userId = "6e3e20ef-8d98-4f25-9415-124248bcb7ec";
    private final String userName = "VrushaliTestAPI";
    private final String password = "Test@123";
    private String authToken;
    private String isbn;

    @Test
    public void generateAuthorizationToken() {
        BookStoreRequest requestBody = new BookStoreRequest();
        requestBody.setUserName(userName);
        requestBody.setPassword(password);

        RestAssured.baseURI = "https://demoqa.com";
        Response response = given()
                .header("Content-Type", contentType)
                .body(requestBody)
                .when()
                .post("Account/v1/GenerateToken");

        //System.out.println(response.getBody().asString());
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        authToken = response.getBody().jsonPath().get("token");
        //System.out.println(authToken);
        System.out.println("generateAuthorizationToken executed successfully");
    }

    @Test
    public void getBookStoreBooks() {
        RestAssured.baseURI = "https://demoqa.com";
        Response response = given()
                .header("Content-Type", contentType)
                .when()
                .get("/BookStore/v1/Books");

        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        List<Map<String, String>> books = response.getBody().jsonPath().get("books");
        assertTrue(books.size() > 0);
        isbn = books.get(0).get("isbn");
        System.out.println(books);
        System.out.println(isbn);
        System.out.println("getBookStoreBooks executed successfully");
    }

    @Test(dependsOnMethods = {"generateAuthorizationToken", "getBookStoreBooks"})
    public void addBookWithAuthToken() {
        RestAssured.baseURI = "https://demoqa.com";
        Response response = given()
                .header("Content-Type", contentType)
                .header("Authorization", "Bearer " + authToken)
                .body("{\"userId\":\"" + userId + "\",\"collectionOfIsbns\":[{\"isbn\":\"" + isbn + "\"}]}")
                .when()
                .post("/BookStore/v1/Books");

        System.out.println(response.getBody().asString());
        assertEquals(response.getStatusCode(), StatusCode.CREATED.code);
        System.out.println("addBookWithAuthToken executed successfully");
    }

    @Test(dependsOnMethods = {"addBookWithAuthToken"})
    public void deleteBookWithAuthToken() {
        RestAssured.baseURI = "https://demoqa.com";
        Response response = given()
                .header("Content-Type", contentType)
                .header("Authorization", "Bearer " + authToken)
                .body("{\"isbn\":\"" + isbn + "\",\"userId\":\"" + userId + "\"}")
                .when()
                .delete("/BookStore/v1/Book");

        assertEquals(response.getStatusCode(), StatusCode.NO_CONTENT.code);
        System.out.println("deleteBookWithAuthToken executed successfully");
    }

    @Test(dependsOnMethods = {"deleteBookWithAuthToken"})
    public void getUserWithUserId() {
        RestAssured.baseURI = "https://demoqa.com";
        Response response = given()
                .header("Content-Type", contentType)
                .header("Authorization", "Bearer " + authToken)
                .when()
                .get("/Account/v1/User/" + userId);

        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        List<Map<String, String>> books = response.getBody().jsonPath().get("books");
        assertEquals(books.size(), 0);
        System.out.println("getUserWithUserId executed successfully");
    }

}
