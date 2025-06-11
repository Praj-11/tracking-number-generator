# 📦 Tracking Number Generator API

A Spring Boot application to generate unique, RFC 3339-compliant parcel tracking numbers based on multiple input parameters. It supports high concurrency and is resilient, scalable, and backed by MongoDB.

---

## 🚀 Features

- Generates unique tracking numbers based on origin, destination, customer info, weight, etc.
- Ensures uniqueness using MongoDB with a unique index.
- Built-in request validation and retry mechanism (via Resilience4j).
- Exception handling and informative error responses.
- JUnit 5 tests with Mockito.
- RESTful API using Spring Boot 3 and Java 17.

---

## 📦 Tech Stack

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data MongoDB
- Resilience4j
- JUnit 5 + Mockito
- MongoDB
- Maven

---

## ⚙️ Prerequisites

- Java 17+
- Maven 3.8+
- MongoDB (local or Docker)
- (Optional) Docker for containerized MongoDB testing

---

## 🛠️ Setup Instructions

### 1. Clone the repository

```bash
git clone https://github.com/your-username/tracking-number-generator.git
cd tracking-number-generator
```

### 2. Configure MongoDB

Ensure MongoDB is running locally. You can start it with Docker:

```bash
docker run -d -p 27017:27017 --name mongo-test \
  -e MONGO_INITDB_ROOT_USERNAME=testuser \
  -e MONGO_INITDB_ROOT_PASSWORD=testpass \
  mongo
```

### 3. Configure application properties

Edit `src/main/resources/application.properties`:

```properties
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=trackingdb
spring.data.mongodb.username=testuser
spring.data.mongodb.password=testpass
spring.data.mongodb.authentication-database=admin

resilience4j.retry.instances.generateTrackingNumber.max-attempts=3
resilience4j.retry.instances.generateTrackingNumber.wait-duration=200ms
```

---

## ▶️ Running the Application

Use Maven to build and run:

```bash
mvn spring-boot:run
```

The API will be accessible at: `http://localhost:8080/next-tracking-number`

---

## 📮 API Endpoint

### `GET /next-tracking-number`

#### Query Parameters:

| Name                 | Type   | Description |
|----------------------|--------|-------------|
| `origin_country_id`      | string | ISO 3166-1 alpha-2 code of origin (e.g., "MY") |
| `destination_country_id` | string | ISO 3166-1 alpha-2 code of destination (e.g., "ID") |
| `weight`                 | double | Weight of the parcel (e.g., `1.234`) |
| `created_at`             | string | RFC 3339 timestamp (e.g., `"2025-06-08T10:00:00Z"`) |
| `customer_id`            | UUID   | Customer UUID |
| `customer_name`          | string | Name of the customer |
| `customer_slug`          | string | Customer slug (e.g., `redbox-logistics`) |

#### Example:

```http
GET http://localhost:8080/api/v1/tracking-service/next-tracking-number?origin_country_id=IN&destination_country_id=US&weight=1.234&created_at=2025-06-08T10:00:00Z&customer_id=de619854-b59b-425e-9db4-943979e1bd49&customer_name=TestCustomer&customer_slug=test-customer
```

#### Sample Response:

```json
{
  "tracking_number": "INUSTEST1234XYZ",
  "created_at": "2025-06-08T10:00:02Z"
}
```

---

## ✅ Running Tests

Use Maven to run unit tests:

```bash
mvn test
```

You can find test cases under `src/test/java/.../TrackingServiceTest.java`

---

## 🧪 Testing Tips

- Simulate failures by inserting duplicate tracking numbers into MongoDB manually and watch Resilience4j retries.
- Enable logs by adding the following to `application.properties`:

```properties
logging.level.org.springframework=INFO
logging.level.com.valuelab=DEBUG
```

---
