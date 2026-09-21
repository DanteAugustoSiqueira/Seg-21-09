
package br.senac.tads.dsw.exemplo2.controller;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.senac.tads.dsw.exemplo2.model.Produto;
import br.senac.tads.dsw.exemplo2.repository.ProdutoRepository;

// @RestController define que a classe manipula requisições de rede e
// retorna dados de forma direta (geralmente formato JSON).
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    private final ProdutoRepository repository;
    // Injeção de Dependência via Construtor.
    // O Spring Boot, ao iniciar, detecta a dependência de
    // ProdutoRepository, cria a instância e a entrega para o Controlador automaticamente.
    public ProdutoController(ProdutoRepository repository) {
        this.repository = repository;
    }
    // A anotação @PostMapping mapeia requisições HTTP do tipo POST.
    // Utilizado convencionalmente para criação de novos registros.
    @PostMapping
    public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto) {
        // O método repository.save() executa internamente um INSERT no
        // banco H2 e retorna o objeto persistido contendo o ID gerado pelo banco.
        Produto produtoSalvo = repository.save(produto);
        // O padrão REST indica que a criação de um recurso deve
        // retornar o Status HTTP 201 Created e o cabeçalho Location apontando para o recurso criado.
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(produtoSalvo.getId())
            .toUri()
        ;
        
        return ResponseEntity.created(location).body(produtoSalvo);
    }
}
