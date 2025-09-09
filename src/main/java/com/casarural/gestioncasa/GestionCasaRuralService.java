package com.casarural.gestioncasa;

import com.casarural.gestioncasa.modelo.EstadoReserva;
import com.casarural.gestioncasa.pago.Pago;
import com.casarural.gestioncasa.pago.PagoEfectivo;
import com.casarural.gestioncasa.pago.PagoTarjeta;
import com.casarural.gestioncasa.excepciones.EstadoReservaExcepcion;
import com.casarural.gestioncasa.excepciones.FechasInvalidasExcepcion;
import com.casarural.gestioncasa.excepciones.ImporteInvalidoExcepcion;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class GestionCasaRuralService {
    private CasaRural casaRural;
    private List<Cliente> clientes;
    private List<Reserva> reservas;
    private Scanner scanner;
    private Gson gson;
    private static final String ARCHIVO_JSON = "src/main/resources/data/gestion-cr.json";

    // Adaptadores para GSON
    private static class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        @Override
        public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
            return context.serialize(src.format(formatter));
        }

        @Override
        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return LocalDateTime.parse(json.getAsString(), formatter);
        }
    }

    private static class PagoAdapter implements JsonSerializer<Pago>, JsonDeserializer<Pago> {
        @Override
        public JsonElement serialize(Pago src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = context.serialize(src).getAsJsonObject();
            jsonObject.addProperty("tipo", src.getClass().getSimpleName());
            return jsonObject;
        }

        @Override
        public Pago deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String tipo = jsonObject.get("tipo").getAsString();
            
            if ("PagoTarjeta".equals(tipo)) {
                return context.deserialize(json, PagoTarjeta.class);
            } else if ("PagoEfectivo".equals(tipo)) {
                return context.deserialize(json, PagoEfectivo.class);
            } else {
                throw new JsonParseException("Tipo de pago desconocido: " + tipo);
            }
        }
    }

    public GestionCasaRuralService() {
        this.casaRural = new CasaRural();
        this.clientes = new ArrayList<>();
        this.reservas = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        
        // Configurar GSON con adaptadores
        this.gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(Pago.class, new PagoAdapter())
            .setPrettyPrinting()
            .create();
    }

    // ===== MÉTODOS DE PERSISTENCIA =====
    
    public void guardar() {
        try {
            DatosSistema datos = new DatosSistema(casaRural, clientes, reservas);
            FileWriter writer = new FileWriter(ARCHIVO_JSON);
            gson.toJson(datos, writer);
            writer.close();
            System.out.println("✅ Datos guardados correctamente en: " + ARCHIVO_JSON);
        } catch (IOException e) {
            System.out.println("❌ Error al guardar los datos: " + e.getMessage());
        }
    }

    public void cargar() {
        try {
            FileReader reader = new FileReader(ARCHIVO_JSON);
            DatosSistema datos = gson.fromJson(reader, DatosSistema.class);
            reader.close();
            
            this.casaRural = datos.casaRural;
            this.clientes = datos.clientes;
            this.reservas = datos.reservas;
            
            System.out.println("✅ Datos cargados correctamente desde: " + ARCHIVO_JSON);
        } catch (IOException e) {
            System.out.println("❌ Error al cargar los datos: " + e.getMessage());
            System.out.println("ℹ️  Se iniciará con datos vacíos.");
        }
    }

    // ===== GESTIÓN DE HABITACIONES =====
    
    public void darAltaHabitacion() {
        System.out.println("\n🏠 === DAR DE ALTA HABITACIÓN ===");
        
        System.out.print("Código de la habitación: ");
        String codigo = scanner.nextLine().trim();
        
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine().trim();
        
        System.out.print("Capacidad (personas): ");
        int capacidad = Integer.parseInt(scanner.nextLine().trim());
        
        System.out.print("Precio por noche (€): ");
        BigDecimal precioNoche = new BigDecimal(scanner.nextLine().trim());
        
        try {
            Habitacion habitacion = new Habitacion(codigo, descripcion, capacidad, precioNoche);
            casaRural.agregarHabitacion(habitacion);
            System.out.println("✅ Habitación creada correctamente: " + habitacion.getCodigo());
        } catch (Exception e) {
            System.out.println("❌ Error al crear habitación: " + e.getMessage());
        }
    }

    public void listarHabitaciones() {
        casaRural.listarHabitaciones();
    }

    public List<Habitacion> getHabitacionesDisponibles() {
        List<Habitacion> disponibles = new ArrayList<>();
        for (Habitacion hab : casaRural.getHabitaciones()) {
            if (hab.isDisponible()) {
                disponibles.add(hab);
            }
        }
        return disponibles;
    }

    // ===== GESTIÓN DE CLIENTES =====
    
    public void registrarCliente() {
        System.out.println("\n👤 === REGISTRAR NUEVO CLIENTE ===");
        
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine().trim();
        
        System.out.print("Número de documento: ");
        String numeroDocumento = scanner.nextLine().trim();
        
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine().trim();
        
        Cliente cliente = new Cliente(nombre, numeroDocumento, email, telefono);
        clientes.add(cliente);
        
        System.out.println("✅ Cliente registrado correctamente: " + cliente.getNombre());
    }

    public void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        
        System.out.println("\n👥 === LISTA DE CLIENTES ===");
        for (int i = 0; i < clientes.size(); i++) {
            Cliente cliente = clientes.get(i);
            System.out.println("\nCliente " + (i + 1) + ":");
            System.out.println("  Nombre: " + cliente.getNombre());
            System.out.println("  Documento: " + cliente.getNumeroDocumento());
            System.out.println("  Email: " + cliente.getEmail());
            System.out.println("  Teléfono: " + cliente.getTelefono());
        }
        System.out.println("\n=================================");
    }

    // ===== GESTIÓN DE RESERVAS =====
    
    public void crearReserva() {
        System.out.println("\n📅 === CREAR NUEVA RESERVA ===");
        
        // Verificar que hay habitaciones disponibles
        List<Habitacion> disponibles = getHabitacionesDisponibles();
        if (disponibles.isEmpty()) {
            System.out.println("❌ No hay habitaciones disponibles.");
            return;
        }
        
        // Verificar que hay clientes registrados
        if (clientes.isEmpty()) {
            System.out.println("❌ No hay clientes registrados. Registre un cliente primero.");
            return;
        }
        
        // Seleccionar cliente
        System.out.println("\nSeleccione un cliente:");
        listarClientes();
        System.out.print("Número del cliente: ");
        
        int numCliente = -1;
        boolean clienteValido = false;
        while (!clienteValido) {
            try {
                numCliente = Integer.parseInt(scanner.nextLine().trim()) - 1;
                if (numCliente < 0 || numCliente >= clientes.size()) {
                    System.out.println("❌ Cliente no válido. Debe ingresar un número entre 1 y " + clientes.size());
                    System.out.print("Número del cliente: ");
                } else {
                    clienteValido = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada no válida. Debe ingresar un número entero correspondiente a un cliente existente.");
                System.out.print("Número del cliente: ");
            }
        }
        Cliente cliente = clientes.get(numCliente);
        
        // Seleccionar habitación
        System.out.println("\nHabitaciones disponibles:");
        for (int i = 0; i < disponibles.size(); i++) {
            Habitacion hab = disponibles.get(i);
            System.out.println((i + 1) + ". " + hab.getCodigo() + " - " + hab.getDescripcion() + " (" + hab.getPrecioNoche() + "€/noche)");
        }
        System.out.print("Número de la habitación: ");
        
        int numHabitacion = -1;
        boolean habitacionValida = false;
        while (!habitacionValida) {
            try {
                numHabitacion = Integer.parseInt(scanner.nextLine().trim()) - 1;
                if (numHabitacion < 0 || numHabitacion >= disponibles.size()) {
                    System.out.println("❌ Habitación no válida. Debe ingresar un número entre 1 y " + disponibles.size());
                    System.out.print("Número de la habitación: ");
                } else {
                    habitacionValida = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada no válida. Debe ingresar un número entero correspondiente a una habitación disponible.");
                System.out.print("Número de la habitación: ");
            }
        }
        Habitacion habitacion = disponibles.get(numHabitacion);
        
        // Ingresar fechas
        System.out.print("Fecha de inicio (YYYY-MM-DD HH:mm): ");
        String fechaInicioStr = scanner.nextLine().trim();
        System.out.print("Fecha de fin (YYYY-MM-DD HH:mm): ");
        String fechaFinStr = scanner.nextLine().trim();
        
        try {
            // Usar DateTimeFormatter para parsear el formato con espacio
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime fechaInicio = LocalDateTime.parse(fechaInicioStr, formatter);
            LocalDateTime fechaFin = LocalDateTime.parse(fechaFinStr, formatter);
            
            // Calcular precio total
            long noches = java.time.temporal.ChronoUnit.DAYS.between(fechaInicio.toLocalDate(), fechaFin.toLocalDate());
            BigDecimal precioTotal = habitacion.getPrecioNoche().multiply(BigDecimal.valueOf(noches));
            
            // Crear reserva
            Reserva reserva = new Reserva(cliente, habitacion, fechaInicio, fechaFin, precioTotal);
            reservas.add(reserva);
            
            System.out.println("✅ Reserva creada correctamente:");
            System.out.println("   Cliente: " + cliente.getNombre());
            System.out.println("   Habitación: " + habitacion.getCodigo());
            System.out.println("   Noches: " + noches);
            System.out.println("   Precio total: " + precioTotal + "€");
            
        } catch (Exception e) {
            System.out.println("❌ Error al crear reserva: " + e.getMessage());
            System.out.println("💡 Formato esperado: YYYY-MM-DD HH:mm (ejemplo: 2026-01-01 20:00)");
        }
    }

    public void listarReservas() {
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
            return;
        }
        
        System.out.println("\n📋 === LISTA DE RESERVAS ===");
        for (int i = 0; i < reservas.size(); i++) {
            Reserva reserva = reservas.get(i);
            System.out.println("\nReserva " + (i + 1) + ":");
            System.out.println("  Cliente: " + reserva.getCliente().getNombre());
            System.out.println("  Habitación: " + reserva.getHabitacion().getCodigo());
            System.out.println("  Fechas: " + reserva.getFechaInicio().toLocalDate() + " - " + reserva.getFechaFin().toLocalDate());
            System.out.println("  Noches: " + reserva.getNoches());
            System.out.println("  Precio total: " + reserva.getPrecioTotal() + "€");
            System.out.println("  Estado: " + reserva.getEstado());
            System.out.println("  Pago: " + (reserva.getPago() != null ? "Pagado" : "Pendiente"));
        }
        System.out.println("\n=================================");
    }

    public void cambiarEstadoReserva() {
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
            return;
        }
        
        System.out.println("\n🔄 === CAMBIAR ESTADO DE RESERVA ===");
        listarReservas();
        
        System.out.print("Número de la reserva: ");
        int numReserva = Integer.parseInt(scanner.nextLine().trim()) - 1;
        
        if (numReserva < 0 || numReserva >= reservas.size()) {
            System.out.println("❌ Reserva no válida.");
            return;
        }
        
        Reserva reserva = reservas.get(numReserva);
        
        System.out.println("Estados disponibles:");
        System.out.println("1. PENDIENTE");
        System.out.println("2. CONFIRMADA");
        System.out.println("3. CANCELADA");
        System.out.println("4. COMPLETADA");
        System.out.print("Seleccione nuevo estado: ");
        
        int opcionEstado = Integer.parseInt(scanner.nextLine().trim());
        EstadoReserva nuevoEstado = null;
        
        switch (opcionEstado) {
            case 1: nuevoEstado = EstadoReserva.PENDIENTE; break;
            case 2: nuevoEstado = EstadoReserva.CONFIRMADA; break;
            case 3: nuevoEstado = EstadoReserva.CANCELADA; break;
            case 4: nuevoEstado = EstadoReserva.COMPLETADA; break;
            default:
                System.out.println("❌ Opción no válida.");
                return;
        }
        
        try {
            reserva.cambiarEstado(nuevoEstado);
            System.out.println("✅ Estado de la reserva actualizado a: " + nuevoEstado);
        } catch (EstadoReservaExcepcion e) {
            System.out.println("❌ Error al cambiar estado: " + e.getMessage());
        }
    }

    // ===== GESTIÓN DE PAGOS =====
    
    public void asociarPago() {
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
            return;
        }
        
        System.out.println("\n💳 === ASOCIAR PAGO A RESERVA ===");
        
        // Mostrar solo reservas sin pago
        List<Reserva> reservasSinPago = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (reserva.getPago() == null) {
                reservasSinPago.add(reserva);
            }
        }
        
        if (reservasSinPago.isEmpty()) {
            System.out.println("Todas las reservas ya tienen pago asociado.");
            return;
        }
        
        System.out.println("Reservas pendientes de pago:");
        for (int i = 0; i < reservasSinPago.size(); i++) {
            Reserva reserva = reservasSinPago.get(i);
            System.out.println((i + 1) + ". " + reserva.getCliente().getNombre() + " - " + reserva.getHabitacion().getCodigo() + " (" + reserva.getPrecioTotal() + "€)");
        }
        
        System.out.print("Número de la reserva: ");
        int numReserva = Integer.parseInt(scanner.nextLine().trim()) - 1;
        
        if (numReserva < 0 || numReserva >= reservasSinPago.size()) {
            System.out.println("❌ Reserva no válida.");
            return;
        }
        
        Reserva reserva = reservasSinPago.get(numReserva);
        
        System.out.println("Métodos de pago:");
        System.out.println("1. Tarjeta");
        System.out.println("2. Efectivo");
        System.out.print("Seleccione método de pago: ");
        
        int metodoPago = Integer.parseInt(scanner.nextLine().trim());
        Pago pago = null;
        
        try {
            if (metodoPago == 1) {
                // Pago con tarjeta
                System.out.print("Titular de la tarjeta: ");
                String titular = scanner.nextLine().trim();
                
                System.out.print("Número de tarjeta: ");
                String numeroTarjeta = scanner.nextLine().trim();
                
                System.out.print("CVV: ");
                String cvv = scanner.nextLine().trim();
                
                System.out.print("Fecha de vencimiento (MM/YY): ");
                String fechaVencimiento = scanner.nextLine().trim();
                
                pago = new PagoTarjeta(reserva.getPrecioTotal(), "Tarjeta", titular, numeroTarjeta, cvv, fechaVencimiento);
                
            } else if (metodoPago == 2) {
                // Pago en efectivo
                System.out.print("Importe recibido en efectivo (€): ");
                BigDecimal importeEfectivo = new BigDecimal(scanner.nextLine().trim());
                
                // Validar que el importe en efectivo sea suficiente
                if (importeEfectivo.compareTo(reserva.getPrecioTotal()) < 0) {
                    System.out.println("❌ Error: El importe en efectivo debe ser mayor o igual al precio total.");
                    System.out.println("   Precio total: " + reserva.getPrecioTotal() + "€");
                    System.out.println("   Importe recibido: " + importeEfectivo + "€");
                    return;
                }
                
                pago = new PagoEfectivo(reserva.getPrecioTotal(), "Efectivo", importeEfectivo);
                
            } else {
                System.out.println("❌ Método de pago no válido.");
                return;
            }
            
            reserva.setPago(pago);
            pago.procesarPago();
            System.out.println("✅ Pago asociado correctamente a la reserva.");
            
        } catch (Exception e) {
            System.out.println("❌ Error al procesar el pago: " + e.getMessage());
        }
    }

    // ===== ANULAR PAGOS =====

    public void anularPago() {
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
            return;
        }
        
        System.out.println("\n❌ === ANULAR PAGO DE RESERVA ===");
        
        // Mostrar solo reservas con pago
        List<Reserva> reservasConPago = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (reserva.getPago() != null) {
                reservasConPago.add(reserva);
            }
        }
        
        if (reservasConPago.isEmpty()) {
            System.out.println("No hay reservas con pago asociado.");
            return;
        }
        
        System.out.println("Reservas con pago asociado:");
        for (int i = 0; i < reservasConPago.size(); i++) {
            Reserva reserva = reservasConPago.get(i);
            Pago pago = reserva.getPago();
            System.out.println((i + 1) + ". " + reserva.getCliente().getNombre() + " - " + reserva.getHabitacion().getCodigo() + 
                            " (" + pago.getMetodoPago() + " - " + pago.getImporte() + "€)");
        }
        
        System.out.print("Número de la reserva: ");
        int numReserva = Integer.parseInt(scanner.nextLine().trim()) - 1;
        
        if (numReserva < 0 || numReserva >= reservasConPago.size()) {
            System.out.println("❌ Reserva no válida.");
            return;
        }
        
        Reserva reserva = reservasConPago.get(numReserva);
        
        // Confirmar anulación
        System.out.println("\n¿Está seguro de que desea anular el pago?");
        System.out.println("Reserva: " + reserva.getCliente().getNombre() + " - " + reserva.getHabitacion().getCodigo());
        System.out.println("Pago: " + reserva.getPago().getMetodoPago() + " - " + reserva.getPago().getImporte() + "€");
        System.out.print("Confirmar (s/n): ");
        
        String confirmacion = scanner.nextLine().trim().toLowerCase();
        
        if (confirmacion.equals("s") || confirmacion.equals("si")) {
            // Anular el pago
            reserva.setPago(null);
            
            // Cambiar estado a PENDIENTE si estaba CONFIRMADA
            if (reserva.getEstado() == EstadoReserva.CONFIRMADA) {
                try {
                    reserva.cambiarEstado(EstadoReserva.PENDIENTE);
                } catch (EstadoReservaExcepcion e) {
                    System.out.println("⚠️ No se pudo cambiar el estado automáticamente: " + e.getMessage());
                }
            }
            
            System.out.println("✅ Pago anulado correctamente.");
            System.out.println("ℹ️ El estado de la reserva se cambió a PENDIENTE.");
        } else {
            System.out.println("ℹ️ Operación cancelada.");
        }
    }

    // ===== DATOS DE PRUEBA =====
    
    public void crearDatosPrueba() {
        System.out.println("\n🧪 === CREANDO DATOS DE PRUEBA ===");
        
        try {
            // Configurar casa rural
            casaRural.setId(UUID.randomUUID());
            casaRural.setNombre("Casa Rural Los Álamos");
            casaRural.setDireccion("Calle Principal 123, Pueblo Viejo");
            casaRural.setTelefono("912345678");
            
            // Crear habitaciones
            Habitacion hab1 = new Habitacion("H001", "Habitación Doble Estándar", 2, new BigDecimal("45.00"));
            Habitacion hab2 = new Habitacion("H002", "Habitación Triple con Terraza", 3, new BigDecimal("65.00"));
            
            casaRural.agregarHabitacion(hab1);
            casaRural.agregarHabitacion(hab2);
            
            // Crear cliente
            Cliente cliente = new Cliente("María García López", "12345678A", "maria.garcia@email.com", "611223344");
            clientes.add(cliente);
            
            // Crear reserva
            LocalDateTime fechaInicio = LocalDateTime.of(2025, 12, 20, 15, 0);
            LocalDateTime fechaFin = LocalDateTime.of(2025, 12, 22, 11, 0);
            BigDecimal precioTotal = hab1.getPrecioNoche().multiply(BigDecimal.valueOf(2));
            
            Reserva reserva = new Reserva(cliente, hab1, fechaInicio, fechaFin, precioTotal, EstadoReserva.CONFIRMADA);
            
            // Asociar pago
            Pago pago = new PagoTarjeta(precioTotal, "Tarjeta", "María García López", "4111111111111111", "123", "12/28");
            reserva.setPago(pago);
            
            reservas.add(reserva);
            
            System.out.println("✅ Datos de prueba creados correctamente:");
            System.out.println("   - Casa Rural: " + casaRural.getNombre());
            System.out.println("   - 2 habitaciones");
            System.out.println("   - 1 cliente registrado");
            System.out.println("   - 1 reserva confirmada con pago");
            
        } catch (Exception e) {
            System.out.println("❌ Error al crear datos de prueba: " + e.getMessage());
        }
    }

    // ===== CLASE INTERNA PARA DATOS DEL SISTEMA =====
    
    private static class DatosSistema {
        private CasaRural casaRural;
        private List<Cliente> clientes;
        private List<Reserva> reservas;
        
        public DatosSistema(CasaRural casaRural, List<Cliente> clientes, List<Reserva> reservas) {
            this.casaRural = casaRural;
            this.clientes = clientes;
            this.reservas = reservas;
        }
    }
}
