# Frontend for consuming VLSM API

don't forget customizing ``application.properties`` with the url of VLSM-API

## Local
### Compilation

```
./mvnw clean install
```

### Launch
```
java -jar target/vlsm*.jar
```

## Using Docker

### Compilation

```
docker build -t begr/vlsm-front .
```

### Launch

```
docker run -d -p 8090:8090 begr/vlsm-front 
```
