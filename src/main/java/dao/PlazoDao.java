package dao;

import java.util.List;

import entidades.Plazo;

public interface PlazoDao {
	public List<Plazo> listar();
	public Plazo obtenerPlazoPorId(int id);
}
