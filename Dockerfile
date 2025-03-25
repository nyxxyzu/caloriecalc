FROM eclipse-temurin:21-jre-jammy
ARG JAR_FILE=target/caloriecalculator-1.0-SNAPSHOT-jar-with-dependencies.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar"]