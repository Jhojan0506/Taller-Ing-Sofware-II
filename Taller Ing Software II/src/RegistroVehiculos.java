/**
 *
 * @author Jhojan Stiven Carabali / Cristhian Farley Garces / Juan Pablo Vazques
 */
import java.util.HashMap;
import java.util.Map;

/**
 * Aqui se hace el Registro de plantillas Prototype.
 * Guarda un ejemplar base de cada tipo de vehículo y lo clona
 * cuando se solicita uno nuevo, asignando la placa indicada.
 *
 * Ventaja: agregar un nuevo tipo sólo requiere registrar una plantilla,
 * sin modificar lógica existente (Principio Abierto/Cerrado).
 */
public class RegistroVehiculos {

    private final Map<String, Vehiculo> plantillas = new HashMap<>();

    /** Registra un prototipo bajo una clave (p. ej. "auto_urbano"). */
    public void registrar(String clave, Vehiculo prototipo) {
        plantillas.put(clave, prototipo);
    }

    /**
     * Clona el prototipo indicado por clave y le asigna la nueva placa.
     * @throws IllegalArgumentException si la clave no existe.
     */
    public Vehiculo clonar(String clave, String nuevaPlaca) {
        Vehiculo prototipo = plantillas.get(clave);
        if (prototipo == null) {
            throw new IllegalArgumentException("Tipo de vehículo no registrado: " + clave);
        }
        return prototipo.clonar(nuevaPlaca);
    }

    /** Lista todas las claves registradas (útil para depuración). */
    public void mostrarPlantillas() {
        System.out.println("=== Plantillas registradas ===");
        plantillas.forEach((k, v) ->
                System.out.println("  " + k + " → " + v));
    }
}