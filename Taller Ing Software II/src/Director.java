/**
 *
 * @author Jhojan Stiven Carabali / Cristhian Farley Garces / Juan Pablo Vazques
 */
/**
 * En esta clase esta el Director del patrón Builder.
 * Define recetas predefinidas de contratos para los casos de uso más comunes,
 * ocultando la secuencia de llamadas al Builder.
 *
 * El Director es OPCIONAL en el patrón Builder: el cliente puede usar
 * el Builder directamente si necesita un contrato personalizado.
 */
public class Director {

    /**
     * Contrato básico: sin accesorios, plan diario.
     */
    public ContratoAlquiler construirContratoBasico(
            String cliente, Vehiculo vehiculo, int dias) {

        return new ContratoAlquilerBuilder()
                .setCliente(cliente)
                .setVehiculo(vehiculo)
                .setPlan("Diario")
                .setDuracionDias(dias)
                .build();
    }

    /**
     * Contrato completo: GPS + seguro + cargador, plan mensual.
     * Aplica descuento automáticamente si dias > 30.
     */
    public ContratoAlquiler construirContratoCompleto(
            String cliente, Vehiculo vehiculo, int dias) {

        return new ContratoAlquilerBuilder()
                .setCliente(cliente)
                .setVehiculo(vehiculo)
                .setPlan("Mensual")
                .setDuracionDias(dias)
                .addGPS()
                .addSeguro()
                .addCargadorPortatil()
                .build();
    }

    /**
     * Contrato empresarial: seguro obligatorio, plan semanal o mensual.
     */
    public ContratoAlquiler construirContratoEmpresarial(
            String empresa, Vehiculo vehiculo, int dias) {

        return new ContratoAlquilerBuilder()
                .setCliente("(Empresa) " + empresa)
                .setVehiculo(vehiculo)
                .setPlan(dias >= 30 ? "Mensual" : "Semanal")
                .setDuracionDias(dias)
                .addSeguro()
                .build();
    }
}