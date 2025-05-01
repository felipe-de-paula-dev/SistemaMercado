# 🛒 Sistema de Vendas para Pequenos Comércios

Este projeto é uma aplicação web completa (backend + frontend) desenvolvida para agilizar o processo de vendas em pequenos comércios, permitindo o cadastro e gerenciamento de produtos, clientes e vendas.

## 📌 Funcionalidades

- CRUD de **Produtos**, **Clientes** e **Vendas**
- Validação de **CPF/CNPJ** na criação de clientes
- Frontend intuitivo e responsivo com HTML, CSS e JavaScript
- Backend REST orientado a objetos com banco de dados relacional
- Frontend intuitivo e responsivo
- Exportação de relatórios em `.xlsx` com filtros por data e cliente

---

## ⚙️ Tecnologias Utilizadas

### 🖥️ Frontend

- Angular
- Tailwind CSS

### 🔧 Backend

- Java + Spring Boot
- Spring Data JPA
- PostgreSQL (usando Neon Database)
- Maven
- Flyway (migrations)
- Spring Security (autenticação e autorização)
- JUnit (testes unitários)

### 🐳 Docker

- Containers para frontend e backend

### Relatórios:

- Exportação em `.xlsx` com filtros por data e cliente

---

## 🚀 Como Rodar o Projeto com Docker

### 1. Clonar o Repositório

```bash
git clone https://github.com/felipe-de-paula-dev/sistemamercado
cd sistema-vendas
```

### 2. Conecte o projeto ao banco de dados

Vá Até o Diretório

```bash
cd back-end/src/main/resources
```

Abra o application.properties e
conecte seu db mudando o `spring.datasource.url=`**seuDb**

Observação: Este projeto utiliza o banco de dados Neon (PostgreSQL na nuvem). Portanto, você pode usar apenas a URL fornecida pelo Neon como spring.datasource.url, e completar com seu usuário e senha, se necessário.

### 3. Rodar Docker FrontEnd

```bash
- cd front-end
- docker build -t nome-da-imagem .
- docker run -p 80:4200 nome-da-imagem
```

### 4. Rodar Docker BackEnd

```bash
- cd back-end
- docker build -t nome-da-imagem .
- docker run -p 8080:8080 nome-da-imagem
```
