# product-feedback

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)

> This project has no real application, the intention is just to practice and demonstrate the application of Clean Arch, DDD, Design Patters, automated testing and documentation concepts.

### Adjustments and improvements

The project is still under development and the next updates will focus on the following tasks:

- [ ] Create interfaces for domain services and use cases, so that their use no longer depends on concrete implementations
- [ ] Use the Spring authentication context to obtain the user sending the request on endpoints that require this information to persist records
- [ ] Use the Spring authentication context to authorize only the user who created the feedback to change it
- [ ] Improve automated test coverage for API controllers
- [ ] Create automated tests for UseCases and classes related to project security settings
- [ ] Creation of the application's Dockerfile to facilitate deployment

## 💻 Prerequisites

Before starting, you will need to have the following tools installed on your machine:

- [Git](https://git-scm.com)
- [Java 17 (OpenJDK 17.0.9)](https://openjdk.java.net/projects/jdk/17/)
- [PostgreSQL](https://www.postgresql.org/download/)

Also, it's good to have an editor to work with the code, like [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) or [VSCode](https://code.visualstudio.com/download).

## 🚀 Installing product-feedback

To install product-feedback, follow these steps:

## Database configuration:

### 1. Creating a New Database in PostgreSQL
- **Open the PostgreSQL terminal:** This can be done through **psql**, an interactive terminal for PostgreSQL, allowing interaction with the database server. Access it by executing psql in your terminal or command prompt.
- **Log in as a superuser:** Typically, when PostgreSQL is installed, a default user named postgres is created. To log in, you might use the following command:

```sh
psql -U postgres
```
You will be prompted to enter the password set during the PostgreSQL installation.

- **Create the new database:** Use the following SQL command to create a new database. Replace fbmanagement with your desired database name if you want a different name.
- 

```sql
CREATE DATABASE fbmanagement;
```

- **Create the database user:** Create a user who will have access to this database. Replace efborchardt with your desired username and efb123 with your desired password.

```sql
CREATE USER efborchardt WITH ENCRYPTED PASSWORD 'efb123';
```

- **Grant privileges to the user:** Grant all privileges of the created database to the user.

```sql
GRANT ALL PRIVILEGES ON DATABASE fbmanagement TO efborchardt;
```

- **Exit psql:** You can exit the PostgreSQL terminal by typing `\q`.

### 2. Setting Up Environment Variables or Using Default Credentials

Set up environment variables on your operating system:

#### Linux & macOS:

- Open your `~/.shrc`, `~/.zshrc`, or the configuration file of your preferred shell.
- Add the following lines at the end of the file (replace the values as necessary):

```sh
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=fbmanagement
export DB_USERNAME=efborchardt
export DB_PASSWORD=efb123
```

- Save the file and reload your shell's configuration. For **.shrc**, you can use `source ~/.shrc`.

#### Windows:

- Search for "Edit the system environment variables" and open it.
- Click on "Environment Variables".
- Create new environment variables by clicking on "New" under the system or user variables panel, as appropriate.
- Add DB_HOST, DB_PORT, DB_NAME, DB_USERNAME, and DB_PASSWORD as environment variables, with their respective values.

#### Using Default Credentials
- If you prefer not to use environment variables and stick with default credentials, simply ensure the credentials in the `application.properties` file match those used when creating the database and user in PostgreSQL. This means you don't need to do anything further, as Spring Boot will directly use these credentials from the configuration file.

### 3. Download the Project

- To get the project, run the following command in the terminal:

HTTPS

```sh
git clone https://github.com/efborchardt/product-feedback.git
```

SSH
```sh
git clone git@github.com:efborchardt/product-feedback.git
```

### 4. Running the Project
- Access the project folder in terminal/cmd:

```sh
cd project-path/product-feedback
```

- Use Gradle Wrapper to run the project:

Linux & macOS

```sh
./gradlew bootRun
```

Windows

```sh
.\gradlew.bat bootRun
```
Or run the project through your IDE.

- The project will be running on port **50001**.

## ☕ Using product-feedback API

To use product-feedback, follow these steps:

- Check the API documentation by going to:

```sh
http://localhost:50001/api/documentation
```

- When starting the project for the first time, two standard users will be available, created through Flyway migration.

```
1.  username: admin
    password: admin
    role: ADMIN

2.  username: user
    password: user
    role: USER
```

- The API uses stateless authorization through JWT Tokens. You can obtain a token by authenticating through the endpoint:

```sh
http://localhost:50001/api/public/auth/login
```

- All other endpoints are authenticated and the Header **Authorization** must be entered as **Bearer Token**. Example:

```sh
curl --request GET \
  --url http://localhost:50001/api/products \
  --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJwcm9kdWN0LWZlZWRiYWNrLWFwaSIsInN1YiI6ImFkbWluIiwiZXhwIjoxNzEwNjY1OTg0fQ.D8b8fU9IaETRYiBnHugPCG3yluEcls59qIjnDd3E6ew'
```

- If you want to create more users, log in using the default admin user, the user creation endpoint is protected for admins only.

## Project Highlights

- Application of Clean Architecture and Domain Driven Design (DDD) concepts to separate responsibilities into layers and make project maintenance and extension easier.
- The domain layer does not depend on Spring Data JPA entities and repositories, allowing greater flexibility to change the persistence layer without impacting the application domain.
- Application controllers are anemic, and access the application's functionalities through use cases, which are responsible for bridging the gap between the external world and the application domain, where all rules and checks are centralized.
- Spring Security implementation to configure and manage all API security settings. [Security Configs](https://github.com/efborchardt/product-feedback/blob/master/src/main/java/com/efborchardt/productfeedback/infrastructure/interfaces/rest/security/SecurityConfigs.java)
- Using JWT tokens to perform stateless authorization. [Example](https://github.com/efborchardt/product-feedback/blob/master/src/main/java/com/efborchardt/productfeedback/infrastructure/interfaces/rest/security/JwtTokenService.java)
- Using the Spring authentication context to obtain the user from the authorization token, propagating this information to the use case where it will later be used in business rule validations. [Example](https://github.com/efborchardt/product-feedback/blob/6e2f7acfb79b74ba1b72e93b8ddd27e009ed5989/src/main/java/com/efborchardt/productfeedback/infrastructure/interfaces/rest/routes/product/ProductController.java#L77)
- Use of notification pattern to validate several business rules at once and return only one exception to the user containing all the content of the validations that were not met. [Example](https://github.com/efborchardt/product-feedback/blob/6e2f7acfb79b74ba1b72e93b8ddd27e009ed5989/src/main/java/com/efborchardt/productfeedback/domain/user/model/User.java#L73)
- Implementation of Exception Handlers to handle errors globally in the API and avoid unhandled errors and unwanted returns. [Example](https://github.com/efborchardt/product-feedback/blob/master/src/main/java/com/efborchardt/productfeedback/infrastructure/interfaces/rest/errors/ApiExceptionHandler.java)
- Implemented unit tests across the entire application domain using JUnit and Mockito. [Example](https://github.com/efborchardt/product-feedback/blob/master/src/test/java/com/efborchardt/productfeedback/domain/product/service/ProductServiceTest.java)
- Implemented integration tests in the application persistence layer, using H2 in-memory database when running the application in the testing context. [Example](https://github.com/efborchardt/product-feedback/blob/master/src/test/java/com/efborchardt/productfeedback/domain/user/repository/UserRepositoryTest.java)
- Implemented tests on the application's api endpoints using MockMVC to perform the requests. [Example](https://github.com/efborchardt/product-feedback/blob/master/src/test/java/com/efborchardt/productfeedback/infrastructure/interfaces/rest/routes/auth/AuthControllerTest.java)
