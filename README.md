# notification-service

Basic Spring Boot service for storing and sending coach-scoped notifications through placeholder Email and SMS providers.

## What is included

- JWT-protected notification APIs
- JPA entity, repository, service, controller, DTOs
- Flyway migration for the `notifications` table
- Mock `EmailSender` and `SmsSender` implementations
- Validation and a global exception handler

## Run

1. Create a PostgreSQL database named `coach_notification`.
2. Update env vars if needed:
   - `DB_URL`
   - `DB_USERNAME`
   - `DB_PASSWORD`
   - `JWT_SECRET`
3. Start the service:

```bash
./mvnw spring-boot:run
```

The app runs on `http://localhost:8087`.

## API examples

Send notification:

```bash
curl -X POST http://localhost:8087/notifications/send \
  -H "Authorization: Bearer <jwt>" \
  -H "Content-Type: application/json" \
  -d '{
    "memberId": "11111111-1111-1111-1111-111111111111",
    "channel": "EMAIL",
    "type": "WELCOME",
    "recipient": "member@example.com",
    "subject": "Welcome",
    "message": "Welcome to the program"
  }'
```

Get notification by id:

```bash
curl -H "Authorization: Bearer <jwt>" \
  http://localhost:8087/notifications/22222222-2222-2222-2222-222222222222
```

Filter notifications:

```bash
curl -H "Authorization: Bearer <jwt>" \
  "http://localhost:8087/notifications?memberId=11111111-1111-1111-1111-111111111111&channel=EMAIL&status=SENT"
```

## Placeholder providers

- Email is currently routed through [MockEmailSender](/Users/varundeoghare/Documents/Fitness-Coach-App/notification-service/src/main/java/com/coach/notification/provider/MockEmailSender.java).
- SMS is currently routed through [MockSmsSender](/Users/varundeoghare/Documents/Fitness-Coach-App/notification-service/src/main/java/com/coach/notification/provider/MockSmsSender.java).

Real SMTP and SMS gateway integrations should replace those classes later while keeping the `EmailSender` and `SmsSender` interfaces stable.
# notification-service
