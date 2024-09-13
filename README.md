# Parking Management System

## Technologies Used

* **Java 17**: The programming language used to develop the application.
* **Spring Boot**: Provides the framework for building the application.
* **Spring Data JPA**: Used for data persistence and ORM (Object-Relational Mapping).
* **Hibernate**: ORM tool to map Java classes to database tables.
* **Spring Security**: Secures the application and provides authentication and authorization functionalities.
* **Flyway**: Manages database migrations.
* **PostgreSQL**: The database used to persist the application's data.
* **ModelMapper**: Used to map DTOs to entities and vice versa.
* **Lombok**: Reduces boilerplate code with annotations for getters, setters, and constructors.
* **Swagger/OpenAPI**: Provides API documentation and testing UI.
* **JUnit 5**: Framework used for writing unit tests.
* **Mockito**: Used for mocking dependencies in tests.
* **Docker**: Used to containerize the application.
* **Docker Compose**: Used to define and run multi-container Docker applications. 

## Prerequisites
* **Docker**: Ensure Docker is installed on your machine.
* **Docker Compose**: Ensure Docker Compose is installed to manage multi-container applications.
* **Java 17**: Make sure you have JDK 17 installed.
* **PostgreSQL**: The application connects to a PostgreSQL database.


## Configuration

### Database
The application uses a PostgreSQL database. 
The database configuration can be found in the `application.yaml` file located in the `src/main/resources` directory.

### Flyway
Flyway is used for database migrations. 
The migration scripts are located in the `src/main/resources/db/migration` directory. 
Flyway will automatically apply these migrations on startup.

### Docker
Docker is used to containerize the application. 
Docker Compose is used to manage the containers for the application and the PostgreSQL database.

1. **Build and Start the Application**: Run the following command to build the application and start the containers.
```bash
docker-compose up --build
```
This will build the Docker images and start the application along with a PostgreSQL database.

2. **Stop the Application**: To stop the application and remove the containers, run the following command.
```bash
docker-compose down
```

### Running Tests
To run the tests, execute the following command:
```bash
gradle test
```
## API Endpoints

### Authentication Controller

* **Register User**: `POST /api/auth/register`
* **Request Body**:
```json
{
  "username": "user1",
  "password": "password"
}
```
* **Responses**:
  * Success: 200 ok
    ```json
    {
      "message": "User registered successfully!"
    }
    ```
  * Error: 400  Bad Request
    ```json
    {
      "message": "Username is already taken!"
    }
    ```
  
    
### Company Controller
* **Create a new Company**: `POST /api/companies`
* **Request Body**:
```json
{
     "name": "Parking Lot A",
     "cnpj": "12345678901234",
     "address": "123 Main Street",
     "phone": "(11) 98765-4321",
     "motorcycleSpots": 10,
     "carSpots": 20
}

```
* **Get All Companies**: `GET /api/companies`

* **Get Company by ID**: `GET /api/companies/{companyId}`

* **Update a Company by ID**: `PUT /api/companies/{companyId}`
* **Request Body**:
```json
{
     "name": "Parking Lot A",
     "cnpj": "12345678901234",
     "address": "123 Main Street",
     "phone": "(11) 98765-4321",
     "motorcycleSpots": 10,
     "carSpots": 20
}
```
* **Delete Company**: `DELETE /api/companies/{companyId}`

### Vehicle Controller
* **Create a new Vehicle**: `POST /api/vehicles`
* **Request Body**:
```json
{
     "brand": "Honda",
     "model": "HRV",
     "color": "gray",
     "plate": "kvs2234",
     "type": "CAR",
     "companyId":1
} 
```
* **Get All Vehicles**: `GET /api/vehicles`
* **Get Vehicle by ID**: `GET /api/vehicles/{vehicleId}`
* **Update Vehicle**: `PUT /api/vehicles/{vehicleId}`
* **Request Body**:
```json
{
     "brand": "Honda",
     "model": "HRV",
     "color": "gray",
     "plate": "kvs2234",
     "type": "CAR",
     "companyId":1
}
```
* **Delete Vehicle**: `DELETE /api/vehicles/{vehicleId}`

