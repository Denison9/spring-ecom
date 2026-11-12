# SpringEcom

Backend for a small e-commerce app, written in Java with Spring Boot and PostgreSQL.

I built this while following the Spring Boot course by Telusko. The React frontend comes from the course; I wrote the backend myself, coding along with the videos and changing things where I wanted to understand them better.

You can add, edit, delete and search products (each product has an image), place orders, and see past orders.

## Tech

- Java 21
- Spring Boot 4 (Web MVC, Data JPA)
- PostgreSQL
- Lombok
- Maven

## Project structure

```
controller/   REST endpoints
service/      business logic
repo/         Spring Data JPA repositories
model/        entities (Product, Order, OrderItem)
model/dto/    records used for order requests/responses
```

The usual controller -> service -> repository setup. For orders I used DTOs (Java records) instead of returning the entities directly. The client only sends the product id and quantity, and the server works out the price and decreases the stock.

## Endpoints

All under `http://localhost:8080/api`

| Method | Path | What it does |
|---|---|---|
| GET | /products | all products |
| GET | /product/{id} | one product |
| GET | /product/{id}/image | product image |
| GET | /products/search?keyword=... | search by name, brand, category or description |
| POST | /product | add product (multipart: `product` json + `imageFile`) |
| PUT | /product/{id} | update product |
| DELETE | /product/{id} | delete product |
| POST | /orders/place | place an order |
| GET | /orders | all orders |

Example body for placing an order:

```json
{
  "customerName": "John",
  "email": "john@example.com",
  "items": [
    { "productId": 1, "quantity": 2 }
  ]
}
```

## Running it

You need JDK 21 and PostgreSQL.

1. Create a database called `denison` (or change the URL in `application.properties`).
2. Set the `DB_PASSWORD` environment variable to your postgres password.
3. Run `./mvnw spring-boot:run` (or `mvnw.cmd spring-boot:run` on Windows).

Hibernate creates the tables on startup. If you want some sample products, run the SQL in `src/main/resources/insert-data`.

The frontend is a separate React project. It expects the API at `http://localhost:8080`.

## Things I learned

- How JPA relationships work (`@OneToMany` / `@ManyToOne`, `mappedBy`, cascade) and how they map to tables
- Lazy loading and why `@Transactional` was needed when reading orders
- Sending a file and JSON in the same request with multipart and `@RequestPart`
- Why you don't expose entities directly, and when DTOs help
- CORS, and why the frontend couldn't call the API at first

## TODO

There are some things I know aren't right yet and want to fix:

- `placeOrder` isn't transactional. If one product in the order doesn't exist, stock for the others is still reduced.
- No stock check, so stock can go negative
- Missing products return 500 (or 200 on delete) instead of 404
- Update uses the id from the body instead of the URL, and always needs an image
- No input validation
- Product list returns the image bytes too, which is heavy
- No tests yet
