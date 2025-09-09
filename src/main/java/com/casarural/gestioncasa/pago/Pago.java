package com.casarural.gestioncasa.pago;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import com.casarural.gestioncasa.excepciones.ImporteInvalidoExcepcion;

public abstract class Pago {
    private UUID id;
    private BigDecimal importe;
    private LocalDateTime fechaPago;
    private String metodoPago;

    // Constructor
    public Pago() {
        this.id = UUID.randomUUID();
        this.importe = BigDecimal.ZERO;
        this.fechaPago = LocalDateTime.now();
        this.metodoPago = "Sin metodo de pago";
    }
    
    // Constructor con parámetros (sin fechaPago)
    public Pago(BigDecimal importe, String metodoPago) {
        this.id = UUID.randomUUID();
        this.importe = importe;
        this.fechaPago = LocalDateTime.now();  // Fecha automática
        this.metodoPago = metodoPago;
    }
    
    // Getters y setters
    public UUID getId() {
        return id;
    }
    public BigDecimal getImporte() {
        return importe;
    }
    public LocalDateTime getFechaPago() {
        return fechaPago;
    }
    public String getMetodoPago() {
        return metodoPago;
    }
    public void setImporte(BigDecimal importe) throws ImporteInvalidoExcepcion {
        if (importe == null) {
            throw new ImporteInvalidoExcepcion("El importe no puede ser nulo");
        }
        if (importe.compareTo(BigDecimal.ZERO) < 0) {
            throw new ImporteInvalidoExcepcion("El importe no puede ser negativo: " + importe);
        }
        this.importe = importe;
    }
    
    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    // Metodo abstracto para procesar el pago
    public abstract void procesarPago();
}
