package com.casarural;

import com.casarural.gestioncasa.GestionCasaRuralService;
import java.util.Scanner;

public class Main {
    private static GestionCasaRuralService servicio;
    private static Scanner scanner;

    public static void main(String[] args) {
        servicio = new GestionCasaRuralService();
        scanner = new Scanner(System.in);
        
        // Intentar cargar datos existentes
        servicio.cargar();
        
        boolean salir = false;
        
        while (!salir) {
            mostrarMenuPrincipal();
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    gestionarHabitaciones();
                    break;
                case 2:
                    gestionarClientes();
                    break;
                case 3:
                    gestionarReservas();
                    break;
                case 4:
                    gestionarPagos();
                    break;
                case 5:
                    servicio.guardar();
                    break;
                case 6:
                    servicio.crearDatosPrueba();
                    break;
                case 7:
                    mostrarEstadisticas();
                    break;
                case 8:
                    salir = true;
                    System.out.println("👋 ¡Hasta luego!");
                    break;
                default:
                    System.out.println("❌ Opción no válida. Intente nuevamente.");
            }
            
            if (!salir) {
                System.out.println("\nPresione Enter para continuar...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("🏠 SISTEMA DE GESTIÓN - CASA RURAL");
        System.out.println("=".repeat(50));
        System.out.println("1. 🏠 Gestionar Habitaciones");
        System.out.println("2. 👤 Gestionar Clientes");
        System.out.println("3. 📅 Gestionar Reservas");
        System.out.println("4. 💳 Gestionar Pagos");
        System.out.println("5. 💾 Guardar Datos");
        System.out.println("6. 🧪 Crear Datos de Prueba");
        System.out.println("7. 📊 Ver Estadísticas");
        System.out.println("8. 🚪 Salir");
        System.out.println("=".repeat(50));
        System.out.print("Seleccione una opción: ");
    }

    private static void gestionarHabitaciones() {
        boolean volver = false;
        
        while (!volver) {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("🏠 GESTIÓN DE HABITACIONES");
            System.out.println("=".repeat(40));
            System.out.println("1. ➕ Dar de alta habitación");
            System.out.println("2. 📋 Listar habitaciones");
            System.out.println("3. 🔙 Volver al menú principal");
            System.out.println("=".repeat(40));
            System.out.print("Seleccione una opción: ");
            
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    servicio.darAltaHabitacion();
                    break;
                case 2:
                    servicio.listarHabitaciones();
                    break;
                case 3:
                    volver = true;
                    break;
                default:
                    System.out.println("❌ Opción no válida.");
            }
        }
    }

    private static void gestionarClientes() {
        boolean volver = false;
        
        while (!volver) {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("👤 GESTIÓN DE CLIENTES");
            System.out.println("=".repeat(40));
            System.out.println("1. ➕ Registrar nuevo cliente");
            System.out.println("2. 📋 Listar clientes");
            System.out.println("3. 🗑️ Eliminar cliente");
            System.out.println("4. 🔙 Volver al menú principal");
            System.out.println("=".repeat(40));
            System.out.print("Seleccione una opción: ");
            
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    servicio.registrarCliente();
                    break;
                case 2:
                    servicio.listarClientes();
                    break;
                case 3:
                    servicio.eliminarCliente();
                    break;
                case 4:
                    volver = true;
                    break;
                default:
                    System.out.println("❌ Opción no válida.");
            }
        }
    }

    private static void gestionarReservas() {
        boolean volver = false;
        
        while (!volver) {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("📅 GESTIÓN DE RESERVAS");
            System.out.println("=".repeat(40));
            System.out.println("1. ➕ Crear nueva reserva");
            System.out.println("2. 📋 Listar reservas");
            System.out.println("3. 🔄 Cambiar estado de reserva");
            System.out.println("4. 🔙 Volver al menú principal");
            System.out.println("=".repeat(40));
            System.out.print("Seleccione una opción: ");
            
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    servicio.crearReserva();
                    break;
                case 2:
                    servicio.listarReservas();
                    break;
                case 3:
                    servicio.cambiarEstadoReserva();
                    break;
                case 4:
                    volver = true;
                    break;
                default:
                    System.out.println("❌ Opción no válida.");
            }
        }
    }

    private static void gestionarPagos() {
        boolean volver = false;
        
        while (!volver) {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("💳 GESTIÓN DE PAGOS");
            System.out.println("=".repeat(40));
            System.out.println("1. 💳 Asociar pago a reserva");
            System.out.println("2. ❌ Anular pago de reserva");
            System.out.println("3. 🔙 Volver al menú principal");
            System.out.println("=".repeat(40));
            System.out.print("Seleccione una opción: ");
            
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    servicio.asociarPago();
                    break;
                case 2:
                    servicio.anularPago();
                    break;
                case 3:
                    volver = true;
                    break;
                default:
                    System.out.println("❌ Opción no válida.");
            }
        }
    }

    private static void mostrarEstadisticas() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("📊 ESTADÍSTICAS DEL SISTEMA");
        System.out.println("=".repeat(40));
        
        // Aquí podríamos agregar estadísticas más detalladas en el futuro
        System.out.println("📈 Información general del sistema:");
        System.out.println("   - Sistema operativo: " + System.getProperty("os.name"));
        System.out.println("   - Versión de Java: " + System.getProperty("java.version"));
        System.out.println("   - Archivo de datos: src/main/resources/data/gestion-cr.json");
        System.out.println("   - Estado: Sistema funcionando correctamente ✅");
        
        System.out.println("\n💡 Funcionalidades implementadas:");
        System.out.println("   ✅ Gestión completa de habitaciones");
        System.out.println("   ✅ Gestión completa de clientes");
        System.out.println("   ✅ Gestión completa de reservas");
        System.out.println("   ✅ Sistema de pagos (tarjeta y efectivo)");
        System.out.println("   ✅ Persistencia JSON con GSON");
        System.out.println("   ✅ Validaciones de negocio");
        System.out.println("   ✅ Manejo de excepciones");
    }

    private static int leerOpcion() {
        try {
            int opcion = Integer.parseInt(scanner.nextLine().trim());
            return opcion;
        } catch (NumberFormatException e) {
            return -1; // Opción inválida
        }
    }

}
