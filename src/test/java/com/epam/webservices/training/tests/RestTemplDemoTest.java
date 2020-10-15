package com.epam.webservices.training.tests;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.epam.webservices.training.model.post.Post;

import java.io.IOException;

public class RestTemplDemoTest {

	RestTemplate restTeample;
	ResponseEntity<Post[]> response;

	@BeforeTest
	public void setUp(){
		String url = "https://jsonplaceholder.typicode.com/users";
		restTeample = new RestTemplate();
		response = restTeample.getForEntity(url, Post[].class);
	}

	@Test
	public void checkStatusCode() {
		int actStatusCode = response.getStatusCode().value();
		Assert.assertEquals(actStatusCode, 200);
	}
	
	@Test
	public void checkResponseHeader() {
		HttpHeaders headers = response.getHeaders();
		String actContentTypeValue = headers.getContentType().toString();		
		Assert.assertEquals(actContentTypeValue, "application/json;charset=utf-8");
	}
	
	@Test()
	public void checkResponseBody() {
		Post[] actPosts = response.getBody();
		Assert.assertEquals(actPosts.length, 10);
	}
}
