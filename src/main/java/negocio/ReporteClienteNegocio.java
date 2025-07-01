package negocio;

import entidades.ReporteDeCliente;

public interface ReporteClienteNegocio {
	public ReporteDeCliente obtenerReporteDeCliente(String dni);
	public ReporteDeCliente obtenerResumenIngresosEgresosPorDniYAño(String dni, int anio);
}
