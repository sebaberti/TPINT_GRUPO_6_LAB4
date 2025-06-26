package negocioImplementacion;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import daoImplementacion.PrestamoDaoImplementacion;
import entidades.Cliente;
import entidades.Prestamo;
import excepciones.MontoInvalidoException;
import negocio.PrestamoNegocio;

public class PrestamoNegocioImplementacion implements PrestamoNegocio{

	@Override
	public boolean solicitarPrestamo(int idCliente, int idCuenta, BigDecimal monto, int idPlazo) {
		PrestamoDaoImplementacion pdi= new PrestamoDaoImplementacion();
		return pdi.ejecutarSPsolicitarPrestamo(idCliente, idCuenta, monto, idPlazo);
	}
	
	@Override
	public boolean autorizarPrestamo(int id) {
		PrestamoDaoImplementacion pdi= new PrestamoDaoImplementacion();
		return pdi.autorizarPrestamo(id);
	}

	@Override
	public boolean rechazarPrestamo(int id) {
		PrestamoDaoImplementacion pdi= new PrestamoDaoImplementacion();
		return pdi.rechazarPrestamo(id);
	}

	@Override
	public BigDecimal calcularCuota(BigDecimal monto, int idPlazo) {
		PrestamoDaoImplementacion pdi= new PrestamoDaoImplementacion();
		return pdi.obtenerCuotaDesdeBD(monto, idPlazo);
	}
<<<<<<< HEAD

	@Override
	public List<Prestamo> listarPrestamos() {
		PrestamoDaoImplementacion pdi= new PrestamoDaoImplementacion();
		return pdi.listar();
	}
	
=======
>>>>>>> 8d5c7fe4bd88c5cd41b02e9e0f93b194c3155932

	@Override
	public List<Prestamo> listarPrestamos() {
		PrestamoDaoImplementacion pdi= new PrestamoDaoImplementacion();
		return pdi.listar();
	}
	
	public void validarMonto(BigDecimal monto) throws MontoInvalidoException {
	    BigDecimal MONTO_MINIMO = new BigDecimal("500000");
	    BigDecimal MONTO_MAXIMO = new BigDecimal("150000000");

	    if (monto.compareTo(MONTO_MINIMO) < 0) {
	        throw new MontoInvalidoException("El monto mínimo es $500.000.");
	    }

	    if (monto.compareTo(MONTO_MAXIMO) > 0) {
	        throw new MontoInvalidoException("El monto no puede superar los $150.000.000.");
	    }
	}
	
}
