package com.afonso.todo.repository;

import com.afonso.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CAMADA: Repository (acesso à base de dados)
 *
 * @Repository — marca esta interface como um componente de acesso a dados.
 * O Spring deteta-a automaticamente e cria uma implementação em tempo de execução.
 *
 * Ao extender JpaRepository&lt;Todo, Long&gt;, ganhamos GRATUITAMENTE os métodos:
 *   - findAll()      → SELECT * FROM todos
 *   - findById(id)   → SELECT * FROM todos WHERE id = ?
 *   - save(todo)     → INSERT ou UPDATE consoante o id existe ou não
 *   - deleteById(id) → DELETE FROM todos WHERE id = ?
 *   - count()        → SELECT COUNT(*) FROM todos
 *
 * Os dois parâmetros genéricos são:
 *   - Todo → a entidade que este repositório gere
 *   - Long → o tipo da chave primária (o campo @Id)
 */
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    /**
     * Filtra todos pelo estado de conclusão.
     * O Spring Data JPA gera automaticamente o SQL:
     *   SELECT * FROM todos WHERE concluido = ?
     *
     * @param concluido true para devolver apenas concluídos, false para pendentes
     */
    List<Todo> findByConcluido(boolean concluido);
}
