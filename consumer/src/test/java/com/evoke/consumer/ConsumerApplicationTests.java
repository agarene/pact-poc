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
import io.pactfoundation.consumer.dsl.LambdaDslObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static io.pactfoundation.consumer.dsl.LambdaDsl.newJsonArray;

@ExtendWith(PactConsumerTestExt.class)
@SpringBootTest()
@SpringJUnitConfig()
class ConsumerApplicationTests {
	@BeforeAll
	public static void before(){
		System.setProperty("pact.rootDir","../pacts");
	}

	private static void getOrderStub(LambdaDslObject o) {
		o.uuid("orderId").decimalType("total", 0.12)
				.array("products", (productsArray) -> {
					productsArray.object((product) -> {
						product.uuid("productId")
								.decimalType("cost", 0.12)
								.numberType("quantity", 0)
								.stringMatcher("productName",".*");
					});
				});
	}

	@Pact(provider = "get-all-orders-provider" ,consumer = "get-all-orders-consumer")
	public RequestResponsePact getAllOrders(PactDslWithProvider builder){
		Map<String,String> headers=new HashMap<>();
		DslPart pactDslJsonBody = newJsonArray((array) -> array.object(ConsumerApplicationTests::getOrderStub))
				.build();
		return builder.given("get all orders").uponReceiving("get all orders")
				.path("/orders").method("GET").headers(headers).willRespondWith().status(200).
						body(pactDslJsonBody).toPact();
	}

	@Pact(provider = "get-one-order-provider" ,consumer = "get-one-order-consumer")
	public RequestResponsePact getOrder(PactDslWithProvider builder){
		Map<String,String> headers=new HashMap<>();
		PactDslJsonBody body = new PactDslJsonBody().
				uuid("orderId").decimalType("total",0.12).
				object("products", newJsonArray((productsArray) -> {
					productsArray.object((product) -> {
						product.uuid("productId")
								.numberType("cost", 0.12)
								.numberType("quantity", 0)
								.stringMatcher("productName",".*");
					});
				}).build());
		return builder.given("get single order").uponReceiving("get one order")
				.matchPath("/orders/getOne/[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}").method("GET")
				.headers(headers).willRespondWith().status(200)
				.body(body).toPact();
	}

	@Test
	@PactTestFor(pactMethod = "getAllOrders")
	public void createAllFooPactTest(MockServer mockServer) throws IOException {
		String url=mockServer.getUrl() + "/orders";
		System.out.println(url);
		HttpResponse httpResponse = getResponse(url);
		System.out.println(EntityUtils.toString(httpResponse.getEntity()));

	}

	HttpResponse getResponse(String url) throws IOException {
		return Request.Get(url).addHeader("Content-Type","application/json").execute().returnResponse();
	}
	@Test
	@PactTestFor(pactMethod = "getOrder")
	public void getOneFooProviderTest(MockServer mockServer) throws IOException{
		String url = mockServer.getUrl() + "/orders/getOne/"+UUID.randomUUID().toString();
		System.out.println(url);
		HttpResponse httpResponse = getResponse(url);
		System.out.println(EntityUtils.toString(httpResponse.getEntity()));
	}

}
