package negocioImplementacion;

import java.util.List;

import daoImplementacion.LocalidadDaoImplementacion;
import entidades.Localidad;
import negocio.LocalidadNegocio;

public class LocalidadNegocioImplementacion implements LocalidadNegocio{
	
	private LocalidadDaoImplementacion localidadNegocio;
	
	public LocalidadNegocioImplementacion() {
		localidadNegocio = new LocalidadDaoImplementacion();
	}
	
	public List<Localidad> listarLocalidades() {
		return localidadNegocio.listarLocalidades();
	}
	
	public List<Localidad> listarLocalidadesBuenosAires() {
		return localidadNegocio.listarLocalidadesBuenosAires();
	}
	 public Localidad obtenerLocalidadPorID(int id) {
		 return localidadNegocio.obtenerLocalidadPorID(id);
	 }
}
