package com.afonso.todo.controller;

import com.afonso.todo.model.Todo;
import com.afonso.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CAMADA: Controller (entrada dos pedidos HTTP)
 *
 * @RestController — combina duas anotações:
 *   - @Controller    → marca a classe como um controller Spring MVC
 *   - @ResponseBody  → diz ao Spring para converter o retorno dos métodos em JSON
 *                      automaticamente (usando Jackson)
 *
 * @RequestMapping("/api/todos") — todos os endpoints desta classe começam com
 * este prefixo. Exemplo: GET /api/todos, POST /api/todos, etc.
 *
 * @RequiredArgsConstructor — gera o construtor que injeta o TodoService.
 */
@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    /**
     * O controller não acede à BD diretamente — delega sempre no Service.
     * Esta separação mantém o controller focado apenas em HTTP (receber pedidos,
     * devolver respostas) e o Service focado na lógica de negócio.
     */
    private final TodoService todoService;

    /**
     * GET /api/todos
     * Devolve a lista completa de todos.
     * ResponseEntity.ok(...) é um atalho para status 200 OK + body com os dados.
     */
    @GetMapping
    public ResponseEntity<List<Todo>> listarTodos() {
        return ResponseEntity.ok(todoService.listarTodos());
    }

    /**
     * GET /api/todos/{id}
     * @PathVariable extrai o {id} da URL e injeta-o como parâmetro do método.
     * Exemplo: GET /api/todos/5 → id = 5
     * Se o id não existir, o Service lança TodoNaoEncontradoException → 404.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Todo> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(todoService.buscarPorId(id));
    }

    /**
     * POST /api/todos
     * @RequestBody lê o JSON do corpo do pedido e converte-o num objeto Todo.
     * Exemplo de body: { "titulo": "Estudar Spring", "descricao": "..." }
     * Devolve 201 Created (não 200) — convenção para criação de recursos.
     */
    @PostMapping
    public ResponseEntity<Todo> criar(@RequestBody Todo todo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.criar(todo));
    }

    /**
     * PUT /api/todos/{id}
     * Atualiza o Todo com o id indicado na URL usando os dados do body.
     * PUT substitui o recurso inteiro (em oposição ao PATCH que é parcial).
     * Devolve 200 OK com o Todo atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Todo> atualizar(@PathVariable Long id, @RequestBody Todo todo) {
        return ResponseEntity.ok(todoService.atualizar(id, todo));
    }

    /**
     * DELETE /api/todos/{id}
     * Elimina o Todo com o id indicado.
     * Devolve 204 No Content — convenção para eliminações bem-sucedidas
     * (sem body na resposta, porque não há nada a devolver).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        todoService.eliminar(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
