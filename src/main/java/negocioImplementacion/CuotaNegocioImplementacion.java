package negocioImplementacion;

import java.util.List;
import daoImplementacion.CuotaDaoImplementacion;
import entidades.Cuota;
import negocio.CuotaNegocio;

public class CuotaNegocioImplementacion implements CuotaNegocio{

	private CuotaDaoImplementacion tsi= new CuotaDaoImplementacion();
	
	@Override
	public Cuota cuotaPorId(int idCuota) {
	    return tsi.cuotaPorId(idCuota);
	}
	
	@Override
	public List<Cuota> cuotasPorClienteYEstado(int idCliente, boolean estado) {
		return tsi.cuotasPorClienteYEstado(idCliente, estado);
	}
	
	@Override
	public List<Cuota> cuotasPorCliente(int idCliente) {
		return tsi.cuotasPorCliente(idCliente);
	}
	
	@Override
	public boolean pagarCuota(int idCuota, int idCuenta) {
		return tsi.pagarCuota(idCuota, idCuenta);
	}

	@Override
	public Cuota obtenerCuotaPorId(int idCuota) {		
		return tsi.obtenerCuotaPorId(idCuota);
	}

	@Override
	public boolean adeudaCuotaPrevia(int idPrestamo, int nroCuotaActual) {
		return tsi.adeudaCuotaPrevia(idPrestamo, nroCuotaActual);
	}
}
