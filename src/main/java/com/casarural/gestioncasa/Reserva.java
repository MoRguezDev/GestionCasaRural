package com.casarural.gestioncasa;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import com.casarural.gestioncasa.modelo.EstadoReserva;
import com.casarural.gestioncasa.pago.Pago;

public class Reserva {
    private UUID id;
    private Cliente cliente;
    private Habitacion habitacion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private int noches;
    private BigDecimal precioTotal;
    private EstadoReserva estado;
    private Pago pago;

    // Constructor vacío
    public Reserva() {
        this.id = UUID.randomUUID();
        this.cliente = new Cliente();
        this.habitacion = new Habitacion();
        this.fechaInicio = LocalDateTime.now();
        this.fechaFin = LocalDateTime.now();
        this.noches = 0;
        this.precioTotal = BigDecimal.ZERO;
        this.estado = EstadoReserva.PENDIENTE;
        this.pago = null;
    }

    // Constructor con parámetros
    public Reserva(Cliente cliente, Habitacion habitacion, LocalDateTime fechaInicio, LocalDateTime fechaFin, BigDecimal precioTotal) {
        this.id = UUID.randomUUID();
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.noches = (int) ChronoUnit.DAYS.between(fechaInicio.toLocalDate(), fechaFin.toLocalDate());
        this.precioTotal = precioTotal;
        this.estado = EstadoReserva.PENDIENTE;
        this.pago = null;
    }

    // Constructor con parámetros (con estado personalizado)
    public Reserva(Cliente cliente, Habitacion habitacion, LocalDateTime fechaInicio, LocalDateTime fechaFin, BigDecimal precioTotal, EstadoReserva estado) {
        this.id = UUID.randomUUID();
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.noches = (int) ChronoUnit.DAYS.between(fechaInicio.toLocalDate(), fechaFin.toLocalDate());
        this.precioTotal = precioTotal;
        this.estado = estado;
        this.pago = null;
    }

    // Constructor con parámetros (con pago incluido)
    public Reserva(Cliente cliente, Habitacion habitacion, LocalDateTime fechaInicio, LocalDateTime fechaFin, BigDecimal precioTotal, EstadoReserva estado, Pago pago) {
        this.id = UUID.randomUUID();
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.noches = (int) ChronoUnit.DAYS.between(fechaInicio.toLocalDate(), fechaFin.toLocalDate());
        this.precioTotal = precioTotal;
        this.estado = estado;
        this.pago = pago;
    }
    
}
