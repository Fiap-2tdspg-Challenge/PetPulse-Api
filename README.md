# 🐾 PetPulse API

API REST desenvolvida com **Spring Boot** para gerenciamento de pets, tutores, histórico clínico, dispositivos IoT e alertas inteligentes — a carteira digital do seu pet.

---
## Membros da Equipe
Pietro Paranhos Wilhelm rm561378
João Vitor Biribi rm565594
Pedro Matos rm564184
Gabriel Neris Losano rm564093
Felipe Monte rm562019


## 📋 Sobre o Projeto

O **PetPulse** é uma plataforma voltada ao bem-estar animal que centraliza informações sobre pets, seus tutores e dados de saúde. Através da integração com dispositivos IoT, o sistema é capaz de gerar alertas inteligentes baseados em dados coletados em tempo real.

---

## 🚀 Tecnologias Utilizadas

| Tecnologia | Versão |
|---|---|
| Java | 17 |
| Spring Boot | 4.0.6 |
| Spring Data JPA | - |
| Spring Web MVC | - |
| H2 Database | - |
| Lombok | - |
| SpringDoc OpenAPI (Swagger) | 3.0.2 |
| Spring Boot Actuator | - |
| Spring Boot Admin Client | 4.0.3 |
| Spring Cache | - |

---

## 🏗️ Estrutura do Projeto

```
src/
└── main/
    ├── java/fiap/com/br/petpulse/
    │   ├── config/          # Configurações (Swagger, etc.)
    │   ├── controller/      # Camada de entrada (endpoints REST)
    │   ├── dto/             # Objetos de transferência de dados (Request/Response)
    │   ├── enums/           # Enumerações do domínio
    │   ├── model/           # Entidades JPA
    │   ├── repositories/    # Interfaces de acesso ao banco de dados
    │   ├── service/         # Regras de negócio
    │   └── validation/      # Tratamento global de erros
    └── resources/
        └── application.properties
```

---

## 📦 Entidades Principais

- **Tutor** — Responsável pelo pet (nome, contato, etc.)
- **Pet** — Animal cadastrado (espécie, raça, peso, sexo, doenças conhecidas, etc.)
- **ClinicalHistory** — Histórico clínico do pet (consultas, vacinas, exames, etc.)
- **IoTDevice** — Dispositivos inteligentes vinculados ao pet
- **SmartAlert** — Alertas gerados automaticamente com base em dados IoT ou eventos clínicos

---

## 🔗 Endpoints Disponíveis

| Recurso | Base URL |
|---|---|
| Tutores | `/tutors` |
| Pets | `/pets` |
| Histórico Clínico | `/clinical-history` |
| Dispositivos IoT | `/iot-devices` |
| Alertas Inteligentes | `/smart-alerts` |

> A documentação completa dos endpoints está disponível via **Swagger UI** após subir a aplicação.

---

## 📖 Documentação da API (Swagger)

Com a aplicação em execução, acesse:

```
http://localhost:8080/swagger-ui.html
```

---

## ⚙️ Como Executar

### Pré-requisitos

- Java 17+
- Maven 3.8+

### Rodando a aplicação

```bash
# Clonar o repositório
git clone <url-do-repositorio>
cd PetPulse-Api

# Executar com Maven Wrapper
./mvnw spring-boot:run
```

No Windows:

```cmd
mvnw.cmd spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

---

## 🗄️ Banco de Dados

A aplicação utiliza o banco de dados **H2** em memória, ideal para desenvolvimento e testes.

Console H2 (quando habilitado):

```
http://localhost:8080/h2-console
```

---

## 📊 Monitoramento

A API conta com o **Spring Boot Actuator** para monitoramento de saúde e métricas, além de integração com o **Spring Boot Admin**.

Endpoints de actuator disponíveis em:

```
http://localhost:8080/actuator
```

---

## 🧪 Testes

```bash
./mvnw test
```

---

## 👥 Equipe

Projeto desenvolvido para o **Challenge FIAP — 2TDSPG**.
