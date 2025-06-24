package negocioImplementacion;

import daoImplementacion.PrestamoDaoImplementacion;
import entidades.Cliente;
import entidades.Prestamo;
import negocio.PrestamoNegocio;

public class PrestamoNegocioImplementacion implements PrestamoNegocio{

	@Override
	public boolean solicitarPrestamo(Prestamo prestamo, Cliente cliente) {
		PrestamoDaoImplementacion pdi= new PrestamoDaoImplementacion();
		return pdi.ejecutarSPsolicitarPrestamo(prestamo, cliente);
	}

}
