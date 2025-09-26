package userManagement;

import core.StatusCode;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;
import utils.PropertyReader;

import java.io.File;

import static io.restassured.RestAssured.given;

public class JsonSchemaValidationTest {

    @Test
    public void validateJsonSchema() {

        String apiKey = PropertyReader.propertyReader("config.properties", "api.key");
        File expectedSchema = new File("resources/TestExpectedSchema.json");

        given()
                .header("x-api-key",apiKey)
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .assertThat()
                .statusCode(StatusCode.SUCCESS.code)
                .body(JsonSchemaValidator.matchesJsonSchema(expectedSchema));

        System.out.println("validateJsonSchema executed successfully");
    }
}
