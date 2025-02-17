# Price Service
This project is a REST API that provides a price for a product. The application is built using Spring Boot and Java 17.

GitHub repository link:
https://github.com/eugeniobenito/price-service
## Table of contents

- [How to Run](#how-to-run)
- [Dependencies](#dependencies)
- [Implementation](#implementation)
  - [Package structure](#package-structure)
  - [Testing](#testing)
  - [Code Coverage](#code-coverage)
  - [Error handling](#error-handling)
  - [OpenAPI Documentation](#openapi-documentation)
  - [Docker](#docker)
  - [CI/CD](#cicd)

## How to Run
You can run the application in two ways:
- Using Docker:
```shell
docker pull eugeniobenito/price-service:latest
```
```shell
docker run -p 8080:8080 eugeniobenito/price-service:latest
```
- Using Maven:
```shell
.\mvnw clean spring-boot:run
```
Make sure you have Java 17 installed and Maven 3.3 or higher.

## Dependencies
The following dependencies are listed in the [pom.xml](pom.xml):
- spring-boot-starter-web
- lombok
- spring-boot-starter-data-jpa
- h2
- mapstruct
- spring-doc-openapi-ui
- jacoco

For testing:

- javafaker
- cucumber-java
- cucumber-junit
- cucumber-spring
- junit-vintage-engine
- spring-boot-starter-test
- jsonassert

## Implementation
The application follows the **hexagonal architecture**, which helps protect the domain and use cases from the infrastructure.
### Package structure
- price
  - **domain** => `Price` model and repository interface that serves as a port.
  - **application** => Use cases with business logic (such as error handling when the price cannot be found).
  - **infrastructure** => Controller for the `GET /api/price` endpoint and repository adapter with the implementation for H2 database.
- shared => Application exception logic, implemented to be centralized and easily added to the [error enum](src/main/java/dev/eugeniobenito/price_service/shared/domain/exception/PriceError.java).
### Testing
**Unit tests** have been carried out on the 3 layers to cover all possible cases and errors that may occur. 

**Integration tests** for testing repositories and its interaction with the database.

**Acceptance tests** using Cucumber for testing the endpoint `GET /api/price` with a [Scenario Outline](src/test/resources/price.feature) to add all cases easily.

To run unit tests, use the following Maven command:
```shell
./mvnw test
```
To run integration tests, use the following Maven command:
```shell
./mvnw verify
```
### Code Coverage
The project uses Jacoco for code coverage. It helps in measuring and reporting the test coverage of the application.

To generate the code coverage report, run the following Maven command:
```shell
mvn clean verify
```
### Error handling
The application has a centralized error handling system that returns a JSON with the error code and message. The error codes are defined in the [PriceError enum](src/main/java/dev/eugeniobenito/price_service/shared/domain/exception/PriceError.java).
Use the following controller to handle all exceptions: [GlobalExceptionHandler](src/main/java/dev/eugeniobenito/price_service/shared/infrastructure/exception/GlobalExcepcionHandler.java).
### OpenAPI Documentation
The project uses Swagger for API documentation, which provides an user interface to interact with the API endpoints.

To access the Swagger UI, navigate to:
http://localhost:8080/swagger-ui.html

### Docker
The application can be containerized using Docker. A Dockerfile is provided to build the Docker image.

To build the Docker image, run the following command:
```shell
docker build -t eugeniobenito/price-service .
```

To run the Docker container, use:
```shell
docker run -p 8080:8080 eugeniobenito/price-service
```

### CI/CD
Continuous Integration and Continuous Deployment (CI/CD) are set up using GitHub Actions. The workflow is defined in the `.github/workflows` directory.

The CI/CD pipeline includes steps for:
- Building the application
- Running unit and integration tests
