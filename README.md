# Study

![Project Badge](https://img.shields.io/badge/Java-21+-blue) ![Project Badge](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen)

Welcome to **Study**! This is a personal project I've been working on to study and explore new technologies with Spring Boot. The project is designed to demonstrate various aspects of Spring Boot and associated libraries, and serves as a practical way for me to learn and grow as a developer.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Usage](#usage)
- [Configuration](#configuration)
- [Contributing](#contributing)
- [License](#license)

## Features

- **RESTful API**: Built using Spring Boot's powerful REST capabilities.
- **Testing**: Unit and integration tests using JUnit and Mockito.
- **Security**: Secured using [Spring Security] and [KeyClock]
- **Observability**: Configured production ready monitoring using [Spring Acuator] and [Elastic Stack]

## Technologies Used

- **Java**: Version 21.
- **Spring Boot**: Version 3.2.5.
- **Maven**: For project build and dependency management.
- **PostgreSQL**: For database management.
- **Docker**: For containerization.
- **Spring docker compose**: For docker compose integration.
- **Testcontainer**: For integration and database testing, bringing the test environment closer to production.
- **Cypress and Playwright**: For E2E and frontend components testing. Using both tools, simply to create a comparison between them.
- **Keycloak**: For user management.
- **Elastic Stack**: For observability.

## Usage

- Once the application is running, it will be available at `http://localhost:8080`.
- You can try the endpoints using the Swagger UI in the url: `http://localhost:8080/swagger-ui/index.html`.
- The endpoint `http://localhost:8080/private` is authenticated. The authentication will be handled by Keycloak in the helm "Study".
- The endpoint `http://localhost:8080/post` and the Swagger UI still public (no authentication is required).
- In order to execute the Cypress' automated tests execute the command: `npx cypress open`
- In order to execute the Playwright automated tests execute the command: `npx playwright test -- ui`

## Configuration

- Modify the application properties in `src/main/resources/application.properties` or the `docker-compose.yml`.
- You can also create environment-specific configuration files (e.g., `application-dev.properties`, `application-prod.properties`) for different deployment environments.
- Docker will import automatically the helm "Study" used in Keycloak. In case the import is not successful, the import file can be found in the Keycloak folder.

## Contributing

Contributions, issues, and feature requests are welcome! Feel free to check the [issues page](https://github.com/your-username/study/issues) to report any bugs or request new features.

## License

This project is licensed under the [MIT License](LICENSE). Feel free to use, modify, and distribute the code as you wish!

---

Thank you for checking out this project! I hope you find it useful and informative. If you have any feedback or questions, feel free to reach out!

**Please keep in mind that this project is a work in progress. More features will be implemented over time.**
