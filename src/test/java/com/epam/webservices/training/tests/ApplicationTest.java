package com.epam.webservices.training.tests;

import com.epam.webservices.training.model.bugtracker.Application;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class ApplicationTest {

	Response response;
	Application application;
	int userId;

	@BeforeClass
	public void setUp() {
		RestAssured.baseURI = "http://localhost:8080/v1";
		RestAssured.defaultParser = Parser.JSON;
		application = new Application();
		userId = (int)(1+Math.random()*(9-1+1));
		System.out.println(userId);
	}

	@Test(priority = 1)
	public void checkCreateApplication() {
		application.setId(userId);
		application.setName("application " + userId);
		application.setOwner("Mike");
		application.setDescription("application " + userId + " desc");
		Gson gson = new Gson();
		String applicationJson = gson.toJson(application);
		response = RestAssured.given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON).contentType(ContentType.JSON).body(applicationJson).when().post("/application").andReturn();
		Assert.assertEquals(response.getStatusCode(), 201);
	}

	@Test(priority = 2)
	public void checkUpdateApplication() {
		application.setName("application " + userId + " updated");
		application.setOwner("Mike Xi");
		application.setDescription("application " + userId + " desc updated");
		Gson gson = new Gson();
		String applicationJson = gson.toJson(application);
		response = RestAssured.given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON).contentType(ContentType.JSON).body(applicationJson).when().put("/application").andReturn();
		Application responseApplication = response.as(Application.class);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(responseApplication.getName(), application.getName());
		Assert.assertEquals(responseApplication.getOwner(), application.getOwner());
		Assert.assertEquals(responseApplication.getDescription(), application.getDescription());
	}

	@Test(priority = 3)
	public void checkGetApplication() {
		response = RestAssured.given().headers("Accept", ContentType.JSON).when().get("/application/" + userId).andReturn();
		Application responseApplication = response.as(Application.class);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(responseApplication.getName(), application.getName());
		Assert.assertEquals(responseApplication.getOwner(), application.getOwner());
		Assert.assertEquals(responseApplication.getDescription(), application.getDescription());
	}

	@Test(priority = 4)
	public void checkDeleteApplication() {
		response = RestAssured.given().headers("Accept", ContentType.JSON).when().delete("/application/" + userId).andReturn();
		Assert.assertEquals(response.getStatusCode(), 204);
	}

}
