Feature: Atualizar cliente

  Scenario: Atualizar cliente com sucesso
    Given informando o id do cliente 1
    Given o cliente com nome Gabe
    And o cliente com idade 22
    When atualizar o cliente
    Then deve retornar o cliente atualizado
    And a resposta deve retornar com status 200

  Scenario: Atualizar cliente devolve erro, pois não existe cliente com o id informado
    Given informando o id do cliente 9999
    Given o cliente com nome Gabe
    And o cliente com idade 22
    When atualizar o cliente
    Then a resposta deve retornar com status 400