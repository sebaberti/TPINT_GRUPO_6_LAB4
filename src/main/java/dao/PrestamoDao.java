package dao;

import java.util.List;

import entidades.Cliente;
import entidades.Prestamo;

public interface PrestamoDao {
	public boolean ejecutarSPsolicitarPrestamo(Prestamo prestamo, Cliente cliente);
	/*public boolean modificar(Prestamo prestamo);
	public boolean bajaLogica(int id);
	public List<Prestamo> listarPrestamos();*/
}
