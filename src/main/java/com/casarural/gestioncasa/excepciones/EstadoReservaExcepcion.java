package com.casarural.gestioncasa.excepciones;

public class EstadoReservaExcepcion extends Exception {
    
    public EstadoReservaExcepcion(String mensaje) {
        super(mensaje);
    }
    
    public EstadoReservaExcepcion(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
