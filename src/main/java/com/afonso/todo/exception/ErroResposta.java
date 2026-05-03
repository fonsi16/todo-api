package com.afonso.todo.exception;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Define o formato JSON devolvido ao cliente quando ocorre um erro.
 *
 * Em vez de uma stack trace enorme, o cliente recebe sempre uma resposta limpa:
 * {
 *   "status": 404,
 *   "mensagem": "Todo não encontrado com id: 99",
 *   "timestamp": "2026-05-02T14:32:01"
 * }
 *
 * Os getters são necessários para o Jackson (a biblioteca que converte objetos
 * Java em JSON) conseguir serializar os campos. Sem getters, o JSON ficaria vazio {}.
 */
public class ErroResposta {

    private int status;                  // código HTTP (ex: 400, 404, 500)
    private String mensagem;             // descrição legível do erro
    private List<String> detalhes;       // erros campo a campo (só em erros de validação)
    private LocalDateTime timestamp;     // momento em que o erro ocorreu

    /**
     * Construtor para erros simples (404, 500) — sem lista de detalhes.
     */
    public ErroResposta(int status, String mensagem) {
        this.status = status;
        this.mensagem = mensagem;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Construtor para erros de validação (400) — inclui a lista de campos inválidos.
     */
    public ErroResposta(int status, String mensagem, List<String> detalhes) {
        this.status = status;
        this.mensagem = mensagem;
        this.detalhes = detalhes;
        this.timestamp = LocalDateTime.now();
    }

    // Getters — necessários para o Jackson serializar os campos em JSON
    public int getStatus() { return status; }
    public String getMensagem() { return mensagem; }
    public List<String> getDetalhes() { return detalhes; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
