package com.gabrielspassos.cliente.repository;

import com.gabrielspassos.cliente.entity.ClienteEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClienteRepository extends CrudRepository<ClienteEntity, Long> {

    ClienteEntity findById(Long id);
    List<ClienteEntity> findAll();
    ClienteEntity save(ClienteEntity clienteEntity);
}
