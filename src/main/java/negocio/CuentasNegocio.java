package negocio;

import java.util.List;

import entidades.Cuentas;

public interface CuentasNegocio {
	public List<Cuentas> listarCuentas();
	public boolean existeCuenta(Cuentas cuenta);
}


