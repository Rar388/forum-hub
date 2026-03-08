package br.com.alura.forumhub.domain.topico; //Atenção: ajuste o nome do pacote para o seu projeto

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private String status;
    private String autor;
    private String curso;

    // ... construtor que você já tinha feito antes ...
    public Topico(DadosCadastroTopico dados) {
        // ...
    }

    // Cole o método aqui!
    public void atualizarInformacoes(DadosAtualizacaoTopico dados) {
        if (dados.titulo() != null && !dados.titulo().trim().isEmpty()) {
            this.titulo = dados.titulo();
        }
        if (dados.mensagem() != null && !dados.mensagem().trim().isEmpty()) {
            this.mensagem = dados.mensagem();
        }
        if (dados.curso() != null && !dados.curso().trim().isEmpty()) {
            this.curso = dados.curso();
        }
    }
} // <-- Última chave da classe Topico
