package com.qa.tests;

import com.qa.base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * Suite de ejemplo contra la API pública https://reqres.in
 * Reemplaza estos tests por los de tu servicio real una vez
 * que el proyecto esté importado y corriendo en IntelliJ.
 */
public class SampleApiTest extends BaseTest {

    @Test(description = "Verifica que se pueda obtener la lista de usuarios")
    public void testGetUsersList() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get("/api/users?page=2")
                .then()
                .statusCode(200)
                .body("page", equalTo(2))
                .extract().response();

        Assert.assertTrue(response.jsonPath().getList("data").size() > 0,
                "La lista de usuarios no debería estar vacía");
    }

    @Test(description = "Verifica la obtención de un usuario único por ID")
    public void testGetSingleUser() {
        given()
                .spec(requestSpec)
                .when()
                .get("/api/users/2")
                .then()
                .statusCode(200)
                .body("data.id", equalTo(2));
    }

    @Test(description = "Verifica la creación de un usuario")
    public void testCreateUser() {
        String requestBody = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

        given()
                .spec(requestSpec)
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("leader"));
    }
}
