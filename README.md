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

Simply start the system with this simple command docker-compose up! (Or not)
Sadly keycloak has a problem with the iss token host url 
(https://stackoverflow.com/questions/50670734/keycloak-in-docker-compose-network)

to solve the issue we will need to run

### Linux/Mac
```bash
sudo bash -c 'echo "127.0.0.1 keycloak" >> /etc/hosts'
```

### Windows
```powershell
Add-Content -Path "C:\Windows\System32\drivers\etc\hosts" -Value "127.0.0.1 keycloak"
```

### Ready to spin up the system? run:

```bash
docker-compose up
```

## Keycloak Configuration

#### http://keycloak:8080/admin/master/console/

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

### OPEN-API

- **Trailblazer-producer:** http://localhost:8081/swagger-ui/index.html#/
- **Trailblazer-consumer:** http://localhost:8082/swagger-ui/index.html#/


The easiest way to test the API is via Postman using the Authorization Code with PKCE grant type.

### Keycloak Information

- **Client ID:** trailblazer-app
- **Client Secret:** tJc5LJv1Rngd5IFVtqASGGmrPgavAJyc
- **Token Endpoint:** http://keycloak:8080/realms/trailblazer/protocol/openid-connect/token

### Get Access Token via cURL

To get an access token for the admin user, use the following cURL command:

```bash
curl -d 'username=admin' \
     -d 'password=12345' \
     -d "client_id=trailblazer-app" \
     -d "client_secret=tJc5LJv1Rngd5IFVtqASGGmrPgavAJyc" \
     -d "grant_type=password" \
     "http://keycloak:8080/realms/trailblazer/protocol/openid-connect/token"
```

To get an access token for the user Ivan, use the following cURL command:

```bash
curl -d 'username=ivan' \
     -d 'password=123' \
     -d "client_id=trailblazer-app" \
     -d "client_secret=tJc5LJv1Rngd5IFVtqASGGmrPgavAJyc" \
     -d "grant_type=password" \
     "http://keycloak:8080/realms/trailblazer/protocol/openid-connect/token"
```


