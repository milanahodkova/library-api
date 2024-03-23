FROM eclipse-temurin:17-jre

ADD ./target/service-registry-*.jar /app/service-registry.jar

ENTRYPOINT ["java", "-jar", "/app/service-registry.jar"]
