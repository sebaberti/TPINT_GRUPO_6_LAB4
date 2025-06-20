package dao;

import java.util.List;

import entidades.Cuenta;

public interface CuentaDao {
	public boolean insertarCuenta(Cuenta cuenta);
	public List<Cuenta> listar();
	List<Cuenta> listarPorDNI(int dni);
}
