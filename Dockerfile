FROM maven:3.9.6-amazoncorretto-17 as builder
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

FROM amazoncorretto:17.0.13
WORKDIR /app
COPY --from=builder /app/target/auth-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]