package com.evoke.provider;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProviderApplication.class)
/*@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL,
        ids = "com.evoke:provider:+:stubs:10000",
        repositoryRoot = "https://repo.spring.io/libs-snapshot")*/
@AutoConfigureMockMvc
public class StubsApplicationTests{


    @Autowired
    WebApplicationContext webApplicationContext;

    @PostConstruct
    public void setup() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

    }
    @Test
    public void contextLoads(){

    }
}

