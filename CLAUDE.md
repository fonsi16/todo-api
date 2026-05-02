# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Compilar
./mvnw compile

# Correr testes
./mvnw test

# Correr um teste específico
./mvnw test -Dtest=TodoApiApplicationTests

# Build completo (compila + testa + empacota)
./mvnw package

# Arrancar a aplicação
./mvnw spring-boot:run
```

A aplicação fica disponível em `http://localhost:8080`.

## Pré-requisito: base de dados

Os testes com `@SpringBootTest` requerem uma base de dados PostgreSQL activa. Configuração em `src/main/resources/application.properties`:
- Host: `localhost:5432`
- Base de dados: `tododb`
- Utilizador: `todouser` / Password: `todopass`

O schema é gerido automaticamente pelo Hibernate (`ddl-auto=update`).

## Arquitectura

REST API em Spring Boot 4 com a arquitectura em três camadas:

```
HTTP → Controller → Service → Repository → PostgreSQL
```

- **Controller** (`controller/`) — recebe pedidos HTTP, delega no Service, devolve `ResponseEntity`
- **Service** (`service/`) — lógica de negócio; lança `TodoNaoEncontradoException` quando um id não existe
- **Repository** (`repository/`) — interface `JpaRepository<Todo, Long>` sem métodos adicionais; o Spring Data JPA gera as queries
- **Model** (`model/`) — entidade `Todo` mapeada para a tabela `todos`; campos: `id`, `titulo`, `descricao`, `concluido`, `criadoEm`
- **Exception** (`exception/`) — `GlobalExceptionHandler` (`@RestControllerAdvice`) intercepta `TodoNaoEncontradoException` → 404 e `Exception` → 500, devolvendo sempre `ErroResposta` em JSON

## Convenções de comentários

- `/** */` (Javadoc) — acima de classes públicas, métodos públicos e construtores
- `//` — dentro do corpo dos métodos
