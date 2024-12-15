# SelectGearMotos - Clients API

Api do projeto Select Gear Motos para cadastro de Clientes.

## Descrição breve do projeto

Este projeto é uma API para cadastro de clientes. A API foi desenvolvida em Spring Boot 3. Este README fornece instruções para desenvolvedores que desejam rodar o projeto localmente, incluindo como rodar o JAR usando Maven, configurar o ambiente no IntelliJ IDEA, utilizar arquivos `.env` para gerenciar variáveis de ambiente e como rodar o PostgreSQL e o SonarQube usando Docker Compose.

## API do Backend - status
[![Deploy to Amazon EKS](https://github.com/hackthon-fiap-sub/selectgearmotors-client-api/actions/workflows/build-eks.yml/badge.svg)](https://github.com/Grupo-G03-4SOAT-FIAP/Health-Med-api/actions/workflows/deploy.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=hackthon-fiap-sub_selectgearmotors-client-api&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=hackthon-fiap-sub_selectgearmotors-client-api)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=hackthon-fiap-sub_selectgearmotors-client-api&metric=coverage)](https://sonarcloud.io/summary/new_code?id=hackthon-fiap-sub_selectgearmotors-client-api)

### 📋 Pré-requisitos

Ferramantas que precisam estar instaladas para rodar o projeto

```
- Java 17+
- Maven 3.6+
- Docker e Docker Compose
- IntelliJ IDEA
```

## 🚀 Começando

Essas instruções permitirão que você obtenha uma cópia do projeto em operação na sua máquina local para fins de desenvolvimento e teste.

Consulte **[Implantação](#-implanta%C3%A7%C3%A3o)** para saber como implantar o projeto.

## Rodando o JAR usando Maven

1. Clone o repositório:
    ```bash
    git clone https://github.com/fiapg70/sevenfood-client-api
    cd sevenfood-client-api
    ```

2. Compile e rode o JAR:
    ```bash
    mvn clean install
    java -jar target/nome-do-jar.jar
    ```

## Configurando o Ambiente no IntelliJ IDEA

1. Abra o IntelliJ IDEA e selecione `File -> Open...` e escolha o diretório do projeto.

2. Configure as variáveis de ambiente:
   - Clique com o botão direito no projeto no painel lateral e selecione `Edit Configurations...`.
   - Clique no ícone `+` no canto superior esquerdo e selecione `Application`.
   - Configure os campos:
      - **Name**: Nome do projeto
      - **Main class**: `br.com.sevenfood.client.sevenfoodclientapi.RunApplication` (substitua `br.com.sevenfood.client.sevenfoodclientapi.RunApplication` pela sua classe principal)
      - **VM options**: `-Dspring.profiles.active=prod` (Configuração para perfil de produção)
      - **Environment variables**: Clique no ícone `...` e adicione as variáveis necessárias:
        ```properties
        API_PORT=9999
        AWS_ACCESS_KEY_ID=<<Valor>>
        AWS_REGION=<<Valor>>
        AWS_SECRET_ACCESS_KEY=<<Valor>>
        AWS_SQS_QUEUE_ARN=<<Valor>>
        AWS_SQS_QUEUE_NAME=<<Valor>>
        AWS_SQS_QUEUE_URL=<<Valor>>
        DATABASE_PASSWORD=<<Valor>>
        DATABASE_URL=jdbc:postgresql://localhost:5432/<<DatabaseValor>>
        DATABASE_USERNAME=<<Valor>>
        MAIL_HOST=<<Valor>>
        MAIL_PASSWORD=<<Valor>>
        MAIL_PORT=<<Valor>>
        MAIL_USERNAME=<<Valor>>
        SECURITY_JWT_SECRET_KEY=<<Valor>>
        SES_SMTP_PASSWORD=<<Valor>>
        SES_SMTP_USERNAME=<<Valor>>
        SNS_TOPIC_EMAIL_ARN=<<Valor>>
        API_GATEWAY_URL_PRIVACY_NOTIFICATION_STATUS=<<Valor>>
        ```

3. Rode a aplicação:
   - Clique no ícone `Run` no canto superior direito ou pressione `Shift + F10`.

## Utilizando Arquivos `.env`

Para gerenciar variáveis de ambiente usando arquivos `.env`, siga estas etapas:

1. Crie um arquivo `.env` na raiz do projeto:
    ```properties
    API_PORT=9999
    AWS_ACCESS_KEY_ID=<<Valor>>
    AWS_REGION=<<Valor>>
    AWS_SECRET_ACCESS_KEY=<<Valor>>
    AWS_SQS_QUEUE_ARN=<<Valor>>
    AWS_SQS_QUEUE_NAME=<<Valor>>
    AWS_SQS_QUEUE_URL=<<Valor>>
    DATABASE_PASSWORD=<<Valor>>
    DATABASE_URL=jdbc:postgresql://localhost:5432/<<DatabaseValor>>
    DATABASE_USERNAME=<<Valor>>
    MAIL_HOST=<<Valor>>
    MAIL_PASSWORD=<<Valor>>
    MAIL_PORT=<<Valor>>
    MAIL_USERNAME=<<Valor>>
    SECURITY_JWT_SECRET_KEY=<<Valor>>
    SES_SMTP_PASSWORD=<<Valor>>
    SES_SMTP_USERNAME=<<Valor>>
    SNS_TOPIC_EMAIL_ARN=<<Valor>>
    API_GATEWAY_URL_PRIVACY_NOTIFICATION_STATUS=<<Valor>>
    ```

2. Adicione a dependência `spring-boot-dotenv` ao seu `pom.xml`:
    ```xml
    <dependency>
        <groupId>io.github.cdimascio</groupId>
        <artifactId>java-dotenv</artifactId>
        <version>5.2.2</version>
    </dependency>
    ```

3. Configure sua aplicação para ler o arquivo `.env`. Adicione o seguinte código à classe principal ou a uma configuração:
    ```java
    import io.github.cdimascio.dotenv.Dotenv;

    @SpringBootApplication
    public class RunApplication {
        public static void main(String[] args) {
            Dotenv dotenv = Dotenv.configure().load();
            System.setProperty("DB_URL", dotenv.get("DB_URL"));
            System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
            System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
            SpringApplication.run(RunApplication.class, args);
        }
    }
    ```

4. Configure as propriedades do Spring Boot para usar as variáveis de ambiente:
    ```properties
    spring.datasource.url=${DB_URL}
    spring.datasource.username=${DB_USERNAME}
    spring.datasource.password=${DB_PASSWORD}
    ```

## Rodando PostgreSQL e SonarQube com Docker Compose

O projeto inclui um arquivo `docker-compose.yml` no pacote `infra/` para rodar o PostgreSQL e o SonarQube. Siga as instruções abaixo para configurar e rodar esses serviços:

1. Navegue até o diretório `infra`:
    ```bash
    cd infra/postgres
    # ou
    cd infra/sonar
    ```

2. Rode o Docker Compose:
    ```bash
    docker-compose up -d
    ```

3. Verifique se os serviços estão rodando:
    ```bash
    docker-compose ps
    ```

O PostgreSQL estará disponível na porta 5432 e o SonarQube na porta 9000.

## Configurando o Perfil de Produção

Para rodar o perfil de produção, adicione a opção `-Dspring.profiles.active=prod` ao rodar o Maven ou configurar no IntelliJ IDEA.

### Rodando com Maven

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```
Ambientes: prod,hom,dev
# selectgearmotors-client-api
# selectgearmotors-client-api
# selectgearmotors-client-api