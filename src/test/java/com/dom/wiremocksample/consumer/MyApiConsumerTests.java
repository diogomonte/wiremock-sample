package com.dom.wiremocksample.consumer;

import io.restassured.response.ExtractableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyApiConsumerTests {

	@Test
	public void itShouldTestContractWithMyController() {
		ExtractableResponse response = given()
				.header(HttpHeaders.CONTENT_TYPE, "application/json")
				.header(HttpHeaders.ACCEPT, "application/json")
				.when().get("http://localhost:8080/devices/123")
				.then().assertThat().statusCode(HttpStatus.OK.value())
				.extract();

		Device deviceResponse = response.as(Device.class);
		UUID.fromString(deviceResponse.getId());
		assert deviceResponse.getTenant().getClass() == String.class;
	}

}
