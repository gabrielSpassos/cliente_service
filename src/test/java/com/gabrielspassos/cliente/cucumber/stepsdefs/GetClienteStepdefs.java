package com.gabrielspassos.cliente.cucumber.stepsdefs;

import com.gabrielspassos.cliente.controller.dto.ClienteDto;
import com.gabrielspassos.cliente.cucumber.World;
import cucumber.api.java8.En;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;

public class GetClienteStepdefs extends CucumberTestsRunner implements En {

    @Autowired
    private World world;
    @LocalServerPort
    private String port;
    private RestTemplate restTemplate;

    public GetClienteStepdefs() {

        Before(() -> {
            restTemplate = new RestTemplate();
        });

        Given("^informando o id do cliente (\\d+)$", (Long id) -> {
            world.map.put("id", id);
        });

        When("^busco o cliente pelo seu id$", () -> {
            try {
                world.status = 200;

                world.cliente = restTemplate.getForEntity(
                        String.format("http://localhost:%s/cliente-service/api/v1/clientes/%s", port, world.map.get("id")),
                        ClienteDto.class)
                        .getBody();
            } catch (HttpServerErrorException | HttpClientErrorException e) {
                world.status = e.getRawStatusCode();
            }
        });

        Then("^deve retornar um cliente com sucesso$", () -> {
            ClienteDto expectedCliente = new ClienteDto();
            expectedCliente.setId(1L);
            expectedCliente.setName("Gabriel");
            expectedCliente.setAge(21);

            assertEquals(expectedCliente.getId(), world.cliente.getId());
            assertEquals(expectedCliente.getName(), world.cliente.getName());
            assertEquals(expectedCliente.getAge(), world.cliente.getAge());
        });

        And("^a resposta deve retornar com status (\\d+)$", (Integer status) -> {
            assertEquals(status, world.status);
        });

    }
}
