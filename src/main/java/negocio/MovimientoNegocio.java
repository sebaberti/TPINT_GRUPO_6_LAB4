package negocio;

import entidades.Movimiento;
import java.util.List;

public interface MovimientoNegocio {
    boolean registrarMovimiento(Movimiento movimiento);
    List<Movimiento> obtenerMovimientosPorCuenta(int cuentaId);
}