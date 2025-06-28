package negocioImplementacion;

import java.util.List;

import daoImplementacion.PaisDaoImplementacion;
import entidades.Pais;
import negocio.PaisNegocio;

public class PaisNegocioImplementacion implements PaisNegocio {

	@Override
	public List<Pais> listar() {
		PaisDaoImplementacion pdi= new PaisDaoImplementacion();
		return pdi.listar();
	}
	
	@Override
	public Pais obtenerPaisPorID(int id) {
		PaisDaoImplementacion pdi= new PaisDaoImplementacion();
		return pdi.obtenerPaisPorID(id) ;
	}
}
