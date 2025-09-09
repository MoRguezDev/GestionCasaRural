package com.casarural.gestioncasa;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import com.casarural.gestioncasa.modelo.EstadoReserva;
import com.casarural.gestioncasa.pago.Pago;
import com.casarural.gestioncasa.excepciones.EstadoReservaExcepcion;
import com.casarural.gestioncasa.excepciones.FechasInvalidasExcepcion;
import com.casarural.gestioncasa.excepciones.ImporteInvalidoExcepcion;

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
    public Reserva(Cliente cliente, Habitacion habitacion, LocalDateTime fechaInicio, LocalDateTime fechaFin, BigDecimal precioTotal) throws FechasInvalidasExcepcion {
        // Validar fechas antes de crear la reserva
        validarFechas(fechaInicio, fechaFin);
        
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
    public Reserva(Cliente cliente, Habitacion habitacion, LocalDateTime fechaInicio, LocalDateTime fechaFin, BigDecimal precioTotal, EstadoReserva estado) throws FechasInvalidasExcepcion {
        // Validar fechas antes de crear la reserva
        validarFechas(fechaInicio, fechaFin);
        
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
    public Reserva(Cliente cliente, Habitacion habitacion, LocalDateTime fechaInicio, LocalDateTime fechaFin, BigDecimal precioTotal, EstadoReserva estado, Pago pago) throws FechasInvalidasExcepcion {
        // Validar fechas antes de crear la reserva
        validarFechas(fechaInicio, fechaFin);
        
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
    
    // Getters y setters
    public UUID getId() {
        return id;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public Habitacion getHabitacion() {
        return habitacion;
    }
    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }
    public LocalDateTime getFechaFin() {
        return fechaFin;
    }
    public int getNoches() {
        return noches;
    }
    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }
    public EstadoReserva getEstado() {
        return estado;
    }
    public Pago getPago() {
        return pago;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }
    public void setFechaInicio(LocalDateTime fechaInicio) throws FechasInvalidasExcepcion {
        validarFechas(fechaInicio, this.fechaFin);
        this.fechaInicio = fechaInicio;
    }
    public void setFechaFin(LocalDateTime fechaFin) throws FechasInvalidasExcepcion {
        validarFechas(this.fechaInicio, fechaFin);
        this.fechaFin = fechaFin;
    }
    public void setNoches(int noches) {
        this.noches = noches;
    }
    public void setPrecioTotal(BigDecimal precioTotal) throws ImporteInvalidoExcepcion {
        if (precioTotal == null) {
            throw new ImporteInvalidoExcepcion("El precio total no puede ser nulo");
        }
        if (precioTotal.compareTo(BigDecimal.ZERO) < 0) {
            throw new ImporteInvalidoExcepcion("El precio total no puede ser negativo: " + precioTotal);
        }
        this.precioTotal = precioTotal;
    }
    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }
    public void setPago(Pago pago) {
        this.pago = pago;
    }

    // Método para cancelar la reserva
    public void cancelarReserva() throws EstadoReservaExcepcion {
        // Verificar que la reserva pueda ser cancelada
        if (this.estado == EstadoReserva.CANCELADA) {
            throw new EstadoReservaExcepcion("La reserva ya está cancelada");
        }
        
        if (this.estado == EstadoReserva.COMPLETADA) {
            throw new EstadoReservaExcepcion("No se puede cancelar una reserva que ya ha sido completada");
        }
        
        // Cambiar el estado a CANCELADA
        this.estado = EstadoReserva.CANCELADA;
        
        // Si hay un pago asociado, procesar posible reembolso
        if (this.pago != null) {
            System.out.println("Procesando reembolso para la reserva cancelada...");
            // Aquí se podría agregar lógica adicional para manejar el reembolso
            // dependiendo del tipo de pago (efectivo, tarjeta, etc.)
        }
        
        System.out.println("Reserva cancelada exitosamente. ID: " + this.id);
    }

    // Método para asociar una reserva a un pago
    public void asociarPago(Pago pago) throws EstadoReservaExcepcion, ImporteInvalidoExcepcion {
        // Validar que el pago no sea null
        if (pago == null) {
            throw new EstadoReservaExcepcion("El pago no puede ser nulo");
        }
        
        // Verificar que no haya ya un pago asociado
        if (this.pago != null) {
            throw new EstadoReservaExcepcion("La reserva ya tiene un pago asociado");
        }
        
        // Verificar que la reserva esté en un estado válido para asociar pago
        if (this.estado == EstadoReserva.CANCELADA) {
            throw new EstadoReservaExcepcion("No se puede asociar un pago a una reserva cancelada");
        }
        
        if (this.estado == EstadoReserva.COMPLETADA) {
            throw new EstadoReservaExcepcion("No se puede asociar un pago a una reserva completada");
        }
        
        // Validación del importe según el tipo de pago
        validarImportePago(pago);
        
        // Asociar el pago
        this.pago = pago;
        
        // Si la reserva estaba PENDIENTE, cambiar a CONFIRMADA
        if (this.estado == EstadoReserva.PENDIENTE) {
            this.estado = EstadoReserva.CONFIRMADA;
        }
        
        System.out.println("Pago asociado exitosamente a la reserva. ID: " + this.id);
        System.out.println("Estado actualizado a: " + this.estado);
    }

    // Método auxiliar para validar el importe del pago según su tipo
    private void validarImportePago(Pago pago) throws ImporteInvalidoExcepcion, EstadoReservaExcepcion {
        String metodoPago = pago.getMetodoPago().toLowerCase();
        
        if (metodoPago.contains("efectivo")) {
            // Para pagos en efectivo: el importe pagado debe ser mayor o igual al precio total
            if (pago.getImporte().compareTo(this.precioTotal) < 0) {
                throw new ImporteInvalidoExcepcion(
                    "Pago insuficiente. Importe pagado: " + pago.getImporte() + "€, Precio total: " + this.precioTotal + "€"
                );
            }
            // Es válido que pague más (recibirá cambio)
        } else if (metodoPago.contains("tarjeta") || metodoPago.contains("credito") || metodoPago.contains("debito")) {
            // Para pagos con tarjeta: el importe debe coincidir exactamente (con pequeña tolerancia)
            BigDecimal diferencia = pago.getImporte().subtract(this.precioTotal).abs();
            if (diferencia.compareTo(new BigDecimal("0.01")) > 0) {
                throw new ImporteInvalidoExcepcion(
                    "El importe del pago con tarjeta debe coincidir exactamente. Importe pagado: " + pago.getImporte() + "€, Precio total: " + this.precioTotal + "€"
                );
            }
        } else {
            // Para otros métodos de pago: permitir flexibilidad pero no pagos insuficientes
            if (pago.getImporte().compareTo(this.precioTotal) < 0) {
                throw new ImporteInvalidoExcepcion(
                    "Pago insuficiente. Importe pagado: " + pago.getImporte() + "€, Precio total: " + this.precioTotal + "€"
                );
            }
        }
    }

    // Método para calcular el precio total de la reserva
    public BigDecimal calcularPrecioTotal() throws EstadoReservaExcepcion, FechasInvalidasExcepcion {
        // Validar que tengamos los datos necesarios
        if (this.habitacion == null) {
            throw new EstadoReservaExcepcion("No se puede calcular el precio: la habitación no está asignada");
        }
        
        if (this.fechaInicio == null || this.fechaFin == null) {
            throw new EstadoReservaExcepcion("No se puede calcular el precio: las fechas no están definidas");
        }
        
        if (this.habitacion.getPrecioNoche() == null || this.habitacion.getPrecioNoche().compareTo(BigDecimal.ZERO) <= 0) {
            throw new EstadoReservaExcepcion("No se puede calcular el precio: el precio por noche de la habitación no es válido");
        }
        
        // Validar fechas usando la nueva excepción
        validarFechas(this.fechaInicio, this.fechaFin);
        
        // Calcular el número de noches
        this.noches = (int) ChronoUnit.DAYS.between(this.fechaInicio.toLocalDate(), this.fechaFin.toLocalDate());
        
        if (this.noches <= 0) {
            throw new FechasInvalidasExcepcion("El número de noches debe ser mayor a 0");
        }
        
        // Calcular el precio total: precio por noche * número de noches
        this.precioTotal = this.habitacion.getPrecioNoche().multiply(BigDecimal.valueOf(this.noches));
        
        System.out.println("Precio total calculado: " + this.precioTotal + "€ (" + this.noches + " noches x " + this.habitacion.getPrecioNoche() + "€/noche)");
        
        return this.precioTotal;
    }

    // Método auxiliar para validar fechas
    private void validarFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) throws FechasInvalidasExcepcion {
        if (fechaInicio == null) {
            throw new FechasInvalidasExcepcion("La fecha de inicio no puede ser nula");
        }
        
        if (fechaFin == null) {
            throw new FechasInvalidasExcepcion("La fecha de fin no puede ser nula");
        }
        
        if (fechaFin.isBefore(fechaInicio) || fechaFin.isEqual(fechaInicio)) {
            throw new FechasInvalidasExcepcion("La fecha de fin debe ser posterior a la fecha de inicio");
        }
        
        if (fechaInicio.isBefore(LocalDateTime.now())) {
            throw new FechasInvalidasExcepcion("La fecha de inicio no puede ser anterior a la fecha actual");
        }
    }

    // Método para mostrar información completa de la reserva
    public void mostrarInformacionCompleta() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("           INFORMACIÓN COMPLETA DE LA RESERVA");
        System.out.println("=".repeat(60));
        
        // Información básica de la reserva
        System.out.println("\n📋 DATOS DE LA RESERVA:");
        System.out.println("   ID de Reserva: " + (this.id != null ? this.id.toString() : "No asignado"));
        System.out.println("   Estado: " + (this.estado != null ? this.estado : "No definido"));
        System.out.println("   Número de noches: " + this.noches);
        
        // Información del cliente
        System.out.println("\n👤 INFORMACIÓN DEL CLIENTE:");
        if (this.cliente != null) {
            System.out.println("   Nombre: " + (this.cliente.getNombre() != null ? this.cliente.getNombre() : "No especificado"));
            System.out.println("   Documento: " + (this.cliente.getNumeroDocumento() != null ? this.cliente.getNumeroDocumento() : "No especificado"));
            System.out.println("   Email: " + (this.cliente.getEmail() != null ? this.cliente.getEmail() : "No especificado"));
            System.out.println("   Teléfono: " + (this.cliente.getTelefono() != null ? this.cliente.getTelefono() : "No especificado"));
        } else {
            System.out.println("   ❌ No hay cliente asignado a esta reserva");
        }
        
        // Información de la habitación
        System.out.println("\n🏠 INFORMACIÓN DE LA HABITACIÓN:");
        if (this.habitacion != null) {
            System.out.println("   Código: " + (this.habitacion.getCodigo() != null ? this.habitacion.getCodigo() : "No especificado"));
            System.out.println("   Descripción: " + (this.habitacion.getDescripcion() != null ? this.habitacion.getDescripcion() : "No especificada"));
            System.out.println("   Capacidad: " + this.habitacion.getCapacidad() + " persona(s)");
            System.out.println("   Precio por noche: " + (this.habitacion.getPrecioNoche() != null ? this.habitacion.getPrecioNoche() + "€" : "No definido"));
            System.out.println("   Disponible: " + (this.habitacion.isDisponible() ? "✅ Sí" : "❌ No"));
        } else {
            System.out.println("   ❌ No hay habitación asignada a esta reserva");
        }
        
        // Información de fechas
        System.out.println("\n📅 FECHAS DE LA RESERVA:");
        if (this.fechaInicio != null && this.fechaFin != null) {
            System.out.println("   Check-in: " + this.fechaInicio.toLocalDate() + " a las " + this.fechaInicio.toLocalTime());
            System.out.println("   Check-out: " + this.fechaFin.toLocalDate() + " a las " + this.fechaFin.toLocalTime());
            System.out.println("   Duración: " + this.noches + " noche(s)");
            
            // Calcular días hasta check-in
            long diasHastaCheckIn = java.time.temporal.ChronoUnit.DAYS.between(java.time.LocalDate.now(), this.fechaInicio.toLocalDate());
            if (diasHastaCheckIn > 0) {
                System.out.println("   ⏰ Días hasta check-in: " + diasHastaCheckIn);
            } else if (diasHastaCheckIn == 0) {
                System.out.println("   ⏰ ¡Check-in hoy!");
            } else {
                System.out.println("   ⏰ Reserva pasada");
            }
        } else {
            System.out.println("   ❌ Fechas no definidas");
        }
        
        // Información del pago
        System.out.println("\n💳 INFORMACIÓN DEL PAGO:");
        if (this.pago != null) {
            System.out.println("   Método de pago: " + (this.pago.getMetodoPago() != null ? this.pago.getMetodoPago() : "No especificado"));
            System.out.println("   Importe pagado: " + (this.pago.getImporte() != null ? this.pago.getImporte() + "€" : "No definido"));
            System.out.println("   Fecha de pago: " + (this.pago.getFechaPago() != null ? this.pago.getFechaPago().toLocalDate() : "No definida"));
            
            // Calcular diferencia si hay pago
            if (this.precioTotal != null && this.pago.getImporte() != null) {
                java.math.BigDecimal diferencia = this.pago.getImporte().subtract(this.precioTotal);
                if (diferencia.compareTo(java.math.BigDecimal.ZERO) > 0) {
                    System.out.println("   💰 Cambio a devolver: " + diferencia + "€");
                } else if (diferencia.compareTo(java.math.BigDecimal.ZERO) < 0) {
                    System.out.println("   ⚠️ Pago pendiente: " + diferencia.abs() + "€");
                } else {
                    System.out.println("   ✅ Pago completo");
                }
            }
        } else {
            System.out.println("   ❌ No hay pago registrado");
        }
        
        // Información financiera
        System.out.println("\n💰 RESUMEN FINANCIERO:");
        System.out.println("   Precio total calculado: " + (this.precioTotal != null ? this.precioTotal + "€" : "No calculado"));
        if (this.habitacion != null && this.habitacion.getPrecioNoche() != null && this.noches > 0) {
            System.out.println("   Desglose: " + this.noches + " noche(s) × " + this.habitacion.getPrecioNoche() + "€ = " + 
                              this.habitacion.getPrecioNoche().multiply(java.math.BigDecimal.valueOf(this.noches)) + "€");
        }
        
        System.out.println("=".repeat(60));
    }

    // Método para cambiar el estado de la reserva de manera controlada
    public void cambiarEstado(EstadoReserva nuevoEstado) throws EstadoReservaExcepcion {
        // Validar que el nuevo estado no sea null
        if (nuevoEstado == null) {
            throw new EstadoReservaExcepcion("El nuevo estado no puede ser nulo");
        }
        
        // Validaciones de transición de estados
        if (this.estado == EstadoReserva.CANCELADA && nuevoEstado != EstadoReserva.CANCELADA) {
            throw new EstadoReservaExcepcion("No se puede cambiar el estado de una reserva cancelada");
        }
        
        if (this.estado == EstadoReserva.COMPLETADA && nuevoEstado != EstadoReserva.COMPLETADA) {
            throw new EstadoReservaExcepcion("No se puede cambiar el estado de una reserva completada");
        }
        
        // No permitir confirmar una reserva cancelada
        if (this.estado == EstadoReserva.CANCELADA && nuevoEstado == EstadoReserva.CONFIRMADA) {
            throw new EstadoReservaExcepcion("No se puede confirmar una reserva cancelada");
        }
        
        // Cambiar el estado
        EstadoReserva estadoAnterior = this.estado;
        this.estado = nuevoEstado;
        
        System.out.println("Estado de la reserva cambiado de " + estadoAnterior + " a " + nuevoEstado);
    }
}