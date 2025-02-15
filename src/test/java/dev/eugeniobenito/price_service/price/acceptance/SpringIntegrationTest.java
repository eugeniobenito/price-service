package dev.eugeniobenito.price_service.price.acceptance;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;

@SpringBootTest
@CucumberContextConfiguration
@AutoConfigureMockMvc(addFilters = false)
public class SpringIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected CucumberContext context;

    @When("^the client calls (.+) endpoint with (.+) method$")
    public void whenClientCalls(String path, String method) throws Exception {
        HttpMethod httpMethod = HttpMethod.valueOf(method);
        MvcResult response = mockMvc.perform(request(httpMethod, path)).andReturn();
        context.setResponseEntity(response);
    }

    @Then("^the client receives status code of (\\d+)$")
    public void thenClientReceivesStatusCode(int expectedStatusCode) {
        int actualStatusCode = context.getResponseEntity().getResponse().getStatus();
        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @And("^the client receives response body data from file (.+)$")
    public void thenClientReceivesResponseBody(String file) throws Exception {
        String expectedResponseBody = Files.readString(Paths.get("src/test/resources/price_body_examples/" + file));
        String actualResponseBody = context.getResponseEntity().getResponse().getContentAsString();

        JSONObject expectedJson = new JSONObject(expectedResponseBody);
        JSONObject actualJson = new JSONObject(actualResponseBody);

        JSONAssert.assertEquals(expectedJson, actualJson, false);
    }

}
