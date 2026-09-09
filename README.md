# 🐾 PetPulse API

API REST desenvolvida com Spring Boot para gestão de pets, tutores, histórico clínico, dispositivos IoT e alertas inteligentes, com autenticação JWT e integração com banco Oracle.

---

## 👥 Membros da equipe

- Pietro Paranhos Wilhelm — RM561378
- João Vitor Biribi — RM565594
- Pedro Matos — RM564184
- Gabriel Neris Losano — RM564093

---

## 📌 Sobre o projeto

O PetPulse é uma solução voltada para o bem-estar animal, centralizando informações de pets, tutores, saúde, sensores e alertas em uma única API. A plataforma permite:

- cadastro e consulta de tutores e pets;
- registro de histórico clínico e informações de saúde;
- manutenção de espécies, raças, portes, estados e cidades;
- gerenciamento de dispositivos IoT e leituras;
- geração de alertas inteligentes com base em dados coletados;
- autenticação por JWT com chaves RSA.

---

## 🧰 Tecnologias utilizadas

- Java 17
- Spring Boot 4.0.6
- Spring Data JPA
- Spring Security + OAuth2 Resource Server
- JWT com chaves RSA
- Oracle Database
- Flyway
- Springdoc OpenAPI (Swagger)
- Spring Boot Actuator
- Lombok
- Maven

---

## 🏗️ Estrutura do projeto

```text
src/
├── main/
│   ├── java/fiap/com/br/petpulse/
│   │   ├── assembler/
│   │   ├── config/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── enums/
│   │   ├── model/
│   │   ├── repositories/
│   │   ├── security/
│   │   ├── service/
│   │   └── validation/
│   └── resources/
│       ├── application.properties
│       ├── certs/
│       └── db/migration/
└── test/
```

---

## 🔐 Autenticação e segurança

A API usa autenticação JWT assinada com RSA.

- Endpoint público de login: `POST /login`
- Endpoint público de cadastro: `POST /tutors`
- A maioria dos demais endpoints exige token válido no header:

```http
Authorization: Bearer <token>
```

Os arquivos de chave ficam em:

- `src/main/resources/keys`
- `src/main/resources/keys`

A configuração de segurança está em:

- `src/main/java/fiap/com/br/petpulse/config/SecurityConfig.java`

---

## 📚 Endpoints principais

A API expõe recursos para:

- `/login`
- `/tutors`
- `/tutor-phones`
- `/tutor-addresses`
- `/states`
- `/cities`
- `/professionals`
- `/pets`
- `/species`
- `/breeds`
- `/pet-sizes`
- `/clinical-histories`
- `/iot-devices`
- `/iot-readings`
- `/smart-alerts`

A documentação completa dos endpoints está disponível via Swagger após a aplicação subir.

---

## 📖 Swagger

Com a aplicação em execução, acesse:

```text
http://localhost:8080/swagger-ui/index.html
```

Ou a documentação OpenAPI em:

```text
http://localhost:8080/v3/api-docs
```

---

## ⚙️ Como executar

### Pré-requisitos

- Java 17+
- Maven 3.8+
- Acesso a um banco Oracle configurado

### Configuração do banco e JWT

Configure as propriedades do banco e das chaves RSA em `src/main/resources/application.properties`.

Exemplo:

```properties
spring.datasource.url=jdbc:oracle:thin:@<host>:1521:<service>
spring.datasource.username=<usuario>
spring.datasource.password=<senha>
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

rsa.public-key=classpath:keys/public_key.pem
rsa.private-key=classpath:keys/private_key.pem
```

### Executar localmente

Linux/macOS:

```bash
./mvnw spring-boot:run
```

Windows:

```cmd
mvnw.cmd spring-boot:run
```

A API ficará disponível em:

```text
http://localhost:8080
```

---

## 🗄️ Banco de dados

A aplicação utiliza Oracle como banco principal e Flyway para versionamento e migração do schema.

Os scripts de migração ficam em:

```text
src/main/resources/db/migration/
```

---

## 📊 Monitoramento

A API expõe endpoints do Spring Boot Actuator, incluindo healthcheck.

```text
http://localhost:8080/actuator/health
```

---

## 🧪 Testes

Para rodar a suíte de testes:

```bash
./mvnw test
```

---

## 🚀 Contexto do desafio

Projeto desenvolvido para o Challenge FIAP — 2TDSPG.

