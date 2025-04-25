FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy Maven wrapper and everything needed to build
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src ./src

# Make the wrapper executable and build the JAR
RUN mvn clean package -DskipTests
COPY target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
