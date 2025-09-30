# Stage 1: Build do projeto com Maven
FROM maven:3.9.3-eclipse-temurin-17 AS build

WORKDIR /app

# Copia apenas o pom.xml primeiro para aproveitar cache do Docker
COPY pom.xml .

# Baixa dependências do Maven
RUN mvn dependency:go-offline

# Copia o código fonte
COPY src ./src

# Compila o projeto e gera o JAR
RUN mvn clean package -DskipTests

# Stage 2: Imagem final mais leve
FROM openjdk:17-jdk-slim

WORKDIR /app

# Porta que a aplicação vai usar no Render
EXPOSE 8083

# Copia o jar do stage de build
COPY --from=build /app/target/*.jar app.jar

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
