package negocio;

import java.util.List;
import entidades.Cuota;

public interface CuotaNegocio {
	public Cuota cuotaPorId(int idCuota);
	public  List<Cuota> cuotasPendientesPorCliente(int idCliente);
	public boolean pagarCuota(int idCuota, int idCuenta);
}
