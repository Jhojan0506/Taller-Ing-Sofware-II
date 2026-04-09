/**
 *
 * @author Jhojan Stiven Carabali / Cristhian Farley Garces / Juan Pablo Vazques
 */
/**
 * Esta es una Subclase concreta: Auto eléctrico/híbrido.
 * Patrón Prototype: clonar() usa el constructor de copia para
 * replicar todos los atributos y sólo cambia la placa.
 */
public class Auto extends Vehiculo {

    private int numPuertas;

    public Auto(String placa, int autonomiaKm, String modelo, int anio, int numPuertas) {
        super(placa, autonomiaKm, "Auto", modelo, anio);
        this.numPuertas = numPuertas;
    }

    /** Constructor de copia interno (usado por clonar). */
    private Auto(Auto original, String nuevaPlaca) {
        super(nuevaPlaca, original.autonomiaKm, original.tipo,
              original.modelo, original.anio);
        this.numPuertas = original.numPuertas;
    }

    @Override
    public Vehiculo clonar(String nuevaPlaca) {
        return new Auto(this, nuevaPlaca);
    }

    @Override
    public String toString() {
        return super.toString() + " | puertas: " + numPuertas;
    }
}