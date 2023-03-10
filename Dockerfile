FROM gradle:7-jdk8 AS build
WORKDIR /app
COPY . .
RUN gradle build -x test --stacktrace

FROM openjdk AS deploy
WORKDIR /app
COPY --from=build /app/build/libs/backend-dashboard.jar .
CMD java -jar backend-dashboard.jar
