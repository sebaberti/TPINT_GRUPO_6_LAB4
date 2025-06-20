package dao;

import java.util.ArrayList;

import entidades.Cuenta;

public interface CuentasDao {
	public boolean insertarCuenta(Cuenta cuenta);
	public ArrayList<Cuenta> listar();
	public String manejarCaracterEspecial(String texto);
}
