FROM eclipse-temurin:21-jre

RUN addgroup --system --gid 1000 aptpeeker && \
    adduser --system --uid 1000 --gid 1000 aptpeeker
USER aptpeeker

VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]