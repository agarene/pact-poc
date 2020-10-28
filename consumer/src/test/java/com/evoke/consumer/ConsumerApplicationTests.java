package com.evoke.consumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@ExtendWith(PactConsumerTestExt.class)
@SpringBootTest()
@SpringJUnitConfig()
class ConsumerApplicationTests {
	@BeforeAll
	public static void before(){
		System.setProperty("pact.rootDir","../pacts");
	}



	@Pact(provider = "getAllOrderProvider" ,consumer = "getAllOrdersConsumer")
	public RequestResponsePact getAllOrders(PactDslWithProvider builder){
		Map<String,String> headers=new HashMap<>();
		DslPart dslPart = PactDslJsonArray.arrayMinLike(1).uuid("orderId").decimalType("total", 0.12)
				.minArrayLike("products",1).uuid("productId").
				decimalType("cost", 0.12)
				.numberType("quantity", 0)
				.stringMatcher("productName",".*").closeObject().closeArray();
		return builder.given("get all orders").uponReceiving("get all orders")
				.path("/orders").method("GET").headers(headers).willRespondWith().status(200).
						body(dslPart).toPact();
	}

	@Pact(provider = "getOneOrderProvider" ,consumer = "getOneOrderConsumer")
	public RequestResponsePact getOrder(PactDslWithProvider builder){
		Map<String,String> headers=new HashMap<>();
		DslPart body = new PactDslJsonBody().uuid("orderId").decimalType("total", 0.12)
				.minArrayLike("products",1).uuid("productId").
						decimalType("cost", 0.12)
				.numberType("quantity", 0)
				.stringMatcher("productName",".*").closeObject();
		return builder.given("get single order").uponReceiving("get one order")
				.matchPath("/orders/getOne/[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}").method("GET")
				.headers(headers).willRespondWith().status(200)
				.body(body).toPact();
	}

	@Test
	@PactTestFor(pactMethod = "getAllOrders")
	public void createAllFooPactTest(MockServer mockServer) throws IOException {
		String url=mockServer.getUrl() + "/orders";
		HttpResponse httpResponse = getResponse(url);
		assertEquals(httpResponse.getStatusLine().getStatusCode(),200);

	}

	HttpResponse getResponse(String url) throws IOException {
		return Request.Get(url).addHeader("Content-Type","application/json").execute().returnResponse();
	}
	@Test
	@PactTestFor(pactMethod = "getOrder")
	public void getOneFooProviderTest(MockServer mockServer) throws IOException{
		String url = mockServer.getUrl() + "/orders/getOne/"+UUID.randomUUID().toString();
		HttpResponse httpResponse = getResponse(url);
		assertEquals(httpResponse.getStatusLine().getStatusCode(),200);

	}

}
