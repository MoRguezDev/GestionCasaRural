package com.casarural.gestioncasa.excepciones;

public class ImporteInvalidoExcepcion extends Exception {
    
    // Constructor sin mensaje
    public ImporteInvalidoExcepcion() {
        super("El importe proporcionado no es válido");
    }
    
    // Constructor con mensaje personalizado
    public ImporteInvalidoExcepcion(String mensaje) {
        super(mensaje);
    }
    
    // Constructor con mensaje y causa
    public ImporteInvalidoExcepcion(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
