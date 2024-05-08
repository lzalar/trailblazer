# Trailblazer Service

This repository contains the Trailblazer service, consisting of two main components: `trailblazer_producer` and `trailblazer_consumer`.

## Running Tests

To run the tests for the `trailblazer_producer` and `trailblazer_consumer`, execute the following commands:

```bash
./gradlew :raceproducer:clean :raceproducer:test
./gradlew :raceconsumer:clean :raceconsumer:test
```

## Building Docker Images

To build the Docker images of the service, use the following commands:

```bash
./gradlew :raceproducer:bootBuildImage
./gradlew :raceconsumer:bootBuildImage
```

## Running the docker-compose

the services are called trailbazer-producer and traiblazer-consumer
and are started with all their dependencies by running the docker-compose command

```bash
docker-compose up
```

## Keycloak Configuration

There are two users configured in Keycloak:

1. **Admin User**
    - Username: admin
    - Password: 12345
    - Roles: applicant, administrator

2. **Ivan User**
    - Username: ivan
    - Password: 123
    - Roles: applicant

## Testing the API

The easiest way to test the API is via Postman using the Authorization Code with PKCE grant type.

### Keycloak Information

- **Client ID:** trailblazer-app
- **Client Secret:** tJc5LJv1Rngd5IFVtqASGGmrPgavAJyc
- **Token Endpoint:** http://localhost:8090/realms/trailblazer/protocol/openid-connect/token

### Get Access Token via cURL

To get an access token for the admin user, use the following cURL command:

```bash
curl -d 'username=admin' \
     -d 'password=12345' \
     -d "client_id=trailblazer-app" \
     -d "client_secret=tJc5LJv1Rngd5IFVtqASGGmrPgavAJyc" \
     -d "grant_type=password" \
     "http://localhost:8090/realms/trailblazer/protocol/openid-connect/token"
```

To get an access token for the user Ivan, use the following cURL command:

```bash
curl -d 'username=ivan' \
     -d 'password=123' \
     -d "client_id=trailblazer-app" \
     -d "client_secret=tJc5LJv1Rngd5IFVtqASGGmrPgavAJyc" \
     -d "grant_type=password" \
     "http://localhost:8090/realms/trailblazer/protocol/openid-connect/token"
```
