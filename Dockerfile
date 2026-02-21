# Fase 1: Build (usiamo Maven e Java 17)
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Fase 2: Runtime (usiamo un'immagine leggera solo per far girare il file .jar)
FROM openjdk:17-jdk-slim
COPY --from=build /target/*.jar app.jar

# Espone la porta (Render userà la variabile d'ambiente PORT)
EXPOSE 8080

# Avvia l'app passando la porta corretta a Spring Boot
ENTRYPOINT ["java", "-Dserver.port=${PORT:8080}", "-jar", "app.jar"]
