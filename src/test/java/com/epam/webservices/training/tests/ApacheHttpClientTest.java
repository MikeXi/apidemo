package com.epam.webservices.training.tests;

import com.epam.webservices.training.model.post.Post;
import com.google.gson.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.IOException;

public class ApacheHttpClientTest {
	CloseableHttpClient httpClient = null;
	CloseableHttpResponse httpResponse = null;

	@BeforeTest
	public void setUp(){
		String url = "https://jsonplaceholder.typicode.com/users";
		httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpGet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void checkStatusCodeTest() {
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode,200);
	}
	
	@Test
	public void checkResponseContentTest() {
		String rpContentTypeHeader = httpResponse.getFirstHeader("Content-Type").getValue();
		Assert.assertEquals(rpContentTypeHeader, "application/json; charset=utf-8");
	}

	@Test
	public void checkResponseBody() {
		HttpEntity httpEntity = httpResponse.getEntity();
		try {
			String result = EntityUtils.toString(httpEntity);
			Gson gson = new Gson();
			Post[] user = gson.fromJson(result, Post[].class);
			Assert.assertEquals(user.length, 10);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@AfterTest
	public void tearDown(){
		if(null != httpResponse){
			try {
				httpResponse.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(null != httpClient){
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
