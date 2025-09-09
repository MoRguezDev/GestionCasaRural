# Sistema de Gestión para Casa Rural

Aplicación Java para gestionar reservas, clientes, habitaciones y pagos en una casa rural con persistencia JSON.

## Requisitos

- Java 24 o superior
- Maven 3.6+

## Compilación

```bash
mvn clean compile
```

## Ejecución

Abrir archivo Main.java de la carpeta src/main/java/com/casarural con tu IDE preferido y ejecutar.

O introducir comando:

```bash
mvn exec:java -Dexec.mainClass="com.casarural.Main"
```

O compilar y ejecutar manualmente:

```bash
mvn clean package
java -cp target/classes com.casarural.Main
```



## Archivo JSON

**Ubicación:** `src/main/resources/data/gestion-cr.json`

Este archivo se genera automáticamente al guardar datos desde la aplicación y contiene:
- Información de la casa rural
- Habitaciones registradas
- Clientes
- Reservas con sus estados
- Pagos asociados (tarjeta/efectivo)

## Uso

1. Ejecuta la aplicación
2. Usa la opción 7 para crear datos de prueba
3. Explora las diferentes opciones del menú
4. Los datos se guardan automáticamente en el archivo JSON

## Funcionalidades

- ✅ Gestión completa de habitaciones
- ✅ Gestión de clientes
- ✅ Sistema de reservas con validaciones
- ✅ Pagos por tarjeta y efectivo
- ✅ Persistencia automática en JSON
