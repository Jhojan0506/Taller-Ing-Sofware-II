/**
 *
 * @author Jhojan Stiven Carabali / Cristhian Farley Garces / Juan Pablo Vazques
 */
/**
 * Esta Clase abstracta es la base para todos los vehículos del sistema AutoCar.
 * Implementa el patrón Prototype: cada subclase sobrescribe clonar()
 * para devolver una copia de sí misma con nuevos datos de registro.
 */
public abstract class Vehiculo implements Cloneable {

    protected String placa;
    protected int autonomiaKm;   // criterio de ordenamiento
    protected String tipo;       // "Auto", "Van", "CamionLigero"
    protected String modelo;
    protected int anio;

    public Vehiculo(String placa, int autonomiaKm, String tipo, String modelo, int anio) {
        this.placa       = placa;
        this.autonomiaKm = autonomiaKm;
        this.tipo        = tipo;
        this.modelo      = modelo;
        this.anio        = anio;
    }

    /** Método Prototype: crea una copia del vehículo con nueva placa. */
    public abstract Vehiculo clonar(String nuevaPlaca);

    // ── getters ──────────────────────────────────────────────────────────
    public String getPlaca()       { return placa; }
    public int    getAutonomia()   { return autonomiaKm; }
    public String getTipo()        { return tipo; }
    public String getModelo()      { return modelo; }
    public int    getAnio()        { return anio; }

    @Override
    public String toString() {
        return String.format("[%s] %s %s (%d) — autonomía: %d km",
                tipo, modelo, placa, anio, autonomiaKm);
    }
}