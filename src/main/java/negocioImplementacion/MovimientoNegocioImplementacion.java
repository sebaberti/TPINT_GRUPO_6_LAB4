package negocioImplementacion;

import daoImplementacion.MovimientoDaoImplementacion;
import entidades.Movimiento;
import negocio.MovimientoNegocio;
import java.time.LocalDate;
import java.util.List;

public class MovimientoNegocioImplementacion implements MovimientoNegocio {

    private MovimientoDaoImplementacion movDao = new MovimientoDaoImplementacion();

    @Override
    public boolean registrarMovimiento(Movimiento movimiento) {
        return movDao.insertarMovimiento(movimiento);
    }

    @Override
    public List<Movimiento> obtenerMovimientosPorCuenta(int cuentaId) {
        return movDao.listarMovimientosPorCuenta(cuentaId);
    }

    @Override
	public double obtenerIngresos(LocalDate desde, LocalDate hasta) {
		
		return movDao.obtenerTotalIngresos(desde, hasta);
	}

	@Override
	public double obtenerEgresos(LocalDate desde, LocalDate hasta) {
		
		return movDao.obtenerTotalEgresos(desde, hasta);
	}
}
