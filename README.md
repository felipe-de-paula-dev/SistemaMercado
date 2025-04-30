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
git clone https://github.com/seu-usuario/sistema-vendas.git
cd sistema-vendas
```

### 2. Rodar Docker FrontEnd

- cd front-end
- docker build -t nome-da-imagem .
- docker run -p 80:4200 nome-da-imagem

### 2. Rodar Docker BackEnd

- cd back-end
- docker build -t nome-da-imagem .
- docker run -p 8080:8080 nome-da-imagem
