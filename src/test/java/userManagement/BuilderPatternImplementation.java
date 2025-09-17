package userManagement;

import core.StatusCode;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.JsonReader;
import utils.PropertyReader;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BuilderPatternImplementation {

    private String baseUri;
    private String endpoint;
    private String contentType;
    private RequestSpecification requestSpecification;
    private ResponseSpecification responseSpecification;

    @BeforeClass
    public void setUp() throws IOException, ParseException {
        baseUri = PropertyReader.propertyReader("config.properties", "builder.pattern.test.base.url");
        endpoint = JsonReader.getTestData("builder pattern test endpoint");
        contentType = JsonReader.getTestData("content-type");
    }

    @Test
    public void restAssuredNormalApproachTest() {
        RestAssured.baseURI = baseUri;

        given()
                .header("Content-Type",contentType)
                .queryParam("userId","3")
                .when()
                .get(endpoint)
                .then()
                .assertThat()
                .statusCode(200)
                .body(not(isEmptyString()));
    }

    private RequestSpecification getRequestSpecificationBuilder(String contentType, String queryParam){
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setContentType(contentType)
                .addQueryParam("userId", queryParam)
                .build();
        return requestSpecification;
    }

    private ResponseSpecification getResponseSpecificationBuilder(int statusCode, String contentType){
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectContentType(contentType)
                .build();
        return responseSpecification;
    }

    @Test
    public void restAssuredBuilderPatternTest() {
        given()
                .spec(getRequestSpecificationBuilder(contentType, "3"))
                .when()
                .get(endpoint)
                .then()
                .spec(getResponseSpecificationBuilder(StatusCode.SUCCESS.code,contentType));
    }

}
