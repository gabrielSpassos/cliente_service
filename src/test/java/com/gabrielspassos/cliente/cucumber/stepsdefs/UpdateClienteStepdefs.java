package com.gabrielspassos.cliente.cucumber.stepsdefs;

import com.gabrielspassos.cliente.TestConfig;
import com.gabrielspassos.cliente.controller.dto.ClienteDto;
import com.gabrielspassos.cliente.controller.dto.ClienteSaveDto;
import com.gabrielspassos.cliente.cucumber.World;
import cucumber.api.java8.En;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class UpdateClienteStepdefs extends TestConfig implements En {

    @Autowired
    private World world;
    @LocalServerPort
    private String port;
    private RestTemplate restTemplate;

    public UpdateClienteStepdefs() {

        Before(() -> {
            restTemplate = new RestTemplate();
        });

        When("^atualizar o cliente$", () -> {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<ClienteSaveDto> entity = new HttpEntity<>(
                    createClienteSaveDto((String) world.map.get("name"), (Integer) world.map.get("age")),
                    headers);
            try {
                world.status = 200;
                world.cliente = restTemplate.exchange(
                        String.format("http://localhost:%s/cliente-service/api/v1/clientes/%s", port, world.map.get("id")),
                        HttpMethod.PUT,
                        entity,
                        ClienteDto.class)
                        .getBody();
            } catch (HttpServerErrorException | HttpClientErrorException e) {
                world.status = e.getRawStatusCode();
            }
        });

        Then("^deve retornar o cliente atualizado$", () -> {
            ClienteDto expectedCliente = createClienteDto(1L, "Gabe", 22);

            assertEquals(expectedCliente.getId(), world.cliente.getId());
            assertEquals(expectedCliente.getName(), world.cliente.getName());
            assertEquals(expectedCliente.getAge(), world.cliente.getAge());
        });
    }

}
