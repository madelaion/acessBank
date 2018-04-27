# valepresente-teste #

### Sobre o projeto ###
Aplicação Java Web com as soluções do teste da valepresente. Além de código fonte e configurações, eu compartilho uma pequena documentação descrevendo como o projeto foi desenvolvido, e quais foram as estratégias para implementar os exercícios solicitados.

Essa aplicação foi desenvolvida seguindo os modelos arquiteturais MVC e REST. Conta com um banco de dados e ambiente de execução embutidos. 

Esse README é parte de uma pequena documentação técnica descrevendo características do projeto, e estratégias de implementação das soluções propostas.

### Tecnologias Utilizadas ###

* Java versão 8.
* Google Guava: lib complementar Java, utilizada para definir pre-condições e geração de Equals / Hashcode.
* JPA / Hibernate: mapeamento de entidades persistentes em pojos de domínio, resolvendo o ORM, abstraindo a escrita de código SQL (DDL e DML).
* Bean Validations: framework para definição de regras de validação em entidades JPA via anotações.
* Logback: geração de logs.
* Spring Data JPA: tecnologia responsável por gerar boa parte do código relacionado a camada de persistência. No nível da aplicação eu escrevo os contratos de persistência, que funcionam como ponto de partida para a criação dos comandos de manipulação (CRUD), consultas simples e sofisticadas.
* Spring Web MVC: framework web usado como solução MVC e para a definição de componentes seguindo o modelo arquitetural REST.
* Jackson: API para conversão de dados Java em Json e vice-versa.
* Thymeleaf: engine para geração de páginas Web baseadas na definição de templates e fragmentos, que é integrável ao Spring Web MVC. Uso essa tecnologia para criar páginas HTML da aplicação.
* JQuery: acesso a camada REST via Ajax, de forma stateless, responsável pela manipulação desses dados na estrutura HTML.
* Bootstrap: solução CSS.

As configurações core para execução do projeto, ocorrem de forma programática. Um detalhe importante a respeito do front, é que a integração das páginas com os dados é stateless e ocorre de forma assíncrona, sempre utilizando os serviços REST. As labels das páginas foram definidas no arquivo src/main/resources/messages_pt_BR.properties.

### Camadas e pacotes ###

* br.com.valepresente.boot: Pacote com as configurações necessárias para o start da aplicação.
* br.com.valepresente.domain: Pacote formado pelas entidades persistentes, mapeadas com anotações JPA.
* br.com.valepresente.exception: Pacote conta com uma exceção customizada e com uma classe util para ler mensagens de erro.
* br.com.valepresente.repository: Pacote formado pelos contratos de persistência.
* br.com.valepresente.service: Pacote formado por componentes de negócio, que orquestram os componentes de acesso a dados, transação com banco de dados e eventuais validações.
* br.com.valepresente.web: Pacote com os componentes Controller e serviços REST.
* src/main/resources/templates: Definição do layout principal e das páginas do projeto.
* src/main/resources/static: Arquivos css, js, fonts e ico.


### Tecnologias Complementares ###

* Banco de dados: HSQLDB embutido na aplicação. O banco é criado durante o startup da aplicação. O arquivo src/main/resources/dataConta.sql é um script para inserção de dados nas tabelas do banco. Esses dados  são utilizados nos componentes de testes (mock) e ficam disponíveis durante a navegação pelas páginas web. No fim da execução o banco é destruído.
* Testes: os testes são definidos como Use Case do JUnit. Os testes dos serviços REST contam com: o próprio Spring Web MVC para mock da infra-estrutura web; JsonPath e hamcrest para acesso e assertions no conteúdo Json. Os testes foram disponibilizados na estrutura src/test/java.
* Spring Boot: tecnologia utilizada para criar um ambiente embutido de execução, simplificar o uso de tecnologias Spring e controlar o escopo do banco. No arquivo src/main/resources/application.properties constam algumas propriedades do Spring Boot para o projeto.
* Tomcat embutido: disponibilizado pelo Spring Boot.
* Maven: gestão de ciclo de vida e build do projeto.


### Pré-requisitos ###

* JDK - versão 1.8 do Java;
* Qualquer IDE Java com suporte ao Maven;
* Maven - para build e dependências.


Após baixar os fontes, para executar a aplicação execute o comando maven:
```
#!bash
$ mvn clean package spring-boot:run
```