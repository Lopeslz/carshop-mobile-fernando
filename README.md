# CarShop Mobile - Projeto API e App Flutter

Este projeto é um **aplicativo mobile** desenvolvido em Flutter para a disciplina de Desenvolvimento de API com Banco de Dados.  
Ele consome uma API REST feita em Java Spring Boot e utiliza um banco de dados **MySQL**.

## 📋 Requisitos

- Java 17+ (recomendado Corretto ou OpenJDK)
- Maven 3.8+
- MySQL Server 8.0+
- Flutter SDK 3.x.x (recomendado a versão compatível com seu ambiente atual)
- Android Studio ou IntelliJ IDEA (para rodar o app mobile)
- Git

## ⚙️ Banco de Dados

- Banco: `carshop`
- Usuário: `root`  
- Senha: `root`  
*(Altere se for diferente na sua máquina)*

Importante: o banco já deve estar criado antes de rodar a aplicação:

```sql
CREATE DATABASE carshop DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
