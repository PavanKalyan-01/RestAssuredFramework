package com.qa.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.base.AuthService;
import com.api.models.request.SignUpRequest;

import io.restassured.response.Response;

public class AccountCreationTest {
	
	
	@Test
	public void accountCreationtest() {
		
		SignUpRequest signUpRequest = new  SignUpRequest.Builder()
		.userName("pavan123")
		.email("pavan123@gmail.com")
		.firstName("Pavan")
		.lastName("Kalyan")
		.password("pavan123")
		.mobileNumber("989879908")
		.build();
		
		AuthService authService = new AuthService();
		Response response = authService.signUp(signUpRequest);
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.statusCode(), 200);
	}

}
