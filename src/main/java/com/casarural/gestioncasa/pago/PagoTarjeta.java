package com.casarural.gestioncasa.pago;

import java.math.BigDecimal;

public class PagoTarjeta extends Pago {
    private String titular;
    private String numeroTarjeta;
    private String ultimos4Digitos;
    private String cvv;
    private String fechaVencimiento;

    // Constructor
    public PagoTarjeta() {
        super();
        this.titular = "Sin titular";
        this.numeroTarjeta = "Sin numero de tarjeta";
        this.ultimos4Digitos = "Sin ultimos 4 digitos";
        this.cvv = "Sin cvv";
        this.fechaVencimiento = "Sin fecha de vencimiento";
    }

    // Constructor con parámetros (modificado)
    public PagoTarjeta(BigDecimal importe, String metodoPago, String titular, String numeroTarjeta, String cvv, String fechaVencimiento) {
        super(importe, metodoPago);
        this.titular = titular;
        this.numeroTarjeta = numeroTarjeta;
        this.ultimos4Digitos = calcularUltimos4Digitos(numeroTarjeta);
        this.cvv = cvv;
        this.fechaVencimiento = fechaVencimiento;
    }

    // Método privado para calcular los últimos 4 dígitos
    private String calcularUltimos4Digitos(String numeroTarjeta) {
        if (numeroTarjeta == null || numeroTarjeta.length() < 4) {
            return "XXXX"; // Valor por defecto si el número es inválido
        }
        return numeroTarjeta.substring(numeroTarjeta.length() - 4);
    }

    // Getters y setters
    public String getTitular() {
        return titular;
    }
    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }
    public String getUltimos4Digitos() {
        return ultimos4Digitos;
    }
    public String getCvv() {
        return cvv;
    }
    public String getFechaVencimiento() {
        return fechaVencimiento;
    }
    public void setTitular(String titular) {
        this.titular = titular;
    }
    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }
    public void setUltimos4Digitos(String ultimos4Digitos) {
        this.ultimos4Digitos = ultimos4Digitos;
    }
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    // Metodo abstracto para procesar el pago
    public void procesarPago() {
        System.out.println("Pago procesado con tarjeta terminada en " + ultimos4Digitos);
    }
}
