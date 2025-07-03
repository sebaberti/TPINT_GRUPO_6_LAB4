package negocio;

import entidades.Movimiento;
import java.util.List;
import java.time.LocalDate;

public interface MovimientoNegocio {
    boolean registrarMovimiento(Movimiento movimiento);
    List<Movimiento> obtenerMovimientosPorCuenta(int cuentaId);
    double obtenerIngresos(LocalDate desde,LocalDate hasta);
    double obtenerEgresos(LocalDate desde,LocalDate hasta);
}
