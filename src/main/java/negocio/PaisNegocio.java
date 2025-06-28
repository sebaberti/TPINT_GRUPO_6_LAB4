package negocio;

import java.util.List;

import entidades.Pais;

public interface PaisNegocio {
	public List<Pais> listar();
	public Pais obtenerPaisPorID(int id);
}
