package dao;

import java.util.List;

import entidades.Cuota;

public interface CuotaDao {

	List<Cuota> cuotasPendientesPorCliente(int idCliente);

}
