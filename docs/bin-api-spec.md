# Bin API Specification

This document outlines the API endpoints for bin binding management in the Cashlez Promo Engine
application.

## Base URL

```
/bin-bind
```

## Endpoints

### 1. Bind Bins to Promo

Binds a list of binIds to a specific promo.

**Endpoint:** `POST /bin-bind/{promoId}`

#### Path Parameters

| Parameter | Type   | Description                |
|-----------|--------|----------------------------|
| promoId   | string | ID of the promo to bind to |

#### Request Body

```json
{
  "binIds ": [
    "string"
  ]
}
```

| Field  | Type         | Required | Description             |
|--------|--------------|----------|-------------------------|
| binIds | list<string> | Yes      | List of bin IDs to bind |

#### Success Response

**Status Code:** `200 OK`

The response is wrapped in a `BaseResponse` object. For a successful operation, `error` and`message`
fields in `BaseResponse` will be `null`.

```json
{
  "data": {
    "boundBins": [
      "string"
    ]
  }
}
```

| Field            | Type         | Description                      |
|------------------|--------------|----------------------------------|
| data.boundBins[] | list<string> | List of bins successfully bound. |

#### Error Response

**Status Code:** `400 Bad Request`

```json
{
  "error": "ERROR_CODE",
  "message": "Error message details"
}
```

Common error codes and messages:

- `PROMO_NOT_FOUND`: "Promo with ID {promoId} not found"
- `EMPTY_BIN_ID`: "Bin IDs must not be null or empty"
- `EMPTY_PARTNER_ID`: "Partner ID must not be null"

**Status Code:** `500 Internal Server Error`

```json
{
  "error": "INTERNAL_SERVER_ERROR",
  "message": "An unexpected error occurred"
}
```

---

### 2. Unbind Bins from Promo

Unbinds a list of bins from a specific promo.

**Endpoint:** `DELETE /bin-bind/{promoId}`

#### Path Parameters

| Parameter | Type   | Description                    |
|-----------|--------|--------------------------------|
| promoId   | string | ID of the promo to unbind from |

#### Request Body

```json
{
  "binIds": [
    "string"
  ]
}
```

| Field  | Type         | Required | Description               |
|--------|--------------|----------|---------------------------|
| binIds | list<string> | Yes      | List of bin IDs to unbind |

#### Success Response

**Status Code:** `200 OK`

The response is wrapped in a `BaseResponse` object. For a successful operation, `error` and`message`
fields in `BaseResponse` will be `null`.

```json
{
  "data": {
    "unboundBins": [
      "string"
    ]
  }
}
```

| Field              | Type         | Description         |
|--------------------|--------------|---------------------|
| data.unboundBins[] | list<string> | List of unboud bins |

#### Error Response

**Status Code:** `400 Bad Request`

```json
{
  "error": "ERROR_CODE",
  "message": "Error message details"
}
```

Common error codes and messages:

- `EMPTY_BIN_ID`: "Bin IDs must not be null or empty"
- `EMPTY_PARTNER_ID`: "Partner ID must not be null"

**Status Code:** `500 Internal Server Error`

```json
{
  "error": "INTERNAL_SERVER_ERROR",
  "message": "An unexpected error occurred"
}
```

---

### 3. List bound Merchants to Promo

Get list of merchants bound to promo.

**Endpoint:** `GET /bin-bind/list/{promoId}`

#### Path Parameters

| Parameter | Type   | Description                |
|-----------|--------|----------------------------|
| promoId   | string | ID of the promo (optional) |

#### Success Response

**Status Code:** `200 OK`

The response is wrapped in a `BaseResponse` object. For a successful operation, `error` and
`message` fields in `BaseResponse` will be `null`.

```json
{
  "data": [
    {
      "id": 1,
      "binNumber": "124356",
      "promoId": 4,
      "createdAt": "2025-06-13T02:21:12.792418"
    },
    {
      "id": 2,
      "binNumber": "420663",
      "promoId": 4,
      "createdAt": "2025-06-13T00:00:00"
    }
  ]
}
```

| Field            | Type   | Description          |
|------------------|--------|----------------------|
| data[].id        | number | id of merchant promo |
| data[].binNumber | text   | number of bin        |
| data[].promoId   | number | id of bind promo     |
| data[].createAt  | text   | create date time     |

#### Error Response

**Status Code:** `500 Internal Server Error`

```json
{
  "error": "INTERNAL_SERVER_ERROR",
  "message": "An unexpected error occurred"
}
```

---

## Authentication

Refer to the [User Authentication API Specification](auth-api-spec.md) for details on how to
authenticate and use the JWT token.
The token should be included in the `Authorization` header for all bin API calls:

```
Authorization: Bearer <token>
```

