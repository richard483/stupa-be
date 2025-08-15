# Midware Merchant API Specification

This document outlines the API endpoints for searching midware merchants in the Cashlez Promo Engine
application.

## Base URL

```
/midware-merchant
```

## Endpoints

### 1. Search Merchants by Name

Retrieves a list of merchants whose names contain the provided keyword.

**Endpoint:** `GET /midware-merchant/search`

#### Query Parameters

| Parameter | Type   | Required | Description                                  |
|-----------|--------|----------|----------------------------------------------|
| keyword   | string | Yes      | The keyword to search for in merchant names. |

#### Success Response

**Status Code:** `200 OK`

The response is wrapped in a `BaseResponse` object. For a successful operation, `error` and
`message` fields in `BaseResponse` will be `null`.

```json
{
  "data": [
    {
      "id": 2,
      "name": "kklghnvb",
      "merchantReference": "sdgerr",
      "merchantUniqueCode": "adssd"
    }
  ],
  "error": null,
  "message": null
}
```

| Field                     | Type                  | Description                                                                                                                   |
|---------------------------|-----------------------|-------------------------------------------------------------------------------------------------------------------------------|
| data                      | list<MidwareMerchant> | List of `MidwareMerchant` objects matching the search keyword. May be empty if no matches are found.                          |
| data[].id                 | string                | Id of merchant                                                                                                                |
| data[].name               | string                | Example: Name of the merchant. (Property names are illustrative and depend on the actual `MidwareMerchant` model)             |
| data[].merchantReference  | string                | Example: Physical address of the merchant. (Property names are illustrative and depend on the actual `MidwareMerchant` model) |
| data[].merchantUniqueCode | string                | Merchant unique code                                                                                                          |

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

