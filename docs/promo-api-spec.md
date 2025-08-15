# Promo API Specification

This document outlines the API endpoints for promo management in the Cashlez Promo Engine
application.

## Base URL

```
/promo
```

## Endpoints

### 1. Create Promo

Create a new promo in the system.

**Endpoint:** `POST /promo/create`

#### Request

```json
{
  "name": "string",
  "promoType": "PERCENTAGE | FIXED_AMOUNT",
  "promoValue": 0,
  "minTransaction": 0,
  "maxTransaction": 0,
  "maxSubsidy": 0,
  "channelType": "QRIS | EDCREADER | PAYMENT_LINK",
  "partnerId": 0,
  "validFrom": "yyyy-MM-dd",
  "validTo": "yyyy-MM-dd"
}
```

| Field          | Type          | Required | Description                                                  |
|----------------|---------------|----------|--------------------------------------------------------------|
| name           | string        | Yes      | Name of the promo                                            |
| promoType      | enum (string) | Yes      | Type of the promo (PERCENTAGE, FIXED_AMOUNT)                 |
| promoValue     | number        | Yes      | Value of the promo (e.g., 10 for 10% or 10000 for Rp 10.000) |
| minTransaction | number        | Yes      | Minimum transaction amount to be eligible for the promo      |
| maxTransaction | number        | Yes      | Maximum transaction amount to be eligible for the promo      |
| maxSubsidy     | number        | Yes      | Maximum subsidy amount for the promo                         |
| channelType    | enum (string) | Yes      | Channel type for the promo (QRIS, EDCREADER, PAYMENT_LINK)   |
| partnerId      | number        | No       | ID of the partner associated with the promo (if applicable)  |
| validFrom      | string        | Yes      | Start date of the promo validity period (format: yyyy-MM-dd) |
| validTo        | string        | Yes      | End date of the promo validity period (format: yyyy-MM-dd)   |

#### Success Response

**Status Code:** `200 OK`

```json
{
  "message": "Promo created successfully",
  "data": {
    "promoId": 1
  }
}
```

| Field        | Type   | Description                     |
|--------------|--------|---------------------------------|
| message      | string | Success message                 |
| data.promoId | number | Unique identifier for the promo |

#### Error Response

**Status Code:** `400 Bad Request`

```json
{
  "error": "ERROR_CODE",
  "message": "Error message details"
}
```

Common error messages:

- "Partner with id {partnerId} not found"

**Status Code:** `500 Internal Server Error`

```json
{
  "error": "INTERNAL_SERVER_ERROR",
  "message": "An unexpected error occurred"
}
```

---

### 2. List All Promos

List all promos in the system.

**Endpoint:** `GET /promo/list`

#### Success Response

**Status Code:** `200 OK`

```json
{
  "data": [
    {
      "id": 0,
      "name": "string",
      "promoType": "PERCENTAGE",
      "promoValue": 0,
      "minTransaction": 0,
      "maxTransaction": 0,
      "maxSubsidy": 0,
      "channelType": "QRIS",
      "partnerId": 0,
      "isActive": true,
      "validFrom": "yyyy-MM-dd",
      "validTo": "yyyy-MM-dd"
    }
  ]
}
```

| Field                 | Type    | Description                                                  |
|-----------------------|---------|--------------------------------------------------------------|
| data[].id             | number  | Unique identifier for the promo                              |
| data[].name           | string  | Name of the promo                                            |
| data[].promoType      | enum    | Type of the promo (PERCENTAGE, FIXED_AMOUNT)                 |
| data[].promoValue     | number  | Value of the promo (e.g., 10 for 10% or 10000 for Rp 10.000) |
| data[].minTransaction | number  | Minimum transaction amount to be eligible for the promo      |
| data[].maxTransaction | number  | Maximum transaction amount to be eligible for the promo      |
| data[].maxSubsidy     | number  | Maximum subsidy amount for the promo                         |
| data[].channelType    | enum    | Channel type for the promo (QRIS, EDCREADER, PAYMENT_LINK)   |
| data[].partnerId      | number  | ID of the partner associated with the promo (nullable)       |
| data[].isActive       | boolean | Whether the promo is active                                  |
| data[].validFrom      | string  | Start date of the promo validity period (format: yyyy-MM-dd) |
| data[].validTo        | string  | End date of the promo validity period (format: yyyy-MM-dd)   |

#### Error Response

**Status Code:** `500 Internal Server Error`

```json
{
  "error": "INTERNAL_SERVER_ERROR",
  "message": "An unexpected error occurred"
}
```

