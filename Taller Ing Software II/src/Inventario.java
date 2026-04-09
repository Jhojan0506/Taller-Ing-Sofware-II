/**
 *
 * @author Jhojan Stiven Carabali / Cristhian Farley Garces / Juan Pablo Vazques
 */
/**
 * Este es el Inventario de vehículos basado en ARREGLO DE OBJETOS (no lista dinámica).
 *
 * Operaciones implementadas:
 *   - agregar(Vehiculo)         → O(1) amortizado
 *   - buscarPorPlaca(String)    → O(n) búsqueda lineal
 *   - ordenarPorAutonomia()     → O(n²) burbuja (simple y legible para el taller)
 *   - mostrar()                 → imprime todos los vehículos
 */
public class Inventario {

    private static final int CAPACIDAD_MAX = 100;

    // Arreglo estático de objetos — requisito explícito del taller
    private final Vehiculo[] vehiculos = new Vehiculo[CAPACIDAD_MAX];
    private int cantidad = 0; // número de elementos activos

    /**
     * Agrega un vehículo al inventario.
     * @throws IllegalStateException si el inventario está lleno.
     * @throws IllegalArgumentException si la placa ya existe.
     */
    public void agregar(Vehiculo v) {
        if (cantidad == CAPACIDAD_MAX) {
            throw new IllegalStateException("Inventario lleno (máx " + CAPACIDAD_MAX + ").");
        }
        if (buscarPorPlaca(v.getPlaca()) != null) {
            throw new IllegalArgumentException("Ya existe un vehículo con placa: " + v.getPlaca());
        }
        vehiculos[cantidad++] = v;
    }

    /**
     * Busca un vehículo por placa (insensible a mayúsculas).
     * @return el vehículo encontrado, o null si no existe.
     */
    public Vehiculo buscarPorPlaca(String placa) {
        for (int i = 0; i < cantidad; i++) {
            if (vehiculos[i].getPlaca().equalsIgnoreCase(placa)) {
                return vehiculos[i];
            }
        }
        return null;
    }

    /**
     * Ordena el arreglo por autonomía de MAYOR a MENOR usando burbuja.
     * Modifica el arreglo in-place.
     */
    public void ordenarPorAutonomia() {
        for (int i = 0; i < cantidad - 1; i++) {
            for (int j = 0; j < cantidad - 1 - i; j++) {
                if (vehiculos[j].getAutonomia() < vehiculos[j + 1].getAutonomia()) {
                    Vehiculo tmp      = vehiculos[j];
                    vehiculos[j]      = vehiculos[j + 1];
                    vehiculos[j + 1]  = tmp;
                }
            }
        }
    }

    /** Imprime todos los vehículos en el orden actual del arreglo. */
    public void mostrar() {
        System.out.println("=== Inventario AutoCar (" + cantidad + " vehículos) ===");
        for (int i = 0; i < cantidad; i++) {
            System.out.println("  " + (i + 1) + ". " + vehiculos[i]);
        }
        if (cantidad == 0) System.out.println("  (vacío)");
    }

    public int getCantidad() { return cantidad; }
}