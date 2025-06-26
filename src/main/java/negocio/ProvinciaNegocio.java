package negocio;

import java.util.List;

import entidades.Provincia;

public interface ProvinciaNegocio {
	public List<Provincia> listar();
	public Provincia obtenerProvinciaPorID(int id);
}
