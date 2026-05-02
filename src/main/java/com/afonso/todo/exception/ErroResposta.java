package com.afonso.todo.exception;

import java.time.LocalDateTime;

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

    private int status;                  // código HTTP (ex: 404, 500)
    private String mensagem;             // descrição legível do erro
    private LocalDateTime timestamp;     // momento em que o erro ocorreu

    /**
     * Construtor — preenche o status e a mensagem.
     * O timestamp é definido automaticamente como "agora".
     */
    public ErroResposta(int status, String mensagem) {
        this.status = status;
        this.mensagem = mensagem;
        this.timestamp = LocalDateTime.now();
    }

    // Getters — necessários para o Jackson serializar os campos em JSON
    public int getStatus() { return status; }
    public String getMensagem() { return mensagem; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
