package userManagement;

import core.StatusCode;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
import utils.JsonReader;

import java.io.IOException;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class PostmanEchoApiTest {

    @Test(groups = "SmokeSuite")
    public void validateWithTestDataFromJson() throws IOException, ParseException {
        String userName = JsonReader.getTestData("username");
        String password = JsonReader.getTestData("password");
        System.out.println("username from json file is : " + userName + "\npassword from json file is : " + password);

        Response response = given()
                .auth()
                .basic(userName, password)
                .when()
                .get("https://postman-echo.com/basic-auth");

        int actualStatusCode = response.getStatusCode();
        assertEquals(actualStatusCode, StatusCode.SUCCESS.code);
        System.out.println("validateWithTestDataFromJson executed successfully");
    }

    @Test(groups = {"SmokeSuite", "RegressionSuite"})
    public void validateGetResponseBodyWithBasicAuth() {
        Response response = given()
                .auth()
                .basic("postman", "password")
                .when()
                .get("https://postman-echo.com/basic-auth");

        int actualStatusCode = response.getStatusCode();
        assertEquals(actualStatusCode, StatusCode.SUCCESS.code);
        System.out.println(response.getBody().asString());
        System.out.println("validateGetResponseBodyWithBasicAuth executed successfully");
    }

    @Test(groups = {"SmokeSuite", "RegressionSuite"})
    public void validateGetResponseBodyWithDigestAuth() {
        Response response = given()
                .auth()
                .digest("postman", "password")
                .when()
                .get("https://postman-echo.com/digest-auth");

        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println(response.getBody().asString());
        System.out.println("validateGetResponseBodyWithDigestAuth executed successfully");
    }

    @Test(groups = "RegressionSuite")
    public void testFetchCookies() {
        Response response = given()
                .when()
                .get("https://postman-echo.com/cookies")
                .then()
                .extract().response();

        Map<String, String> cookies = response.getCookies();
        //Cookies cookies1 = response.getDetailedCookies();
        //System.out.println(cookies);
        //System.out.println(cookies1);

        for(Map.Entry<String, String> cookie : cookies.entrySet()) {
            System.out.println(cookie.getKey() + " : " + cookie.getValue());
        }
        System.out.println("testFetchCookies executed successfully");
    }
}
