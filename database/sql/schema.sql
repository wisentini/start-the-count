DROP TRIGGER IF EXISTS tr_atualizar_coluna_atualizado_em ON public.usuario;

DROP TRIGGER IF EXISTS tr_atualizar_coluna_atualizado_em ON public.boletim_urna_usuario;

DROP FUNCTION IF EXISTS fn_atualizar_coluna_atualizado_em;

DROP TABLE IF EXISTS apuracao_votos_cargo_boletim_urna;

DROP TABLE IF EXISTS apuracao_votos_partido_boletim_urna;

DROP TABLE IF EXISTS apuracao_votos_candidatura_boletim_urna;

DROP TABLE IF EXISTS qr_code_boletim_urna;

DROP TABLE IF EXISTS boletim_urna_usuario;

DROP TABLE IF EXISTS boletim_urna;

DROP TABLE IF EXISTS secao_pleito;

DROP TABLE IF EXISTS agregacao_secao;

DROP TABLE IF EXISTS fase;

DROP TABLE IF EXISTS origem_boletim_urna;

DROP TABLE IF EXISTS candidatura;

DROP TABLE IF EXISTS candidato;

DROP TABLE IF EXISTS partido;

DROP TABLE IF EXISTS cargo_eleicao;

DROP TABLE IF EXISTS cargo;

DROP TABLE IF EXISTS tipo_cargo;

DROP TABLE IF EXISTS eleicao;

DROP TABLE IF EXISTS pleito;

DROP TABLE IF EXISTS secao_processo_eleitoral;

DROP TABLE IF EXISTS processo_eleitoral;

DROP TABLE IF EXISTS origem_configuracao_processo_eleitoral;

DROP TABLE IF EXISTS secao;

DROP TABLE IF EXISTS local_votacao;

DROP TABLE IF EXISTS zona;

DROP TABLE IF EXISTS urna_eletronica;

DROP TABLE IF EXISTS municipio;

DROP TABLE IF EXISTS uf;

DROP TABLE IF EXISTS regiao;

DROP TABLE IF EXISTS papel_usuario;

DROP TABLE IF EXISTS usuario;

DROP TABLE IF EXISTS papel_permissao;

DROP TABLE IF EXISTS papel;

DROP TABLE IF EXISTS permissao;


CREATE TABLE IF NOT EXISTS papel (
    id_papel  INT         NOT NULL GENERATED ALWAYS AS IDENTITY,
    nome      VARCHAR(15)  NOT NULL,
    descricao VARCHAR(31) NOT NULL,

    PRIMARY KEY (id_papel),
    UNIQUE      (nome),
    UNIQUE      (descricao)
);


CREATE TABLE IF NOT EXISTS permissao (
    id_permissao INT         NOT NULL GENERATED ALWAYS AS IDENTITY,
    nome         VARCHAR(31)  NOT NULL,
    descricao    VARCHAR(63) NOT NULL,

    PRIMARY KEY (id_permissao),
    UNIQUE      (nome)
);

CREATE TABLE IF NOT EXISTS papel_permissao (
    id_papel_permissao INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    id_papel           INT NOT NULL,
    id_permissao       INT NOT NULL,

    PRIMARY KEY (id_papel_permissao),
    FOREIGN KEY (id_permissao) REFERENCES permissao (id_permissao),
    FOREIGN KEY (id_papel)     REFERENCES papel (id_papel),
    UNIQUE      (id_permissao, id_papel)
);


CREATE TABLE IF NOT EXISTS usuario (
    id_usuario      INT          NOT NULL GENERATED ALWAYS AS IDENTITY,
    username        VARCHAR(31)  NOT NULL,
    senha           CHAR(60)     NOT NULL,
    nome            VARCHAR(127)     NULL,
    sobrenome       VARCHAR(127)     NULL,
    criado_em       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em   TIMESTAMP        NULL,

    PRIMARY KEY (id_usuario),
    UNIQUE      (username)
);


