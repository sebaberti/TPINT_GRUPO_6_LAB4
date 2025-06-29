package dao;

import java.util.List;

import entidades.Cuota;

public interface CuotaDao {
	public Cuota cuotaPorId(int idCuota);
	public List<Cuota> cuotasPendientesPorCliente(int idCliente, boolean soloPendientes);
	public boolean pagarCuota(int idCuota, int idCuenta);	
}
