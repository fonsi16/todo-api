package com.afonso.todo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * TRATAMENTO GLOBAL DE ERROS
 *
 * @RestControllerAdvice combina:
 *   - @ControllerAdvice  → esta classe "envolve" todos os controllers e pode
 *                          interceptar exceções antes de chegarem ao cliente
 *   - @ResponseBody      → as respostas são automaticamente convertidas em JSON
 *
 * Sem esta classe, quando uma exceção não é apanhada num controller, o Spring
 * devolve uma página de erro HTML genérica (ou uma stack trace), o que é mau
 * para uma API REST. Com esta classe, garantimos respostas JSON consistentes.
 *
 * O Spring encontra esta classe automaticamente pelo @ComponentScan do
 * @SpringBootApplication (está na mesma package base: com.afonso.todo).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura erros de validação lançados quando o @Valid falha no @RequestBody.
     *
     * Quando o Spring valida o body e encontra campos inválidos, lança
     * MethodArgumentNotValidException com a lista de todos os erros.
     * Extraímos os erros campo a campo e devolvemos um 400 estruturado:
     * {
     *   "status": 400,
     *   "mensagem": "Erro de validação",
     *   "detalhes": ["titulo: O título é obrigatório", "descricao: ..."],
     *   "timestamp": "..."
     * }
     *
     * getBindingResult().getFieldErrors() — devolve a lista de campos que falharam.
     * Cada FieldError tem: getField() (nome do campo) + getDefaultMessage() (a nossa mensagem).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> handleValidacao(MethodArgumentNotValidException ex) {
        // extrai "campo: mensagem" para cada erro de campo
        List<String> detalhes = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .toList();

        ErroResposta erro = new ErroResposta(
                HttpStatus.BAD_REQUEST.value(), // 400
                "Erro de validação",
                detalhes
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    /**
     * @ExceptionHandler
     * Diz ao Spring: "quando qualquer controller lançar esta exceção,
     * executa este método em vez de propagar o erro".
     * O parâmetro "ex" recebe a exceção — usamos ex.getMessage() para
     * incluir a mensagem na resposta (ex: "Todo não encontrado com id: 5").
     * Devolve HTTP 404 Not Found com um corpo JSON estruturado.
     */
    @ExceptionHandler(TodoNaoEncontradoException.class)
    public ResponseEntity<ErroResposta> handleTodoNaoEncontrado(TodoNaoEncontradoException ex) {
        ErroResposta erro = new ErroResposta(
                HttpStatus.NOT_FOUND.value(), // 404
                ex.getMessage()              // "Todo não encontrado com id: X"
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    /**
     * Safety net — apanha qualquer outra exceção não prevista.
     *
     * É boa prática em produção não devolver a mensagem real da exceção
     * (pode conter informação sensível do servidor), por isso devolvemos
     * uma mensagem genérica.
     *
     * Devolve HTTP 500 Internal Server Error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> handleErroGenerico(Exception ex) {
        ErroResposta erro = new ErroResposta(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // 500
                "Ocorreu um erro interno. Tente mais tarde."
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}
