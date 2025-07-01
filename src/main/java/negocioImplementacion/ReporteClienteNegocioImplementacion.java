package negocioImplementacion;

import daoImplementacion.ReporteDeClienteDaoImplementacion;
import entidades.ReporteDeCliente;
import negocio.ReporteClienteNegocio;

public class ReporteClienteNegocioImplementacion implements ReporteClienteNegocio{

	@Override
	public ReporteDeCliente obtenerReporteDeCliente(String dni) {
		ReporteDeClienteDaoImplementacion rep= new ReporteDeClienteDaoImplementacion();
		return rep.obtenerReporteDeCliente(dni);
	}

	@Override
	public ReporteDeCliente obtenerResumenIngresosEgresosPorDniYAño(String dni, int anio) {
		ReporteDeClienteDaoImplementacion repCliente= new ReporteDeClienteDaoImplementacion();
		return repCliente.obtenerResumenIngresosEgresosPorDniYAnio(dni, anio);
	}
	
}
