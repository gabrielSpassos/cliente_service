Feature: Insiro cliente

  Scenario: Salvo o um cliente com sucesso
    Given o cliente com nome Maria
    And o cliente com idade 20
    When salvo um cliente
    Then deve retornar o cliente salvo
    And a resposta deve retornar com status 200