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
	public List<Cuota> cuotasPendientesPorCliente(int idCliente) {
		CuotaDaoImplementacion tsi= new CuotaDaoImplementacion();
		return tsi.cuotasPendientesPorCliente(idCliente);
	}

	@Override
	public boolean pagarCuota(int idCuota, int idCuenta) {
		CuotaDaoImplementacion tsi= new CuotaDaoImplementacion();
		return tsi.pagarCuota(idCuota, idCuenta);
	}

}
