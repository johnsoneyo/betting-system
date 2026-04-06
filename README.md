---

# Sports Betting Event Settlement System

This project simulates a **sports betting event outcome handling and bet settlement system** using **Kafka** and **RocketMQ**. It is implemented as a **modular monolith** with three microservice modules.

---

## Table of Contents

1. [Project Modules](#project-modules)
2. [Project Structure](#project-structure)
3. [Prerequisites](#prerequisites)
4. [Running the System](#running-the-system)

    1. [Step 1: Start Kafka](#step-1-start-kafka)
    2. [Step 2: Start RocketMQ](#step-2-start-rocketmq)
    3. [Step 3: Run the Modules](#step-3-run-the-modules)
5. [Event Outcomes Processor and H2 Database](#event-outcomes-processor-and-h2-database)
6. [Kafka High Availability](#kafka-high-availability)
7. [API Usage](#api-usage)

    1. [Endpoint](#endpoint)
    2. [Example Payload](#example-payload)
8. [Testing the Flow](#testing-the-flow)
9. [Testing EventOutcome API (1X2 Bets)](#testing-eventoutcome-api-1x2-bets)

    1. [Pre-seeded Outcomes for Event 1001 (Reference Only)](#pre-seeded-outcomes-for-event-1001-reference-only)
    2. [API Examples](#api-examples)
    3. [How to Test](#how-to-test)
10. [Logs and Verification](#logs-and-verification)
11. [Docker Cleanup](#docker-cleanup)
12. [Notes](#notes)

---

## Project Modules

1. **event-outcome-api** – API for publishing sports event outcomes to Kafka.
2. **event-outcomes-processor** – Kafka consumer that listens to event outcomes, stores data in H2, matches bets, and produces settlement messages to RocketMQ.
3. **event-bet-settlement** – RocketMQ consumer that receives bet settlements and processes them.

---

## Project Structure

```text
.
├── api
│   └── event-outcome-api
├── messaging
│   ├── event-outcomes-processor
│   └── event-bet-settlement
├── libs
│   ├── domain
│   │   └── common-domain       # Shared domain entities (Event, Bet, Odds, etc.)
│   └── dto
│       └── common-dto          # Shared Data Transfer Objects for API and messaging
├── docker
│   ├── docker-compose-kafka.yml
│   └── docker-compose-rocketmq.yml
└── README.md
```

**Notes:**

* `libs/domain/common-domain`: Contains shared entities like Event, Market, Outcome, Bet slip / Bet, and Odds.
* `libs/dto/common-dto`: Contains DTOs for API requests/responses and Kafka/RocketMQ messages.
* This structure promotes **reusability and separation of concerns**.

---

## Prerequisites

* Java 17+
* IntelliJ IDEA or any Java IDE
* Docker & Docker Compose
* Optional: Postman or cURL for API testing

---

## Running the System

### Step 1: Start Kafka

```bash
cd docker
docker-compose -f docker-compose-kafka.yml up -d
```

* Starts **Kafka brokers in KRaft mode**.
* Kafka topic: `event-outcomes`.

---

### Step 2: Start RocketMQ

RocketMQ must start **in order**:

1. **Name Server**:

```bash
docker-compose -f docker-compose-rocketmq.yml up namesrv
```

2. **Broker**:

```bash
docker-compose -f docker-compose-rocketmq.yml up broker
```
If you shuold see the below broker logs, then you are ready to start the next step.:
```
The broker[broker-a, 127.0.0.1:10911] boot success. serializeType=JSON and name server is namesrv:9876
```


---

### Step 3: Run the Modules

Run each module in IntelliJ:

1. `event-outcome-api` → Port: **8084**
2. `event-outcomes-processor` → Port: **8081**
3. `event-bet-settlement` → Port: as configured

---

## Event Outcomes Processor and H2 Database

* `event-outcomes-processor` uses an **H2 in-memory database** to store:

    * **Odds** – available betting odds for each market
    * **Bet slips / bets** – dummy bets for settlement
    * **Event outcomes** – published by the API
    * **Other domain data** – sports, markets, etc.

* Processor matches incoming event outcomes with bets, calculates settlements, and publishes to RocketMQ.

* H2 is **in-memory**, so all data resets when the processor restarts.

> This setup simulates a realistic betting system while keeping it self-contained.

---

## Kafka High Availability

* Two brokers (`kafka-1` and `kafka-2`)
* Producers and consumers can connect to any broker
* KRaft mode handles controller and leader election
* Topics are **replicated (`replication factor = 2`)** to maintain durability

> Ensures sports event outcomes and settlements remain available and consistent.

---

## API Usage

### Endpoint

Once the application is running, you can use the Swagger UI to explore and test the API endpoints:

> [http://localhost:8084/swagger-ui/index.html#/event-outcome-controller/publishEventOutcome](http://localhost:8084/swagger-ui/index.html#/event-outcome-controller/publishEventOutcome)

### Using the Swagger Schema

1. Open the Swagger UI in your browser.
2. Find the `POST /api/v1/event-outcomes` endpoint under `Event Outcome Controller`.
3. Click **Try it out**.
4. Provide the payload according to the Swagger schema.

### Example Payload

```json
{
  "eventId": 10123,
  "sportId": 1,
  "marketId": 555,
  "outcomeId": 2,
  "outcomeName": "Team A Wins",
  "voidOutcomeId": 0,
  "voidFactor": 0.0,
  "specifiers": {
    "playerId": "P456",
    "handicap": "-1.5",
    "period": "fullTime",
    "score": "2-1"
  }
}
```

5. Click **Execute** to send the request and view the response according to the API schema.

## Testing the Flow

1. **Publish Event Outcome**: Send payload to `/api/v1/event-outcomes`.
2. **Kafka Processing**: `event-outcomes-processor` consumes the message from `event-outcomes`.
3. **Settlement**: Processor pushes settlement messages to RocketMQ (`bet-settlements`).
4. **Verification**: `event-bet-settlement` consumes settlement messages and logs results.

**Optional H2 Check**:

* URL: `http://localhost:8081/h2-console`
* JDBC URL: `jdbc:h2:mem:betting_system;DB_CLOSE_DELAY=-1`
* User: `sa`, Password: (empty)

---

## Testing EventOutcome API (1X2 Bets)

The application has **pre-seeded outcomes** for `event_id = 1001`, but **other events exist** in the system.
Testers **cannot rely on [seed.sql](messaging/event-outcomes-processor/src/main/resources/db/seed.sql)  or H2** — the API is the **single source of truth**.

> **Important:** The API automatically **upserts** — it updates an outcome if a row with the same `eventId` and `marketId` exists, or inserts a new one otherwise.

---

### Pre-seeded Outcomes for Event 1001 (Reference Only)

| Outcome Type | outcomeId | outcomeName |
| ------------ | --------- | ----------- |
| Home Win     | 1         | Home Win    |
| Draw         | 2         | Draw        |
| Away Win     | 3         | Away Win    |

> **Note:** Other events exist. Always use the API to validate outcomes.

---

### API Examples

#### Update Home Win

```bash
curl -X POST \
  'http://localhost:8084/api/v1/event-outcomes' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "eventId": 1001,
  "sportId": 1,
  "marketId": 10,
  "outcomeId": 1,
  "outcomeName": "Home Team Victory",
  "status": 1
}'
```

#### Switch to Draw

```bash
curl -X POST \
  'http://localhost:8084/api/v1/event-outcomes' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "eventId": 1001,
  "sportId": 1,
  "marketId": 10,
  "outcomeId": 2,
  "outcomeName": "Match Draw",
  "status": 1
}'
```

#### Switch to Away Win

```bash
curl -X POST \
  'http://localhost:8084/api/v1/event-outcomes' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "eventId": 1001,
  "sportId": 1,
  "marketId": 10,
  "outcomeId": 3,
  "outcomeName": "Away Team Victory",
  "status": 1
}'
```

---

### How to Test

1. Pick an `outcomeId`: Home (1), Draw (2), Away (3).
2. Post JSON to `/api/v1/event-outcomes`.
3. API **updates** if the outcome exists, or **inserts** if it does not.
4. Repeat to simulate switching between Home, Draw, and Away.
5. Remember: **other events exist** — testing must use the API.

---

## Logs and Verification

* **Processor logs**: Show matched bets and settlement messages.
* **RocketMQ consumer logs**: Confirm settlements received.
* **H2 console**: Optional verification of bet status.


## Notes

* Modular monolith design allows running all modules in a single JVM or separately.
* Kafka and RocketMQ topics must exist before publishing messages.
* H2 in-memory database resets on restart; persistent storage can be added.
* **Shared libs for domain and DTOs** promote reusability and separation of concerns.
* The system is easily extendable for more sports, bet types.
---

