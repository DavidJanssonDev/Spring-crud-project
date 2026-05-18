# 📚 Spring Boot Book Management API

A secure RESTful API for managing books and authors with JWT authentication and role-based access control, built with Spring Boot, Spring Security, JPA, and MySQL.

## 🚀 Features

- ✅ **JWT Authentication** - Secure token-based authentication
- ✅ **Role-Based Access Control** - USER and ADMIN roles with different permissions
- ✅ **CRUD Operations** for Books with authorization
- ✅ **Smart Author Management** - Automatically reuses existing authors or creates new ones
- ✅ **Input Validation** - Jakarta Bean Validation on DTOs
- ✅ **Global Exception Handling** - Custom error responses
- ✅ **RESTful API** with JSON responses
- ✅ **MySQL Database** integration with JPA/Hibernate
- ✅ **DTO Pattern** with MapStruct for clean separation
- ✅ **Public Read Access** - Anyone can view books without authentication
- ✅ **Protected Write Operations** - Authentication required for creating/updating/deleting

---

## 🛠️ Tech Stack

- **Java** 17+
- **Spring Boot** 3.x
- **Spring Security** - Authentication & Authorization
- **Spring Data JPA** - Database access
- **JWT (JJWT)** - Token generation and validation
- **MySQL** - Database
- **MapStruct** - DTO mapping
- **Jakarta Validation** - Input validation
- **Maven** - Dependency management

---

## 📋 Prerequisites

Before running this application, make sure you have:

- ✅ **JDK 17** or higher installed
- ✅ **Maven** installed
- ✅ **MySQL Server** running (via XAMPP or standalone)
- ✅ **IDE** (IntelliJ IDEA recommended)
- ✅ **Postman** or similar API testing tool

---

## ⚙️ Setup & Installation

### 1. Clone the Repository

```bash
git clone <your-repo-url>
cd Spring-crud-project
```

### 2. Setup MySQL Database

Start **XAMPP** or your MySQL server, then create the database:

```sql
CREATE DATABASE spring_crud_db;
```

### 3. Configure Database Connection

The `application.properties` is already configured:

```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/spring_crud_db
spring.datasource.username=root
spring.datasource.password=

# JWT Configuration
jwt.secret=ShCeWHcPXolx5lxjQqFx6ZlCmJEmzAUtoRNR8UIvn2c
jwt.expiration=3600000

# Server Port
server.port=9000
```

**Note:**
- If your MySQL has a password, update the `password` field
- The JWT secret is for development only - generate a new one for production
- JWT expiration is set to 1 hour (3600000 ms)

### 4. Install Dependencies

```bash
mvn clean install
```

### 5. Run the Application

```bash
mvn spring-boot:run
```

Or run `SpringCrudProjectApplication.java` from your IDE.

The application will start on: **http://localhost:9000**

---

## 🔐 Authentication & Authorization

### User Roles

| Role | Permissions |
|------|-------------|
| **USER** | Can create books |
| **ADMIN** | Can create, update, and delete books |
| **Anonymous** | Can only read/view books (GET endpoints) |

### Security Rules

- 🔓 **Public endpoints:** All GET requests to `/api/books/**`
- 🔓 **Auth endpoints:** `/auth/login`, `/auth/register`, `/auth/create-admin`
- 🔒 **Protected endpoints:** POST, PUT, DELETE require authentication

---

## 📡 API Endpoints

### Base URL
```
http://localhost:9000
```

### 🔑 Authentication Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `POST` | `/auth/register` | Register new user (USER role) | No |
| `POST` | `/auth/login` | Login and get JWT token | No |
| `POST` | `/auth/create-admin` | Create admin user (⚠️ BACKDOOR) | No |

### 📖 Book Endpoints

| Method | Endpoint | Description | Auth Required | Role Required |
|--------|----------|-------------|---------------|---------------|
| `GET` | `/api/books` | Get all books | No | - |
| `GET` | `/api/books/{id}` | Get book by ID | No | - |
| `GET` | `/api/books/title/{title}` | Search books by title | No | - |
| `POST` | `/api/books` | Create a new book | Yes | USER or ADMIN |
| `PUT` | `/api/books/{id}` | Update a book | Yes | ADMIN only |
| `DELETE` | `/api/books/{id}` | Delete a book | Yes | ADMIN only |

