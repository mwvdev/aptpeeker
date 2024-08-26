FROM eclipse-temurin:21-jre

RUN groupadd --system --gid 1001 aptpeeker
RUN useradd --system --uid 1001 --gid 1001 aptpeeker
USER aptpeeker

VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]