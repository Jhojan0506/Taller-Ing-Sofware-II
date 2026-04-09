/**
 *
 * @author Jhojan Stiven Carabali / Cristhian Farley Garces / Juan Pablo Vazques
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Aqui tenemos el Builder concreto para construir ContratoAlquiler paso a paso.
 *
 * Reglas de negocio integradas en build():
 *   - cliente, vehiculo y plan son OBLIGATORIOS.
 *   - Si duracionDias > 30 se aplica automáticamente un 10% de descuento.
 *   - Precio base: $5 por día * autonomía (km) / 100 (tarifa simplificada).
 *
 * Ventaja del Builder: nunca se genera un contrato en estado inválido,
 * ya que todas las validaciones ocurren en build() antes de crear el objeto.
 */
public class ContratoAlquilerBuilder {

    // Campos obligatorios
    private String   cliente;
    private Vehiculo vehiculo;
    private String   plan;
    private int      duracionDias;

    // Campos opcionales (accesorios)
    private final List<String> accesorios = new ArrayList<>();

    // Precios de accesorios por día
    private static final double PRECIO_GPS      = 3.0;
    private static final double PRECIO_SEGURO   = 8.0;
    private static final double PRECIO_CARGADOR = 2.5;

    //Métodos de configuración (devuelven this para flujo encadenado) 

    public ContratoAlquilerBuilder setCliente(String cliente) {
        this.cliente = cliente;
        return this;
    }

    public ContratoAlquilerBuilder setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
        return this;
    }

    public ContratoAlquilerBuilder setPlan(String plan) {
        this.plan = plan;
        return this;
    }

    public ContratoAlquilerBuilder setDuracionDias(int dias) {
        if (dias <= 0) throw new IllegalArgumentException("La duración debe ser mayor a 0 días.");
        this.duracionDias = dias;
        return this;
    }

    public ContratoAlquilerBuilder addGPS() {
        accesorios.add("GPS ($" + PRECIO_GPS + "/día)");
        return this;
    }

    public ContratoAlquilerBuilder addSeguro() {
        accesorios.add("Seguro ($" + PRECIO_SEGURO + "/día)");
        return this;
    }

    public ContratoAlquilerBuilder addCargadorPortatil() {
        accesorios.add("Cargador portátil ($" + PRECIO_CARGADOR + "/día)");
        return this;
    }

    /**
     * Construye y valida el contrato.
     * @throws IllegalStateException si faltan campos obligatorios.
     */
    public ContratoAlquiler build() {
        //Validaciones 
        if (cliente == null || cliente.trim().isEmpty()) {
            throw new IllegalStateException("El cliente es obligatorio.");
        }
        if (vehiculo == null) {
            throw new IllegalStateException("El vehículo es obligatorio.");
        }
        if (plan == null || plan.trim().isEmpty()) {
            throw new IllegalStateException("El plan de alquiler es obligatorio.");
        }
        if (duracionDias <= 0) {
            throw new IllegalStateException("La duración debe ser mayor a 0 días.");
        }

        //Cálculo de precio
        double tarifaBase = 5.0 * (vehiculo.getAutonomia() / 100.0);

        double extras = 0;
        for (String acc : accesorios) {
            if (acc.contains("GPS"))      extras += PRECIO_GPS;
            if (acc.contains("Seguro"))   extras += PRECIO_SEGURO;
            if (acc.contains("Cargador")) extras += PRECIO_CARGADOR;
        }

        double precioTotal = (tarifaBase + extras) * duracionDias;

        // Regla: descuento si duración > 30 días 
        boolean descuento = duracionDias > 30;
        if (descuento) {
            precioTotal *= 0.90; // 10% de descuento
        }

        return new ContratoAlquiler(
                cliente, vehiculo, plan,
                duracionDias, accesorios,
                precioTotal, descuento
        );
    }
}