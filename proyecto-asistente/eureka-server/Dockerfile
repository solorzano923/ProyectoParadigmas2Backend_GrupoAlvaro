# Usa una imagen base de Java 17
FROM eclipse-temurin:17-jdk-jammy
# El argumento JAR_FILE apuntará al .jar construido por Maven
ARG JAR_FILE=target/eureka-server-0.0.1-SNAPSHOT.jar
# Copia el .jar a la imagen
COPY ${JAR_FILE} app.jar
# Expone el puerto que definimos en application.properties
EXPOSE 8761
# Comando para ejecutar la aplicación
ENTRYPOINT ["java","-jar","/app.jar"]