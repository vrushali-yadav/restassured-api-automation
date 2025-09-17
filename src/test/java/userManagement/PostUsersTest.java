package userManagement;

import core.StatusCode;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojo.City;
import pojo.PostRequestBody;
import utils.PropertyReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class PostUsersTest {

    private static String apiKey;
    private static FileInputStream fileInputStream;

    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = PropertyReader.propertyReader("config.properties", "base.url");
        apiKey = PropertyReader.propertyReader("config.properties", "api.key");
    }

    private static FileInputStream fileInputStreamMethod(String requestBodyFileName) {
        try {
            fileInputStream = new FileInputStream(
                    new File(System.getProperty("user.dir") + "/resources/TestData/" + requestBodyFileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return fileInputStream;
    }

    @Test
    public void testPostRequestBodyAsString() {
        Response response = given()
                .header("Content-Type", "application/json")
                .header("x-api-key", apiKey)
                .body("{\"name\":\"morpheus\",\"job\":\"leader\"}")
                .when()
                .post("/users/2");

        assertEquals(response.getStatusCode(), StatusCode.CREATED.code);
        System.out.println(response.getBody().asString());
        System.out.println("testPostRequestBodyAsString executed successfully");
    }

    @Test
    public void testPutRequestBodyAsString() {
        Response response = given()
                .header("Content-Type", "application/json")
                .header("x-api-key", apiKey)
                .body("{\"name\":\"morpheus\",\"job\":\"test engineer\"}")
                .when()
                .put("/users/2");

        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println(response.getBody().asString());
        System.out.println("testPutRequestBodyAsString executed successfully");
    }

    @Test
    public void testPatchRequestBodyAsString() {
        Response response = given()
                .header("Content-Type", "application/json")
                .header("x-api-key", apiKey)
                .body("{\"name\":\"Scott\"}")
                .when()
                .patch("/users/2");

        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println(response.getBody().asString());
        System.out.println("testPatchRequestBodyAsString executed successfully");
    }

    @Test
    public void testPostRequestBodyAsJsonFile() throws IOException {
        Response response = given()
                .header("Content-Type", "application/json")
                .header("x-api-key", apiKey)
                .body(IOUtils.toString(fileInputStreamMethod("postRequestBody.json")))
                .when()
                .post("/users/2");

        assertEquals(response.getStatusCode(), StatusCode.CREATED.code);
        System.out.println(response.getBody().asString());
        System.out.println("testPostRequestBodyAsJsonFile executed successfully");
    }

    @Test
    public void testPutRequestBodyAsJsonFile() throws IOException {
        Response response = given()
                .header("Content-Type", "application/json")
                .header("x-api-key", apiKey)
                .body(IOUtils.toString(fileInputStreamMethod("putRequestBody.json")))
                .when()
                .put("/users/2");

        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println(response.getBody().asString());
        System.out.println("testPutRequestBodyAsJsonFile executed successfully");
    }

    @Test
    public void testPatchRequestBodyAsJsonFile() throws IOException {
        Response response = given()
                .header("Content-Type", "application/json")
                .header("x-api-key", apiKey)
                .body(IOUtils.toString(fileInputStreamMethod("patchRequestBody.json")))
                .when()
                .put("/users/2");

        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println(response.getBody().asString());
        System.out.println("testPatchRequestBodyAsJsonFile executed successfully");
    }

    @Test
    public void testPostRequestBodyAsPojo() {
        PostRequestBody requestBody = new PostRequestBody();
        requestBody.setName("morpheus");
        requestBody.setJob("leader");

        Response response = given()
                .header("Content-Type", "application/json")
                .header("x-api-key", apiKey)
                .body(requestBody)
                .when()
                .post("/users/2");

        assertEquals(response.getStatusCode(), StatusCode.CREATED.code);
        System.out.println(response.getBody().asString());
        System.out.println("testPostRequestBodyAsPojo executed successfully");
    }

    @Test
    public void testPostRequestBodyWithListAsPojo() {
        PostRequestBody requestBody = new PostRequestBody();
        requestBody.setName("morpheus");
        requestBody.setJob("leader");

        List<String> languages = new ArrayList<>(Arrays.asList("Python", "Java", "C#"));
        languages.add("C++");
        requestBody.setLanguages(languages);

        Response response = given()
                .header("Content-Type", "application/json")
                .header("x-api-key", apiKey)
                .body(requestBody)
                .when()
                .post("/users/2");

        assertEquals(response.getStatusCode(), StatusCode.CREATED.code);
        System.out.println(response.getBody().asString());
        System.out.println("testPostRequestBodyWithListAsPojo executed successfully");
    }

    @Test
    public void testPostRequestBodyWithListObjectAsPojo() {
        PostRequestBody requestBody = new PostRequestBody();
        requestBody.setName("morpheus");
        requestBody.setJob("leader");

        List<String> languages = new ArrayList<>(Arrays.asList("Python", "Java", "C#"));
        languages.add("C++");
        requestBody.setLanguages(languages);

        City city1 = new City();
        city1.setName("Pune");
        city1.setTemperature(37);
        City city2 = new City();
        city2.setName("Mumbai");
        city2.setTemperature(40);
        City city3 = new City();
        city3.setName("Satara");
        city3.setTemperature(28);

        List<City> cities = new ArrayList<>(Arrays.asList(city1, city2, city3));
        requestBody.setCities(cities);

        Response response = given()
                .header("Content-Type","application/json")
                .header("x-api-key", apiKey)
                .body(requestBody)
                .when()
                .post("/users/2");

        assertEquals(response.getStatusCode(),StatusCode.CREATED.code);
        System.out.println(response.getBody().asString());
        System.out.println("testPostRequestBodyWithListAsPojo executed successfully");
    }

    @Test
    public void testPutRequestBodyAsPojo(){
        PostRequestBody requestBody = new PostRequestBody();
        requestBody.setName("morpheus");
        requestBody.setJob("test engineer");

        Response response = given()
                .header("Content-Type","application/json")
                .header("x-api-key", apiKey)
                .body(requestBody)
                .when()
                .put("/users/2");

        assertEquals(response.getStatusCode(),StatusCode.SUCCESS.code);
        System.out.println(response.getBody().asString());
        System.out.println("testPutRequestBodyAsPojo executed successfully");
    }

    @Test
    public void testPatchRequestBodyAsPojo(){
        PostRequestBody requestBody = new PostRequestBody();
        requestBody.setName("david");

        Response response = given()
                .header("Content-Type","application/json")
                .header("x-api-key", apiKey)
                .body(requestBody)
                .when()
                .patch("/users/2");

        assertEquals(response.getStatusCode(),StatusCode.SUCCESS.code);
        System.out.println(response.getBody().asString());
        System.out.println("testPatchRequestBodyAsPojo executed successfully");
    }
}
