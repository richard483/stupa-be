# User Authentication API Specification

This document outlines the API endpoints for user authentication in the Cashlez Promo Engine
application.

## Base URL

```
/user
```

## Endpoints

### 1. Register

Register a new user in the system.

**Endpoint:** `POST /user/register`

#### Request

```json
{
  "username": "string",
  "password": "string",
  "role": "ADMIN | VIEWER"
}
```

| Field    | Type          | Required | Description                                   |
|----------|---------------|----------|-----------------------------------------------|
| username | string        | Yes      | Unique username for the user                  |
| password | string        | Yes      | Password (must be at least 8 characters long) |
| role     | enum (string) | Yes      | User role (ADMIN, VIEWER)                     |

#### Success Response

**Status Code:** `200 OK`

```json
{
  "data": "Registration Success"
}
```

#### Error Response

**Status Code:** `400 Bad Request`

```json
{
  "error": "ERROR_CODE",
  "message": "Error message details"
}
```

Common error messages:

- "Username already exists"
- "Password cannot be null or empty"
- "Password must be at least 8 characters"

**Status Code:** `500 Internal Server Error`

```json
{
  "error": "INTERNAL_SERVER_ERROR",
  "message": "An unexpected error occurred"
}
```

---

### 2. Create User

Create a new user in the system (without a password).

**Endpoint:** `POST /user/create`

#### Request

```json
{
  "username": "string",
  "role": "ADMIN | VIEWER",
  "partnerId": 123,
  "isActive": true
}
```

| Field     | Type          | Required | Description                                        |
|-----------|---------------|----------|----------------------------------------------------|
| username  | string        | Yes      | Unique username for the user                       |
| role      | enum (string) | Yes      | User role (ADMIN, VIEWER)                          |
| partnerId | number        | No       | ID of the partner associated with the user         |
| isActive  | boolean       | No       | Whether the user account is active (default: true) |

#### Success Response

**Status Code:** `200 OK`

```json
{
  "data": {
    "userId": 2
  }
}
```

| Field       | Type   | Description                    |
|-------------|--------|--------------------------------|
| data.userId | number | Unique identifier for the user |

#### Error Response

**Status Code:** `400 Bad Request`

```json
{
  "error": "ERROR_CODE",
  "message": "Error message details"
}
```

Common error messages:

- "Username already exists"

**Status Code:** `500 Internal Server Error`

```json
{
  "error": "INTERNAL_SERVER_ERROR",
  "message": "An unexpected error occurred"
}
```

---

### 3. Login

Authenticate a user and retrieve an access token.

**Endpoint:** `POST /user/login`

#### Request

```json
{
  "username": "string",
  "password": "string"
}
```

| Field    | Type   | Required | Description                     |
|----------|--------|----------|---------------------------------|
| username | string | Yes      | Username of the registered user |
| password | string | Yes      | Password of the user            |

#### Success Response

**Status Code:** `200 OK`

```json
{
  "data": {
    "token": "string",
    "user": {
      "id": 123,
      "username": "string",
      "role": "ADMIN | VIEWER",
      "partnerId": 123
    }
  }
}
```

| Field               | Type   | Description                                         |
|---------------------|--------|-----------------------------------------------------|
| data.token          | string | JWT authentication token                            |
| data.user.id        | number | Unique identifier for the user                      |
| data.user.username  | string | Username of the authenticated user                  |
| data.user.role      | enum   | User's role (ADMIN, or VIEWER)                      |
| data.user.partnerId | number | Partner ID associated with the user (if applicable) |

#### Error Response

**Status Code:** `400 Bad Request`

```json
{
  "error": "ERROR_CODE",
  "message": "Error message details"
}
```

Common error messages:

- "Wrong username or password"
- "User account is disabled"

**Status Code:** `500 Internal Server Error`

```json
{
  "error": "INTERNAL_SERVER_ERROR",
  "message": "An unexpected error occurred"
}
```

---

### 4. List All Users

List all users in the system.

**Endpoint:** `GET /user/list`

#### Success Response

**Status Code:** `200 OK`

```json
{
  "data": [
    {
      "id": 1,
      "username": "string1",
      "role": "ADMIN",
      "partnerId": null,
      "isActive": true
    },
    {
      "id": 2,
      "username": "string2",
      "role": "ADMIN",
      "partnerId": null,
      "isActive": true
    },
    ...
  ]
}
```

| Field     | Type    | Description                                |
|-----------|---------|--------------------------------------------|
| id        | number  | Unique identifier for the user             |
| username  | string  | Username of the user                       |
| role      | enum    | User's role \(ADMIN or VIEWER\)            |
| partnerId | number  | Partner ID associated with user \(if any\) |
| isActive  | boolean | Whether the user account is active         |

#### Error Response

**Status Code:** `500 Internal Server Error`

```json
{
  "error": "INTERNAL_SERVER_ERROR",
  "message": "An unexpected error occurred"
}
```

## Authentication

After successful login, use the provided JWT token in the Authorization header for subsequent API
calls:

```
Authorization: Bearer <token>
```
