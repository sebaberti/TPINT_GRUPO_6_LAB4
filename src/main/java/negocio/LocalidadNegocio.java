package negocio;

import java.util.List;

import entidades.Localidad;

public interface LocalidadNegocio {
	public List<Localidad> listarLocalidadesBuenosAires();
	 public Localidad obtenerLocalidadPorID(int id);
	 public List<Localidad> listarLocalidades();
}
