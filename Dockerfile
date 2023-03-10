FROM gradle:8.0.2-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle build -x test --stacktrace

FROM openjdk AS deploy
EXPOSE 8080
WORKDIR /app
COPY --from=build /app/build/libs/backend-dashboard.jar .
CMD java -jar backend-dashboard.jar