---

## 🧪 API Testing Guide

### Step 1: Create an Admin User (⚠️ Development Only)

**URL:** `http://localhost:9000/auth/create-admin`  
**Method:** `POST`  
**Headers:**
```
Content-Type: application/json
```
**Body:**

```json
{
  "username": "admin",
  "password": "admin123"
}
```

**Response (200 OK):**

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcxNjAyNzY0MCwiZXhwIjoxNzE2MDMxMjQwfQ..."
}
```

**⚠️ IMPORTANT:** This is a backdoor endpoint for development. Remove it before deploying to production!

---

### Step 2: Register a Regular User

**URL:** `http://localhost:9000/auth/register`  
**Method:** `POST`  
**Headers:**
```
Content-Type: application/json
```
**Body:**

```json
{
  "username": "john_doe",
  "password": "password123"
}
```

**Response (200 OK):**

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huX2RvZSIsImlhdCI6MTcxNjAyNzY0MCwiZXhwIjoxNzE2MDMxMjQwfQ..."
}
```

---

### Step 3: Login

**URL:** `http://localhost:9000/auth/login`  
**Method:** `POST`  
**Headers:**
```
Content-Type: application/json
```
**Body:**

```json
{
  "username": "admin",
  "password": "admin123"
}
```

**Response (200 OK):**

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcxNjAyNzY0MCwiZXhwIjoxNzE2MDMxMjQwfQ..."
}
```

**💡 Save this token!** You'll need it for authenticated requests.

---

### Step 4: Create a Book (Authenticated - USER or ADMIN)

**URL:** `http://localhost:9000/api/books`  
**Method:** `POST`  
**Headers:**
```
Content-Type: application/json
Authorization: Bearer YOUR_TOKEN_HERE
```
**Body:**

```json
{
  "title": "Clean Code",
  "isbn": "978-0132350884",
  "author": {
    "firstName": "Robert",
    "lastName": "Martin"
  }
}
```

**Response (201 Created):**

```json
{
  "title": "Clean Code",
  "isbn": "978-0132350884",
  "author": {
    "id": 1,
    "firstName": "Robert",
    "lastName": "Martin"
  }
}
```

---

### Step 5: Create Another Book with Same Author

**URL:** `http://localhost:9000/api/books`  
**Method:** `POST`  
**Headers:**
```
Content-Type: application/json
Authorization: Bearer YOUR_TOKEN_HERE
```
**Body:**

```json
{
  "title": "Clean Architecture",
  "isbn": "978-0134494166",
  "author": {
    "firstName": "Robert",
    "lastName": "Martin"
  }
}
```

**Response (201 Created):**

```json
{
  "title": "Clean Architecture",
  "isbn": "978-0134494166",
  "author": {
    "id": 1,
    "firstName": "Robert",
    "lastName": "Martin"
  }
}
```

**Note:** The `author.id` is still `1` - the existing author was reused! ✅

---

### Step 6: Get All Books (Public - No Auth Required)

**URL:** `http://localhost:9000/api/books`  
**Method:** `GET`

**Response (200 OK):**

```json
[
  {
    "title": "Clean Code",
    "isbn": "978-0132350884",
    "author": {
      "id": 1,
      "firstName": "Robert",
      "lastName": "Martin"
    }
  },
  {
    "title": "Clean Architecture",
    "isbn": "978-0134494166",
    "author": {
      "id": 1,
      "firstName": "Robert",
      "lastName": "Martin"
    }
  }
]
```

---

### Step 7: Get Book by ID (Public - No Auth Required)

**URL:** `http://localhost:9000/api/books/1`  
**Method:** `GET`

**Response (200 OK):**

```json
{
  "title": "Clean Code",
  "isbn": "978-0132350884",
  "author": {
    "id": 1,
    "firstName": "Robert",
    "lastName": "Martin"
  }
}
```

---

### Step 8: Search Books by Title (Public - No Auth Required)

**URL:** `http://localhost:9000/api/books/title/clean`  
**Method:** `GET`

**Response (200 OK):**

