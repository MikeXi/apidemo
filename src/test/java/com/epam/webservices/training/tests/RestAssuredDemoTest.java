package com.epam.webservices.training.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.epam.webservices.training.model.post.Post;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

public class RestAssuredDemoTest {

	Response response;

	@BeforeTest
	public void setUp() {
		RestAssured.baseURI = "http://jsonplaceholder.typicode.com";
		response = RestAssured.when().get("/users").andReturn();
	}
	
	@Test
	public void checkStatusCode() {
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test
	public void checkResponseHeader() {
		String rpContentTypeHeader = response.getHeader("Content-Type");
		Assert.assertEquals(rpContentTypeHeader, "application/json; charset=utf-8");
	}
	
	@Test
	public void checkResponseBody() {
		ResponseBody<?> responseBody = response.getBody();
		Post[] posts = responseBody.as(Post[].class);		
		Assert.assertEquals(posts.length, 10);
	}
	
}
