package negocio;

import entidades.Prestamo;
import excepciones.MontoInvalidoException;

import java.math.BigDecimal;
import java.util.List;

public interface PrestamoNegocio {

	boolean solicitarPrestamo(int idCliente, int idCuenta, BigDecimal monto, int idPlazo);
	public boolean autorizarPrestamo(int id);
	public boolean rechazarPrestamo(int id);
	public BigDecimal calcularCuota(BigDecimal monto, int idPlazo);
	public List<Prestamo> listarPrestamos();
	public void validarMonto(BigDecimal monto) throws MontoInvalidoException;
	
}