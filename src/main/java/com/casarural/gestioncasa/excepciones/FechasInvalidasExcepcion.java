package com.casarural.gestioncasa.excepciones;

public class FechasInvalidasExcepcion extends Exception {
    
    public FechasInvalidasExcepcion(String mensaje) {
        super(mensaje);
    }
    
    public FechasInvalidasExcepcion(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}