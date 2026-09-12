# 🐾 PetPulse API

API REST desenvolvida para o projeto **PetPulse**, solução voltada ao acompanhamento da saúde e bem-estar de pets por meio do gerenciamento de tutores, animais, histórico clínico, profissionais veterinários, dispositivos IoT, leituras e alertas inteligentes.

O backend foi desenvolvido com **Java, Spring Boot, Oracle Database, Flyway e Spring Security com autenticação JWT utilizando chaves RSA**.

---

# 🌐 API publicada

A API está publicada no Render:

```text
https://petpulse-api-j1k8.onrender.com
```

Health Check:

```text
https://petpulse-api-j1k8.onrender.com/actuator/health
```

Swagger:

```text
https://petpulse-api-j1k8.onrender.com/swagger-ui/index.html
```

OpenAPI:

```text
https://petpulse-api-j1k8.onrender.com/v3/api-docs
```
Adendo: Devido ao serviço Deploy ser gratuito a primeira requisição pode demorar cerca de cinco minutos para acontecer.
---

# 👥 Integrantes

- Pietro Paranhos Wilhelm — RM561378
- João Vitor Biribilli Ravelli  — RM565594
- Pedro Matos — RM564184
- Gabriel Neris Losano — RM564093

Turma: **2TDSPG**

Projeto desenvolvido para o **Challenge FIAP 2026**.

---

# 📌 Sobre o PetPulse

O PetPulse tem como objetivo centralizar informações relacionadas à saúde e ao acompanhamento dos animais.

A API permite:

- cadastro e gerenciamento de tutores;
- cadastro e gerenciamento de pets;
- associação de espécies, raças e portes;
- cadastro de telefones e endereços dos tutores;
- gerenciamento de profissionais veterinários;
- registro de históricos clínicos;
- gerenciamento de dispositivos IoT;
- registro de leituras dos dispositivos;
- atualização automática dos dados atuais do dispositivo;
- análise automática das leituras IoT;
- geração automática de alertas inteligentes;
- consulta consolidada da saúde do pet;
- autenticação utilizando JWT;
- controle de acesso por perfil de usuário;
- versionamento do banco de dados utilizando Flyway;
- validação de dados de entrada;
- tratamento de erros HTTP.

---

# 🧰 Tecnologias utilizadas

- Java 17
- Spring Boot 4.0.6
- Spring MVC
- Spring Data JPA
- Spring Security
- OAuth2 Resource Server
- JWT
- RSA
- BCrypt
- Oracle Database
- Flyway
- Spring Cache
- Spring Boot Actuator
- Springdoc OpenAPI / Swagger
- Lombok
- Maven
- Maven Wrapper
- Docker
- Render

---

# 🏗️ Arquitetura

A aplicação segue uma arquitetura em camadas.

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Oracle Database
```

As responsabilidades estão organizadas principalmente nos seguintes pacotes:

```text
src/main/java/fiap/com/br/petpulse/

├── assembler/       Conversão entre entidades e DTOs
├── config/          Configurações da aplicação e segurança
├── controller/      Endpoints REST
├── dto/
│   ├── request/     Dados recebidos pela API
│   └── response/    Dados devolvidos pela API
├── enums/           Enumerações do domínio
├── model/           Entidades JPA
├── repositories/    Acesso ao banco com Spring Data JPA
├── security/        Representação do usuário autenticado
├── service/         Regras de negócio
└── validation/      Tratamento global de validações e erros
```

Os recursos da aplicação ficam em:

```text
src/main/resources/

├── application.properties
├── certs/
└── db/
    └── migration/
