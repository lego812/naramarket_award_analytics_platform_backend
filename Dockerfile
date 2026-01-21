FROM gradle:8.5-jdk17 AS build
WORKDIR /app
COPY gradlew gradlew.bat gradle/ ./
RUN chmod +x gradlew
COPY build.gradle settings.gradle ./
COPY src ./src
RUN ./gradlew bootJar -x test

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
