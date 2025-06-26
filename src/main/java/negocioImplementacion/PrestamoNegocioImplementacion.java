package negocioImplementacion;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import daoImplementacion.PrestamoDaoImplementacion;
import entidades.Cliente;
import entidades.Prestamo;
import negocio.PrestamoNegocio;

public class PrestamoNegocioImplementacion implements PrestamoNegocio{

	@Override
	public boolean solicitarPrestamo(int idCliente, int idCuenta, BigDecimal monto, int idPlazo) {
		PrestamoDaoImplementacion pdi= new PrestamoDaoImplementacion();
		return pdi.ejecutarSPsolicitarPrestamo(idCliente, idCuenta, monto, idPlazo);
	}

	@Override
	public BigDecimal calcularCuota(BigDecimal monto, int idPlazo) {
		 if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0 || idPlazo <= 0) {
		        return BigDecimal.ZERO;
		    }
		PrestamoDaoImplementacion pdi= new PrestamoDaoImplementacion();
		return pdi.obtenerCuotaDesdeBD(monto, idPlazo);
	}

	@Override
	public List<Prestamo> listarPrestamos() {
		PrestamoDaoImplementacion pdi= new PrestamoDaoImplementacion();
		return pdi.listar();
	}
	

}
