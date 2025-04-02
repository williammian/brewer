# Brewer

**Brewer** é um sistema web desenvolvido para gerenciar pedidos de venda de cervejas e representantes comerciais. O projeto utiliza tecnologias modernas para oferecer uma interface intuitiva e funcionalidades robustas.

## Funcionalidades

- **Gerenciamento de Pedidos**: Permite o cadastro, edição e acompanhamento de pedidos de venda.
- **Controle de Representantes**: Administra informações dos representantes comerciais e suas respectivas vendas.
- **Dashboard Interativo**: Fornece uma visão geral das vendas e desempenho através de gráficos e indicadores.
- **Relatórios**: Gera relatórios detalhados utilizando JasperReports.

## Tecnologias Utilizadas

### Backend
- **Spring MVC**
- **Spring Security**
- **Spring Data**
- **JPA/Hibernate**

### Frontend
- **Thymeleaf**
- **JavaScript**
- **Bootstrap**
- **CSS**

### Relatórios
- **JasperReports**

## Estrutura do Projeto

O repositório está organizado da seguinte forma:

- **src**: Contém o código-fonte principal da aplicação.
- **others**: Inclui arquivos e recursos adicionais relacionados ao projeto.
- **Procfile**: Arquivo de configuração para implantação em plataformas como Heroku.
- **pom.xml**: Arquivo de configuração do Maven, contendo as dependências e plugins do projeto.

## Configuração e Execução

### Pré-requisitos

- **Java Development Kit (JDK) 8 ou superior**
- **Maven**
- **Banco de dados compatível com JPA/Hibernate** (por exemplo, MySQL ou PostgreSQL)

### Passos para Execução

1. **Clone o repositório**:

   ```bash
   git clone https://github.com/williammian/brewer.git
   ```

2. **Configure o banco de dados**:

   Atualize as configurações de conexão com o banco de dados no arquivo `application.properties`, localizado em `src/main/resources`.

3. **Instale as dependências e compile o projeto**:

   ```bash
   mvn clean install
   ```

4. **Execute a aplicação**:

   ```bash
   mvn spring-boot:run
   ```

   A aplicação estará disponível em `http://localhost:8080`.


## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).

---
