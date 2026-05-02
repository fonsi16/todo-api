package com.afonso.todo.service;

import com.afonso.todo.exception.TodoNaoEncontradoException;
import com.afonso.todo.model.Todo;
import com.afonso.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CAMADA: Service (lógica de negócio)
 *
 * @Service — marca esta classe como um serviço gerido pelo Spring.
 * É aqui que vive a lógica da aplicação, separada dos controllers (HTTP) e
 * dos repositories (base de dados). Esta separação facilita testes e manutenção.
 *
 * @RequiredArgsConstructor (Lombok) — gera automaticamente um construtor com todos
 * os campos "final". Isto é a forma recomendada de fazer injeção de dependências
 * no Spring: em vez de @Autowired no campo, o Spring injeta pelo construtor.
 */
@Service
@RequiredArgsConstructor
public class TodoService {

    /**
     * "final" + @RequiredArgsConstructor = injeção pelo construtor.
     * O Spring sabe que precisa de fornecer um TodoRepository aqui porque
     * está anotado com @Repository e registado como bean.
     */
    private final TodoRepository todoRepository;

    /**
     * Devolve todos os todos da base de dados.
     * findAll() é fornecido pelo JpaRepository — gera "SELECT * FROM todos".
     */
    public List<Todo> listarTodos() {
        return todoRepository.findAll();
    }

    /**
     * Procura um Todo pelo seu id.
     * findById() devolve um Optional&lt;Todo&gt; — pode conter o objeto ou estar vazio.
     * orElseThrow() lança a nossa exceção personalizada se o Optional estiver vazio,
     * o que fará o GlobalExceptionHandler devolver um 404 ao cliente.
     */
    public Todo buscarPorId(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new TodoNaoEncontradoException(id));
    }

    /**
     * Guarda um novo Todo na base de dados.
     * save() com um objeto sem id faz INSERT; com id faz UPDATE.
     * Devolve o Todo já com o id gerado pela BD.
     */
    public Todo criar(Todo todo) {
        return todoRepository.save(todo);
    }

    /**
     * Atualiza um Todo existente:
     *   1. Verifica que o id existe (lança 404 se não existir)
     *   2. Substitui apenas os campos editáveis (título, descrição, concluído)
     *   3. Guarda as alterações na BD
     *
     * Nota: não atualizamos o "id" nem o "criadoEm" — esses nunca devem mudar.
     */
    public Todo atualizar(Long id, Todo todoAtualizado) {
        Todo todo = buscarPorId(id); // reutiliza o método acima (com validação)
        todo.setTitulo(todoAtualizado.getTitulo());
        todo.setDescricao(todoAtualizado.getDescricao());
        todo.setConcluido(todoAtualizado.isConcluido());
        return todoRepository.save(todo);
    }

    /**
     * Elimina um Todo da base de dados.
     * Primeiro chama buscarPorId() para garantir que existe —
     * se não existir, lança 404 antes de tentar apagar.
     */
    public void eliminar(Long id) {
        buscarPorId(id); // valida que existe antes de apagar
        todoRepository.deleteById(id);
    }
}
