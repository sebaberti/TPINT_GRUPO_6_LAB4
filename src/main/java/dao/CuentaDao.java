package dao;

import java.util.List;

import entidades.Cuenta;

public interface CuentaDao {
	public boolean insertarCuenta(Cuenta cuenta);
	public List<Cuenta> listar();
	public List<Cuenta> listarPorDNI(int dni);
}
