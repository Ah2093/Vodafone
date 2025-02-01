# Vodafone User Authentication System

This project is a Spring Boot application that provides user registration and login functionality using JWT for authentication and authorization. It also includes a welcome page that displays a message to authenticated users.
## Features

- User registration with validation

- JWT-based login authentication

- Secure, authenticated access to protected pages (e.g., Welcome page)

- Frontend forms for registration and login

- Error handling for authentication failures

## Project Structure

### Controllers

- **AuthController**: Handles registration and login requests.

- **ViewController**: Handles rendering of the welcome page.
### Services

- **CustomerServiceAuth**: Contains business logic for user registration, authentication, and fetching user profiles.
### Security

- **SecurityConfig**: Configures security settings, including JWT authentication and endpoint security.

- **JwtTokenProvider**: Generates and validates JWT tokens.

- **JwtAuthenticationFilter**: Filters incoming requests to check for valid JWT tokens.

- **JwtAuthenticationProvider**: Authenticates users based on JWT tokens.

- **SecurityUserAdapter**: Adapts the `Customer` entity to Spring Security's `UserDetails`.

### Models

- **Customer**: Represents a user in the system.

### Repositories

- **UserRepository**: Provides CRUD operations for `Customer` entities.


### DTOs

- **RegisterRequest**: Request DTO for user registration.

- **LogInRequest**: Request DTO for user login.

- **RegisterResponse**: Response DTO for user registration.

- **LogInResponse**: Response DTO for user login.

- **UserProfileResponse**: Response DTO for user profile.

### Validation

- **PhoneNumber**: Custom annotation for phone number validation.

- **PhoneNumberValidator**: Validator for the `PhoneNumber` annotation for Egyptian Vodafone Number .

### Configuration

- **ApplicationConfig**: Configures the `ModelMapper` bean.



## Endpoints

### Authentication Endpoints

- **POST /api/auth/register**: Registers a new user.

- **POST /api/auth/login**: Authenticates a user and returns a JWT token.


### Welcome Page Endpoint

- **GET /view/welcome**: Renders the welcome page.



## Frontend

### JavaScript

- **auth.js**: Handles user authentication and registration, manages user sessions using JWT tokens, and fetches user profile data.

### HTML

- **welcome.html**: Template for the welcome page.



## Unit Tests

### Controllers

- **AuthControllerTest**: Tests the registration and login endpoints to ensure they handle requests and responses correctly.

### Services

- **CustomerServiceAuthTest**: Tests the business logic for user registration and authentication.

## Setup and Running the Project

### Prerequisites

Ensure the following tools are installed:

- Java 17 or higher

- Maven 3.6.3 or higher

- IDE (e.g., IntelliJ IDEA, Eclipse) for development (optional)

### Running the Application

Follow the steps below to set up and run the application:


1. Clone the repository:


```bash

git clone https://github.com/your-repo/vodafone-auth-system.git

cd vodafone-auth-system
```

  

  

2. Build the project:
```bash


   mvn clean install
```



3. Run the application:

```bash

   mvn spring-boot:run

```  


4. Access the application:

- Registration: http://localhost:8080/view/register

- Login: http://localhost:8080/view/login

- Welcome: http://localhost:8080/view/welcome



## Example Requests



### Registration

```bash


POST /api/auth/register

Content-Type: application/json

  

{

    "firstName": "Test",

    "lastName": "User",

    "userName": "testuser",

    "email": "testuser@example.com",

    "password": "password123",

    "phoneNumber": "0103456789"

}

  
  ```

### Login

```bash


POST /api/auth/login

Content-Type: application/json

  

{

    "userName": "testuser",

    "password": "password123"

}

  
  ```


### Welcome Page



```bash
GET /view/welcome


Authorization: Bearer <JWT_TOKEN>

  ```



## License

This project is licensed under the Vodafone License.
