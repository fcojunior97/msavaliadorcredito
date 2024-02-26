FROM maven:3.9.6-eclipse-temurin-17 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

ARG RABBITMQ_SERVER=rabbitmq
ARG EUREKA_SERVER=fco-eureka-server-latest

FROM eclipse-temurin:17
WORKDIR /app
COPY --from=build ./app/target/*.jar ./msavaliadorcredito.jar
ENTRYPOINT java -jar msavaliadorcredito.jar