CREATE TABLE IF NOT EXISTS papel_usuario (
    id_papel_usuario INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    id_usuario       INT NOT NULL,
    id_papel         INT NOT NULL,

    PRIMARY KEY (id_papel_usuario),
    FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario),
    FOREIGN KEY (id_papel)   REFERENCES papel (id_papel),
    UNIQUE      (id_usuario, id_papel)
);


CREATE TABLE IF NOT EXISTS regiao (
    id_regiao       INT         NOT NULL GENERATED ALWAYS AS IDENTITY,
    cod_ibge_regiao INT             NULL,
    nome            VARCHAR(15) NOT NULL,
    sigla           VARCHAR(2)  NOT NULL,

    PRIMARY KEY (id_regiao),
    UNIQUE      (cod_ibge_regiao),
    UNIQUE      (nome),
    UNIQUE      (sigla),
    CHECK       (cod_ibge_regiao BETWEEN 1 AND 5)
);


CREATE TABLE IF NOT EXISTS uf (
    id_uf       INT         NOT NULL GENERATED ALWAYS AS IDENTITY,
    cod_ibge_uf INT             NULL,
    nome        VARCHAR(31) NOT NULL,
    sigla       CHAR(2)     NOT NULL,
    id_regiao   INT         NOT NULL,

    PRIMARY KEY (id_uf),
    FOREIGN KEY (id_regiao) REFERENCES regiao (id_regiao),
    UNIQUE      (cod_ibge_uf),
    UNIQUE      (nome),
    UNIQUE      (sigla),
    CHECK       (cod_ibge_uf BETWEEN 11 AND 53)
);


CREATE TABLE IF NOT EXISTS municipio (
    id_municipio       INT         NOT NULL GENERATED ALWAYS AS IDENTITY,
    cod_tse_municipio  INT         NOT NULL,
    cod_ibge_municipio INT             NULL,
    nome               VARCHAR(63) NOT NULL,
    id_uf              INT         NOT NULL,

    PRIMARY KEY (id_municipio),
    FOREIGN KEY (id_uf) REFERENCES uf (id_uf),
    UNIQUE      (cod_tse_municipio),
    UNIQUE      (cod_ibge_municipio),
    UNIQUE      (nome, id_uf),
    CHECK       (cod_tse_municipio BETWEEN 1 AND 99999),
    CHECK       (cod_ibge_municipio BETWEEN 1 AND 9999999)
);


CREATE TABLE IF NOT EXISTS urna_eletronica (
    id_urna_eletronica      INT         NOT NULL GENERATED ALWAYS AS IDENTITY,
    num_serie               INT         NOT NULL,
    cod_identificacao_carga CHAR(24)    NOT NULL,
    versao_software         VARCHAR(15) NOT NULL,
    data_abertura           DATE        NOT NULL,
    horario_abertura        TIME        NOT NULL,
    data_fechamento         DATE        NOT NULL,
    horario_fechamento      TIME        NOT NULL,

    PRIMARY KEY (id_urna_eletronica),
    UNIQUE      (num_serie)
);


CREATE TABLE IF NOT EXISTS zona (
    id_zona      INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    num_tse_zona INT NOT NULL,
    id_uf        INT NOT NULL,

    PRIMARY KEY (id_zona),
    FOREIGN KEY (id_uf) REFERENCES uf (id_uf),
    UNIQUE      (num_tse_zona, id_uf),
    CHECK       (num_tse_zona BETWEEN 1 AND 9999)
);


CREATE TABLE IF NOT EXISTS local_votacao (
    id_local_votacao      INT          NOT NULL GENERATED ALWAYS AS IDENTITY,
    num_tse_local_votacao INT          NOT NULL,
    nome                  VARCHAR(255)     NULL,
    id_zona               INT          NOT NULL,
    id_municipio          INT          NOT NULL,

    PRIMARY KEY (id_local_votacao),
    FOREIGN KEY (id_zona)      REFERENCES zona (id_zona),
    FOREIGN KEY (id_municipio) REFERENCES municipio (id_municipio),
    UNIQUE      (num_tse_local_votacao, id_zona),
    CHECK       (num_tse_local_votacao BETWEEN 1 AND 9999)
);


