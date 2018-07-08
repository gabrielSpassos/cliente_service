Feature: Busca cliente

  Scenario: Busca cliente pelo seu id com sucesso
    Given informando o id do cliente 1
    When busco o cliente pelo seu id
    Then deve retornar um cliente com sucesso
    And a resposta deve retornar com status 200

  Scenario: Busca cliente por id que não corresponde a nenhum cliente
    Given informando o id do cliente 9999
    When busco o cliente pelo seu id
    Then a resposta deve retornar com status 400

  Scenario: Busca de clientes, retorna lista com sucesso
    When busco os clientes
    Then deve retornar uma lista de clientes
    And a resposta deve retornar com status 200
