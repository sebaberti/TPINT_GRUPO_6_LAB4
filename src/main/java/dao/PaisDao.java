package dao;

import java.util.List;
import entidades.Pais;

public interface PaisDao {
	public List<Pais> listar();
	public Pais obtenerPaisPorID(int id);
}
