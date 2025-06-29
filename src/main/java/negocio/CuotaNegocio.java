package negocio;

import java.util.List;
import entidades.Cuota;

public interface CuotaNegocio {
	public Cuota cuotaPorId(int idCuota);
	public boolean pagarCuota(int idCuota, int idCuenta);
	List<Cuota> cuotasPorClienteYEstado(int idCliente, boolean estado);
	List<Cuota> cuotasPorCliente(int idCliente);
}
