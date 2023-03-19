FROM openjdk:17-alpine3.14

VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} print-ms-0.1.0.jar

ENTRYPOINT ["java", "-jar", "/print-ms-0.1.0.jar"]