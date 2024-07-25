# Ticket to ride

### Short description

_This project implements a shortest path finder between two railway stations using Dijkstra's algorithm. The time complexity of the algorithm is O(n^2), where n is the number of stations. The railway stations are represented in a 2D array, which is used to construct a graph for the algorithm.  The system efficiently computes the minimum travel distance between any two stations on the network, providing a practical tool for route optimization in railway systems._

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
- JavaScript

## Features

- Creating a new user ( Users can register by creating a new account )
- Authorisation ( Login using your own data to access personalised functions )
- View Purchased Tickets ( Users can view all tickets they have previously purchased )
- Calculate the price of the most optimal travel route between two towns.
- Purchase tickets if the traveler has enough money.
- Layered architecture with separation of concerns.
- Persistence of successfully bought tickets.

## API 

- URL: /api/user/save_user
- Method: POST
- Content-Type: application/json
- Type: public
- Response:

```json

{
  "id": 0,
  "username": "boris23",
  "password": "$2a$10$LvXXRAU8cQLjjSsj2TpWaud.k8G21KIoaecYxVdVWtZFf4vqxgCtW",
  "realName": "Boris",
  "balance": 60,
  "role": "USER"
}
```
- URL: /api/ticket/save_ticket
- Method: POST
- Content-Type: application/json
- Type: private
- Response:

```json

{
  "id": 0,
  "departure": "BRISTOL",
  "arrival": "BIRMINGHAM",
  "price": 10,
  "currency": "GBP",
  "travellerAmount": 1,
  "user": {
    "id": 30,
    "username": "boris23",
    "password": "$2a$10$LvXXRAU8cQLjjSsj2TpWaud.k8G21KIoaecYxVdVWtZFf4vqxgCtW",
    "role": "USER",
    "balance": 50,
    "realName": "Boris"
  }
}

```

- URL: /api/ticket/calculate_price
- Method: POST
- Content-Type: application/json
- Type: public
- Response:

```json
{
  "departure": "BRISTOL",
  "arrival": "BIRMINGHAM",
  "travellerAmount": 1,
  "price": 10
}