CREATE TABLE IF NOT EXISTS secao (
    id_secao      INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    num_tse_secao INT NOT NULL,
    id_zona       INT NOT NULL,

    PRIMARY KEY (id_secao),
    FOREIGN KEY (id_zona) REFERENCES zona (id_zona),
    UNIQUE      (num_tse_secao, id_zona),
    CHECK       (num_tse_secao BETWEEN 1 AND 9999)
);


CREATE TABLE IF NOT EXISTS origem_configuracao_processo_eleitoral (
    id_origem_configuracao_processo_eleitoral INT         NOT NULL GENERATED ALWAYS AS IDENTITY,
    nome                                      VARCHAR(31) NOT NULL,
    nome_abreviado                            CHAR(3)         NULL,

    PRIMARY KEY (id_origem_configuracao_processo_eleitoral),
    UNIQUE      (nome),
    UNIQUE      (nome_abreviado)
);


CREATE TABLE IF NOT EXISTS processo_eleitoral (
    id_processo_eleitoral                     INT         NOT NULL GENERATED ALWAYS AS IDENTITY,
    cod_tse_processo_eleitoral                INT         NOT NULL,
    nome                                      VARCHAR(63)     NULL,
    id_origem_configuracao_processo_eleitoral INT             NULL,

    PRIMARY KEY (id_processo_eleitoral),
    FOREIGN KEY (id_origem_configuracao_processo_eleitoral) REFERENCES origem_configuracao_processo_eleitoral (id_origem_configuracao_processo_eleitoral),
    UNIQUE      (cod_tse_processo_eleitoral),
    CHECK       (cod_tse_processo_eleitoral BETWEEN 0 AND 99999)
);


CREATE TABLE IF NOT EXISTS pleito (
    id_pleito             INT         NOT NULL GENERATED ALWAYS AS IDENTITY,
    cod_tse_pleito        INT         NOT NULL,
    nome                  VARCHAR(63)     NULL,
    turno                 INT         NOT NULL,
    data                  DATE        NOT NULL,
    id_processo_eleitoral INT         NOT NULL,

    PRIMARY KEY (id_pleito),
    FOREIGN KEY (id_processo_eleitoral) REFERENCES processo_eleitoral (id_processo_eleitoral),
    UNIQUE      (cod_tse_pleito),
    CHECK       (cod_tse_pleito BETWEEN 0 AND 99999),
    CHECK       (turno IN (1, 2))
);


CREATE TABLE IF NOT EXISTS eleicao (
    id_eleicao                      INT         NOT NULL GENERATED ALWAYS AS IDENTITY,
    cod_tse_eleicao                 INT         NOT NULL,
    nome                            VARCHAR(63)     NULL,
    ano                             INT         NOT NULL,
    qtde_votos_cargos_majoritarios  INT             NULL,
    qtde_votos_cargos_proporcionais INT             NULL,
    id_pleito                       INT         NOT NULL,

    PRIMARY KEY (id_eleicao),
    FOREIGN KEY (id_pleito) REFERENCES pleito (id_pleito),
    UNIQUE      (cod_tse_eleicao),
    CHECK       (cod_tse_eleicao BETWEEN 0 AND 99999)
);


CREATE TABLE IF NOT EXISTS secao_processo_eleitoral (
    id_secao_processo_eleitoral INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    id_secao                    INT NOT NULL,
    id_processo_eleitoral       INT NOT NULL,
    id_local_votacao            INT NOT NULL,

    PRIMARY KEY (id_secao_processo_eleitoral),
    FOREIGN KEY (id_secao)              REFERENCES secao (id_secao),
    FOREIGN KEY (id_processo_eleitoral) REFERENCES processo_eleitoral (id_processo_eleitoral),
    FOREIGN KEY (id_local_votacao)      REFERENCES local_votacao (id_local_votacao),
    UNIQUE      (id_secao, id_processo_eleitoral)
);


