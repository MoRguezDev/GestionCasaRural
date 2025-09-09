package com.casarural.gestioncasa.pago;

import java.math.BigDecimal;
import com.casarural.gestioncasa.excepciones.ImporteInvalidoExcepcion;

public class PagoEfectivo extends Pago {
    private BigDecimal importeEfectivo;
    private BigDecimal importeDevuelto;

    // Constructor
    public PagoEfectivo() {
        super();
        this.importeEfectivo = BigDecimal.ZERO;
        this.importeDevuelto = BigDecimal.ZERO;
    }
    
    // Constructor con validación
    public PagoEfectivo(BigDecimal importe, String metodoPago, BigDecimal importeEfectivo) throws ImporteInvalidoExcepcion {
        super(importe, metodoPago);
        
        if (importeEfectivo == null) {
            throw new ImporteInvalidoExcepcion("El importe en efectivo no puede ser nulo");
        }
        if (importeEfectivo.compareTo(BigDecimal.ZERO) < 0) {
            throw new ImporteInvalidoExcepcion("El importe en efectivo no puede ser negativo: " + importeEfectivo);
        }
        
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
