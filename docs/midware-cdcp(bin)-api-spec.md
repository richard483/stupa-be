# Midware CDCP API Specification

This document outlines the API endpoints for searching bin in the Cashlez Promo Engine application.

## Base URL

```
/midware-bin
```

## Endpoints

### 1. Search Bin by Bin Number

Retrieves a list of Bin (String) contain the provided keyword.

**Endpoint:** `GET /midware-bin/search`

#### Query Parameters

| Parameter | Type   | Required | Description                            |
|-----------|--------|----------|----------------------------------------|
| keyword   | string | Yes      | The keyword to search for in Bin list. |

#### Success Response

**Status Code:** `200 OK`

The response is wrapped in a `BaseResponse` object. For a successful operation, `error` and
`message` fields in `BaseResponse` will be `null`.

```json
{
  "data": [
    "123123",
    "321321",
    "456456"
  ]
}
```

| Field | Type         | Description                                                                                 |
|-------|--------------|---------------------------------------------------------------------------------------------|
| data  | list<String> | List of `String` objects matching the search keyword. May be empty if no matches are found. |

#### Error Response

**Status Code:** `500 Internal Server Error`

This occurs if an unexpected error happens on the server.

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
The token should be included in the `Authorization` header for all merchant API calls:

```
Authorization: Bearer <token>
```

