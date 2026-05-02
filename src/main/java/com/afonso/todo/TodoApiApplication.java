package com.afonso.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @SpringBootApplication é uma "mega-anotação" que ativa 3 coisas ao mesmo tempo:
 *   1. @Configuration        → esta classe pode definir beans (objetos geridos pelo Spring)
 *   2. @EnableAutoConfiguration → o Spring configura automaticamente a app com base nas
 *                                  dependências do pom.xml (ex: detecta o PostgreSQL e configura a BD)
 *   3. @ComponentScan        → o Spring procura todas as classes anotadas com @Service,
 *                               @Repository, @Controller, etc., nesta package e sub-packages
 */
@SpringBootApplication
public class TodoApiApplication {

    /**
     * Ponto de entrada da aplicação — o Java começa sempre aqui.
     * SpringApplication.run() arranca o servidor embutido (Tomcat por omissão),
     * carrega todas as configurações e fica à escuta de pedidos HTTP na porta 8080.
     */
    public static void main(String[] args) {
        SpringApplication.run(TodoApiApplication.class, args);
    }

}
