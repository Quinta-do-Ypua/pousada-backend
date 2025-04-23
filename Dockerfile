FROM maven:3.9.5-amazoncorretto-21 AS build

RUN mkdir /app

COPY . /app

WORKDIR /app

RUN mvn clean package

FROM amazoncorretto:21

RUN mkdir /app

COPY --from=build /app/target/pousada-backend-0.0.1-SNAPSHOT.jar /app/pousada-backend.jar

WORKDIR /app

CMD java $JAVA_OPTS -jar ifin.jar