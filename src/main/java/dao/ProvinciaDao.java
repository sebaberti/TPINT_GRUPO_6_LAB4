package dao;

import java.util.List;

import entidades.Provincia;

public interface ProvinciaDao {

	public List<Provincia> listar();
	public Provincia obtenerProvinciaPorID(int id);
}
