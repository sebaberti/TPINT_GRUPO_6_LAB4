package negocioImplementacion;

import java.math.BigDecimal;
import java.util.List;

import daoImplementacion.CuotaDaoImplementacion;
import entidades.Cuota;
import negocio.CuotaNegocio;

public class CuotaNegocioImplementacion implements CuotaNegocio{

	@Override
	public List<Cuota> cuotasPendientesPorCliente(int idCliente) {
		CuotaDaoImplementacion tsi= new CuotaDaoImplementacion();
		return tsi.cuotasPendientesPorCliente(idCliente);
	}

	@Override
	public boolean PagarCuota(int NroCuenta, int idCuota, BigDecimal monto, String detalle, int idCliente) {
		// TODO Auto-generated method stub
		return false;
	}

}
