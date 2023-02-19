# back-end

## Execução

- Antes de rodar a aplicação, você deve iniciar os bancos de dados. Para isso, execute o comando `docker compose up -d` no diretório raíz do projeto.

- Com os bancos de dados rodando, basta executar o comando `./gradlew bootRun` e a aplicação estará disponível em [`localhost:8080`][endpoint-aplicacao].

## Documentação

- Após rodar a aplicação, a documentação estará disponível em [`/swagger-ui/index.html`][endpoint-documentacao].

- Com exceção dos endpoints [`/usuarios/create`][endpoint-criacao-usuario] e [`/usuarios/login`][endpoint-login-usuario], todos os demais possuem autenticação.

- O token de autenticação pode ser gerado na criação de um novo usuário ([`/usuarios/create`][endpoint-criacao-usuario]) ou na autenticação de um usuário já existente ([`/usuarios/login`][endpoint-login-usuario]).

## Tecnologias

| Tecnologia               | Versão |
| ------------------------ | ------ |
| [Java][java]             | 19     |
| [Gradle][gradle]         | 7.6    |
| [PostgreSQL][postgreSQL] | 15     |
| [Redis][redis]           | 7      |

[endpoint-aplicacao]:       <http:localhost:8080>                         "Endpoint da aplicação"
[endpoint-documentacao]:    <http://localhost:8080/swagger-ui/index.html> "Endpoint da documentação"
[endpoint-criacao-usuario]: <http://localhost:8080/usuarios/create>       "Endpoint de criação de usuário"
[endpoint-login-usuario]:   <http://localhost:8080/usuarios/login>        "Endpoint de login de usuário"
[java]:                     <https://java.com>                            "Java"
[gradle]:                   <https://gradle.org/>                         "Gradle"
[postgreSQL]:               <https://www.postgresql.org/>                 "PostgreSQL"
[redis]:                    <https://redis.io/>                           "Redis"
