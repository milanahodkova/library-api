FROM eclipse-temurin:17-jre

WORKDIR /app

COPY ./target/api-gateway-*.jar /app/api-gateway.jar

ENTRYPOINT ["java", "-jar", "/app/api-gateway.jar"]
