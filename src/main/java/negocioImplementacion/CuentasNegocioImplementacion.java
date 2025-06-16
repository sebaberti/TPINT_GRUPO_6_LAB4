package negocioImplementacion;

import java.util.List;

import daoImplementacion.CuentasImplementacion;
import entidades.Cuentas;
import negocio.CuentasNegocio;

public class CuentasNegocioImplementacion implements CuentasNegocio {

	public List<Cuentas> listarCuentas() {
		CuentasImplementacion cuentas = new CuentasImplementacion();
		return cuentas.listarCuentas();
	}

	public boolean existeCuenta(Cuentas cuenta) {
		List<Cuentas> cuentas = listarCuentas();
		
		for (Cuentas  cta : cuentas) {
			if(cta.equals(cuenta))
				return true;
		}
		return false;
	}

}
