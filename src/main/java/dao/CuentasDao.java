package dao;

import java.util.List;

import entidades.Cuentas;

public interface CuentasDao {
	public boolean insertarCuenta(Cuentas cuenta);
	public List<Cuentas> listarCuentas();
	public String manejarCaracterEspecial(String texto);
}
