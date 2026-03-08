package br.com.alura.forumhub.controller; // Mantenha o seu package original aqui se for diferente

import br.com.alura.forumhub.domain.topico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> cadastrar(@RequestBody @Valid DadosCadastroTopico dados, UriComponentsBuilder uriBuilder) {
        var topico = new Topico(dados);
        repository.save(topico);

        // Cria a URI (endereço) do novo recurso que foi criado
        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        // Retorna o status 201 (Created) e o corpo com os dados do tópico salvo
        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
    }

    // O método GET agora está DENTRO da classe TopicoController!
    @GetMapping
    public ResponseEntity<Page<DadosListagemTopico>> listar(@PageableDefault(size = 10, sort = {"dataCriacao"}) Pageable paginacao) {
        var page = repository.findAll(paginacao).map(DadosListagemTopico::new);
        return ResponseEntity.ok(page);
    }
    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var topico = repository.findById(id);

        // Verifica se o tópico existe no banco de dados
        if (topico.isPresent()) {
            return ResponseEntity.ok(new DadosDetalhamentoTopico(topico.get()));
        }

        // Se não existir, devolve o erro 404 Not Found (padrão REST)
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoTopico dados) {
        var topicoOptional = repository.findById(id);

        if (topicoOptional.isPresent()) {
            var topico = topicoOptional.get();
            topico.atualizarInformacoes(dados);
            return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
        }

        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var topicoOptional = repository.findById(id);

        // Verifica se o tópico com aquele ID realmente existe no banco
        if (topicoOptional.isPresent()) {
            repository.deleteById(id); // Deleta o tópico do banco de dados
            return ResponseEntity.noContent().build(); // Retorna o Status 204 (No Content)
        }

        // Se tentarem deletar um ID que não existe, devolve 404
        return ResponseEntity.notFound().build();
    }
} // <-- A classe só fecha aqui na última linha!