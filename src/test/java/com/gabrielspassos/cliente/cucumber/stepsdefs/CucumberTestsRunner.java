package com.gabrielspassos.cliente.cucumber.stepsdefs;

import com.gabrielspassos.cliente.ClienteServiceApplication;
import com.gabrielspassos.cliente.TestConfig;
import com.gabrielspassos.cliente.controller.dto.ClienteDto;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = "com.gabrielspassos.cliente")
@ContextConfiguration(loader = SpringBootContextLoader.class, classes = ClienteServiceApplication.class)
public class CucumberTestsRunner extends TestConfig {

    public ClienteDto createClienteDto(Long id, String name, Integer age){
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setId(id);
        clienteDto.setName(name);
        clienteDto.setAge(age);
        return clienteDto;
    }
}