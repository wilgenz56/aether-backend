# Fase 1: Build (usiamo Maven con Eclipse Temurin 21)
FROM maven:3.9.6-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -DskipTests

# Fase 2: Runtime (usiamo l'immagine ufficiale e stabile di Eclipse Temurin 21)
FROM eclipse-temurin:21-jre-jammy
COPY --from=build /target/*.jar app.jar

# Espone la porta
EXPOSE 8080

# Avvia l'app passando la porta corretta
ENTRYPOINT ["java", "-Dserver.port=${PORT:8080}", "-jar", "app.jar"]
