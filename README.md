# 📚 Spring CRUD Project - Book Management API

A RESTful API for managing books and authors built with Spring Boot, JPA, and MySQL.

## 🚀 Features

- **CRUD Operations** for Books
- **Automatic Author Management** - Reuses existing authors or creates new ones
- **RESTful API** with JSON responses
- **MySQL Database** integration with JPA/Hibernate
- **DTO Pattern** with ModelMapper for clean separation
- **Detailed Logging** for debugging and monitoring

---

## 🛠️ Tech Stack

- **Java** 17+
- **Spring Boot** 3.x
- **Spring Data JPA** - Database access
- **MySQL** - Database
- **ModelMapper** - DTO mapping
- **Lombok** - Reduce boilerplate code
- **Maven** - Dependency management

---

## 📋 Prerequisites

Before running this application, make sure you have:

- ✅ **JDK 17** or higher installed
- ✅ **Maven** installed
- ✅ **MySQL Server** running (via XAMPP or standalone)
- ✅ **IDE** (IntelliJ IDEA recommended)

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
spring.datasource.url=jdbc:mysql://localhost:3306/spring_crud_db
spring.datasource.username=root
spring.datasource.password=
```

**Note:** If your MySQL has a password, update the `password` field.

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

## 📡 API Endpoints

### Base URL
```
http://localhost:9000
```

### 📖 Book Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/books` | Create a new book |
| `GET` | `/books` | Get all books |
| `GET` | `/books/{id}` | Get book by ID |

---

## 🧪 API Testing Guide

### Using Postman

#### 1️⃣ Create a Book (POST)

**URL:** `http://localhost:9000/books`  
**Method:** `POST`  
**Body (JSON):**

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

**Response (200 OK):**

```json
{
  "id": 1,
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

#### 2️⃣ Create Another Book with Same Author

**Body (JSON):**

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

**Response:**

```json
{
  "id": 2,
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

#### 3️⃣ Get All Books (GET)

**URL:** `http://localhost:9000/books`  
**Method:** `GET`

**Response:**

```json
[
  {
    "id": 1,
    "title": "Clean Code",
    "isbn": "978-0132350884",
    "author": {
      "id": 1,
      "firstName": "Robert",
      "lastName": "Martin"
    }
  },
  {
    "id": 2,
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

#### 4️⃣ Get Book by ID (GET)

**URL:** `http://localhost:9000/books/1`  
**Method:** `GET`

**Response:**

```json
{
  "id": 1,
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

### Using cURL

#### Create a Book:

```bash
curl -X POST http://localhost:9000/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Clean Code",
    "isbn": "978-0132350884",
    "author": {
      "firstName": "Robert",
      "lastName": "Martin"
    }
  }'
```

#### Get All Books:

```bash
curl http://localhost:9000/books
```

#### Get Book by ID:

```bash
curl http://localhost:9000/books/1
```

---

### Using IntelliJ HTTP Client

Create a file `api-test.http`:

```http
### Create a book
POST http://localhost:9000/books
Content-Type: application/json

{
  "title": "Clean Code",
  "isbn": "978-0132350884",
  "author": {
    "firstName": "Robert",
    "lastName": "Martin"
  }
}

### Get all books
GET http://localhost:9000/books

### Get book by ID
GET http://localhost:9000/books/1
```

---

## 🏗️ Project Structure

```
src/main/java/com/example/Spring_crud_project/
├── controller/
│   └── BookController.java          # REST endpoints
├── dto/
│   ├── classes/
│   │   ├── AuthorDto.java           # Author data transfer object
│   │   ├── BookDto.java             # Book data transfer object
│   │   └── BookCreateDto.java       # Book creation DTO
│   └── mapper/
│       ├── AuthorMapper.java        # Author DTO ↔ Entity mapping
│       └── BookMapper.java          # Book DTO ↔ Entity mapping
├── entity/
│   ├── Author.java                  # Author JPA entity
│   └── Book.java                    # Book JPA entity
├── repository/
│   ├── AuthorRepository.java        # Author database operations
│   └── BookRepository.java          # Book database operations
├── service/
│   ├── BookService.java             # Service interface
│   └── impl/
│       └── BookServiceImpl.java     # Business logic implementation
└── SpringCrudProjectApplication.java # Main application class
```

---

## 🔑 Key Features Explained

### 1. Smart Author Management

When you create a book:
- The system checks if the author (by first name + last name) already exists
- If **exists**: Reuses the existing author
- If **not exists**: Creates a new author

This prevents duplicate authors in the database! 🎯

### 2. DTO Pattern

- **DTOs** (Data Transfer Objects) are used for API communication
- **Entities** are used for database persistence
- **Mappers** convert between DTOs and Entities
- This keeps your API contract separate from database structure

### 3. Detailed Logging

The application logs every step:

```
📥 Incoming POST /books request: BookDto(...)
🔄 Mapped DTO → Entity: Book(...)
🔍 Checking author: Robert Martin
🆕 Author not found → creating new one
👤 Using author ID: 1
📚 Book saved with ID: 1
💾 Saved Book in DB: Book(...)
```

---

## 📊 Database Schema

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
| `isbn` | VARCHAR(255) | ISBN number |
| `author_id` | BIGINT | Foreign key to `author` table |

**Relationship:** Many Books → One Author (Many-to-One)

---

## ⚠️ Common Issues & Solutions

### Problem: "Connection refused"
**Cause:** Application not running  
**Solution:** Start the Spring Boot application

### Problem: "404 Not Found"
**Cause:** Wrong URL or port  
**Solution:** Verify you're using `http://localhost:9000`

### Problem: "SSL Error" in Postman
**Cause:** Using `https://` instead of `http://`  
**Solution:** Change URL to `http://localhost:9000`

### Problem: "500 Internal Server Error"
**Cause:** MySQL not running  
**Solution:** Start XAMPP MySQL server

### Problem: Database connection error
**Cause:** Database `spring_crud_db` doesn't exist  
**Solution:** Create the database in MySQL:
```sql
CREATE DATABASE spring_crud_db;
```

---

## 🔧 Configuration

### Change Server Port

Edit `application.properties`:

```properties
server.port=8080
```

### Enable SQL Logging

```properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### Change Database Name

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
```

---

## 🚀 Future Enhancements

Potential features to add:

- [ ] Update book endpoint (PUT)
- [ ] Delete book endpoint (DELETE)
- [ ] Search books by title or author
- [ ] Pagination for book listings
- [ ] Input validation with `@Valid`
- [ ] Custom exception handling
- [ ] API documentation with Swagger/OpenAPI
- [ ] Unit and integration tests
- [ ] Authentication & Authorization

---

## 📝 License

This project is created for educational purposes.

---

## 👨‍💻 Author

Created as a learning project for Spring Boot CRUD operations.

---

## 🤝 Contributing

Feel free to fork this project and submit pull requests!

---

## 📞 Support

If you encounter any issues:
1. Check the **Common Issues** section above
2. Verify MySQL is running
3. Check application logs in the console
4. Ensure all dependencies are installed

---

**Happy Coding! 🚀📚**