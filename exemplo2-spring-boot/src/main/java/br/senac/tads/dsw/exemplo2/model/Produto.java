
package br.senac.tads.dsw.exemplo2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

// A anotação @Entity indica ao Spring e ao Hibernate que esta classe é
// uma tabela no banco de dados H2.
@Entity
public class Produto {
    // A anotação @Id indica que este atributo é a Chave Primária(Primary Key) da tabela.
    @Id
    // A anotação @GeneratedValue define que o banco de dados será responsável por 
    // gerar o valor deste ID automaticamente (Auto Increment).
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    // @NotNull
    @Positive
    private Double preco;

    // Construtor padrão vazio (Exigência da especificação JPA)
    public Produto() {}

    // Construtor com parâmetros para facilitar a criação de objetos
    public Produto(String nome, Double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    // Getters e Setters: Métodos públicos para acessar e modificar os
    // atributos privados (Encapsulamento).
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }
    public void setPreco(Double preco) {
        this.preco = preco;
    }
}
