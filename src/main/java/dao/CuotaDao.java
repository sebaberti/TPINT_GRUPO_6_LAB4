package dao;

import java.util.List;

import entidades.Cuota;

public interface CuotaDao {
	public Cuota cuotaPorId(int idCuota);
	public boolean pagarCuota(int idCuota, int idCuenta);
	public List<Cuota> cuotasPorClienteYEstado(int idCliente, boolean estado);
	public List<Cuota> cuotasPorCliente(int idCliente);
	public Cuota obtenerCuotaPorId(int idCuota);	
	public int contarCuotasPagas();
	public int contarCuotasImpagas();
	public double sumarSaldosImpagos();
	public boolean adeudaCuotaPrevia(int idPrestamo, int nroCuotaActual);
}