### Vehicle Parking Record Controller
* **Register vehicle entry**: `POST /api/vehicle-parking-records/entry/{id}`
* **Register vehicle exit**: `POST /api/vehicle-parking-records/exit/{id}`
* **Get Vehicle summary**: `GET /api/vehicle-parking-records/summary/{companyId}`
* **Get Vehicle hourly summary by companyId**: `GET /api/vehicle-parking-records/summary/hourly/{companyId}`
* **Get general report by companyId**: `GET /api/vehicle-parking-records/report/{companyId}`

# Questionário para Avaliação de Competências
1. **Banco de Dados (Nível Básico)**

   **Pergunta 1: Explique os principais conceitos de um banco de dados relacional, como tabelas, chaves primárias e estrangeiras.**

    *  Tabelas: Em um banco de dados relacional, uma tabela é uma coleção de dados organizados em linhas e colunas. 
    Cada linha representa um registro e cada coluna representa um atributo desse registro. As tabelas são usadas para armazenar dados de entidades do mundo real.
    * Chave Primaria: A chave primária é um campo ou conjunto de campos que identifica exclusivamente cada registro em uma tabela.
    * Chave Estrangeira: A chave estrangeira é um campo ou conjunto de campos em uma tabela que se refere à chave primária de outra tabela.
   
   **Pergunta 2: No contexto de uma aplicação de gerenciamento de estacionamento, como você organizaria a modelagem de dados para suportar as funcionalidades de controle de entrada e saída de veículos?**

    * Para suportar as funcionalidades de controle de entrada e saída de veículos, a modelagem de dados deve incluir entidades para veículos, estabelecimentos e registros de entrada/saída de veículos. 
    A entidade de veículo deve conter informações como marca, modelo, cor, placa e tipo. A entidade de estabelecimento deve conter informações como nome, CNPJ, endereço, telefone, quantidade de vagas para motos e carros. 
    A entidade de registro de entrada/saída de veículos deve conter informações como data/hora de entrada, data/hora de saída, veículo, estabelecimento e status (entrada ou saída).
   
   **Pergunta 3: Quais seriam as vantagens e desvantagens de utilizar um banco de dados NoSQL neste projeto?**
    
        Vantagens:
        * Escalabilidade: Os bancos de dados NoSQL são altamente escaláveis e podem lidar com grandes volumes de dados e tráfego.
        * Flexibilidade: Os bancos de dados NoSQL permitem armazenar dados de diferentes tipos e formatos sem a necessidade de um esquema rígido.
        * Desempenho: Os bancos de dados NoSQL são otimizados para consultas rápidas e eficientes em grandes conjuntos de dados.
        Desvantagens:
        * Consistência: Os bancos de dados NoSQL geralmente sacrificam a consistência em favor da disponibilidade e da tolerância a falhas.
        * Complexidade: Os bancos de dados NoSQL podem ser mais complexos de configurar e gerenciar do que os bancos de dados relacionais tradicionais.
        * Menos suporte: Os bancos de dados NoSQL podem ter menos suporte e documentação do que os bancos de dados relacionais estabelecidos.

