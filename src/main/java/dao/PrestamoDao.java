package dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import entidades.Cliente;
import entidades.Prestamo;

public interface PrestamoDao {
	public boolean ejecutarSPsolicitarPrestamo(int idCliente, int idCuenta, BigDecimal monto, int idPlazo);
	public BigDecimal obtenerCuotaDesdeBD(BigDecimal monto, int idPlazo);
	public List<Prestamo> listar();
}
