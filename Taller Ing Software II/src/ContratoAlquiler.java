/**
 *
 * @author Jhojan Stiven Carabali / Cristhian Farley Garces / Juan Pablo Vazques
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Aqui se hace el Producto final del patrón Builder.
 * Representa un contrato de alquiler completamente construido.
 * Los atributos son de solo lectura (no hay setters) para garantizar
 * inmutabilidad una vez construido.
 */
public class ContratoAlquiler {

    private final String        cliente;
    private final Vehiculo      vehiculo;
    private final String        plan;        // "Diario", "Semanal", "Mensual"
    private final int           duracionDias;
    private final List<String>  accesorios;
    private final double        precioFinal;
    private final boolean       descuentoAplicado;

    // El constructor es package-private: sólo el Builder puede instanciarlo.
    ContratoAlquiler(String cliente, Vehiculo vehiculo, String plan,
                     int duracionDias, List<String> accesorios,
                     double precioFinal, boolean descuentoAplicado) {
        this.cliente           = cliente;
        this.vehiculo          = vehiculo;
        this.plan              = plan;
        this.duracionDias      = duracionDias;
        this.accesorios        = new ArrayList<>(accesorios);
        this.precioFinal       = precioFinal;
        this.descuentoAplicado = descuentoAplicado;
    }

    //getters 
    public String       getCliente()            { return cliente; }
    public Vehiculo     getVehiculo()           { return vehiculo; }
    public String       getPlan()               { return plan; }
    public int          getDuracionDias()       { return duracionDias; }
    public List<String> getAccesorios()         { return accesorios; }
    public double       getPrecioFinal()        { return precioFinal; }
    public boolean      isDescuentoAplicado()   { return descuentoAplicado; }

    @Override
    public String toString() {
        return "\n╔═══════════════════════════════════════════╗"
             + "\n║        CONTRATO DE ALQUILER AutoCar       ║"
             + "\n╠═══════════════════════════════════════════╣"
             + "\n  Cliente    : " + cliente
             + "\n  Vehículo   : " + vehiculo
             + "\n  Plan       : " + plan
             + "\n  Duración   : " + duracionDias + " días"
             + "\n  Accesorios : " + (accesorios.isEmpty() ? "ninguno" : accesorios)
             + "\n  Descuento  : " + (descuentoAplicado ? "10% (≥30 días)" : "no")
             + "\n  Precio     : $" + String.format("%.2f", precioFinal)
             + "\n╚═══════════════════════════════════════════╝";
    }
}