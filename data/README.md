# data

## Preparação do ambiente

```bash
# cria um ambiente virtual
python -m venv venv

# acessa o ambiente virtual (linux)
source venv/bin/activate

# acessa o ambiente virtual (windows)
.\venv\Scripts\activate

# atualiza o gerenciador de pacotes do python
python -m pip install --upgrade pip

# instala as dependências necessárias
pip install -r requirements.txt
```

Se tudo ocorreu como o esperado, agora você deve conseguir ver o nome do ambiente virtual prefixado ao seu `host` no terminal. Por exemplo:

```bash
(venv) joao@silva:~$
```

Caso você deseje sair do ambiente virtual, é só executar o comando `deactivate`.
