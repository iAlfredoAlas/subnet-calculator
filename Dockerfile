# 1. Usar una imagen oficial de Java 21 con JDK
FROM eclipse-temurin:21-jdk-alpine

# 2. Agregar una etiqueta de versión
LABEL version="V.0.0.1"

# 3. Establecer directorio de trabajo dentro del contenedor
WORKDIR /app

# 4. Copiar el archivo JAR generado
COPY target/subnet-calculator-0.0.1-SNAPSHOT.jar app.jar

# 5. Exponer el puerto donde corre la aplicación (8090)
EXPOSE 8090

# 6. Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]