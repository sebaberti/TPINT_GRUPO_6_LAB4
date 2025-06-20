package negocioImplementacion;

import java.util.ArrayList;

import daoImplementacion.CuentaDaoImplementacion;
import entidades.Cuenta;
import negocio.CuentaNegocio;

public class CuentaNegocioImplementacion implements CuentaNegocio {

	@Override
	public boolean insertarCuenta(Cuenta cuenta) {
		CuentaDaoImplementacion cuentas = new CuentaDaoImplementacion();
	        return cuentas.insertarCuenta(cuenta);
	}
	
	@Override
	public ArrayList<Cuenta> listarCuentas() {
		CuentaDaoImplementacion cuentas = new CuentaDaoImplementacion();
		return cuentas.listar();
	}

	public boolean existeCuenta(Cuenta cuenta) {
		ArrayList<Cuenta> cuentas = listarCuentas();
		
		for (Cuenta  cta : cuentas) {
			if(cta.equals(cuenta))
				return true;
		}
		return false;
	}

}
