FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# 1️⃣ gradle wrapper & 설정 (캐시 핵심)
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

RUN chmod +x gradlew
RUN ./gradlew --no-daemon build -x test || true

# 2️⃣ 소스 복사
COPY src src

# 3️⃣ 실제 빌드
RUN ./gradlew bootJar -x test --no-daemon