```

---

# 🔐 Segurança

A API utiliza **Spring Security** com autenticação baseada em **JWT**.

O token JWT é assinado utilizando um par de chaves RSA:

```text
private_key.pem
public_key.pem
```

A senha dos usuários é armazenada utilizando **BCrypt**.

A aplicação possui dois perfis de autenticação:

```text
ROLE_TUTOR
ROLE_PROFESSIONAL
```

A role do usuário é incluída como uma claim no JWT e utilizada pelo Spring Security para controlar o acesso aos endpoints através de `@PreAuthorize`.

---

# 👤 Perfis e permissões

## TUTOR

Representa o usuário final responsável pelo pet.

Entre suas permissões estão:

- cadastrar sua conta;
- acessar funcionalidades destinadas aos tutores;
- cadastrar e gerenciar pets;
- consultar profissionais;
- gerenciar dispositivos IoT;
- consultar leituras;
- acessar informações de saúde dos pets.

## PROFESSIONAL

Representa profissionais veterinários ou usuários internos relacionados ao acompanhamento clínico.

Entre suas permissões estão:

- consultar pets;
- consultar profissionais;
- cadastrar, atualizar e remover profissionais;
- acessar históricos clínicos;
- consultar leituras IoT;
- consultar e gerenciar alertas relacionados ao acompanhamento dos pets.

> O controle implementado nesta etapa é baseado em perfis de usuário. Uma evolução futura do projeto pode adicionar autorização baseada também na propriedade dos recursos, garantindo, por exemplo, que cada tutor visualize exclusivamente seus próprios dados.

---

# 🔓 Endpoints públicos

Os seguintes endpoints não exigem autenticação:

```text
POST /login
POST /tutors

