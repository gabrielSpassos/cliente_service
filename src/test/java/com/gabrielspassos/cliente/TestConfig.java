package com.gabrielspassos.cliente;

import com.gabrielspassos.cliente.controller.dto.ClienteDto;
import com.gabrielspassos.cliente.controller.dto.ClienteSaveDto;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = {ClienteServiceApplication.class})
public class TestConfig {

    protected ClienteDto createClienteDto(Long id, String name, Integer age){
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setId(id);
        clienteDto.setName(name);
        clienteDto.setAge(age);
        return clienteDto;
    }

    protected ClienteSaveDto createClienteSaveDto(String name, Integer age){
        ClienteSaveDto clienteSaveDto = new ClienteSaveDto();
        clienteSaveDto.setName(name);
        clienteSaveDto.setAge(age);
        return clienteSaveDto;
    }
}