package dao;

import java.util.List;
import java.time.LocalDate;

import entidades.Movimiento;

public interface MovimientoDao {
    boolean insertarMovimiento(Movimiento movimiento);
    List<Movimiento> listarMovimientosPorCuenta(int cuentaId);
    double sumarMovimientosPorTipo(String tipoMovimiento);
    double obtenerTotalIngresos(LocalDate desde,LocalDate hasta);
    double obtenerTotalEgresos(LocalDate desde,LocalDate hasta);

}
