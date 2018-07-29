FROM openjdk:8-jre-alpine

RUN addgroup -g 1000 -S aptpeeker && \
    adduser -u 1000 -S aptpeeker -G aptpeeker
USER aptpeeker

VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]