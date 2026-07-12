FROM eclipse-temurin:25-jre-alpine
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Xms300m", "-Xmx500m", "-jar", "/app.jar"]
