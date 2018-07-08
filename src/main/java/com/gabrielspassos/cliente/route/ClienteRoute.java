package com.gabrielspassos.cliente.route;

import com.gabrielspassos.cliente.entity.ClienteEntity;
import com.gabrielspassos.cliente.repository.ClienteRepository;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ClienteRoute extends RouteBuilder {

    @Autowired
    private ClienteRepository clienteRepository;

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
}
