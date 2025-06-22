package dao;

import java.util.List;

import entidades.Prestamo;

public interface PrestamoDao {
	public boolean insertar(Prestamo prestamo);
	public boolean modificar(Prestamo prestamo);
	public boolean bajaLogica(int id);
	public List<Prestamo> listarPrestamos();
}
