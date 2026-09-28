
package br.senac.tads.dsw.exemplo2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

// A anotação @Entity indica ao Spring e ao Hibernate que esta classe é
// uma tabela no banco de dados H2.
@Entity
public class Avaliacao {
    // A anotação @Id indica que este atributo é a Chave Primária(Primary Key) da tabela.
    @Id
    // A anotação @GeneratedValue define que o banco de dados será responsável por 
    // gerar o valor deste ID automaticamente (Auto Increment).
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String titulo;

    // Validações adequadas para o contexto de uma avaliação (ex: nota de 1 a 5)
    @Min(1)
    @Max(5)
    private Integer nota;

    // Construtor padrão vazio (Exigência da especificação JPA)
    public Avaliacao() {}

    // Construtor com parâmetros para facilitar a criação de objetos
    public Avaliacao(String titulo, Integer nota) {
        this.titulo = titulo;
        this.nota = nota;
    }

    // Getters e Setters: Métodos públicos para acessar e modificar os
    // atributos privados (Encapsulamento).
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNota() {
        return nota;
    }
    public void setNota(Integer nota) {
        this.nota = nota;
    }
}
