FROM amazoncorretto:21

RUN mkdir /app

COPY target/PousadaBackend-0.0.1-SNAPSHOT.jar /app/pousada-backend.jar

WORKDIR /app

CMD exec java $JAVA_OPTS -jar pousada-backend.jar
