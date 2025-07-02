package dao;

import java.math.BigDecimal;
import java.util.List;
import entidades.Prestamo;

public interface PrestamoDao {
	public boolean ejecutarSPsolicitarPrestamo(int idCliente, int idCuenta, BigDecimal monto, int idPlazo);
	public boolean autorizarPrestamo(int idPrestamo);
	public boolean rechazarPrestamo(int idPrestamo);
	public BigDecimal obtenerCuotaDesdeBD(BigDecimal monto, int idPlazo);
	public List<Prestamo> listar();
	public List<Prestamo> listarPrestamosPorCliente(int idCliente);
}
