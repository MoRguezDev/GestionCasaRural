package com.casarural.gestioncasa.pago;

import java.math.BigDecimal;

public class PagoEfectivo extends Pago {
    private BigDecimal importeEfectivo;
    private BigDecimal importeDevuelto;

    // Constructor
    public PagoEfectivo() {
        super();
        this.importeEfectivo = BigDecimal.ZERO;
        this.importeDevuelto = BigDecimal.ZERO;
    }
    
    // Constructor con parámetros (sin importeDevuelto)
    public PagoEfectivo(BigDecimal importe, String metodoPago, BigDecimal importeEfectivo) {
        super(importe, metodoPago);
        this.importeEfectivo = importeEfectivo;
        this.importeDevuelto = calcularImporteDevuelto(importeEfectivo, importe);
    }

    // Método para calcular el importe devuelto
    private BigDecimal calcularImporteDevuelto(BigDecimal importeEfectivo, BigDecimal importe) {
        if (importeEfectivo == null || importe == null) {
            return BigDecimal.ZERO;
        }
        return importeEfectivo.subtract(importe);
    }

    // Getters y setters
    public BigDecimal getImporteEfectivo() {
        return importeEfectivo;
    }

    public BigDecimal getImporteDevuelto() {
        return importeDevuelto;
    }

    public void setImporteEfectivo(BigDecimal importeEfectivo) {
        this.importeEfectivo = importeEfectivo;
        this.importeDevuelto = calcularImporteDevuelto(importeEfectivo, getImporte());
    }

    // Método para procesar el pago
    public void procesarPago() {
        System.out.println("Pago procesado en efectivo");
        System.out.println("Importe recibido: " + importeEfectivo + " €");
        System.out.println("Importe a pagar: " + getImporte() + " €");
        System.out.println("Importe devuelto: " + importeDevuelto + " €");
    }
}
