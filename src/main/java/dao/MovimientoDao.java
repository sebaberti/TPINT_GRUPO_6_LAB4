package dao;

import java.util.List;

import entidades.Movimiento;

public interface MovimientoDao {
    boolean insertarMovimiento(Movimiento movimiento);
    List<Movimiento> listarMovimientosPorCuenta(int cuentaId);
    double sumarMovimientosPorTipo(String tipoMovimiento);

}