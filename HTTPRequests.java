package com.qa.tests;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import org.testng.annotations.Test;

public class HTTPRequests {
    int id = 100; // Use a unique pet ID for demos

    @Test(priority = 1)
    public void createUser() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", "Doggie");
        map.put("status", "available");

        given()
            .contentType("application/json")
            .body(map)
        .when()
            .post("https://petstore.swagger.io/v2/pet")
        .then()
            .statusCode(200)
            .body("name", equalTo("Doggie"))
            .body("status", equalTo("available"))
            .log().all();
    }

    @Test(priority = 3)
    public void getUser() {
        given()
        .when()
            .get("https://petstore.swagger.io/v2/pet/" + id)
        .then()
            .statusCode(200)
            .body("id", equalTo(id))
            .log().all();
    }

    @Test(priority = 2,dependsOnMethods= {"createUser"})
    public void updateUser() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", "DoggieUpdated");
        map.put("status", "sold");

        given()
            .contentType("application/json")
            .body(map)
        .when()
            .put("https://petstore.swagger.io/v2/pet")
        .then()
            .statusCode(200)
            .body("name", equalTo("DoggieUpdated"))
            .body("status", equalTo("sold"))
            .log().all();
    }

    @Test(priority = 4)
    public void deleteUser() {
        given()
        .when()
            .delete("https://petstore.swagger.io/v2/pet/" + id)
        .then()
            .statusCode(200)
            .body("message", equalTo(Integer.toString(id)))
            .log().all();
    }
}
