/**
 *
 * @author Jhojan Stiven Carabali / Cristhian Farley Garces / Juan Pablo Vazques
 */
/**
 * Esta es una Subclase concreta: Van eléctrica.
 */
public class Van extends Vehiculo {

    private int capacidadPasajeros;

    public Van(String placa, int autonomiaKm, String modelo, int anio, int capacidadPasajeros) {
        super(placa, autonomiaKm, "Van", modelo, anio);
        this.capacidadPasajeros = capacidadPasajeros;
    }

    private Van(Van original, String nuevaPlaca) {
        super(nuevaPlaca, original.autonomiaKm, original.tipo,
              original.modelo, original.anio);
        this.capacidadPasajeros = original.capacidadPasajeros;
    }

    @Override
    public Vehiculo clonar(String nuevaPlaca) {
        return new Van(this, nuevaPlaca);
    }

    @Override
    public String toString() {
        return super.toString() + " | pasajeros: " + capacidadPasajeros;
    }
}