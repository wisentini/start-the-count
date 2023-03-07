# Start the Count

O Start the Count é um totalizador paralelo de boletins de urna de eleições brasileiras desenvolvido como trabalho final da disciplina de Projeto e Gerência de Banco de Dados no 5º semestre do curso de Bacharelado em Sistemas de Informação da Universidade Federal de Santa Maria.

## Execução

### Requisitos

O projeto está containerizado, ou seja, basta você ter o [Docker][docker] funcionando em sua máquina para conseguir rodar o projeto.

### Instruções

1. Se você deseja rodar o projeto em um contexto de desenvolvimento, certifique-se de que a variável `spring.profiles.active` no arquivo [`application.yaml`][arquivo-configuracao-spring-boot] tenha o valor `development`. Caso contrário, ela deve ter o valor `production`.

2. No diretório raíz desse repositório, abra um terminal e execute o script [`up.ps1`][arquivo-inicializacao-windows] se você estiver em um ambiente Windows ou [`up.sh`][arquivo-inicializacao-unix] se você estiver em um ambiente Unix.

3. A instrução anterior faz com que todos os containers do projeto sejam inicializados. É comum e preferível que, em um contexto de desenvolvimento, alguns serviços rodem de forma local ao invés de rodar pelo Docker. Se esse for o seu caso, execute o comando `docker compose stop <nome-do-servico>` em um terminal, substituindo `nome-do-servico` pelo nome do serviço que você deseja parar a execução. Os serviços disponíveis estão descritos no arquivo [`compose.yaml`][arquivo-compose-docker].

## Serviços

Após rodar o projeto, estarão disponíveis os seguintes serviços:

- [`localhost`][endpoint-front-end]: O front-end do projeto.

- [`localhost/api`][endpoint-api]: A API do projeto.

- [`localhost/api/docs`][endpoint-documentacao-api]: A documentação da API do projeto.

## Tecnologias

| Tecnologia                       | Versão |
| -------------------------------- | ------ |
| [Docker Desktop][docker-desktop] | 4.17.0 |
| [Java][java]                     | 19     |
| [Gradle][gradle]                 | 8.0.2  |
| [PostgreSQL][postgreSQL]         | 15     |
| [Redis][redis]                   | 7      |
| [Nginx][nginx]                   | 1.23.3 |
| [Swagger UI][swagger-ui]         | 4.17.1 |

[docker]:                           <https://www.docker.com/>                        "Docker"
[arquivo-configuracao-spring-boot]: <./back-end/src/main/resources/application.yaml> "Arquivo de configuração do Spring Boot"
[arquivo-inicializacao-windows]:    <./up.ps1>                                       "Arquivo de inicialização para Windows"
[arquivo-inicializacao-unix]:       <./up.sh>                                        "Arquivo de inicialização para Unix"
[arquivo-compose-docker]:           <./compose.yaml>                                 "Arquivo Compose do Docker"
[endpoint-front-end]:               <http://localhost>                               "Endpoint do front-end"
[endpoint-api]:                     <http://localhost/api>                           "Endpoint da API"
[endpoint-documentacao-api]:        <http://localhost/api/docs>                      "Endpoint da documentação da API"
[docker-desktop]:                   <https://docs.docker.com/desktop/>               "Docker Desktop"
[java]:                             <https://java.com>                               "Java"
[gradle]:                           <https://gradle.org/>                            "Gradle"
[postgreSQL]:                       <https://www.postgresql.org/>                    "PostgreSQL"
[redis]:                            <https://redis.io/>                              "Redis"
[nginx]:                            <https://www.nginx.com/>                         "Nginx"
[swagger-ui]:                       <https://swagger.io/>                            "Swagger UI"
