package dao;

import entidades.ReporteDeCliente;

public interface ReporteDeClienteDao {
	public ReporteDeCliente obtenerReporteDeCliente(String dni);
	public ReporteDeCliente obtenerResumenIngresosEgresosPorDniYAnio(String dni, int anio);
}
