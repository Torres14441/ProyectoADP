FROM amazoncorretto:21-alpine-jdk

COPY target/Proyecto_Comerciales_Melly-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]