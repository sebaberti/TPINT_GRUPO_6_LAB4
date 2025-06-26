package negocioImplementacion;

import java.util.List;

import daoImplementacion.ProvinciaDaoImplementacion;
import entidades.Provincia;
import negocio.ProvinciaNegocio;

public class ProvinciaNegocioImplementacion implements ProvinciaNegocio {
	
	private ProvinciaDaoImplementacion provinciaNegocio;
	
	public ProvinciaNegocioImplementacion() { provinciaNegocio = new ProvinciaDaoImplementacion();}
	
	public List<Provincia> listar() {
		return provinciaNegocio.listar();
	}
	public Provincia obtenerProvinciaPorID(int id) {
		return provinciaNegocio.obtenerProvinciaPorID(id);
	}
}