CREATE TABLE IF NOT EXISTS secao_pleito (
    id_secao_pleito                         INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    id_secao                                INT NOT NULL,
    id_pleito                               INT NOT NULL,
    qtde_eleitores_aptos                    INT     NULL,
    qtde_eleitores_comparecentes            INT     NULL,
    qtde_eleitores_faltosos                 INT     NULL,
    qtde_eleitores_habilitados_por_ano_nasc INT     NULL,

    PRIMARY KEY (id_secao_pleito),
    FOREIGN KEY (id_secao)  REFERENCES secao (id_secao),
    FOREIGN KEY (id_pleito) REFERENCES pleito (id_pleito),
    UNIQUE      (id_secao, id_pleito)
);


CREATE TABLE IF NOT EXISTS agregacao_secao (
    id_agregacao_secao    INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    id_secao_principal    INT NOT NULL,
    id_secao_agregada     INT NOT NULL,
    id_processo_eleitoral INT NOT NULL,

    PRIMARY KEY (id_agregacao_secao),
    FOREIGN KEY (id_secao_principal)    REFERENCES secao (id_secao),
    FOREIGN KEY (id_secao_agregada)     REFERENCES secao (id_secao),
    FOREIGN KEY (id_processo_eleitoral) REFERENCES processo_eleitoral (id_processo_eleitoral),
    UNIQUE      (id_secao_principal, id_secao_agregada, id_processo_eleitoral)
);


CREATE TABLE IF NOT EXISTS tipo_cargo (
    id_tipo_cargo      INT         NOT NULL GENERATED ALWAYS AS IDENTITY,
    cod_tse_tipo_cargo INT         NOT NULL,
    nome               VARCHAR(15)     NULL,

    PRIMARY KEY (id_tipo_cargo),
    UNIQUE      (cod_tse_tipo_cargo)
);


CREATE TABLE IF NOT EXISTS cargo (
    id_cargo       INT         NOT NULL GENERATED ALWAYS AS IDENTITY,
    cod_tse_cargo  INT         NOT NULL,
    nome           VARCHAR(31)     NULL,
    nome_abreviado VARCHAR(15)     NULL,
    id_tipo_cargo  INT         NOT NULL,

    PRIMARY KEY (id_cargo),
    FOREIGN KEY (id_tipo_cargo) REFERENCES tipo_cargo (id_tipo_cargo),
    UNIQUE      (cod_tse_cargo)
);


CREATE TABLE IF NOT EXISTS cargo_eleicao (
    id_cargo_eleicao INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    id_cargo         INT NOT NULL,
    id_eleicao       INT NOT NULL,

    PRIMARY KEY (id_cargo_eleicao),
    FOREIGN KEY (id_cargo)   REFERENCES cargo (id_cargo),
    FOREIGN KEY (id_eleicao) REFERENCES eleicao (id_eleicao),
    UNIQUE      (id_cargo, id_eleicao)
);


CREATE TABLE IF NOT EXISTS partido (
    id_partido      INT         NOT NULL GENERATED ALWAYS AS IDENTITY,
    num_tse_partido INT         NOT NULL,
    nome            VARCHAR(63) NOT NULL,
    sigla           VARCHAR(15) NOT NULL,

    PRIMARY KEY (id_partido),
    UNIQUE      (num_tse_partido),
    CHECK       (num_tse_partido BETWEEN 0 AND 99)
);


CREATE TABLE IF NOT EXISTS candidato (
    id_candidato      INT         NOT NULL GENERATED ALWAYS AS IDENTITY,
    num_tse_candidato INT         NOT NULL,
    cod_tse_candidato VARCHAR(15) NOT NULL,
    nome              VARCHAR(63) NOT NULL,

    PRIMARY KEY (id_candidato),
    UNIQUE      (cod_tse_candidato),
    CHECK       (num_tse_candidato BETWEEN 1 AND 99999)
);