```json
[
  {
    "title": "Clean Code",
    "isbn": "978-0132350884",
    "author": {
      "id": 1,
      "firstName": "Robert",
      "lastName": "Martin"
    }
  },
  {
    "title": "Clean Architecture",
    "isbn": "978-0134494166",
    "author": {
      "id": 1,
      "firstName": "Robert",
      "lastName": "Martin"
    }
  }
]
```

**Note:** Search is case-insensitive and matches partial titles.

---

### Step 9: Update a Book (Authenticated - ADMIN Only)

**URL:** `http://localhost:9000/api/books/1`  
**Method:** `PUT`  
**Headers:**
```
Content-Type: application/json
Authorization: Bearer YOUR_ADMIN_TOKEN_HERE
```
**Body:**

```json
{
  "title": "Clean Code - Updated Edition",
  "isbn": "978-0132350884",
  "author": {
    "firstName": "Robert",
    "lastName": "Martin"
  }
}
```

**Response (200 OK):**

```json
{
  "title": "Clean Code - Updated Edition",
  "isbn": "978-0132350884",
  "author": {
    "id": 1,
    "firstName": "Robert",
    "lastName": "Martin"
  }
}
```

**⚠️ Note:** Only users with ADMIN role can update books. USER role will get 403 Forbidden.

---

### Step 10: Delete a Book (Authenticated - ADMIN Only)

**URL:** `http://localhost:9000/api/books/1`  
**Method:** `DELETE`  
**Headers:**
```
Authorization: Bearer YOUR_ADMIN_TOKEN_HERE
```

**Response (204 No Content)**

**⚠️ Note:** Only users with ADMIN role can delete books. USER role will get 403 Forbidden.

---

## 🔴 Error Responses

### Validation Error (400 Bad Request)

When submitting invalid data:

```json
{
  "timestamp": "2026-05-18T09:03:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "validationErrors": {
    "title": "Title is required",
    "isbn": "ISBN must be between 10 and 20 characters",
    "author": "Author is required"
  }
}
```

### Unauthorized (401)

When accessing protected endpoints without a token:

```json
{
  "status": 401,
  "error": "UNAUTHORIZED",
  "message": "You must be logged in to access this resource",
  "path": "/api/books",
  "timestamp": "2026-05-18T09:03:00"
}
```

### Forbidden (403)

When accessing an endpoint without sufficient permissions:

```json
{
  "status": 403,
  "error": "FORBIDDEN",
  "message": "You do not have permission to access this resource",
  "path": "/api/books/1",
  "timestamp": "2026-05-18T09:03:00"
}
```

### Not Found (404)

When requesting a book that doesn't exist:

```json
{
  "timestamp": "2026-05-18T09:03:00",
  "status": 404,
  "error": "Not Found",
  "message": "Book not found with the id of 999",
  "validationErrors": null
}
```

### Conflict (409)

When trying to create a book with a duplicate ISBN:

```json
{
  "timestamp": "2026-05-18T09:03:00",
  "status": 409,
  "error": "Conflict",
  "message": "Book with the 978-0132350884 already exists",
  "validationErrors": null
}
```

---

## 📋 Validation Rules

### BookDto

| Field | Rules |
|-------|-------|
| `title` | Required, 2-100 characters |
| `isbn` | Required, 10-20 characters, unique |
| `author` | Required, valid AuthorDto |

### AuthorDto

| Field | Rules |
|-------|-------|
| `firstName` | Required, 2-50 characters |
| `lastName` | Required, 2-50 characters |

---

## 🏗️ Project Structure

