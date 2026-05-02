package com.afonso.todo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * CAMADA: Model (também chamada Entity)
 *
 * Esta classe representa a tabela "todos" na base de dados.
 * Cada instância de Todo corresponde a uma linha nessa tabela.
 *
 * @Data (Lombok) — gera automaticamente em tempo de compilação:
 *   - getters e setters para todos os campos
 *   - toString(), equals(), hashCode()
 *
 * @Entity — diz ao JPA que esta classe deve ser mapeada para uma tabela na BD.
 *
 * @Table(name = "todos") — define o nome exato da tabela.
 * Sem isto, o JPA usaria o nome da classe ("Todo") como nome da tabela.
 */
@Data
@Entity
@Table(name = "todos")
public class Todo {

    /**
     * @Id — marca este campo como a chave primária da tabela.
     * @GeneratedValue(strategy = GenerationType.IDENTITY) — o valor do id é
     * gerado automaticamente pela base de dados (auto-increment no PostgreSQL).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @Column(nullable = false) — esta coluna não pode ser NULL na base de dados.
     * Se tentarmos guardar um Todo sem título, a BD rejeita.
     */
    @Column(nullable = false)
    private String titulo;

    // Sem @Column especial — a descrição é opcional (pode ser NULL na BD).
    private String descricao;

    /**
     * Valor padrão: false — quando criamos um Todo, começa sempre por não estar concluído.
     * nullable = false garante que a coluna nunca fica a NULL na BD.
     */
    @Column(nullable = false)
    private boolean concluido = false;

    /**
     * LocalDateTime.now() — guarda automaticamente a data e hora de criação
     * no momento em que o objeto é instanciado em memória.
     */
    @Column(nullable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();
}
