package com.qa.tests;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

/**
 * This class demonstrates different ways to send JSON data with Rest Assured to an open API (JSONPlaceholder).
 * Each method shows a different technique to construct the JSON request body.
 */
public class PostRequestBody {

    // 1. POST using HashMap as the request body
    @Test
    public void usingHashMap() {
        // Prepare data using a Java HashMap (keys will map to JSON fields)
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "john");
        map.put("location", "UK");
        map.put("phone", "123");
        String CourseArr[] = { "Java", "C" }; // Array for courses field
        map.put("courses", CourseArr);

        // Build and send the POST request, validate response fields
        given()
            .contentType("application/json")
            .body(map)
        .when()
            .post("https://jsonplaceholder.typicode.com/posts")
        .then()
            .statusCode(201) // Created
            .body("name", equalTo("john"))
            .body("location", equalTo("UK"))
            .body("phone", equalTo("123"))
            .body("courses[0]", equalTo("Java"))
            .body("courses[1]", equalTo("C"))
            .log().all();
    }

    // 2. POST using org.json.JSONObject as the request body
    @Test
    public void usingJSONObject() {
        // Prepare data using org.json.JSONObject (explicit JSON construction)
        JSONObject data = new JSONObject();
        data.put("name", "john");
        data.put("location", "UK");
        data.put("phone", "123");
        String[] courses = {"Java", "C"};
        data.put("courses", courses);

        // Send POST with body as JSON string
        given()
            .contentType("application/json")
            .body(data.toString())
        .when()
            .post("https://jsonplaceholder.typicode.com/posts")
        .then()
            .statusCode(201)
            .body("name", equalTo("john"))
            .body("location", equalTo("UK"))
            .body("phone", equalTo("123"))
            .body("courses[0]", equalTo("Java"))
            .body("courses[1]", equalTo("C"))
            .log().all();
    }

    // 3. POST using a POJO (Plain Old Java Object)
    @Test
    public void usingPOJO() {
        // Prepare a custom POJO and set its properties
        PostRequestPOJO data = new PostRequestPOJO();
        data.setName("john");
        data.setLocation("UK");
        data.setPhone("123");
        String courseArr[] = {"Java","C"};
        data.setCourse(courseArr);

        // Automatically serialized to JSON by Rest Assured
        given()
            .contentType("application/json")
            .body(data)
        .when()
            .post("https://jsonplaceholder.typicode.com/posts")
        .then()
            .statusCode(201)
            .body("name", equalTo("john"))
            .body("location", equalTo("UK"))
            .body("phone", equalTo("123"))
            .body("course[0]", equalTo("Java"))
            .body("course[1]", equalTo("C"))
            .log().all();
    }

    // 4. POST using an external JSON file (body.json must be in the project's root directory)
    //@Test
    public void usingJSONFile() throws FileNotFoundException {
        // Read JSON data from a file, and send as raw JSON
        File file = new File(".\\body.json");
        FileReader fr = new FileReader(file);
        JSONTokener jt = new JSONTokener(fr);
        JSONObject data = new JSONObject(jt);

        given()
            .contentType("application/json")
            .body(data.toString())
        .when()
            .post("https://jsonplaceholder.typicode.com/posts")
        .then()
            .statusCode(201)
            .body("name", equalTo("john"))
            .body("location", equalTo("UK"))
            .body("phone", equalTo("123"))
            .body("courses[0]", equalTo("Java"))
            .body("courses[1]", equalTo("C"))
            .log().all();
    }

    // 5. DELETE example (deletes post with id 1)
    @Test
    public void testDelete() {
        int id = 1; // ID to delete (demo only, JSONPlaceholder always "deletes" successfully)
        given()
        .when()
            .delete("https://jsonplaceholder.typicode.com/posts/" + id)
        .then()
            .statusCode(200); // JSONPlaceholder always returns 200 for DELETE
    }
}

/*
POJO class required for usingPOJO() method:

public class PostRequestPOJO {
    private String name;
    private String location;
    private String phone;
    private String[] courses;

    // getters & setters for all fields

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String[] getCourses() { return courses; }
    public void setCourses(String[] courses) { this.courses = courses; }
}
*/

