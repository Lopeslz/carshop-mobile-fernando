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



## Como rodar a aplicação
1️⃣Rodar a API (Backend)
Clone o repositório da API (exemplo):

bash
Copiar
Editar
git clone https://github.com/SEU_REPO_API.git
Abra o projeto no IntelliJ IDEA

Configure o application.properties com as credenciais corretas do seu MySQL:

spring.datasource.url=jdbc:mysql://localhost:3306/carshop
spring.datasource.username="usuario que você usa de root AQUI"
spring.datasource.password="SENHA QUE VOCÊ USA DE ROOT AQUI"
spring.jpa.hibernate.ddl-auto=update
Rode a aplicação (CarshopApiApplication)

A API estará disponível localmente em:
http://localhost:8080/api

2️⃣ Rodar o App Flutter (Mobile)
Clone este repositório:

git clone https://github.com/Lopeslz/carshop-mobile-fernando.git

Abra o projeto no Android Studio ou VSCode.

Rode:
flutter pub get
flutter run
⚠️ Importante: Para rodar corretamente, o app espera que a API esteja rodando em:
http://10.0.2.2:8080/api
Essa URL funciona no emulador Android.
Se for rodar em dispositivo físico, você deverá configurar a URL para apontar para o IP local da sua máquina.

As imagens de perfil e imagens de carros são armazenadas localmente na pasta /uploads.

É necessário criar a pasta uploads na raiz da API ou configurar um path apropriado.

```sql
CREATE DATABASE carshop DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;


