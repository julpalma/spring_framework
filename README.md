# Spring modules included in this project: 

Spring MVC + Spring Data JPA + Bean Validation, along with testing libraries. No security module is added yet.

1. Spring Boot Starter Web
 * This dependency includes Spring MVC.\
    Provides REST controller support, embedded Tomcat server, and JSON serialization (via Jackson).

2. Spring Data JPA
 * This dependency provides Spring Data JPA.\
   Handles database access via JPA repositories.\
   There is PostgreSQL as runtime database, so this is fully integrated.

3. Spring Boot Starter Validation
 * For unit and integration testing (JUnit, Mockito, etc.).

# This project uses:

 * Spring Boot 3.4.3
 * Java 17
 * PostgreSQL (runtime database)
 * Maven

# Developer setup:
1. Local Java Environment:
   * brew install openjdk@17

2. Set $JAVA_HOME:
   * export JAVA_HOME=/opt/homebrew/opt/openjdk@17
   * export PATH=$JAVA_HOME/bin:$PATH

3. Verify JDK versions:
   * java -version
   * mvn -v

4. IntelliJ set up:
   * Set Project SDK to JDK 17: File → Project Structure → Project → Project SDK → JDK 17
   * Set Module SDK and Language Level to 17.

5. Maven configuratiom:
   * Update maven-compiler-plugin in pom.xml to use Java 17.

6. Refresh Maven dependencies locally:
   * mvn clean install -U

7. Building the Project
   * mvn clean verify
  
   * Ensures all dependencies are up-to-date, project is compiled, and tests are executed.

# Unit Testing:
Unit tests are implemented using JUnit 5 and Mockito.
Jacoco plugin is configured to check test coverage (min of 80%).

1. Used Mockito for mocking UserRepository:
    * @Mock for repository
    * @InjectMocks for UserServiceImpl
2. Tests cover:
    * createUser
    * getUserById
    * updateUser
    * deleteUser
    * Exceptions for user not found
  
# Developer API Reference:
| Method                                  | Description                                                       |
| --------------------------------------- | ----------------------------------------------------------------- |
| `createUser(UserRequest userRequest)`   | Creates a new user from a request DTO and returns `UserResponse`. |
| `getUserById(String id)`                | Retrieves user information by ID and returns `UserResponse`.      |
| `updateUser(UserUpdateRequest request)` | Updates user data by ID and returns updated `UserResponse`.       |
| `deleteUser(String id)`                 | Deletes a user by ID.                                             |

Notes:
DTOs (Data Transfer Objects):
  * UserRequest: Used when creating a new user.
  * UserResponse: Returned after creating or retrieving a user.
  * UserUpdateRequest: Used when updating existing user information.

# REST API Endpoints:
The application exposes CRUD operations for User. All endpoints accept/return JSON.

| HTTP Method | Endpoint      | Request Body        | Description               |
| ----------- | ------------- | ------------------- | ------------------------- |
| `POST`      | `/users`      | `UserRequest`       | Create a new user         |
| `GET`       | `/users/{id}` | N/A                 | Get user details by ID    |
| `PUT`       | `/users/{id}` | `UserUpdateRequest` | Update user details by ID |
| `DELETE`    | `/users/{id}` | N/A                 | Delete a user by ID       |

# Examples:
1. Create user:
  * POST http://localhost:8080/users
  * Content-Type: application/json

  * {
  "firstName": "Juliana",
  "lastName": "Palma",
  "email": "juliana@example.com",
  "phone": "1234567890",
  "address": "123 Main St",
  "country": "Brazil"
 }

Response: Json
  * {
  "id": 1,
  "firstName": "Juliana",
  "lastName": "Palma",
  "email": "juliana@example.com",
  "phone": "1234567890",
  "address": "123 Main St",
  "country": "Brazil"
  }

2. Get user by Id:
  * GET http://localhost:8080/users/1

Response: Json
  * {
  "id": 1,
  "firstName": "Juliana",
  "lastName": "Palma",
  "email": "juliana@example.com",
  "phone": "1234567890",
  "address": "123 Main St",
  "country": "Brazil"
  }

3. Update user by Id:
  * PUT http://localhost:8080/users/1
  * Content-Type: application/json

  * {
  "id": 1,
  "firstName": "Juliana",
  "lastName": "Palma",
  "email": "juliana.new@example.com",
  "phone": "0987654321",
  "address": "NEW ADDRESS",
  "country": "Brazil"
  }

Response: Json
  * {
  "id": 1,
  "firstName": "Juliana",
  "lastName": "Palma",
  "email": "juliana.new@example.com",
  "phone": "0987654321",
  "address": "NEW ADDRESS",
  "country": "Brazil"
  }

4. Delete user by Id:
* DELETE http://localhost:8080/users/1

Response: Json
  * {
  "message": "User deleted successfully"
  }

