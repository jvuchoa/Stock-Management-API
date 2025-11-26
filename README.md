# Stock Management API

## Sistema de gerenciamento de estoque com autenticação JWT e controle de acesso baseado em roles.


### Sobre o Projeto
- API REST desenvolvida em Spring Boot para gerenciamento completo de estoque de produtos, com sistema robusto de autenticação e autorização. O projeto implementa controle de acesso granular através de roles (ADMIN, SELLER, CUSTOMER) e oferece funcionalidades de CRUD para produtos, categorias e controle de estoque.

### Equipe de Desenvolvimento

- João Victor de Lima Uchôa
- Eugenia Rodrigues de Carvalho Vieira de Melo Guedes
- Joab Pereira da Silva Júnior
- João Gabriel Gonçalves de Lima


### Tecnologias Utilizadas

- Java 21
- Spring Boot 3.5.7
- Spring Data JPA
- Spring Security (JWT Authentication)
- H2 Database (desenvolvimento)
- Lombok
- Maven


## Funcionalidades
🔐 Autenticação e Autorização

- Login com geração de token JWT
- Refresh token para renovação de sessão
- Controle de acesso baseado em roles:

- ADMIN → Criar/editar/deletar produtos, categorias e gerenciar sistema
- SELLER → Cadastrar e editar produtos próprios
- CUSTOMER → Visualizar catálogo de produtos



### Gestão de Categorias

- CRUD completo de categorias
- Suporte a hierarquia (categoria pai → filho)
- Validação de unicidade de nome no mesmo nível
- Proteção de endpoints por role (apenas ADMIN)

### Gestão de Produtos

- CRUD de produtos
- Relacionamento obrigatório com categorias
- Controle de permissões por role
- Validações de integridade

## Gestão de Estoque 

- Atualização de quantidade em estoque
- Acesso permitido para ADMIN e SELLER

## Como iniciar o Projeto

1.Clonar o Repositório
- git clone https://github.com/jvuchoa/Stock-Management-API.git
- cd Stock-Management-API
  
2. Gere o Keystore para JWT (RSA)
- Execute no terminal:
keytool -genkeypair -alias minhaChaveRSA -keyalg RSA -keysize 2048 -keystore meuKeystore.jks -storepass senha123
- No Linux/Mac:
keytool -genkeypair -alias minhaChaveRSA -keyalg RSA -keysize 2048 -keystore src/main/resources/meuKeystore.jks -storepass senha123 -validity 3650
- Durante a execução, pressione ENTER em todas as perguntas para usar valores padrão.
O arquivo meuKeystore.jks será criado em src/main/resources/.

3️. Compilar o projeto
mvn clean install

4️. Executar a aplicação
mvn spring-boot:run

5️. Acessar a aplicação

API: http://localhost:8080

## 🗄️ Console H2 Database

URL: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:produtosdb

Username: sa

Password: (deixe vazio)]

## 👤 Usuários Padrão
| Usuário  | Senha       | Role     | Permissões                   |
| -------- | ----------- | -------- | ---------------------------- |
| admin    | admin123    | ADMIN    | Acesso total                 |
| seller   | seller123   | SELLER   | Gerenciar produtos e estoque |
| customer | customer123 | CUSTOMER | Apenas visualização          |


##  Endpoints da API

###  Autenticação
```http
POST /auth/login      → Autenticação e geração de token JWT  
POST /auth/refresh    → Renovação de token  
GET  /auth/me         → Informações do usuário autenticado  

httpGET    /products              # Listar produtos (PUBLIC)
GET    /products/{id}         # Buscar produto por ID (PUBLIC)
POST   /products              # Criar produto (ADMIN/SELLER)
PUT    /products/{id}         # Atualizar produto (ADMIN/SELLER)
DELETE /products/{id}         # Deletar produto (ADMIN)
PATCH  /products/{id}/stock   # Atualizar estoque (ADMIN/SELLER)

Categorias
GET    /categories          → Listar todas as categorias (PUBLIC)  
GET    /categories/{id}     → Buscar categoria por ID (PUBLIC)  
POST   /categories          → Criar nova categoria (ADMIN)  
PUT    /categories/{id}     → Atualizar categoria (ADMIN)  
DELETE /categories/{id}     → Deletar categoria (ADMIN)

Produtos

GET    /products            → Listar produtos (PUBLIC)  
GET    /products/{id}       → Buscar produto por ID (PUBLIC)  
POST   /products            → Criar produto (ADMIN / SELLER)  
PUT    /products/{id}       → Atualizar produto (ADMIN / SELLER)  
DELETE /products/{id}       → Deletar produto (ADMIN)  
PATCH  /products/{id}/stock → Atualizar estoque (ADMIN / SELLER)




