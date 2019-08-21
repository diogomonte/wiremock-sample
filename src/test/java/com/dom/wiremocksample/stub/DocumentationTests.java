package com.dom.wiremocksample.stub;

import com.dom.wiremocksample.controller.DeviceResource;
import com.dom.wiremocksample.controller.MyController;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import wiremock.com.google.common.base.Strings;

import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DocumentationTests {

	@LocalServerPort
	int serverPort;

	@Before
	public void startUp() {
		configureFor("127.0.0.1", 8080);
	}

	@Test
	public void itShouldDocumentAndStubCreateDeviceApi() {
		// Test is documented
		stubFor(post(urlEqualTo("/devices"))
				.withHeader(HttpHeaders.CONTENT_TYPE, equalTo("application/json"))
				.withRequestBody(matchingJsonPath("$.['tenant']"))
				.willReturn(aResponse()
						.withHeader(HttpHeaders.CONTENT_TYPE, "application/json")
						.withStatus(HttpStatus.OK.value())));
	}

	@Test
	public void itShouldDocumentAndStubGetDeviceApi() {
		// Test is documented
		String deviceId = UUID.randomUUID().toString();

		// >>> Breaking change on the devices API
		//long deviceId = 10L;
		String getUrl = "/devices/" + deviceId;
		ExtractableResponse response = RestAssured.given()
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.contentType(ContentType.JSON)
				.when().port(serverPort).get(getUrl)
				.then().assertThat().statusCode(HttpStatus.OK.value())
				.extract();

		DeviceResource resource = response.body().as(DeviceResource.class);
		assert deviceId.equals(resource.id);
		//assert deviceId == resource.id;
		assert "tenant".equals(resource.tenant);

		stubFor(get(urlMatching("/devices/[a-z0-9]+"))
				.withHeader(HttpHeaders.ACCEPT, containing("application/json"))
				.willReturn(aResponse()
						.withHeader(HttpHeaders.CONTENT_TYPE, "application/json")
						.withStatus(response.statusCode())
						.withBody(response.body().asString())));
	}

}