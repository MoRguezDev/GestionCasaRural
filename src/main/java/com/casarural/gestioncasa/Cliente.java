package com.casarural.gestioncasa;

import java.util.UUID;

public class Cliente {
    private UUID id;
    private String nombre;
    private String numeroDocumento;
    private String email;
    private String telefono;

    // Constructor
    public Cliente() {
        this.id = UUID.randomUUID();
        this.nombre = "Sin nombre";
        this.numeroDocumento = "Sin numero de documento";
        this.email = "Sin email";
        this.telefono = "Sin telefono";
    }

    // Constructor con parámetros
    public Cliente(String nombre, String numeroDocumento, String email, String telefono) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.numeroDocumento = numeroDocumento;
        this.email = email;
        this.telefono = telefono;
    }

    // Getters y setters
    public UUID getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public String getNumeroDocumento() {
        return numeroDocumento;
    }
    public String getEmail() {
        return email;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
