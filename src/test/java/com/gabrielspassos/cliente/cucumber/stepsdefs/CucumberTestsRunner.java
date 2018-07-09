package com.gabrielspassos.cliente.cucumber.stepsdefs;

import com.gabrielspassos.cliente.ClienteServiceApplication;
import com.gabrielspassos.cliente.TestConfig;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = "com.gabrielspassos.cliente")
@ContextConfiguration(loader = SpringBootContextLoader.class, classes = ClienteServiceApplication.class)
public class CucumberTestsRunner extends TestConfig {
}