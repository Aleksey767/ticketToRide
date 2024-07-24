# Ticket to ride

## Interface

https://ibb.co/album/ZzpCGj

## Stack

- Java 21
- Spring Boot
- Spring Data
- Spring Security
- JUnit 5
- Mockito
- PostgreSQL
- Maven
- Thymeleaf
- Bootstrap

## Features

- Creating a new user ( Users can register by creating a new account )
- Authorisation ( Login using your own data to access personalised functions )
- View Purchased Tickets ( Users can view all tickets they have previously purchased )
- Calculate the price of the most optimal travel route between two towns.
- Purchase tickets if the traveler has enough money.
- Layered architecture with separation of concerns.
- Persistence of successfully bought tickets.

## API 

- **URL**: `/api/user/save_user`
- **Method**: `POST`
- **Content-Type**: `application/json`
- **Response**:

```json
{
  "username": "boris23",
  "password": "$2a$10$f6Uwmnjr/GXHX69191",
  "realName": "Boris",
  "balance": 100,
  "role": "USER"
}
```
- **URL**: `/api/ticket/save_ticket`
- **Method**: `POST`
- **Content-Type**: `application/json`
- **Response**:

```json
{
  "id": 1,
  "departure": "LONDON",
  "arrival": "SWINDEN",
  "price": 17,
  "currency": "GBT",
  "travellerAmount": 1,
  "user": {
    "id": 9,
    "username": "boris23",
    "realName": "Boris"
  }
}

```

- **URL**: `/api/ticket/calculate_price`
- **Method**: `POST`
- **Content-Type**: `application/json`
- **Response**:

```json
{
  "departure": "LONDON",
  "arrival": "SWINDEN",
  "price": 17
}


