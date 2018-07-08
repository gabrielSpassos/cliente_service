package com.gabrielspassos.cliente;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = {ClienteServiceApplication.class})
public class TestConfig {
}