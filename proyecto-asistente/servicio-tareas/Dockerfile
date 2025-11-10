FROM eclipse-temurin:17-jdk-jammy
ARG JAR_FILE=target/servicio-tareas-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
# Expone el puerto del servicio
EXPOSE 8081
# Le decimos a Spring que use el perfil "docker"
ENTRYPOINT ["java","-jar","/app.jar", "--spring.profiles.active=docker"]