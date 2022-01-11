# Frontend for consuming VLSM API

don't forget customizing ``application.properties`` with the url of VLSM-API

## Compilation

```
./mvnw clean install
```

## Using Docker

```
docker build -t begr/vlsm-front .
docker run -d -p 8090:8090 begr/vlsm-front 
```
