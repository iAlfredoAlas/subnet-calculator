# 1. Usar una imagen oficial de Java 21 con JDK
FROM eclipse-temurin:21-jdk-alpine

# 2. Establecer directorio de trabajo dentro del contenedor
WORKDIR /app

# 3. Copiar el archivo JAR generado
COPY target/subnet-calculator-0.0.1-SNAPSHOT.jar app.jar

# 4. Exponer el puerto donde corre la aplicación (8080)
EXPOSE 8090

# 5. Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
