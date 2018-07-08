package com.gabrielspassos.cliente.cucumber.stepsdefs;

import com.gabrielspassos.cliente.controller.dto.ClienteDto;
import com.gabrielspassos.cliente.cucumber.World;
import cucumber.api.java8.En;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
            ClienteDto expectedCliente = createClienteDto(1L, "Gabriel", 21);

            assertEquals(expectedCliente.getId(), world.cliente.getId());
            assertEquals(expectedCliente.getName(), world.cliente.getName());
            assertEquals(expectedCliente.getAge(), world.cliente.getAge());
        });

        And("^a resposta deve retornar com status (\\d+)$", (Integer status) -> {
            assertEquals(status, world.status);
        });

        When("^busco os clientes$", () -> {
            try {
                world.status = 200;
                world.clientes = restTemplate.getForEntity(
                        String.format("http://localhost:%s/cliente-service/api/v1/clientes", port),
                        List.class)
                        .getBody();
            } catch (HttpServerErrorException | HttpClientErrorException e) {
                world.status = e.getRawStatusCode();
            }
        });

        Then("^deve retornar uma lista de clientes$", () -> {
            List<ClienteDto> expectedList = Lists.newArrayList();
            ClienteDto cliente1 = createClienteDto(1L, "Gabriel", 21);
            ClienteDto cliente2 = createClienteDto(2L, "Fernanda", 21);
            ClienteDto cliente3 = createClienteDto(3L, "Messi", 33);
            ClienteDto cliente4 = createClienteDto(4L, "Ronaldo", 30);
            Collections.addAll(expectedList, cliente1, cliente2, cliente3, cliente4);

            assertNotNull(world.clientes);

//            assertEquals(expectedList.get(0).getId(), world.clientes.get(0).getId());
//            assertEquals(expectedList.get(0).getName(), world.clientes.get(0).getName());
//            assertEquals(expectedList.get(0).getAge(), world.clientes.get(0).getAge());
//
//            assertEquals(expectedList.get(1).getId(), world.clientes.get(1).getId());
//            assertEquals(expectedList.get(1).getName(), world.clientes.get(1).getName());
//            assertEquals(expectedList.get(1).getAge(), world.clientes.get(1).getAge());
//
//            assertEquals(expectedList.get(2).getId(), world.clientes.get(2).getId());
//            assertEquals(expectedList.get(2).getName(), world.clientes.get(2).getName());
//            assertEquals(expectedList.get(2).getAge(), world.clientes.get(2).getAge());
//
//            assertEquals(expectedList.get(3).getId(), world.clientes.get(3).getId());
//            assertEquals(expectedList.get(3).getName(), world.clientes.get(3).getName());
//            assertEquals(expectedList.get(3).getAge(), world.clientes.get(3).getAge());
        });
    }
}
