package negocio;

import entidades.Cliente;
import entidades.Prestamo;

public interface PrestamoNegocio {

	public boolean solicitarPrestamo(Prestamo prestamo, Cliente cliente);
}
