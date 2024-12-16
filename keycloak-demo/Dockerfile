FROM maven:3.9.9-eclipse-temurin-23 AS builder

WORKDIR /kc-demo-backend
COPY . .
RUN mvn clean package -DskipTests=true

FROM eclipse-temurin:23-jre

WORKDIR /kc-demo-backend
COPY --from=builder /kc-demo-backend/target/*.jar kc-demo-backend.jar

CMD ["java", "-jar", "kc-demo-backend.jar"]