CREATE TABLE IF NOT EXISTS fase (
    id_fase      INT         NOT NULL GENERATED ALWAYS AS IDENTITY,
    cod_tse_fase INT         NOT NULL,
    nome         VARCHAR(15) NOT NULL,

    PRIMARY KEY (id_fase),
    UNIQUE      (cod_tse_fase),
    UNIQUE      (nome),
    CHECK       (cod_tse_fase IN (1, 2, 3))
);


CREATE TABLE IF NOT EXISTS candidatura (
    id_candidatura    INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    id_candidato      INT NOT NULL,
    id_cargo_eleicao  INT NOT NULL,
    id_partido        INT NOT NULL,

    PRIMARY KEY (id_candidatura),
    FOREIGN KEY (id_candidato)     REFERENCES candidato (id_candidato),
    FOREIGN KEY (id_cargo_eleicao) REFERENCES cargo_eleicao (id_cargo_eleicao),
    FOREIGN KEY (id_partido)       REFERENCES partido (id_partido),
    UNIQUE      (id_candidato, id_cargo_eleicao)
);


CREATE TABLE IF NOT EXISTS origem_boletim_urna (
    id_origem_boletim_urna INT         NOT NULL GENERATED ALWAYS AS IDENTITY,
    nome                   VARCHAR(31) NOT NULL,
    nome_abreviado         VARCHAR(7)  NOT NULL,

    PRIMARY KEY (id_origem_boletim_urna),
    UNIQUE      (nome),
    UNIQUE      (nome_abreviado)
);


CREATE TABLE IF NOT EXISTS boletim_urna (
    id_boletim_urna        INT       NOT NULL GENERATED ALWAYS AS IDENTITY,
    id_secao_pleito        INT       NOT NULL,
    id_fase                INT       NOT NULL,
    id_origem_boletim_urna INT       NOT NULL,
    id_urna_eletronica     INT       NOT NULL,
    assinatura             CHAR(128) NOT NULL,
    qtde_total_qr_codes    INT       NOT NULL,
    data_emissao           DATE          NULL,
    horario_emissao        TIME          NULL,

    PRIMARY KEY (id_boletim_urna),
    FOREIGN KEY (id_secao_pleito)        REFERENCES secao_pleito (id_secao_pleito),
    FOREIGN KEY (id_fase)                REFERENCES fase (id_fase),
    FOREIGN KEY (id_origem_boletim_urna) REFERENCES origem_boletim_urna (id_origem_boletim_urna),
    FOREIGN KEY (id_urna_eletronica)     REFERENCES urna_eletronica (id_urna_eletronica),
    UNIQUE      (id_secao_pleito),
    UNIQUE      (assinatura)
);


CREATE TABLE IF NOT EXISTS qr_code_boletim_urna (
    id_qr_code_boletim_urna                   INT       NOT NULL GENERATED ALWAYS AS IDENTITY,
    id_boletim_urna                           INT       NOT NULL,
    cabecalho                                 TEXT      NOT NULL,
    conteudo                                  TEXT      NOT NULL,
    hash                                      CHAR(128) NOT NULL,
    indice                                    INT       NOT NULL,
    num_ciclos_eleitorais_desde_implementacao INT           NULL,
    num_revisoes_formato_ciclo                INT           NULL,
    num_versao_chave_assinatura               INT           NULL,

    PRIMARY KEY (id_qr_code_boletim_urna),
    FOREIGN KEY (id_boletim_urna) REFERENCES boletim_urna (id_boletim_urna),
    UNIQUE      (indice, id_boletim_urna),
    UNIQUE      (hash)
);


