FROM maven:3.9.8-eclipse-temurin-21-alpine AS builder

WORKDIR /app

COPY pom.xml /app/
COPY src /app/src/

RUN mvn clean package -Dmaven.test.skip=true

FROM tomcat:10-jdk21-openjdk-slim

RUN rm -rf /usr/local/tomcat/webapps/*

COPY --from=builder /app/target/ticketToRide-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]