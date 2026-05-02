package com.afonso.todo.exception;

/**
 * Exceção personalizada para quando um Todo não existe na base de dados.
 *
 * Porquê criar uma exceção própria em vez de usar RuntimeException diretamente?
 *   - Fica claro no código o que correu mal (semântica)
 *   - O GlobalExceptionHandler consegue capturá-la especificamente e devolver
 *     um HTTP 404, enquanto outras exceções podem devolver 500
 *
 * Extende RuntimeException (unchecked exception) para não obrigar todos os
 * métodos que a possam lançar a declarar "throws TodoNaoEncontradoException".
 * Em Spring Boot, a convenção é usar exceções unchecked.
 */
public class TodoNaoEncontradoException extends RuntimeException {

    /**
     * super(...) chama o construtor da classe pai (RuntimeException)
     * com a mensagem de erro. Essa mensagem é acedida depois via ex.getMessage()
     * no GlobalExceptionHandler para incluir na resposta JSON.
     */
    public TodoNaoEncontradoException(Long id) {
        super("Todo não encontrado com id: " + id);
    }
}
