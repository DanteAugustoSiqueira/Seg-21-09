
package br.senac.tads.dsw.exemplo2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.senac.tads.dsw.exemplo2.model.Avaliacao;

// A interface deve herdar de JpaRepository.
// Os parâmetros entre <> são: O tipo da Entidade que este repositório
// gerencia (Avaliacao) e o tipo do ID da Entidade (Long).
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    // Nenhum código adicional é estritamente necessário aqui.
    // Métodos como save(), findAll(), findById() e deleteById() são
    // fornecidos automaticamente pelo Spring.
}