```
src/main/java/com/example/Spring_crud_project/
├── controller/
│   ├── AuthController.java          # Authentication endpoints
│   └── BookController.java          # Book CRUD endpoints
├── dto/
│   ├── classes/
│   │   ├── AuthorDto.java           # Author data transfer object
│   │   ├── BookDto.java             # Book data transfer object
│   │   ├── AuthResponse.java        # JWT token response
│   │   ├── LoginRequest.java        # Login credentials
│   │   └── RegisterRequest.java     # Registration data
│   └── mapper/
│       ├── AuthorMapper.java        # MapStruct mapper for Author
│       └── BookMapper.java          # MapStruct mapper for Book
├── entity/
│   ├── Author.java                  # Author JPA entity
│   ├── Book.java                    # Book JPA entity
│   ├── User.java                    # User JPA entity
│   └── enums/
│       └── Role.java                # USER, ADMIN roles
├── exception/
│   ├── customExceptions/
│   │   ├── IsbnBookAlreadyExistException.java
│   │   └── NoBookWithIDFoundException.java
│   ├── ErrorResponse.java           # Standard error response
│   ├── ErrorResponseClass.java      # Validation error response
│   └── GlobalExceptionHandler.java  # Global exception handler
├── repository/
│   ├── AuthorRepository.java        # Author database operations
│   ├── BookRepository.java          # Book database operations
│   └── UserRepository.java          # User database operations
├── security/
│   ├── JwtService.java              # JWT token generation & validation
│   ├── JwtAuthenticationFilter.java # JWT authentication filter
│   ├── JwtAuthenticationEntryPoint.java # Unauthorized handler
│   ├── CustomAccessDeniedHandler.java   # Forbidden handler
│   ├── CustomUserDetailsService.java    # Load user for authentication
│   └── SecurityConfig.java          # Security configuration
├── service/
│   ├── BookService.java             # Book service interface
│   ├── impl/
│   │   └── BookServiceImpl.java     # Book business logic
│   └── auth/
│       ├── AuthService.java         # Auth service interface
│       └── AuthServiceImpl.java     # Auth business logic
└── SpringCrudProjectApplication.java # Main application class
```

---

## 🔑 Key Features Explained

### 1. JWT Authentication Flow

```
1. User registers or logs in
2. Server generates JWT token (valid for 1 hour)
3. Client includes token in Authorization header: "Bearer <token>"
4. Server validates token on each request
5. If valid, request is processed; if invalid, 401 Unauthorized
```

### 2. Role-Based Access Control

```
Anonymous User:
  ✅ GET /api/books/**        (read books)
  ❌ POST/PUT/DELETE           (no write access)

USER Role:
  ✅ GET /api/books/**        (read books)
  ✅ POST /api/books          (create books)
  ❌ PUT /api/books/{id}      (cannot update)
  ❌ DELETE /api/books/{id}   (cannot delete)

ADMIN Role:
  ✅ GET /api/books/**        (read books)
  ✅ POST /api/books          (create books)
  ✅ PUT /api/books/{id}      (update books)
  ✅ DELETE /api/books/{id}   (delete books)
```

### 3. Smart Author Management

When you create a book:
- System checks if author (by first name + last name) already exists
- If **exists**: Reuses the existing author
- If **not exists**: Creates a new author

This prevents duplicate authors in the database! 🎯

### 4. DTO Pattern

- **DTOs** (Data Transfer Objects) for API communication
- **Entities** for database persistence
- **MapStruct** automatically converts between them
- Keeps API contract separate from database structure

### 5. Global Exception Handling

All exceptions are caught and returned in a consistent format:
- **Validation errors** → 400 with field-level errors
- **Not found errors** → 404 with descriptive message
- **Duplicate ISBN** → 409 Conflict
- **Authentication errors** → 401 Unauthorized
- **Authorization errors** → 403 Forbidden

---

## 📊 Database Schema

### `users` Table

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | User ID |
| `username` | VARCHAR(255) | UNIQUE, NOT NULL | Username |
| `password` | VARCHAR(255) | NOT NULL | BCrypt hashed password |
| `role` | VARCHAR(50) | NOT NULL | USER or ADMIN |

### `author` Table

| Column | Type | Description |
|--------|------|-------------|
| `id` | BIGINT | Primary key (auto-increment) |
| `first_name` | VARCHAR(255) | Author's first name |
| `last_name` | VARCHAR(255) | Author's last name |

### `book` Table

| Column | Type | Description |
|--------|------|-------------|
| `id` | BIGINT | Primary key (auto-increment) |
| `title` | VARCHAR(255) | Book title |
| `isbn` | VARCHAR(255) | ISBN number (unique) |
| `author_id` | BIGINT | Foreign key to `author` table |

