FROM gradle:8.5-jdk17 AS build
WORKDIR /app

# 1 Gradle 설정 먼저 (캐시 최적화)
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# 2. 실행 권한
RUN chmod +x gradlew

# 3. 의존성 캐시
RUN ./gradlew dependencies --no-daemon

# 4. 소스 복사
COPY src src

# 5. 빌드
RUN ./gradlew bootJar -x test --no-daemon
