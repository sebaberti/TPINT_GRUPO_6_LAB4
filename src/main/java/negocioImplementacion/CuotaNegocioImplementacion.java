package negocioImplementacion;

import java.util.List;
import daoImplementacion.CuotaDaoImplementacion;
import entidades.Cuota;
import negocio.CuotaNegocio;

public class CuotaNegocioImplementacion implements CuotaNegocio{

	@Override
	public Cuota cuotaPorId(int idCuota) {
	    return new CuotaDaoImplementacion().cuotaPorId(idCuota);
	}
	
	@Override
	public List<Cuota> cuotasPorClienteYEstado(int idCliente, boolean estado) {
		CuotaDaoImplementacion tsi= new CuotaDaoImplementacion();
		return tsi.cuotasPorClienteYEstado(idCliente, estado);
	}
	
	@Override
	public List<Cuota> cuotasPorCliente(int idCliente) {
		CuotaDaoImplementacion tsi= new CuotaDaoImplementacion();
		return tsi.cuotasPorCliente(idCliente);
	}
	
	@Override
	public boolean pagarCuota(int idCuota, int idCuenta) {
		CuotaDaoImplementacion tsi= new CuotaDaoImplementacion();
		return tsi.pagarCuota(idCuota, idCuenta);
	}

	@Override
	public Cuota obtenerCuotaPorId(int idCuota) {
		CuotaDaoImplementacion tsi= new CuotaDaoImplementacion();
		return tsi.obtenerCuotaPorId(idCuota);
	}
}
