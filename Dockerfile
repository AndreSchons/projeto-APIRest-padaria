# Stage 1: Build com Maven
FROM maven:3.9.3-eclipse-temurin-17 AS build

WORKDIR /app

# Copia o pom.xml correto
COPY primeiro-exemplo/pom.xml .

# Baixa dependências offline
RUN mvn dependency:go-offline

# Copia o código fonte
COPY primeiro-exemplo/src ./src

# Compila o projeto e gera o JAR
RUN mvn clean package -DskipTests

# Stage 2: Imagem final leve
FROM openjdk:17-jdk-slim

WORKDIR /app
EXPOSE 8083

# Copia o jar do stage de build
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
