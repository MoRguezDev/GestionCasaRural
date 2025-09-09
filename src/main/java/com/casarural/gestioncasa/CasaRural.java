package com.casarural.gestioncasa;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CasaRural {
    private UUID id;
    private String nombre;
    private String direccion;
    private String telefono;
    private List<Habitacion> habitaciones;

    // Constructor
    public CasaRural() {
        this.habitaciones = new ArrayList<>();
    }

    // Constructor con parámetros
    public CasaRural(UUID id, String nombre, String direccion, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.habitaciones = new ArrayList<>();
    }

    // Getters y setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(List<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    // Métodos para trabajar con la lista de habitaciones
    public void agregarHabitacion(Habitacion habitacion) {
        this.habitaciones.add(habitacion);
    }

    public void eliminarHabitacion(Habitacion habitacion) {
        this.habitaciones.remove(habitacion);
    }

    // Listar habitaciones
    public void listarHabitaciones() {
        if (habitaciones.isEmpty()) {
            System.out.println("No hay habitaciones registradas en la casa rural.");
            return;
        }
        
        System.out.println("\n=== HABITACIONES DE LA CASA RURAL ===");
        System.out.println("Casa Rural: " + (nombre != null ? nombre : "Sin nombre"));
        System.out.println("Total de habitaciones: " + habitaciones.size());
        System.out.println("=====================================");
        
        for (int i = 0; i < habitaciones.size(); i++) {
            Habitacion hab = habitaciones.get(i);
            System.out.println("\nHabitación " + (i + 1) + ":");
            System.out.println("  Código: " + hab.getCodigo());
            System.out.println("  Descripción: " + hab.getDescripcion());
            System.out.println("  Capacidad: " + hab.getCapacidad() + " persona(s)");
            System.out.println("  Precio por noche: " + hab.getPrecioNoche() + " €");
            System.out.println("  Estado: " + (hab.isDisponible() ? "Disponible" : "Ocupada"));
        }
        
        System.out.println("\n=====================================");
    }

    public int getNumeroHabitaciones() {
        return habitaciones.size();
    }
}
