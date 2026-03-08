package br.com.alura.forumhub.domain.topico; //Ajuste para o seu pacote

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
}