/**
 *
 * @author Jhojan Stiven Carabali / Cristhian Farley Garces / Juan Pablo Vazques
 */
/**
 * Esta es una Subclase concreta: Camión ligero eléctrico.
 */
public class CamionLigero extends Vehiculo {

    private double cargaMaxToneladas;

    public CamionLigero(String placa, int autonomiaKm, String modelo, int anio, double cargaMaxToneladas) {
        super(placa, autonomiaKm, "CamionLigero", modelo, anio);
        this.cargaMaxToneladas = cargaMaxToneladas;
    }

    private CamionLigero(CamionLigero original, String nuevaPlaca) {
        super(nuevaPlaca, original.autonomiaKm, original.tipo,
              original.modelo, original.anio);
        this.cargaMaxToneladas = original.cargaMaxToneladas;
    }

    @Override
    public Vehiculo clonar(String nuevaPlaca) {
        return new CamionLigero(this, nuevaPlaca);
    }

    @Override
    public String toString() {
        return super.toString() + " | carga máx: " + cargaMaxToneladas + " t";
    }
}