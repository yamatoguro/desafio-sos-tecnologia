# Desafio SOS Tecnologia

## Table of Contents
- [Funcionalidades](#about)
- [Endpoints](#endpoints)
- [Considerações](#consideracoes)

## Sobre <a name = "about"></a>
A aplicação consiste no controle de patrimônios de uma empresa, o usuário pode cadastrar patrimônios que são associados a marcas em relação de one(marca) to many(patrimonios) e pode cadastrar marcas. Para tanto o usuário consulta através de uma lista de cada uma das entidades e os cadastra ou edita através de um formulário simples. A deleção e a seleção do patrimônio ou marca a ser modificado(a) acontece por meio de um menu dropdown que lista por nome os dados mostrados na tabela.

## Endpoints <a name = "endpoints"></a>
```
PatrimonioController
├─@method GET /patrimonios
│    └─@return Obter todos os patrimônios
│
├─@method GET /patrimonios/{id}
│    └─@return Obter um patrimônio por ID
│
├─@method POST /patrimonios
│    └─@return Inserir um novo patrimônio
│
├─@method PUT /patrimonios/{id}
│    └─@return Alterar os dados de um patrimônio
│
└─@method DELETE /patrimonios/{id}
    └─@return Excluir um patrimônio
```
```
MarcaController
├─@method GET /marcas
│    └─@return Obter todos os marcas
│
├─@method GET /marcas/{id}
│    └─@return Obter um marca por ID
│
├─@method POST /marcas
│    └─@return Inserir um novo marca
│
├─@method PUT /marcas/{id}
│    └─@return Alterar os dados de um marca
│
└─@method DELETE /marcas/{id}
    └─@return Excluir um marca
```
```
FileController
├─@method POST /upload
│    └─@return Salva um arquivo na API
│
└─@method GET /arquivos/{filename:.+}
    └─@return Baixa um arquivo da API
```
## Considerações <a name = "consideracoes"></a>
---

### Compile & Run

Para gerenciamento das dependências foi usado o Maven, API desenvolvida em Spring Boot e frontend em JSF.
O frontend em especial foi desenvolvido usando um servidor de aplicações WildFly 19.

### Uso

#### Cadastro e Edição

O cadastro e a edição acontecem por meio de um form simples com os campos que podem ser editados pelo usuário

```
Patrimônio
• Nome - obrigatório
• Marca - obrigatório
• Descrição
• Arquivo
```
<img height=300px src="Imagens\formulario-de-patrimonio.PNG" alt="">

```
Marca
• Nome - obrigatório
• Arquivo
```
<img height=200px src="Imagens\formulario-de-marca.PNG" alt="">

#### Deleção

Para excluir uma marca ou um patrimônio basta selecionar no menu abaixo da tabela presente nas páginas de lista de cada entidade

<img height=200px src="Imagens\menu-de-edicao-delecao.PNG" alt="">

#### Arquivos

Para o upload dos arquivos existe um botão para tal no formulário de cada entidade, para o download existe um botão na tabela que lista as entidades

<img height=50px src="Imagens\patrimonio-row.PNG" alt="">

### Considerações Finais

Houveram dificuldades no desenvolvimento por ser a primeira vez que trabalho com o framework JSF, mas com o tempo foi possível sanar essas dificuldades e chegar a este ponto do desenvolvimento. É uma aplicação básica e não tem o intuito de ser exageradamente refinada, foi focada na funcionalidade, apesar disso existem ainda alguns bugs que não pude resolver a tempo, um exemplo é o upload e download de arquivos, que funciona se não houverem acentos no nome do arquivo.
---

- Iago Faria dos Reis
- oiago@hotmail.com
- +55 61 99935-4110