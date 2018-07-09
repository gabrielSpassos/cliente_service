package com.gabrielspassos.cliente.route;

import com.gabrielspassos.cliente.entity.ClienteEntity;
import com.gabrielspassos.cliente.repository.ClienteRepository;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class ClienteRoute extends RouteBuilder {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProducerTemplate producerTemplate;

    @Override
    public void configure() {

        from("direct:findClienteById")
                .process(this::findClienteById)
                .process(this::hasValidBody)
                .end();

        from("direct:findClientes")
                .process(this::getClientes)
                .end();

        from("direct:saveCliente")
                .process(this::saveCliente)
                .end();

        from("direct:updateCliente")
                .process(this::getClienteToUpdateById)
                .process(this::saveCliente)
                .end();
    }

    private void findClienteById(Exchange exchange) {
        Long id = exchange.getIn().getHeader("id", Long.class);
        ClienteEntity clienteEntity = clienteRepository.findById(id);
        exchange.getIn().setBody(clienteEntity);
    }

    private void hasValidBody(Exchange exchange) {
        ClienteEntity clienteEntity = (ClienteEntity) exchange.getIn().getBody();
        if(!Optional.ofNullable(clienteEntity).isPresent()){
            throw new IllegalArgumentException("Doesn't exist a client with this id");
        }
    }

    private void getClientes(Exchange exchange) {
        List<ClienteEntity> clientes = clienteRepository.findAll();
        exchange.getIn().setBody(clientes);
    }

    private void saveCliente(Exchange exchange) {
        ClienteEntity clienteEntity = (ClienteEntity) exchange.getIn().getBody();
        clienteEntity = clienteRepository.save(clienteEntity);
        exchange.getIn().setBody(clienteEntity);
    }

    private void getClienteToUpdateById(Exchange exchange) {
        Long id = exchange.getIn().getHeader("id", Long.class);
        ClienteEntity clienteEntity = (ClienteEntity) exchange.getIn().getBody();

        ClienteEntity cliente = Stream.of(producerTemplate.requestBodyAndHeaders(
                "direct:findClienteById",
                null,
                createRouteHeaders(id)))
                .map(response -> updateCliente((ClienteEntity) response, clienteEntity))
                .findAny()
                .get();

        exchange.getIn().setBody(cliente);
    }

    private Map<String, Object> createRouteHeaders(Long id){
        Map<String,Object> routeHeaders = new HashMap<>();
        routeHeaders.put("id", id);
        return routeHeaders;
    }

    private ClienteEntity updateCliente(ClienteEntity oldClienteEntity, ClienteEntity newClienteEntity){
        oldClienteEntity.setName(newClienteEntity.getName());
        oldClienteEntity.setAge(newClienteEntity.getAge());
        return oldClienteEntity;
    }
}
