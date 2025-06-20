package negocio;

import java.util.List;

import entidades.Cuenta;

public interface CuentasNegocio {
	public List<Cuenta> listarCuentas();
	public boolean existeCuenta(Cuenta cuenta);
}


