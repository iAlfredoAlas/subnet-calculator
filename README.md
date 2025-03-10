
---

### **📌 Archivo `README.md`**
📂 **Ubicación:** `README.md`


# Subnet Calculator 🖧

[![GitHub](https://img.shields.io/badge/GitHub-Profile-black?style=for-the-badge&logo=github)](https://github.com/iAlfredoAlas)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Profile-blue?style=for-the-badge&logo=linkedin)](https://www.linkedin.com/in/alfredoalas/)

[![GitHub](https://img.shields.io/badge/GitHub-Profile-black?style=social&logo=github)](https://github.com/iAlfredoAlas)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Profile-blue?style=social&logo=linkedin)](https://www.linkedin.com/in/alfredoalas/)



Calculadora de subnetting en **Spring Boot + Thymeleaf** que permite calcular subredes automáticamente según una IP base, CIDR y requisitos de hosts.  
El resultado se muestra en una tabla con opciones para copiar los datos en formato JSON o texto tabulado.

## 🚀 Características

✔ Calcula subredes automáticamente según los requisitos de hosts  
✔ Muestra los datos en una tabla con opción de copiar en formato JSON o tabla tabulada  
✔ Validaciones visuales en el formulario antes de enviar los datos  
✔ Interfaz responsiva con diseño moderno y paleta de colores personalizada  
✔ Backend desarrollado en **Spring Boot** y frontend con **Thymeleaf + Bootstrap**

---

## 📌 Tecnologías Utilizadas

- **Java 21** con **Spring Boot 3**
- **Thymeleaf** para la interfaz web
- **Bootstrap 5** para el diseño
- **Axios** para las peticiones HTTP
- **Maven** como gestor de dependencias

---

## 🛠️ Instalación y Configuración

### **1️⃣ Clonar el repositorio**
```sh
git clone https://github.com/iAlfredoAlas/subnet-calculator.git
cd subnet-calculator
```

### **2️⃣ Configurar Maven**
Asegúrate de tener **Maven** instalado correctamente.  
Puedes verificarlo con:
```sh
mvn -version
```

### **3️⃣ Ejecutar la aplicación**
Para correr el servidor en **Spring Boot**:
```sh
mvn spring-boot:run
```

### **4️⃣ Acceder a la aplicación**
Abre tu navegador y ve a:
```
http://localhost:8080/
```

---

## 📡 Uso de la API

Puedes realizar una solicitud **POST** al backend en:
```
POST /api/subnet/calculate
```

📌 **Ejemplo de Request (JSON)**
```json
{
  "ipBase": "129.2.0.0",
  "cidr": 16,
  "numSubnets": 3,
  "hostRequirements": [2, 3, 4]
}
```

📌 **Ejemplo de Respuesta**
```json
{
  "ipBase": "129.2.0.0",
  "subnetMask": "255.255.0.0",
  "wildcardMask": "0.0.255.255",
  "subnets": [
    {
      "name": "Red 1",
      "networkAddress": "129.2.0.0",
      "subnetMask": "255.255.0.0",
      "gateway": "129.2.0.1",
      "firstUsableIp": "129.2.0.2",
      "lastUsableIp": "129.2.255.254",
      "broadcast": "129.2.255.255"
    }
  ]
}
```

---

## 🎨 Estilo y Diseño

- **Paleta de colores:**
    - `#222831` (Fondo oscuro)
    - `#31363F` (Contenedores)
    - `#76ABAE` (Color principal)
    - `#EEEEEE` (Texto)

- **Diseño responsivo con Bootstrap**
- **Validaciones visuales en el formulario antes de enviar los datos**

---

## 📎 Contacto

🔹 **GitHub:** [@iAlfredoAlas](https://github.com/iAlfredoAlas)  
🔹 **LinkedIn:** [Alfredo Alas](https://www.linkedin.com/in/alfredoalas/)

---

## 📜 Licencia

Este proyecto está licenciado bajo la Licencia Pública General Affero de GNU v3.  
Consulta el archivo [LICENSE](LICENSE) para más detalles.


---

🚀 ¡Siéntete libre de contribuir o sugerir mejoras!  
Si te gusta el proyecto, **⭐ dale un star en GitHub** 😃

