# BUILD
FROM maven:latest AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
COPY lombok.config /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

# CREATE CONTAINER
FROM openjdk:11-oracle
COPY --from=build /usr/src/app/target/vlsm*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8090