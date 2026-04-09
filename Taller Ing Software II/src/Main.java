/**
 *
 * @authores Jhojan Stiven Carabali / Cristhian Farley Garces / Juan Pablo Vazques
 */
/**
 * Esta clase muestra los dos patrones en acción:
 *   1. Prototype + Inventario
 *   2. Builder + Director    
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║     AutoCar — Sistema de Alquiler EV       ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        // ══════════════════════════════════════════════════════════════
        // Patrón Prototype + Inventario (array de objetos)
        // ══════════════════════════════════════════════════════════════
        System.out.println("━━━ ESCENARIO 1: Prototype + Inventario ━━━\n");

        // 1a. Definir plantillas base (prototipos)
        RegistroVehiculos registro = new RegistroVehiculos();
        registro.registrar("auto_urbano",
                new Auto("PLANTILLA-A", 320, "BYD Dolphin", 2024, 4));
        registro.registrar("van_familiar",
                new Van("PLANTILLA-V", 280, "Maxus Mifa 7", 2024, 7));
        registro.registrar("camion_ligero",
                new CamionLigero("PLANTILLA-C", 220, "BYD T3", 2024, 1.5));

        registro.mostrarPlantillas();
        System.out.println();

        // 1b. Clonar plantillas con placas reales → crear vehículos del inventario
        Vehiculo a1 = registro.clonar("auto_urbano",   "ABC-123");
        Vehiculo a2 = registro.clonar("auto_urbano",   "DEF-456");
        Vehiculo v1 = registro.clonar("van_familiar",  "GHI-789");
        Vehiculo c1 = registro.clonar("camion_ligero", "JKL-012");
        Vehiculo a3 = registro.clonar("auto_urbano",   "MNO-345");

        // 1c. Agregar al inventario (ARREGLO de objetos)
        Inventario inventario = new Inventario();
        inventario.agregar(a1);
        inventario.agregar(a2);
        inventario.agregar(v1);
        inventario.agregar(c1);
        inventario.agregar(a3);

        System.out.println("▶ Inventario inicial:");
        inventario.mostrar();

        // 1d. Buscar por placa
        System.out.println("\n▶ Búsqueda por placa 'GHI-789':");
        Vehiculo encontrado = inventario.buscarPorPlaca("GHI-789");
        System.out.println("  " + (encontrado != null ? encontrado : "No encontrado"));

        // 1e. Ordenar por autonomía (mayor → menor)
        inventario.ordenarPorAutonomia();
        System.out.println("\n▶ Inventario ordenado por autonomía (mayor → menor):");
        inventario.mostrar();

        // ══════════════════════════════════════════════════════════════
        // Patrón Builder + Director
        // ══════════════════════════════════════════════════════════════
        System.out.println("\n━━━ ESCENARIO 2: Builder + Director ━━━\n");

        Director director = new Director();

        // 2a. Contrato básico (sin accesorios, 5 días)
        ContratoAlquiler contrato1 = director.construirContratoBasico(
                "Laura Pérez", a1, 5);
        System.out.println("▶ Contrato básico:" + contrato1);

        // 2b. Contrato completo con descuento (35 días > 30 → descuento 10%)
        ContratoAlquiler contrato2 = director.construirContratoCompleto(
                "Carlos Ríos", v1, 35);
        System.out.println("\n▶ Contrato completo con descuento:" + contrato2);

        // 2c. Contrato empresarial
        ContratoAlquiler contrato3 = director.construirContratoEmpresarial(
                "Logística del Valle S.A.", c1, 45);
        System.out.println("\n▶ Contrato empresarial:" + contrato3);

        // 2d. Uso directo del Builder (sin Director) — contrato personalizado
        ContratoAlquiler contratoCustom = new ContratoAlquilerBuilder()
                .setCliente("Andrés Mora")
                .setVehiculo(a2)
                .setPlan("Semanal")
                .setDuracionDias(14)
                .addGPS()
                .addCargadorPortatil()
                .build();
        System.out.println("\n▶ Contrato personalizado (Builder directo):" + contratoCustom);

        // 2e. Demostración de validación: contrato sin vehículo → excepción
        System.out.println("\n▶ Prueba de validación (contrato incompleto):");
        try {
            new ContratoAlquilerBuilder()
                    .setCliente("Test")
                    .setPlan("Diario")
                    .setDuracionDias(3)
                    .build(); // falta vehiculo → debe lanzar excepción
        } catch (IllegalStateException e) {
            System.out.println("  ✔ Excepción capturada correctamente: " + e.getMessage());
        }

        System.out.println("\n✅ Sistema AutoCar ejecutado correctamente.");
    }
}