**Relationships:**
- Many Books → One Author (Many-to-One)
- Many Books → One User (implicitly through creation)

---

## 🧪 Testing with cURL

### Register a User

```bash
curl -X POST http://localhost:9000/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"john","password":"password123"}'
```

### Login

```bash
curl -X POST http://localhost:9000/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"john","password":"password123"}'
```

### Create Admin (Backdoor)

```bash
curl -X POST http://localhost:9000/auth/create-admin \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

### Get All Books (No Auth)

```bash
curl http://localhost:9000/api/books
```

### Create a Book (With Auth)

```bash
curl -X POST http://localhost:9000/api/books \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -d '{
    "title": "1984",
    "isbn": "978-0451524935",
    "author": {
      "firstName": "George",
      "lastName": "Orwell"
    }
  }'
```

### Update a Book (Admin Only)

```bash
curl -X PUT http://localhost:9000/api/books/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE" \
  -d '{
    "title": "1984 - Updated",
    "isbn": "978-0451524935",
    "author": {
      "firstName": "George",
      "lastName": "Orwell"
    }
  }'
```

### Delete a Book (Admin Only)

```bash
curl -X DELETE http://localhost:9000/api/books/1 \
  -H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE"
```

---

## ⚠️ Common Issues & Solutions

### Problem: "401 Unauthorized"
**Cause:** Missing or invalid JWT token  
**Solution:**
1. Login or register to get a token
2. Include token in Authorization header: `Bearer <token>`
3. Check if token has expired (valid for 1 hour)

### Problem: "403 Forbidden"
**Cause:** User doesn't have required role  
**Solution:**
- To update/delete books, you need ADMIN role
- Use `/auth/create-admin` to create an admin user

### Problem: "409 Conflict - ISBN already exists"
**Cause:** Trying to create a book with duplicate ISBN  
**Solution:** Use a different ISBN or update the existing book

### Problem: "Connection refused"
**Cause:** Application not running  
**Solution:** Start the Spring Boot application

### Problem: "404 Not Found"
**Cause:** Wrong URL or endpoint  
**Solution:** Verify you're using `http://localhost:9000/api/books`

### Problem: Database connection error
**Cause:** MySQL not running or database doesn't exist  
**Solution:**
1. Start XAMPP MySQL server
2. Create database: `CREATE DATABASE spring_crud_db;`

### Problem: "User already exists"
**Cause:** Username is already taken  
**Solution:** Use a different username or login with existing credentials

---

## 🔧 Configuration

### Change Server Port

Edit `application.properties`:

```properties
server.port=8080
```

### Change JWT Expiration (Default: 1 hour)

```properties
jwt.expiration=7200000  # 2 hours in milliseconds
```

### Enable SQL Logging

```properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### Generate New JWT Secret

For production, generate a secure random secret:

```bash
openssl rand -base64 32
```

Then update `application.properties`:

```properties
jwt.secret=YOUR_NEW_SECRET_HERE
```

---

## 📝 Sample Test Data

### Create Multiple Books

```json
// Book 1
{
  "title": "The Pragmatic Programmer",
  "isbn": "978-0135957059",
  "author": {"firstName": "David", "lastName": "Thomas"}
}

// Book 2
{
  "title": "Design Patterns",
  "isbn": "978-0201633610",
  "author": {"firstName": "Erich", "lastName": "Gamma"}
}

// Book 3
{
  "title": "Refactoring",
  "isbn": "978-0201485677",
  "author": {"firstName": "Martin", "lastName": "Fowler"}
}

// Book 4 - Same author as Book 1
{
  "title": "Programming Ruby",
  "isbn": "978-0974514055",
  "author": {"firstName": "David", "lastName": "Thomas"}
}
```


---

## 📄 License

This project is created for educational purposes.

---

## 👨‍💻 Author

Created as a learning project for Spring Boot, Spring Security, and JWT authentication.

---

## 📚 Learning Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security Documentation](https://spring.io/projects/spring-security)
- [JWT.io](https://jwt.io/) - Learn about JWT
- [MapStruct Documentation](https://mapstruct.org/)
- [Jakarta Bean Validation](https://beanvalidation.org/)

---

**Happy Coding! 🚀📚🔐**