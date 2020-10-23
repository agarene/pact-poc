package com.evoke.provider;

import com.evoke.provider.domain.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.springframework.boot.test.json.JacksonTester;

public class AbstractTest {
    public JacksonTester<Order> json;

    @Before
    public void setup() {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
    }
}
