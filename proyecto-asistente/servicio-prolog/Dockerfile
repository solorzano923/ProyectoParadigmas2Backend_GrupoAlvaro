# Usamos una imagen base de Ubuntu (Jammy) para poder instalar SWI-Prolog
FROM ubuntu:jammy

# Instala dependencias necesarias y SWI-Prolog
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    software-properties-common \
    gnupg \
    curl \
    && \
    # Añade el repositorio de SWI-Prolog \
    add-apt-repository -y ppa:swi-prolog/stable && \
    apt-get update && \
    # Instala SWI-Prolog y Java (OpenJDK 17)
    apt-get install -y \
    swi-prolog \
    openjdk-17-jre-headless && \
    # Limpia la caché de apt
    rm -rf /var/lib/apt/lists/*

# Configura el entorno de Java
ENV JAVA_HOME /usr/lib/jvm/java-17-openjdk-amd64
ENV PATH $JAVA_HOME/bin:$PATH

# Configura el entorno de JPL (Java Prolog Library)
# Esto es crucial: le dice a Java dónde encontrar los archivos .so de JPL
ENV LD_LIBRARY_PATH /usr/lib/swi-prolog/lib/amd64
ENV CLASSPATH .:/usr/lib/swi-prolog/lib/jpl.jar

# ----- Aplicación Spring -----

# Crea el directorio de la app
WORKDIR /app

# Copia el archivo .jar de la aplicación
ARG JAR_FILE=target/servicio-prolog-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Copia el archivo .pl a la ubicación que espera el application.properties
COPY src/main/resources/prolog/planificador.pl /app/prolog/planificador.pl

# Expone el puerto del servicio
EXPOSE 8082

# Comando de ejecución
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]