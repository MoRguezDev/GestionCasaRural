package com.casarural.gestioncasa;

import java.math.BigDecimal;
import java.util.UUID;
import com.casarural.gestioncasa.excepciones.ImporteInvalidoExcepcion;

public class Habitacion {
    private UUID id;
    private String codigo;
    private String descripcion;
    private int capacidad;
    private BigDecimal precioNoche;
    private boolean disponible;

    // Constructor
    public Habitacion() {
        this.id = UUID.randomUUID();
        this.codigo = "H001";
        this.descripcion = "Habitacion 1";
        this.capacidad = 1;
        this.precioNoche = BigDecimal.ZERO;
        this.disponible = true;
    }

    // Constructor con parámetros
    public Habitacion(String codigo, String descripcion, int capacidad, BigDecimal precioNoche, boolean disponible) {
        this.id = UUID.randomUUID(); // Genera ID automáticamente
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.capacidad = capacidad;
        this.precioNoche = precioNoche;
        this.disponible = disponible;
    }

    // Constructor con parámetros (sobrecargado, asume disponible = true)
    public Habitacion(String codigo, String descripcion, int capacidad, BigDecimal precioNoche) {
        this(codigo, descripcion, capacidad, precioNoche, true);
    }

    // Getters y setters (solo los necesarios)
    
    // ID - solo getter (se genera automáticamente, no debe modificarse)
    public UUID getId() {
        return id;
    }
    
    // Código
    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    // Descripción
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    // Capacidad
    public int getCapacidad() {
        return capacidad;
    }
    
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
    
    // Precio por noche
    public BigDecimal getPrecioNoche() {
        return precioNoche;
    }
    
    // Método setPrecioNoche con validación
    public void setPrecioNoche(BigDecimal precioNoche) throws ImporteInvalidoExcepcion {
        if (precioNoche == null) {
            throw new ImporteInvalidoExcepcion("El precio por noche no puede ser nulo");
        }
        if (precioNoche.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ImporteInvalidoExcepcion("El precio por noche debe ser mayor que cero: " + precioNoche);
        }
        this.precioNoche = precioNoche;
    }
    
    // Disponibilidad
    public boolean isDisponible() {
        return disponible;
    }
    
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
