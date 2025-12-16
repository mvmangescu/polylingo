# Course Service

Course management microservice for the Polylingo language learning platform.

## Overview

This service manages courses, modules, and lessons for learning Romanian, English, and Spanish languages.

## Features

- Course CRUD operations
- Module management within courses
- Lesson management within modules
- Search courses by language pairs
- Filter by difficulty level and tags
- Progressive unlocking of modules and lessons
- OpenAPI/Swagger documentation

## Tech Stack

- Java 21
- Spring Boot 3.2.1
- Spring Data JPA
- PostgreSQL
- Flyway (database migrations)
- SpringDoc OpenAPI 3 (API documentation)
- Lombok
- Maven

## Prerequisites

- Java 21
- PostgreSQL 15+
- Maven 3.8+

## Database Setup

Create a PostgreSQL database:

```sql
CREATE DATABASE polylingo_courses;
```

Update database credentials in `src/main/resources/application.yml` if needed.

## Running the Service

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The service will start on port **8083**.

## API Documentation

Once the service is running, access:

- **Swagger UI**: http://localhost:8083/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8083/api-docs

## API Endpoints

### Courses

- `GET /api/courses` - Get all courses (admin)
- `GET /api/courses/published` - Get published courses
- `GET /api/courses/{id}` - Get course by ID
- `GET /api/courses/search?sourceLanguage=en&targetLanguage=ro` - Search courses
- `GET /api/courses/tags/{tag}` - Get courses by tag
- `POST /api/courses` - Create course (admin)
- `PUT /api/courses/{id}` - Update course (admin)
- `DELETE /api/courses/{id}` - Delete course (admin)

### Modules

- `GET /api/modules/course/{courseId}` - Get modules by course
- `GET /api/modules/course/{courseId}/unlocked` - Get unlocked modules
- `GET /api/modules/{id}` - Get module by ID
- `PATCH /api/modules/{id}/unlock` - Unlock module

### Lessons

- `GET /api/lessons/module/{moduleId}` - Get lessons by module
- `GET /api/lessons/module/{moduleId}/unlocked` - Get unlocked lessons
- `GET /api/lessons/course/{courseId}` - Get all lessons by course
- `GET /api/lessons/{id}` - Get lesson by ID
- `PATCH /api/lessons/{id}/unlock` - Unlock lesson

## Database Schema

The service uses Flyway for database migrations. Initial schema includes:

- **courses** - Course information
- **modules** - Course modules
- **lessons** - Individual lessons
- **course_tags** - Course tagging

## Sample Data

Sample data is included in the initial migration for testing:

- English for Romanian Speakers (BEGINNER)
- Spanish Basics (BEGINNER)
- Advanced Romanian Grammar (ADVANCED)

## Configuration

Key configuration properties in `application.yml`:

```yaml
server:
  port: 8083

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/polylingo_courses
    username: postgres
    password: postgres
```

## Testing

```bash
mvn test
```

## Future Enhancements

- User enrollment tracking (integrate with User Service)
- Progress tracking (integrate with Progress Service)
- Content versioning
- Course recommendations
- Course ratings and reviews
