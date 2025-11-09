package com.api.base;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class BaseService {

	private static final String BASE_URL = "http://64.227.160.186:8080";

	protected RequestSpecification requestSpecification;

	public BaseService() {
		requestSpecification = given().baseUri(BASE_URL);
	}

	// POST method using default BASE_URL
	protected Response postRequest(Object payLoad, String endPoint) {
		return requestSpecification.contentType(ContentType.JSON).body(payLoad).post(endPoint);
	}

	// POST method with custom base URL (useful for multi-environment testing)
	protected Response postRequest(String baseURL, Object payLoad, String endPoint) {
		return given().baseUri(baseURL).contentType(ContentType.JSON).body(payLoad).post(endPoint);
	}

	protected Response gettRequest(String endPoint) {
		return requestSpecification.get(endPoint);
	}

	public Response getRequest(String endPoint) {
		return requestSpecification.get(endPoint);
	}

	public void setAuthToken(String token) {
		requestSpecification.header("Authorization", "Bearer " + token);
	}

}