---

### 3. Update Promo

Update an existing promo.

**Endpoint:** `PUT /promo/update/{promoId}`

#### Path Parameters

| Parameter | Type   | Description               |
|-----------|--------|---------------------------|
| promoId   | string | ID of the promo to update |

#### Request

```json
{
  "promoValue": 0,
  "maxSubsidy": 0,
  "validTo": "yyyy-MM-dd"
}
```

| Field      | Type   | Required | Description                                                      |
|------------|--------|----------|------------------------------------------------------------------|
| promoValue | number | No       | New value of the promo (e.g., 10 for 10% or 10000 for Rp 10.000) |
| maxSubsidy | number | No       | New maximum subsidy amount for the promo                         |
| validTo    | string | No       | New end date of the promo validity period (format: yyyy-MM-dd)   |

#### Success Response

**Status Code:** `200 OK`

```json
{
  "message": "Promo updated successfully",
  "data": {
    "updatedFields": [
      "promo_value",
      "max_subsidy",
      "valid_to"
    ]
  }
}
```

| Field                | Type         | Description                                    |
|----------------------|--------------|------------------------------------------------|
| message              | string       | Success message                                |
| data.updatedFields[] | list<string> | List of fields that were updated for the promo |

#### Error Response

**Status Code:** `400 Bad Request`

```json
{
  "error": "ERROR_CODE",
  "message": "Error message details"
}
```

Common error messages:

- "Promo with id {promoId} not found"
- "No fields were updated for promo with id {promoId}"

**Status Code:** `500 Internal Server Error`

```json
{
  "error": "INTERNAL_SERVER_ERROR",
  "message": "An unexpected error occurred"
}
```

---

### 4. Activate/Deactivate Promo

Activate or deactivate an existing promo.

**Endpoint:** `POST /promo/activate/{promoId}`

#### Path Parameters

| Parameter | Type   | Description                            |
|-----------|--------|----------------------------------------|
| promoId   | string | ID of the promo to activate/deactivate |

#### Request

```json
{
  "isActive": true
}
```

| Field    | Type    | Required | Description                                      |
|----------|---------|----------|--------------------------------------------------|
| isActive | boolean | Yes      | Set to `true` to activate, `false` to deactivate |

#### Success Response

**Status Code:** `200 OK`

```json
{
  "message": "Promo activated successfully",
  "data": {
    "promoId": 0,
    "newStatus": "active | inactive"
  }
}
```

| Field          | Type   | Description                                      |
|----------------|--------|--------------------------------------------------|
| message        | string | Success message                                  |
| data.promoId   | number | ID of the promo that was activated/deactivated   |
| data.newStatus | string | New status of the promo ("active" or "inactive") |

#### Error Response

**Status Code:** `400 Bad Request`

```json
{
  "error": "ERROR_CODE",
  "message": "Error message details"
}
```

Common error messages:

- "Promo ID is required for activation"
- "Promo with id {promoId} not found"
- "isActive field is required for activation"

**Status Code:** `500 Internal Server Error`

```json
{
  "error": "INTERNAL_SERVER_ERROR",
  "message": "An unexpected error occurred"
}
```

---

### 5. Evaluate Promo

Create a new promo in the system.

**Endpoint:** `POST /promo/evaluate`

#### Request

```json
{
  "cardNumber": "124356*****2434",
  "cardHoldername": "RICHRD",
  "cardExpiredDate": "0907",
  "amount": 20000,
  "mid": "000070428097582",
  "tid": "74223162",
  "transactionDate": "2025-06-11T06:33:03.650Z"
}
```

| Field           | Type     | Required | Description                   |
|-----------------|----------|----------|-------------------------------|
| cardNumber      | string   | Yes      | Card number                   |
| cardHoldername  | string   | Yes      | Card holder name              |
| cardExpiredDate | string   | Yes      | 4 digits of card expired date |
| amount          | number   | Yes      | Amount of transaction         |
| mid             | string   | Yes      | MID                           |
| tid             | string   | Yes      | TID                           |
| transactionDate | datetime | Yes      | Transaction date              |

#### Success Response

**Status Code:** `200 OK`

```json
{
  "data": {
    "isEligible": true,
    "subsidizedAmount": 1980000,
    "purchaseAmount": 20000,
    "finalAmount": 2000000,
    "promoTransactionId": "9527d180-f279-4484-902b-87a7e76a88c8",
    "reason": "Promo eligible for this transaction"
  }
}
```

