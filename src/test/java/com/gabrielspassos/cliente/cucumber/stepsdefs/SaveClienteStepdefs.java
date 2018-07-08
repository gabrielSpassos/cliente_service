package com.gabrielspassos.cliente.cucumber.stepsdefs;

import com.gabrielspassos.cliente.controller.dto.ClienteDto;
import com.gabrielspassos.cliente.controller.dto.ClienteSaveDto;
import com.gabrielspassos.cliente.cucumber.World;
import cucumber.api.java8.En;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SaveClienteStepdefs extends CucumberTestsRunner implements En {

    @Autowired
    private World world;
    @LocalServerPort
    private String port;
    private RestTemplate restTemplate;

    public SaveClienteStepdefs() {

        Before(() -> {
            restTemplate = new RestTemplate();
        });

        Given("^o cliente com nome (.*)", (String name) -> {
            world.map.put("name", name);
        });

        And("^o cliente com idade (\\d+)$", (Integer age) -> {
            world.map.put("age", age);
        });

        When("^salvo um cliente$", () -> {
            try {
                world.status = 200;
                world.cliente = restTemplate.postForEntity(
                        String.format("http://localhost:%s/cliente-service/api/v1/clientes", port),
                        createClienteSaveDto((String) world.map.get("name"), (Integer) world.map.get("age")),
                        ClienteDto.class)
                        .getBody();
            } catch (HttpServerErrorException | HttpClientErrorException e) {
                world.status = e.getRawStatusCode();
            }
        });

        Then("^deve retornar o cliente salvo$", () -> {
            ClienteDto expectedCliente = createClienteDto(5L, "Maria", 20);

            assertNotNull(world.cliente);
            assertNotNull(world.cliente.getId());
            assertEquals(expectedCliente.getName(), world.cliente.getName());
            assertEquals(expectedCliente.getAge(), world.cliente.getAge());
        });
    }

    private ClienteSaveDto createClienteSaveDto(String name, Integer age){
        ClienteSaveDto clienteSaveDto = new ClienteSaveDto();
        clienteSaveDto.setName(name);
        clienteSaveDto.setAge(age);
        return clienteSaveDto;
    }
}
