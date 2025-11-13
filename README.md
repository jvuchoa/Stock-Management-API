Stock Management API
Sistema de Gerenciamento de Estoque com Autenticação JWT e Controle de Acesso Baseado em Roles
Sobre o Projeto

API REST desenvolvida em Spring Boot para gerenciamento completo de estoque de produtos, com sistema robusto de autenticação e autorização.
O projeto implementa controle de acesso granular através de roles (ADMIN, SELLER, CUSTOMER) e oferece funcionalidades de CRUD para produtos, categorias e controle de estoque.

Equipe de Desenvolvimento

João Victor de Lima Uchôa

Eugenia Rodrigues de Carvalho Vieira de Melo Guedes

Joab Pereira da Silva Júnior

João Gabriel

Tecnologias Utilizadas

Java 21

Spring Boot 3.5.7

Spring Data JPA

Spring Security (JWT Authentication)

H2 Database (ambiente de desenvolvimento)

Lombok

Maven

Funcionalidades
Autenticação e Autorização

Login com geração de token JWT

Refresh token para renovação de sessão

Controle de acesso baseado em roles:

ADMIN: Criar, editar e deletar produtos, categorias e gerenciar o sistema

SELLER: Cadastrar e editar produtos próprios

CUSTOMER: Visualizar catálogo de produtos

Gestão de Categorias

CRUD completo de categorias

Suporte a hierarquia (categoria pai → filho)

Validação de unicidade de nome no mesmo nível

Proteção de endpoints por role (apenas ADMIN)

Gestão de Produtos

CRUD de produtos

Relacionamento obrigatório com categorias

Controle de permissões por role

Validações de integridade

Controle de Estoque

Atualização de quantidade em estoque

Validações de estoque mínimo

Histórico de movimentações

Endpoints da API
Autenticação
POST /auth/login      # Autenticação e geração de token JWT  
POST /auth/refresh    # Renovação de token  
GET  /auth/me         # Informações do usuário autenticado  

Categorias
GET    /categories           # Listar todas as categorias (PUBLIC)  
GET    /categories/{id}      # Buscar categoria por ID (PUBLIC)  
POST   /categories           # Criar nova categoria (ADMIN)  
PUT    /categories/{id}      # Atualizar categoria (ADMIN)  
DELETE /categories/{id}      # Deletar categoria (ADMIN)  

Produtos
GET    /products             # Listar produtos (PUBLIC)  
GET    /products/{id}        # Buscar produto por ID (PUBLIC)  
POST   /products             # Criar produto (ADMIN/SELLER)  
PUT    /products/{id}        # Atualizar produto (ADMIN/SELLER)  
DELETE /products/{id}        # Deletar produto (ADMIN)  
PATCH  /products/{id}/stock  # Atualizar estoque (ADMIN/SELLER)  