/swagger-ui/**
/v3/api-docs/**

/actuator/health
```

Os demais endpoints exigem autenticação, respeitando também as permissões definidas para cada perfil.

---

# 🔑 Autenticação

## Login

```http
POST /login
Content-Type: application/json
```

Exemplo:

```json
{
  "email": "security.tutor@petpulse.com",
  "password": "123456"
}
```

Resposta:

```json
{
  "token": "eyJ..."
}
```

Depois do login, o token deve ser enviado no header das requisições protegidas:

```http
Authorization: Bearer SEU_TOKEN
```

No Insomnia ou Postman, também é possível selecionar:

```text
Auth
→ Bearer Token
→ inserir o token JWT
```

---

# 👥 Usuários de demonstração

As migrations atuais possuem usuários utilizados para demonstração e testes.

## Tutor

```text
E-mail: security.tutor@petpulse.com
Senha: 123456
Role: ROLE_TUTOR
```

## Professional

```text
E-mail: carlos.andrade@vetcare.com
Senha: 123456
Role: ROLE_PROFESSIONAL
```

As senhas armazenadas no Oracle estão codificadas utilizando BCrypt.

A migration:

```text
V6__encode_seed_passwords.sql
```

garante que os usuários de demonstração sejam criados com senha codificada.

> Essas credenciais são exclusivamente dados de demonstração do ambiente acadêmico.

---

# 🚀 Como executar o projeto do zero

## 1. Pré-requisitos

Para executar a aplicação é necessário possuir:

- Java 17 ou superior;
- Git;
- acesso a uma instância Oracle;
- OpenSSL para geração das chaves RSA.

É possível verificar o Java instalado com:

```bash
java -version
```

---

## 2. Clonar o repositório

```bash
git clone https://github.com/Fiap-2tdspg-Challenge/PetPulse-Api.git
```

Entre na pasta:

```bash
cd PetPulse-Api
```

---

# 🔐 3. Criar as chaves RSA

As chaves RSA utilizadas para geração e validação dos tokens JWT **não devem ser armazenadas no GitHub**.

Crie a pasta:

```text
src/main/resources/certs
```

Linux/macOS:

```bash
mkdir -p src/main/resources/certs
```

No Windows, a pasta também pode ser criada manualmente pelo IntelliJ ou pelo Explorer.

Entre na pasta:

```bash
cd src/main/resources/certs
```

Gere a chave privada:

```bash
openssl genpkey \
  -algorithm RSA \
  -out private_key.pem \
  -pkeyopt rsa_keygen_bits:2048
```

Gere a chave pública:

```bash
openssl rsa \
  -pubout \
  -in private_key.pem \
  -out public_key.pem
```

Ao final, devem existir:

```text
src/main/resources/certs/private_key.pem
src/main/resources/certs/public_key.pem
```

Volte para a raiz do projeto antes de executar o Maven.

As chaves `.pem` devem permanecer ignoradas pelo Git.

Exemplo no `.gitignore`:

```gitignore
src/main/resources/certs/*.pem
```

---

# 🗄️ 4. Configurar o banco Oracle

A aplicação utiliza variáveis de ambiente para impedir que as credenciais reais do Oracle sejam salvas no repositório.

As variáveis necessárias são:

```text
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
```

Exemplo de URL Oracle:

```text
jdbc:oracle:thin:@HOST:1521:SERVICO
```

Substitua pelos dados correspondentes ao banco utilizado.

---

# 🪟 5. Configurar variáveis no Windows

## PowerShell

Exemplo para a sessão atual:

```powershell
$env:SPRING_DATASOURCE_URL="jdbc:oracle:thin:@HOST:1521:SERVICO"
$env:SPRING_DATASOURCE_USERNAME="USUARIO"
$env:SPRING_DATASOURCE_PASSWORD="SENHA"
```

Depois execute a aplicação na mesma janela.

## IntelliJ IDEA

Também é possível configurar diretamente pelo IntelliJ:

```text
Run
→ Edit Configurations
→ PetPulseApplication
→ Environment variables
```

Adicione:

```text
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
```

Não é necessário colocar essas credenciais dentro do `application.properties`.

---

# 🐧 6. Configurar variáveis no Linux/macOS

```bash
export SPRING_DATASOURCE_URL="jdbc:oracle:thin:@HOST:1521:SERVICO"
export SPRING_DATASOURCE_USERNAME="USUARIO"
export SPRING_DATASOURCE_PASSWORD="SENHA"
```

---

# ⚙️ 7. Configuração da aplicação

O `application.properties` deve permanecer semelhante ao seguinte:

```properties
spring.application.name=PetPulse

# =========================
# SERVER
# =========================
server.port=${PORT:8080}

# =========================
# ORACLE DATABASE
# =========================
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

# =========================
# JPA / HIBERNATE
# =========================
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.OracleDialect

# =========================
# FLYWAY
# =========================
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
spring.flyway.baseline-on-migrate=true
spring.flyway.baseline-version=4
spring.flyway.validate-on-migrate=true
spring.flyway.clean-disabled=true

# =========================
# JWT / RSA
# =========================
rsa.private-key=${RSA_PRIVATE_KEY:classpath:certs/private_key.pem}
rsa.public-key=${RSA_PUBLIC_KEY:classpath:certs/public_key.pem}

# =========================
# ACTUATOR
# =========================
management.endpoints.web.exposure.include=health,info
management.endpoint.health.show-details=always
```

Com essa configuração:

- localmente, se `PORT` não estiver definida, a aplicação utiliza a porta `8080`;
- localmente, se `RSA_PRIVATE_KEY` e `RSA_PUBLIC_KEY` não estiverem definidas, a aplicação utiliza os arquivos da pasta `certs`;
- no Render, essas propriedades são fornecidas através de variáveis de ambiente e Secret Files.

---

# 🗃️ 8. Flyway

O projeto utiliza **Flyway** para versionamento do banco de dados.

Os arquivos estão em:

```text
src/main/resources/db/migration/
```

Migrations existentes:

```text
V1__create_schema.sql
V2__create_load_procedures.sql
V3__load_initial_data.sql
V4__database_sprint3_objects.sql
V5__add_iot_reading_index.sql
V6__encode_seed_passwords.sql
```

O Flyway é executado automaticamente quando a aplicação é iniciada.

Em um banco vazio, as migrations são aplicadas para construir o schema e carregar os dados necessários.

Em um schema já existente utilizado durante o desenvolvimento, o projeto utiliza baseline configurado na versão 4.

```properties
spring.flyway.baseline-on-migrate=true
spring.flyway.baseline-version=4
```

As migrations já aplicadas não devem ser alteradas. Novas alterações de banco devem ser criadas em uma nova versão:

```text
V7__descricao_da_alteracao.sql
V8__descricao_da_alteracao.sql
...
```

---

# ▶️ 9. Executar a aplicação

## Windows

```cmd
mvnw.cmd spring-boot:run
```

## Linux/macOS

```bash
./mvnw spring-boot:run
```

Também é possível iniciar pelo IntelliJ executando:

```text
PetPulseApplication.java
```

Quando a inicialização terminar corretamente, a API estará disponível em:

```text
http://localhost:8080
```

---

# 📖 Swagger

## Ambiente local

```text
http://localhost:8080/swagger-ui/index.html
```

Documentação OpenAPI:

```text
http://localhost:8080/v3/api-docs
```

## Ambiente publicado

```text
https://petpulse-api-j1k8.onrender.com/swagger-ui/index.html
```

Documentação OpenAPI:

```text
https://petpulse-api-j1k8.onrender.com/v3/api-docs
```

---

# ❤️ Health Check

O endpoint de monitoramento pode ser consultado em:

```http
GET /actuator/health
```

Ambiente local:

```text
http://localhost:8080/actuator/health
```

Ambiente publicado:

```text
https://petpulse-api-j1k8.onrender.com/actuator/health
```

Exemplo de resposta:

```json
{
  "status": "UP"
}
```

---

# 📚 Principais endpoints

## Autenticação

```text
POST /login
```

## Tutores

```text
POST   /tutors
GET    /tutors
GET    /tutors/{id}
PUT    /tutors/{id}
DELETE /tutors/{id}
GET    /tutors/search
```

## Telefones

```text
/tutor-phones
```

## Endereços

```text
/tutor-addresses
```

## Pets

```text
POST   /pets
GET    /pets
GET    /pets/{id}
PUT    /pets/{id}
DELETE /pets/{id}
GET    /pets/search
GET    /pets/{id}/health-summary
```

## Profissionais

```text
POST   /professionals
GET    /professionals
GET    /professionals/{id}
PUT    /professionals/{id}
DELETE /professionals/{id}
GET    /professionals/search
```

## Espécies

```text
POST /species
GET  /species
```

## Raças

```text
POST /breeds
GET  /breeds?speciesId={id}
```

## Estados

```text
POST /states
GET  /states
```

## Cidades

```text
POST /cities
GET  /cities?stateCode={UF}
```

## Portes

```text
/pet-sizes
```

## Histórico clínico

```text
/clinical-histories
```

## Dispositivos IoT

```text
/iot-devices
```

## Leituras IoT

```text
/iot-readings
```

## Alertas inteligentes

```text
/smart-alerts
```

A documentação completa, incluindo requests e responses, está disponível no Swagger.

---

# 🐶 Exemplo de cadastro de Pet

```http
POST /pets
Authorization: Bearer TOKEN
Content-Type: application/json
```

Exemplo:

```json
{
  "name": "Rex",
  "birthDate": "2024-01-10",
  "weight": 12.5,
  "sex": "M",
  "neutered": true,
  "tutorId": 1,
  "speciesId": 1,
  "breedId": 1,
  "petSizeId": 1
}
```

A aplicação também verifica a consistência entre espécie e raça.

Por exemplo, uma raça pertencente a cachorro não pode ser utilizada junto com a espécie gato.

Uma combinação inválida retorna:

```text
400 Bad Request
```

---

# 🧪 Testando a API com Insomnia

O repositório possui uma coleção do Insomnia pronta para testar os endpoints da API:

```text
PetPulse-Insomnia-Sprint3-DEMO.json
```

## 1. Importar a coleção

No Insomnia:

```text
Import
→ File
→ PetPulse-Insomnia-Sprint3-DEMO.json
```

A coleção está organizada em pastas por funcionalidade.

## 2. Base Environment

A coleção já possui um `Base Environment` configurado para execução local.

Entre as variáveis disponíveis estão:

```json
{
  "base_url": "http://localhost:8080",
  "tutor_token": "",
  "professional_token": "",
  "tutor_id": 1,
  "professional_id": 1,
  "clinic_id": 1,
  "pet_id": 1,
  "species_id": 1,
  "breed_id": 1,
  "pet_size_id": 3,
  "state_code": "SP",
  "city_id": 1,
  "address_type_id": 1,
  "tutor_phone_id": 1,
  "tutor_address_id": 1,
  "clinical_history_id": 1,
  "iot_device_id": 1,
  "iot_reading_id": 1,
  "smart_alert_id": 1,
  "alert_type_id": 1,
  "delete_test_id": 999999
}
```

Os IDs representam registros utilizados nos testes de demonstração.

Caso o banco utilizado possua outros IDs, basta alterar as variáveis correspondentes no `Base Environment`.

## 3. Gerar o token do Tutor

Abra:

```text
00 - Auth
→ Login - Tutor
```

Credenciais:

```text
E-mail: security.tutor@petpulse.com
Senha: 123456
```

Copie o token retornado e salve em:

```text
tutor_token
```

## 4. Gerar o token do Professional

Execute:

```text
00 - Auth
→ Login - Professional
```

Credenciais:

```text
E-mail: carlos.andrade@vetcare.com
Senha: 123456
```

Copie o token retornado e salve em:

```text
professional_token
```

Os requests protegidos já utilizam automaticamente o token adequado.

> Os tokens JWT possuem tempo de expiração. Caso um endpoint protegido retorne `401 Unauthorized` após algum tempo, realize o login novamente e atualize o token no `Base Environment`.

## 5. Testar a API publicada

Para execução local:

```text
base_url = http://localhost:8080
```

Para testar o ambiente publicado no Render:

```text
base_url = https://petpulse-api-j1k8.onrender.com
```

Ao alterar apenas `base_url`, todos os requests passam automaticamente a utilizar o ambiente escolhido.

## 6. Testes preparados para a Sprint 3

A pasta:

```text
12 - Sprint 3 - Testes da Apresentação
```

possui cenários preparados para demonstrar:

- autenticação com JWT;
- acesso autorizado;
- `401 Unauthorized`;
- `403 Forbidden`;
- validação com `400 Bad Request`;
- validação de espécie e raça;
- geração automática de Smart Alert por leitura IoT;
- atualização do snapshot do dispositivo IoT;
- Health Summary do pet;
- conflito de integridade com `409 Conflict`.

---

# 📡 Fluxo inteligente 1 — Leitura IoT e geração automática de alerta

Uma das funcionalidades não-CRUD da aplicação é a análise automática de dados recebidos dos dispositivos IoT.

O fluxo acontece da seguinte forma:

```text
POST /iot-readings
        ↓
Leitura é salva
        ↓
Estado atual do IoTDevice é atualizado
        ↓
IoTReadingAnalysisService analisa os valores
        ↓
Caso seja detectada uma condição configurada
        ↓
SmartAlert é criado automaticamente
```

Exemplo de leitura:

```http
POST /iot-readings
Authorization: Bearer TOKEN
Content-Type: application/json
```

```json
{
  "deviceId": 1,
  "heartRate": 123,
  "activityLevel": 4,
  "pressure": 13
}
```

A leitura atualiza no dispositivo:

```text
heartRate
activityLevel
pressure
lastReadingDate
```

Caso algum campo da leitura não seja informado, o último valor válido armazenado no dispositivo não é apagado.

O processo ocorre dentro de uma transação utilizando `@Transactional`.

Dessa forma:

```text
leitura + atualização do dispositivo + geração de alerta
```

fazem parte da mesma operação.

Se alguma etapa falhar, a operação pode ser revertida para evitar inconsistência de dados.

---

# 🚨 Regras de análise IoT

Nesta versão acadêmica foram definidos limites de negócio compatíveis com a escala dos dados simulados pelo projeto.

Atualmente são analisados:

```text
Frequência cardíaca
Pressão
Nível de atividade
```

Limites configurados no protótipo:

```text
Frequência cardíaca:
- abaixo de 60 → alerta
- acima de 180 → alerta

Pressão:
- acima de 16 → alerta

Atividade:
- abaixo de 2 → alerta
```

Esses valores são utilizados exclusivamente como regras do protótipo.

Em uma aplicação real, os limites deveriam ser definidos e validados por especialistas veterinários e poderiam ser armazenados como parâmetros configuráveis.

---

# 📊 Fluxo inteligente 2 — Resumo de saúde do Pet

A segunda funcionalidade não-CRUD relevante é a consolidação das informações de saúde de um pet.

Endpoint:

```http
GET /pets/{id}/health-summary
```

O serviço reúne informações de diferentes fontes:

```text
Pet
+
Histórico clínico
+
Últimas leituras IoT
+
Alertas inteligentes
        ↓
HealthSummaryResponse
```

Isso permite ao cliente consultar em uma única requisição uma visão consolidada sobre o estado do animal.

---

# ✅ Validações

A API utiliza Jakarta Bean Validation.

Entre as validações implementadas estão:

- nome obrigatório;
- tamanho mínimo e máximo de campos;
- e-mail obrigatório e em formato válido;
- senha obrigatória;
- senha mínima de 6 caracteres no cadastro de Tutor;
- CPF obrigatório;
- formato de CPF;
- IDs obrigatórios e positivos;
- peso do pet positivo;
- data de nascimento não futura;
- sexo obrigatório;
- validação de espécie e raça;
- limites de tamanho para descrição e observações clínicas.

Exemplo de erro de validação:

```json
[
  {
    "field": "email",
    "message": "E-mail inválido"
  }
]
```

---

# 🌐 Códigos HTTP utilizados

A API utiliza códigos HTTP compatíveis com o resultado das operações.

```text
200 OK
Operação realizada com sucesso.

201 Created
Registro criado com sucesso.

204 No Content
Registro removido com sucesso.

400 Bad Request
Dados inválidos ou regra de negócio não atendida.

401 Unauthorized
Usuário não autenticado, credenciais incorretas ou token inválido.

403 Forbidden
Usuário autenticado, mas sem permissão para o recurso.

404 Not Found
Registro solicitado não encontrado.

409 Conflict
Operação não pode ser realizada devido a relacionamento ou integridade dos dados.
```

Exemplo de tentativa de excluir um registro que possui dados relacionados:

```json
{
  "message": "Não é possível excluir este registro porque ele possui dados relacionados."
}
```

---

# 🔒 Exemplo de controle por role

Uma rota pode possuir autorização semelhante a:

```java
@PreAuthorize("hasRole('TUTOR')")
```

ou:

```java
@PreAuthorize("hasAnyRole('TUTOR', 'PROFESSIONAL')")
```

O Spring Security obtém a role através da claim:

```text
role
```

existente no token JWT.

---

# 🧪 Testando autenticação e autorização

## 1. Fazer login como Tutor

```http
POST /login
```

```json
{
  "email": "security.tutor@petpulse.com",
  "password": "123456"
}
```

Copie o token.

---

## 2. Testar endpoint autenticado

```http
GET /pets
Authorization: Bearer TOKEN_DO_TUTOR
```

Resultado esperado:

```text
200 OK
```

---

## 3. Testar sem token

```http
GET /pets
```

Resultado esperado:

```text
401 Unauthorized
```

---

## 4. Testar restrição de perfil

Faça login com um Professional:

```json
{
  "email": "carlos.andrade@vetcare.com",
  "password": "123456"
}
```

Utilize o token em um endpoint permitido somente para Tutor.

Resultado esperado:

```text
403 Forbidden
```

Isso demonstra a diferenciação de permissões entre os dois tipos de usuário.

---

# 🧪 Executar testes automatizados

## Windows

```cmd
mvnw.cmd clean test
```

## Linux/macOS

```bash
./mvnw clean test
```

O build deve terminar com:

```text
BUILD SUCCESS
```

---

# 📦 Gerar o pacote da aplicação

## Windows

```cmd
mvnw.cmd clean package
```

## Linux/macOS

```bash
./mvnw clean package
```

O arquivo `.jar` será criado dentro de:

```text
target/
```

Exemplo:

```text
target/PetPulse-0.0.1-SNAPSHOT.jar
```

Para executar o `.jar`:

```bash
java -jar target/PetPulse-0.0.1-SNAPSHOT.jar
```

As variáveis de ambiente do Oracle precisam continuar configuradas.

---

# 🛡️ Tratamento de integridade dos dados

O Oracle possui relacionamentos através de chaves estrangeiras.

Quando a remoção de um registro quebraria a integridade referencial, o banco impede a exclusão.

A aplicação trata esse cenário através de:

```text
DataIntegrityViolationException
```

e retorna:

```text
409 Conflict
```

em vez de expor um erro interno `500`.

---

# 💾 Transações

Operações que precisam ocorrer de forma atômica utilizam `@Transactional`.

Exemplos:

## Registro de leitura IoT

```text
Salvar leitura
+
Atualizar dispositivo
+
Analisar leitura
+
Gerar alerta
```

## Remoção de Tutor

```text
Validar existência de pets
+
Remover telefones
+
Remover endereços
+
Remover tutor
```

Caso uma operação intermediária falhe, a transação evita que o banco fique parcialmente atualizado.

---

# ⚡ Cache

A aplicação utiliza Spring Cache em diferentes serviços.

Operações de consulta podem utilizar:

```java
@Cacheable
```

Enquanto operações que alteram os dados utilizam:

```java
@CacheEvict(allEntries = true)
```

Isso evita a devolução de listagens antigas após cadastro, alteração ou exclusão de registros.

---

# 🔎 Consultas sem diferenciar maiúsculas e minúsculas

Algumas buscas utilizam métodos Spring Data como:

```text
findByNameContainingIgnoreCase
findByEmailIgnoreCase
```

Isso permite buscas e autenticação sem problemas causados apenas pelo uso de letras maiúsculas ou minúsculas.

---

# 🩺 Histórico clínico

O histórico clínico permite armazenar informações relacionadas ao acompanhamento do pet.

Pode possuir associação com:

```text
Pet
Professional
Tipo de registro
Descrição
Data de retorno
Observações
```

O histórico pode ser acessado pelos perfis autorizados para acompanhamento da saúde do animal.

---

# 🗂️ Dados de referência

O projeto possui recursos para:

```text
Species
Breed
PetSize
State
City
```

Nesta versão acadêmica, alguns desses recursos utilizam uma estratégia `findOrCreate`, facilitando a utilização pelo frontend durante o protótipo.

Em uma aplicação de produção, esses dados poderiam ser previamente carregados ou sincronizados com fontes oficiais e bases externas.

---

# ☁️ Deploy no Render

A API está preparada para execução no Render utilizando Docker.

O projeto possui:

```text
Dockerfile
.dockerignore
```

O container utiliza Java 17 e executa a aplicação Spring Boot através do arquivo `.jar` gerado pelo Maven.

## Variáveis de ambiente

No Render são configuradas:

```text
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
PORT
RSA_PRIVATE_KEY
RSA_PUBLIC_KEY
```

As credenciais do Oracle não são armazenadas no repositório.

## Chaves RSA no Render

As chaves são cadastradas através de Secret Files:

```text
private_key.pem
public_key.pem
```

As variáveis apontam para esses arquivos:

```text
RSA_PRIVATE_KEY=file:/etc/secrets/private_key.pem
RSA_PUBLIC_KEY=file:/etc/secrets/public_key.pem
```

## Porta

A aplicação utiliza:

```properties
server.port=${PORT:8080}
```

Assim:

- localmente, a porta padrão é `8080`;
- no Render, a aplicação utiliza o valor da variável `PORT`.

No ambiente atual do projeto:

```text
PORT=10000
```

## URL publicada

```text
https://petpulse-api-j1k8.onrender.com
```

Health Check:

```text
https://petpulse-api-j1k8.onrender.com/actuator/health
```

Swagger:

```text
https://petpulse-api-j1k8.onrender.com/swagger-ui/index.html
```

---

# 📝 Logs e monitoramento

A aplicação utiliza logs do Spring durante sua execução.

O banco também possui estruturas destinadas ao registro de erros de procedures.

Para ver o estado básico da aplicação:

```text
GET /actuator/health
```

---

# ❗ Solução de problemas

## 401 Unauthorized

Verifique se o Bearer Token foi informado.

```http
Authorization: Bearer TOKEN
```

O token possui tempo de validade limitado.

Caso esteja expirado, faça login novamente.

---

## 403 Forbidden

Significa que o usuário está autenticado, mas sua role não possui acesso ao endpoint solicitado.

Verifique se está utilizando:

```text
ROLE_TUTOR
```

ou:

```text
ROLE_PROFESSIONAL
```

de acordo com a operação desejada.

---

## Erro ao carregar chave RSA

### Ambiente local

Confirme a existência dos arquivos:

```text
src/main/resources/certs/private_key.pem
src/main/resources/certs/public_key.pem
```

O `application.properties` utiliza:

```properties
rsa.private-key=${RSA_PRIVATE_KEY:classpath:certs/private_key.pem}
rsa.public-key=${RSA_PUBLIC_KEY:classpath:certs/public_key.pem}
```

Se as variáveis `RSA_PRIVATE_KEY` e `RSA_PUBLIC_KEY` não estiverem definidas, os arquivos locais serão utilizados.

### Render

Confirme a existência dos Secret Files:

```text
private_key.pem
public_key.pem
```

e das variáveis:

```text
RSA_PRIVATE_KEY=file:/etc/secrets/private_key.pem
RSA_PUBLIC_KEY=file:/etc/secrets/public_key.pem
```

---

## Erro de conexão Oracle

Confira se estas variáveis estão configuradas:

```text
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
```

Também confirme se o banco Oracle está acessível pela máquina utilizada.

---

## Erro Flyway

Não altere migrations que já tenham sido executadas em um banco.

Novas alterações devem receber uma nova versão.

Exemplo:

```text
V7__nova_alteracao.sql
```

---

## 409 Conflict ao excluir

O registro provavelmente possui outros dados relacionados.

Exemplo:

```text
Pet
→ histórico clínico
→ dispositivo IoT
→ leituras
→ alertas
```

A exclusão é impedida para preservar a integridade das informações.

---

## Porta local ocupada

A porta padrão local é:

```text
8080
```

Caso outra aplicação esteja utilizando essa porta, finalize o outro processo ou defina outra porta temporariamente:

### PowerShell

```powershell
$env:PORT="8081"
```

### Linux/macOS

```bash
export PORT=8081
```

---

# 🔐 Segurança das informações

Nunca devem ser enviados para o GitHub:

```text
senha real do Oracle
usuário/senha de ambientes privados
private_key.pem
outros segredos ou tokens
```

O projeto utiliza variáveis de ambiente justamente para evitar que essas informações sejam versionadas.

---

# 📋 Checklist rápido para executar

Depois de clonar o projeto:

```text
1. Ter Java 17+
2. Configurar acesso ao Oracle
3. Criar SPRING_DATASOURCE_URL
4. Criar SPRING_DATASOURCE_USERNAME
5. Criar SPRING_DATASOURCE_PASSWORD
6. Criar src/main/resources/certs
7. Gerar private_key.pem
8. Gerar public_key.pem
9. Executar mvnw.cmd spring-boot:run
10. Esperar o Flyway finalizar
11. Acessar /actuator/health
12. Acessar o Swagger
13. Fazer POST /login
14. Copiar o JWT
15. Testar os endpoints autenticados
```

---

# 🎯 Funcionalidades de destaque da Sprint

Entre os principais recursos implementados nesta etapa estão:

```text
✅ Flyway para versionamento do banco
✅ Spring Security
✅ autenticação JWT
✅ JWT assinado com RSA
✅ senhas utilizando BCrypt
✅ dois tipos de usuário
✅ autorização por perfil
✅ validações
✅ tratamento de erros
✅ análise de leituras IoT
✅ geração automática de SmartAlert
✅ atualização automática do dispositivo
✅ transações
✅ resumo consolidado da saúde do pet
✅ coleção de testes no Insomnia
✅ deploy da API utilizando Docker e Render
```

---

# 📹 Demonstração

O vídeo de demonstração da entrega deve apresentar principalmente:
```text
https://youtu.be/pi_CTIW9Ngk
```

```text
1. Apresentação rápida do PetPulse
2. Aplicação/API funcionando
3. Flyway sendo validado/executado
4. Swagger
5. Login de Tutor
6. Geração do JWT
7. Endpoint protegido funcionando
8. Login de Professional
9. Diferença entre permissões
10. Teste 401 sem autenticação
11. Teste 403 com role incorreta
12. Cadastro de leitura IoT
13. Atualização do IoTDevice
14. Geração automática de SmartAlert
15. Health Summary do pet
16. Validações e tratamento de erros
17. API publicada no Render
```

---

# 📌 Repositório

```text
https://github.com/Fiap-2tdspg-Challenge/PetPulse-Api
```

---

# 🌐 API

```text
https://petpulse-api-j1k8.onrender.com
```

---

# 🐾 PetPulse

**Tecnologia aplicada ao acompanhamento e bem-estar animal.**
