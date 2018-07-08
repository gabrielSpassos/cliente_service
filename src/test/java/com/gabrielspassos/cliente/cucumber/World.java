package com.gabrielspassos.cliente.cucumber;

import com.gabrielspassos.cliente.controller.dto.ClienteDto;
import com.google.common.collect.Maps;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Scope("cucumber-glue")
public class World {

    public Map<String, Object> map = Maps.newHashMap();
    public Integer status;
    public ClienteDto cliente;
}
