package negocio;

import java.util.List;
import entidades.Cuota;

public interface CuotaNegocio {
	public Cuota cuotaPorId(int idCuota);
	public boolean pagarCuota(int idCuota, int idCuenta);
	public List<Cuota> cuotasPorClienteYEstado(int idCliente, boolean estado);
	public List<Cuota> cuotasPorCliente(int idCliente);
	public Cuota obtenerCuotaPorId(int idCuota);
}
