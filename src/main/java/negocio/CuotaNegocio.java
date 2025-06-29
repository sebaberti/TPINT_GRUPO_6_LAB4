package negocio;

import java.math.BigDecimal;
import java.util.List;

import entidades.Cuota;

public interface CuotaNegocio {
	public  List<Cuota> cuotasPendientesPorCliente(int idCliente);
	public boolean PagarCuota(int NroCuenta,int idCuota, BigDecimal monto, String detalle, int idCliente);
}