| Field                   | Type    | Description                                     |
|-------------------------|---------|-------------------------------------------------|
| data.isEligible         | boolean | Promo eligible status                           |
| data.subsidizedAmount   | number  | Subsidized amount of payment                    |
| data.purchaseAmount     | number  | Original amount of payment                      |
| data.finalAmount        | number  | Final amount of payment after promo calculation |
| data.promoTransactionId | number  | Generated transaction id                        |
| data.reason             | string  | Evaluate reason                                 |

#### Error Response

**Status Code:** `400 Bad Request`

```json
{
  "error": "ERROR_CODE",
  "message": "Error message details"
}
```

Common error messages:

- "Card number, card holder name, and card expiry date are required for evaluation"
- "Merchant MID with mid {mid} not found"
- "User detail with TID {tid} and MID ID {mid} not found"
- "No active promos available for evaluation"
- "Promo is ineligible for the provided card details");"

**Status Code:** `500 Internal Server Error`

```json
{
  "error": "INTERNAL_SERVER_ERROR",
  "message": "An unexpected error occurred"
}
```

---

### 6. Callback Promo Usage

Callback to record promo usage.

**Endpoint:** `POST /promo/callback-promo-usage`

#### Request

```json
{
  "promoId": "string",
  "promoTransactionId": "string",
  "cardMasked": "string",
  "nameMasked": "string",
  "cardExpiry": "string",
  "txid": "string",
  "merchantId": "string",
  "mid": "string",
  "tid": "string",
  "amount": 0,
  "subsidyApplied": 0,
  "statusTransaction": "SUCCESS | REVERSAL",
  "channel": "QRIS | CARD |ALL",
  "usedAt": "yyyy-MM-ddTHH:mm:ss.SSSZ"
}
```

| Field              | Type          | Required | Description                                    |
|--------------------|---------------|----------|------------------------------------------------|
| promoId            | string        | Yes      | ID of the promo                                |
| promoTransactionId | string        | Yes      | Transaction ID associated with the promo usage |
| cardMasked         | string        | Yes      | Masked card number                             |
| nameMasked         | string        | Yes      | Masked cardholder name                         |
| cardExpiry         | string        | Yes      | Card expiry date                               |
| txid               | string        | Yes      | Transaction ID from the payment gateway        |
| merchantId         | string        | Yes      | Merchant ID                                    |
| mid                | string        | Yes      | Merchant ID (MID)                              |
| tid                | string        | Yes      | Terminal ID (TID)                              |
| amount             | number        | Yes      | Transaction amount                             |
| subsidyApplied     | number        | Yes      | Subsidy amount applied                         |
| statusTransaction  | enum (string) | Yes      | Status of the transaction (SUCCESS, REVERSAL)  |
| channel            | enum (string) | Yes      | Channel type (QRIS, CARD)                      |
| usedAt             | datetime      | Yes      | Timestamp of when the promo was used           |

#### Success Response

**Status Code:** `200 OK`

```json
{
  "data": "Promo usage callback processed successfully"
}
```

| Field | Type   | Description     |
|-------|--------|-----------------|
| data  | string | Success message |

#### Error Response

**Status Code:** `500 Internal Server Error`

```json
{
  "error": "INTERNAL_SERVER_ERROR",
  "message": "An unexpected error occurred"
}
```

---

### 7. Get Promo Usage History

Retrieve the promo usage history, filterable by card number, promo ID, and merchant ID.

**Endpoint:** `GET /promo/usage-history`

#### Query Parameters

| Parameter  | Type   | Required | Description           |
|------------|--------|----------|-----------------------|
| cardNumber | string | No       | Filter by card number |
| promoId    | long   | No       | Filter by promo ID    |
| merchantId | string | No       | Filter by merchant ID |

#### Success Response

**Status Code:** `200 OK`

```json
{
  "data": [
    {
      "cardNumber": "string",
      "promoId": "string",
      "merchantId": "string",
      "amount": "string",
      "subsidyApplied": 0,
      "usedAt": "string"
    }
  ]
}
```

| Field                 | Type   | Description                                 |
|-----------------------|--------|---------------------------------------------|
| data[].cardNumber     | string | Masked card number used for the transaction |
| data[].promoId        | string | ID of the promo applied                     |
| data[].merchantId     | string | ID of the merchant where the promo was used |
| data[].amount         | string | Original transaction amount                 |
| data[].subsidyApplied | long   | Subsidy amount applied from the promo       |
| data[].usedAt         | string | Timestamp of when the promo was used        |

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
The token should be included in the `Authorization` header for all promo API calls:

```
Authorization: Bearer <token>
```