2. **Agilidade (Nível Básico)**

   **Pergunta 1: Explique o conceito de metodologias ágeis e como elas impactam o desenvolvimento de software.**
    
    Metodologias ágeis são abordagens de desenvolvimento de software que enfatizam a colaboração, a comunicação, a adaptação e a entrega contínua de valor ao cliente. 
    As metodologias ágeis valorizam indivíduos e interações mais do que processos e ferramentas, software funcionando mais do que documentação abrangente, colaboração com o cliente mais do que negociação de contratos e responder a mudanças mais do que seguir um plano rígido. 
    As metodologias ágeis impactam o desenvolvimento de software ao promoverem a entrega contínua de valor ao cliente, a adaptação a mudanças nos requisitos e a colaboração eficaz entre as equipes de desenvolvimento e os stakeholders.

   **Pergunta 2: No desenvolvimento deste projeto, como você aplicaria princípios ágeis para garantir entregas contínuas e com qualidade?**

   Para garantir entregas contínuas e com qualidade no desenvolvimento deste projeto, eu aplicaria os seguintes princípios ágeis:
    * Colaboração: Trabalharia em estreita colaboração com os stakeholders para entender e atender às suas necessidades.
    * Entrega Contínua: Implementaria práticas de integração contínua e entrega contínua para garantir que o software seja entregue de forma rápida e eficiente.
    * Adaptação: Estaria aberto a mudanças nos requisitos e adaptaria o software conforme necessário para atender às necessidades dos usuários.
    * Comunicação: Manteria uma comunicação clara e eficaz com a equipe de desenvolvimento e os stakeholders para garantir que todos estejam alinhados com os objetivos do projeto.
    * Iteração: Utilizaria ciclos curtos de desenvolvimento para iterar rapidamente e obter feedback dos usuários.
    * Priorização: Priorizaria as funcionalidades com base no valor para o cliente e na complexidade técnica para garantir que o software seja entregue de forma eficiente.
   
   **Pergunta 3: Qual a importância da comunicação entre as equipes em um ambiente ágil? Dê exemplos de boas práticas.**

3. **DevOps (Nível Básico)**
   **Pergunta 1: O que é DevOps e qual a sua importância para o ciclo de vida de uma aplicação?**

    DevOps é uma cultura e prática que combina o desenvolvimento de software (Dev) e a operação de infraestrutura (Ops) para acelerar o ciclo de vida de uma aplicação. 
    A importância do DevOps para o ciclo de vida de uma aplicação inclui:
    * Colaboração: DevOps promove a colaboração entre equipes de desenvolvimento e operações para garantir que o software seja entregue de forma eficiente e confiável.
    * Entrega Contínua: DevOps facilita a entrega contínua de software, permitindo que as equipes entreguem novas funcionalidades aos usuários de forma rápida e eficiente.
    * Automação: DevOps promove a automação de processos de desenvolvimento, teste, implantação e operação para reduzir erros e aumentar a eficiência.
    * Monitoramento: DevOps inclui práticas de monitoramento e análise para garantir que o software esteja funcionando corretamente e atendendo às necessidades dos usuários.
   
   **Pergunta 2: Descreva como você integraria práticas de DevOps no desenvolvimento desta aplicação de estacionamento. Inclua exemplos de CI/CD.**

    Para integrar práticas de DevOps no desenvolvimento desta aplicação de estacionamento, eu implementaria o seguinte:
    * Integração Contínua (CI): Utilizaria ferramentas como Jenkins ou GitLab CI para automatizar a compilação, teste e análise de código sempre que houvesse uma alteração no repositório de código.
    * Entrega Contínua (CD): Utilizaria ferramentas como Jenkins, GitLab CI/CD ou GitHub Actions para automatizar a implantação do software em ambientes de teste e produção sempre que houvesse uma alteração no repositório de código.
    * Monitoramento: Implementaria ferramentas de monitoramento como Prometheus e Grafana para monitorar o desempenho da aplicação, identificar problemas e tomar medidas corretivas rapidamente.
    * Automação: Automatizaria tarefas de desenvolvimento, teste, implantação e operação para reduzir erros e aumentar a eficiência do ciclo de vida da aplicação.
   
   **Pergunta 3: Cite as ferramentas que você usaria para automatizar o processo de deploy e monitoramento da aplicação.**

    Para automatizar o processo de deploy e monitoramento da aplicação, eu usaria as seguintes ferramentas:
    * Jenkins: Para automação de integração contínua e entrega contínua.
    * Docker: Para containerização da aplicação e implantação em ambientes de teste e produção.
    * Kubernetes: Para orquestração de contêineres e gerenciamento de implantações em escala.
    * Prometheus: Para monitoramento de métricas e alertas.
    * Grafana: Para visualização de dados e dashboards de monitoramento.
    * ELK Stack (Elasticsearch, Logstash, Kibana): Para análise de logs e monitoramento de eventos.
    * New Relic: Para monitoramento de desempenho de aplicativos e infraestrutura.




