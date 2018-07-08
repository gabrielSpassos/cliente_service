package com.gabrielspassos.cliente.controller;

import com.gabrielspassos.cliente.controller.dto.ClienteDto;
import com.gabrielspassos.cliente.controller.dto.ClienteSaveDto;
import com.gabrielspassos.cliente.entity.ClienteEntity;
import com.gabrielspassos.cliente.error.SimpleError;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.camel.ProducerTemplate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/v1")
public class ClienteController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProducerTemplate producerTemplate;

    @ApiOperation(
            value="Get cliente by id",
            response=ClienteDto.class,
            notes="This operation return the client by his id")
    @ApiResponses(value= {
            @ApiResponse(
                    code=200,
                    message="Return's a client",
                    response=ClienteDto.class
            ),
            @ApiResponse(
                    code=400,
                    message="When there isn't a client with the id informed",
                    response=SimpleError.class
            )

    })
    @GetMapping(value = "/clientes/{id}")
    public ResponseEntity<?> getClienteById(@PathVariable("id") Long id){
        ClienteDto clienteDto = Stream.of(createRouteHeaders(id))
                .map(headers -> producerTemplate
                        .requestBodyAndHeaders(
                                "direct:findClienteById",
                                null,
                                headers
                        ))
                .map(response -> convertToDto((ClienteEntity) response))
                .findFirst()
                .get();

        return ok(clienteDto);
    }

    @ApiOperation(
            value="Get clientes",
            response=ClienteDto.class,
            notes="This operation return a list of clientes")
    @ApiResponses(value= {
            @ApiResponse(
                    code=200,
                    message="Return's the list of clients",
                    response=ClienteDto.class
            )
    })
    @GetMapping(value = "/clientes")
    public ResponseEntity<?> getClientes(){
        return ok(producerTemplate.requestBodyAndHeaders(
        "direct:findClientes",
                null,
        null));
    }

    @ApiOperation(
            value="Save cliente",
            response=ClienteDto.class,
            notes="This operation return a list of clientes")
    @ApiResponses(value= {
            @ApiResponse(
                    code=200,
                    message="Return's the client saved",
                    response=ClienteDto.class
            )
    })
    @PostMapping(value = "/clientes")
    public ResponseEntity<?> saveCliente(@RequestBody ClienteSaveDto clienteSaveDto){
        return ok(
                Stream.of(clienteSaveDto)
                .map(this::convertToEntity)
                .map(clienteEntity -> producerTemplate.requestBody(
                        "direct:saveCliente",
                        clienteEntity)
                ).map(response -> convertToDto((ClienteEntity) response))
                .findFirst()
                .get()
        );
    }

    private Map<String, Object> createRouteHeaders(Long id){
        Map<String,Object> routeHeaders = new HashMap<>();
        routeHeaders.put("id", id);
        return routeHeaders;
    }

    private ClienteEntity convertToEntity(ClienteSaveDto clienteSaveDto) {
        return modelMapper.map(clienteSaveDto, ClienteEntity.class);
    }

    private ClienteDto convertToDto(ClienteEntity clienteEntity){
        return modelMapper.map(clienteEntity, ClienteDto.class);
    }
}
