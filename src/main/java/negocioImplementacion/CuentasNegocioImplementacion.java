package negocioImplementacion;

import java.util.List;

import daoImplementacion.CuentasImplementacion;
import entidades.Cuenta;
import negocio.CuentasNegocio;

public class CuentasNegocioImplementacion implements CuentasNegocio {

	public List<Cuenta> listarCuentas() {
		CuentasImplementacion cuentas = new CuentasImplementacion();
		return cuentas.listarCuentas();
	}

	public boolean existeCuenta(Cuenta cuenta) {
		List<Cuenta> cuentas = listarCuentas();
		
		for (Cuenta  cta : cuentas) {
			if(cta.equals(cuenta))
				return true;
		}
		return false;
	}

}
