# Merchant API Specification

This document outlines the API endpoints for merchant binding management in the Cashlez Promo Engine
application.

## Base URL

```
/merchant-bind
```

## Endpoints

### 1. Bind Merchants to Promo

Binds a list of merchants to a specific promo.

**Endpoint:** `POST /merchant-bind/{promoId}`

#### Path Parameters

| Parameter | Type   | Description                |
|-----------|--------|----------------------------|
| promoId   | string | ID of the promo to bind to |

#### Request Body

```json
{
  "merchantIds": [
    "string"
  ]
}
```

| Field       | Type         | Required | Description                           |
|-------------|--------------|----------|---------------------------------------|
| merchantIds | list<string> | Yes      | List of merchant external IDs to bind |

#### Success Response

**Status Code:** `200 OK`

The response is wrapped in a `BaseResponse` object. For a successful operation, `error` and`message`
fields in `BaseResponse` will be `null`.

```json
{
  "data": {
    "boundMerchants": [
      "string"
    ]
  }
}
```

| Field                 | Type         | Description                                                                         |
|-----------------------|--------------|-------------------------------------------------------------------------------------|
| data.boundMerchants[] | list<string> | List of merchant external IDs successfully bound (mirrors the input `merchantIds`). |

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
- `EMPTY_MERCHANT_ID`: "Merchant IDs must not be null or empty"
- `EMPTY_PARTNER_ID`: "Partner ID must not be null"

**Status Code:** `500 Internal Server Error`

```json
{
  "error": "INTERNAL_SERVER_ERROR",
  "message": "An unexpected error occurred"
}
```

---

### 2. Unbind Merchants from Promo

Unbinds a list of merchants from a specific promo.

**Endpoint:** `DELETE /merchant-bind/{promoId}`

#### Path Parameters

| Parameter | Type   | Description                    |
|-----------|--------|--------------------------------|
| promoId   | string | ID of the promo to unbind from |

#### Request Body

```json
{
  "merchantIds": [
    "string"
  ]
}
```

| Field       | Type         | Required | Description                             |
|-------------|--------------|----------|-----------------------------------------|
| merchantIds | list<string> | Yes      | List of merchant external IDs to unbind |

#### Success Response

**Status Code:** `200 OK`

The response is wrapped in a `BaseResponse` object. For a successful operation, `error` and`message`
fields in `BaseResponse` will be `null`.

```json
{
  "data": {
    "unboundMerchants": [
      "string"
    ]
  }
}
```

| Field                   | Type         | Description                                                                                                                                                                                                                                                                         |
|-------------------------|--------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| data.unboundMerchants[] | list<string> | List of internal database IDs (from the `merchant_promo` table) of the merchant-promo binding records that were successfully unbound. These are **not** the `merchantIdExt` values provided in the request. If no matching merchants were found to unbind, this list will be empty. |

#### Error Response

**Status Code:** `400 Bad Request`

```json
{
  "error": "ERROR_CODE",
  "message": "Error message details"
}
```

Common error codes and messages:

- `EMPTY_MERCHANT_ID`: "Merchant IDs must not be null or empty"
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

**Endpoint:** `GET /merchant-bind/list/{promoId}`

#### Path Parameters

| Parameter | Type   | Description                |
|-----------|--------|----------------------------|
| promoId   | string | ID of the promo (optional) |

#### Success Response

**Status Code:** `200 OK`

The response is wrapped in a `BaseResponse` object. For a successful operation, `error` and`message`
fields in `BaseResponse` will be `null`.

```json
{
  "data": [
    {
      "id": 1,
      "merchantId": 20,
      "promoId": 4,
      "createdAt": "2025-06-13T02:22:00.655532"
    },
    {
      "id": 3,
      "merchantId": 368,
      "promoId": 4,
      "createdAt": "2025-06-13T00:00:00"
    }
  ]
}
```

| Field             | Type   | Description          |
|-------------------|--------|----------------------|
| data[].id         | number | id of merchant promo |
| data[].merchantId | number | id of bind merchant  |
| data[].promoId    | number | id of bind promo     |
| data[].createAt   | text   | create date time     |

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
The token should be included in the `Authorization` header for all merchant API calls:

```
Authorization: Bearer <token>
```

