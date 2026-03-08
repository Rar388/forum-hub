package br.com.alura.forumhub.domain.topico; //Ajuste para o seu pacote

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroTopico(
        @NotBlank // Isso garante que o usuário não mande vazio
        String titulo,

        @NotBlank
        String mensagem,

        @NotBlank
        String autor,

        @NotBlank
        String curso
) {
}