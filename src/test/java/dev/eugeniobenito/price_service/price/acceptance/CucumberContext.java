package dev.eugeniobenito.price_service.price.acceptance;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;

import io.cucumber.spring.ScenarioScope;
import lombok.Data;

@ScenarioScope
@Component
@Data
public class CucumberContext {
    private MvcResult responseEntity;
}
