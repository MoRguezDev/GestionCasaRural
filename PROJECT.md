# Sistema de Gestión para Casa Rural

## Descripción del Proyecto
Modelar e implementar un mini-sistema de gestión para una casa rural que permita crear y gestionar reservas, asociar pagos (tarjeta o efectivo) y persistir los datos en un archivo JSON compartible.

## Requisitos Funcionales

La aplicación debe permitir las siguientes funcionalidades:

### Gestión de Habitaciones
- Dar de alta habitaciones
- Listar habitaciones disponibles

### Gestión de Clientes
- Registrar nuevos clientes

### Gestión de Reservas
- Crear reservas con rango de fechas válido
- Calcular automáticamente el número de noches y el importe total
- Cambiar el estado de las reservas según reglas predefinidas

### Sistema de Pagos
- Asociar pagos a reservas
- Soporte para pagos con tarjeta y efectivo

### Persistencia de Datos
- Guardar y cargar todo el estado del sistema en formato JSON
- Archivo de ejemplo: `gestion-cr.json`
- Debe ser creado en src/main/resources/data.

## Datos de Prueba Mínimos
El sistema debe incluir como mínimo:
- 1 casa rural
- 2 habitaciones
- 1 cliente registrado
- 1 reserva confirmada con pago asociado

## Modelo de Clases
Crear las entidades y relaciones según el diagrama proporcionado.

## Validaciones Obligatorias

### Validaciones de Fechas
- La fecha de inicio debe ser anterior a la fecha de fin

### Validaciones de Importes
- Los importes deben ser mayores a 0
- Utilizar BigDecimal (o equivalente) para cálculos precisos

### Validaciones de Estados
- Transiciones coherentes entre estados de reserva
- No permitir confirmar reservas canceladas

## Persistencia JSON

### Requisitos de Implementación
- Implementar métodos `guardar()` y `cargar()` para el estado completo
- Incluir campo `tipo` en pagos ("PagoTarjeta" / "PagoEfectivo")
- Asegurar consistencia en IDs y referencias (cliente/habitación/reserva)

## Ejecución y Pruebas

### Caso de Uso Principal
Crear un flujo completo que demuestre:
1. Alta de datos iniciales
2. Creación de reserva
3. Asociación de pago
4. Guardado del estado
5. Carga del estado
6. Verificación de integridad

## Estructura del Proyecto

### Organización por Paquetes
- Utilizar paquetes por dominio (ej.: `modelo/`, `servicio/`, `persistencia/`)

### Calidad del Código
- Código comentado en reglas y validaciones
- Documentación clara de la lógica de negocio

## Documentación
- Incluir `README.md` con instrucciones para:
  - Compilación del proyecto
  - Ejecución de la aplicación
  - Ubicación del archivo JSON generado

## Entrega del Proyecto
**Contenido requerido:**
- Código fuente completo
- Archivo `gestion-cr.json` de ejemplo con datos
- Documentación `README.md`

**Formato de entrega:** Repositorio Git o archivo ZIP