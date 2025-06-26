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
<<<<<<< HEAD
=======
	public void validarMonto(BigDecimal monto) throws MontoInvalidoException;
>>>>>>> 8d5c7fe4bd88c5cd41b02e9e0f93b194c3155932
	
}