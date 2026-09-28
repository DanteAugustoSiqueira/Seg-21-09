
package br.senac.tads.dsw.exemplo2.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.senac.tads.dsw.exemplo2.model.Avaliacao;
import br.senac.tads.dsw.exemplo2.repository.AvaliacaoRepository;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// @RestController define que a classe manipula requisições de rede e
// retorna dados de forma direta (geralmente formato JSON).
@RestController
@RequestMapping("/api/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoRepository repository;

    // Injeção de Dependência via Construtor.
    // O Spring Boot, ao iniciar, detecta a dependência de
    // AvaliacaoRepository, cria a instância e a entrega para o Controlador automaticamente.
    public AvaliacaoController(AvaliacaoRepository repository) {
        this.repository = repository;
    }

    // A anotação @PostMapping mapeia requisições HTTP do tipo POST.
    // Utilizado convencionalmente para criação de novos registros.
    @PostMapping
    public ResponseEntity<Avaliacao> criarAvaliacao(@RequestBody @Valid Avaliacao avaliacao) {
        // O método repository.save() executa internamente um INSERT no
        // banco H2 e retorna o objeto persistido contendo o ID gerado pelo banco.
        Avaliacao avaliacaoSalva = repository.save(avaliacao);

        // O padrão REST indica que a criação de um recurso deve
        // retornar o Status HTTP 201 Created e o cabeçalho Location apontando para o recurso criado.
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(avaliacaoSalva.getId())
            .toUri()
        ;
        
        return ResponseEntity.created(location).body(avaliacaoSalva);
    }

    // Retorna a lista completa de avaliações armazenadas no banco de dados.
    @GetMapping
    public List<Avaliacao> listarTodas() {
        // O método findAll() do repositório gera automaticamente um SELECT * FROM avaliacao.
        return repository.findAll();
    }

    // A notação {id} representa um parâmetro de caminho (PathVariable).
    // A URL esperada é /api/avaliacoes/1
    @GetMapping("/{id}")
    public ResponseEntity<Avaliacao> buscarPorId(@PathVariable Long id) {
        // O método findById retorna um Optional, que é uma "caixa" do Java que pode ou não conter a Avaliacao.
        // Ele nos obriga a tratar o cenário onde a avaliação não existe, evitando o erro NullPointerException.
        Optional<Avaliacao> avaliacaoBuscada = repository.findById(id);

        if (avaliacaoBuscada.isPresent()) {
            // Se a caixa não estiver vazia, pegamos a avaliação com .get() e retornamos Status 200 (OK).
            return ResponseEntity.ok(avaliacaoBuscada.get());
        }
        else {
            // Se a caixa estiver vazia (ID não encontrado), retornamos Status 404 (Not Found).
            return ResponseEntity.notFound().build();
        }
    }

    // Mapeia a requisição HTTP PUT para a URL /api/avaliacoes/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Avaliacao> atualizarAvaliacao(@PathVariable Long id, @RequestBody @Valid Avaliacao avaliacaoAtualizada) {
        // 1. Primeiro passo: Verificar se o registro que queremos alterar realmente existe no banco.
        Optional<Avaliacao> avaliacaoBuscada = repository.findById(id);

        if (avaliacaoBuscada.isPresent()) {
            // 2. Recuperamos o objeto original do banco de dados.
            Avaliacao avaliacaoExistente = avaliacaoBuscada.get();

            // 3. Substituímos os atributos do objeto original com os novos dados recebidos no Corpo da Requisição (JSON).
            // Importante: O ID original é mantido intacto.
            avaliacaoExistente.setTitulo(avaliacaoAtualizada.getTitulo());
            avaliacaoExistente.setNota(avaliacaoAtualizada.getNota());

            // 4. Mandamos salvar. O Hibernate é inteligente: como o objeto já possui um ID,
            // ele executará um comando SQL de UPDATE em vez de um INSERT.
            Avaliacao avaliacaoSalva = repository.save(avaliacaoExistente);

            return ResponseEntity.ok(avaliacaoSalva); // Retorna os dados recém-atualizados com Status 200.
        }
        else {
            return ResponseEntity.notFound().build(); // Retorna Status 404 se o ID não for encontrado.
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarAvaliacao(@PathVariable Long id) {
        // Verifica a existência do registro antes de tentar deletá-lo.
        Optional<Avaliacao> avaliacaoBuscada = repository.findById(id);
        
        if (avaliacaoBuscada.isPresent()) {
            // O Spring Data executa o comando SQL DELETE utilizando a chave primária.
            repository.deleteById(id);

            // A classe ResponseEntity indica que não haverá corpo de mensagem.
            // Retorna Status 204 (No Content): indica que a operação teve sucesso e 
            // a página de resposta está intencionalmente vazia.
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