CREATE TABLE IF NOT EXISTS boletim_urna_usuario (
    id_boletim_urna_usuario INT       NOT NULL GENERATED ALWAYS AS IDENTITY,
    id_boletim_urna         INT       NOT NULL,
    id_usuario              INT       NOT NULL,
    coletado_em             TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em           TIMESTAMP     NULL,

    PRIMARY KEY (id_boletim_urna_usuario),
    FOREIGN KEY (id_boletim_urna) REFERENCES boletim_urna (id_boletim_urna),
    FOREIGN KEY (id_usuario)      REFERENCES usuario (id_usuario),
    UNIQUE      (id_boletim_urna, id_usuario)
);


CREATE TABLE IF NOT EXISTS apuracao_votos_candidatura_boletim_urna (
    id_apuracao_votos_candidatura_boletim_urna INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    id_candidatura                             INT NOT NULL,
    id_boletim_urna                            INT NOT NULL,
    total_votos_apurados                       INT NOT NULL,

    PRIMARY KEY (id_apuracao_votos_candidatura_boletim_urna),
    FOREIGN KEY (id_candidatura)  REFERENCES candidatura (id_candidatura),
    FOREIGN KEY (id_boletim_urna) REFERENCES boletim_urna (id_boletim_urna),
    UNIQUE      (id_candidatura, id_boletim_urna)
);


CREATE TABLE IF NOT EXISTS apuracao_votos_cargo_boletim_urna (
    id_apuracao_votos_cargo_boletim_urna     INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    id_cargo_eleicao                         INT NOT NULL,
    id_boletim_urna                          INT NOT NULL,
    qtde_eleitores_aptos                     INT NOT NULL,
    qtde_comparecimento_cargo_sem_candidatos INT     NULL,
    qtde_votos_nominais                      INT NOT NULL,
    qtde_votos_de_legenda                    INT     NULL,
    qtde_votos_em_branco                     INT NOT NULL,
    qtde_votos_nulos                         INT NOT NULL,
    total_votos_apurados                     INT NOT NULL,

    PRIMARY KEY (id_apuracao_votos_cargo_boletim_urna),
    FOREIGN KEY (id_cargo_eleicao) REFERENCES cargo_eleicao (id_cargo_eleicao),
    FOREIGN KEY (id_boletim_urna)  REFERENCES boletim_urna (id_boletim_urna),
    UNIQUE      (id_cargo_eleicao, id_boletim_urna)
);


CREATE TABLE IF NOT EXISTS apuracao_votos_partido_boletim_urna (
    id_apuracao_votos_partido_boletim_urna INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    id_partido                             INT NOT NULL,
    id_boletim_urna                        INT NOT NULL,
    id_cargo_eleicao                       INT NOT NULL,
    qtde_votos_de_legenda                  INT NOT NULL,
    total_votos_apurados                   INT NOT NULL,

    PRIMARY KEY (id_apuracao_votos_partido_boletim_urna),
    FOREIGN KEY (id_partido)       REFERENCES partido (id_partido),
    FOREIGN KEY (id_boletim_urna)  REFERENCES boletim_urna (id_boletim_urna),
    FOREIGN KEY (id_cargo_eleicao) REFERENCES cargo_eleicao (id_cargo_eleicao),
    UNIQUE      (id_partido, id_boletim_urna, id_cargo_eleicao)
);


CREATE OR REPLACE FUNCTION fn_atualizar_coluna_atualizado_em() RETURNS TRIGGER AS
'
BEGIN
    NEW.atualizado_em := CURRENT_TIMESTAMP;

    RETURN NEW;
    END;
'
LANGUAGE plpgsql;


CREATE TRIGGER tr_atualizar_coluna_atualizado_em
BEFORE UPDATE ON usuario
FOR EACH ROW EXECUTE PROCEDURE fn_atualizar_coluna_atualizado_em();


CREATE TRIGGER tr_atualizar_coluna_atualizado_em
BEFORE UPDATE ON boletim_urna_usuario
FOR EACH ROW EXECUTE PROCEDURE fn_atualizar_coluna_atualizado_em();
