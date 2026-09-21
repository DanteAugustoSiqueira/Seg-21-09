
package br.senac.tads.dsw.exemplo2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.senac.tads.dsw.exemplo2.model.Produto;

// A interface deve herdar de JpaRepository.
// Os parâmetros entre <> são: O tipo da Entidade que este repositório
// gerencia (Produto) e o tipo do ID da Entidade (Long).
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // Nenhum código adicional é estritamente necessário aqui.
    // Métodos como save(), findAll(), findById() e deleteById() são
    // fornecidos automaticamente pelo Spring.
}
