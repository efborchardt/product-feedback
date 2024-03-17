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
```
psql -U postgres
```
You will be prompted to enter the password set during the PostgreSQL installation.

- **Create the new database:** Use the following SQL command to create a new database. Replace fbmanagement with your desired database name if you want a different name.
```
CREATE DATABASE fbmanagement;
```
- **Create the database user:** Create a user who will have access to this database. Replace efborchardt with your desired username and efb123 with your desired password.
```
CREATE USER efborchardt WITH ENCRYPTED PASSWORD 'efb123';
```
- **Grant privileges to the user:** Grant all privileges of the created database to the user.
```
GRANT ALL PRIVILEGES ON DATABASE fbmanagement TO efborchardt;
```
- **Exit psql:** You can exit the PostgreSQL terminal by typing `\q`.

### 2. Setting Up Environment Variables or Using Default Credentials

Using Environment Variables to set up environment variables on your operating system:

#### Linux & macOS:

- Open your `~/.bashrc`, `~/.zshrc`, or the configuration file of your preferred shell.
- Add the following lines at the end of the file (replace the values as necessary):

```
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=fbmanagement
export DB_USERNAME=efborchardt
export DB_PASSWORD=efb123
```
- Save the file and reload your shell's configuration. For **.bashrc**, you can use `source ~/.bashrc`.

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
```bash
git clone https://github.com/efborchardt/product-feedback.git
```
SSH
```bash
git clone git@github.com:efborchardt/product-feedback.git
```
### 4. Running the Project
- Access the project folder in terminal/cmd:
```
cd project-path/product-feedback
```
- Use Gradle Wrapper to run the project:

Linux & macOS
```
./gradlew bootRun
```
Windows
```
.\gradlew.bat bootRun
```
Or run the project through your IDE.

- The project will be running on port **50001**.

## ☕ Using product-feedback API

To use product-feedback, follow these steps:

- Check the API documentation by going to:

```
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
```
http://localhost:50001/api/public/auth/login
```
- All other endpoints are authenticated and the Header **Authorization** must be entered as **Bearer Token**. Example:
```
curl --request GET \
  --url http://localhost:50001/api/products \
  --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJwcm9kdWN0LWZlZWRiYWNrLWFwaSIsInN1YiI6ImFkbWluIiwiZXhwIjoxNzEwNjY1OTg0fQ.D8b8fU9IaETRYiBnHugPCG3yluEcls59qIjnDd3E6ew'
```
- If you want to create more users, log in using the default admin user, the user creation endpoint is protected for admins only.