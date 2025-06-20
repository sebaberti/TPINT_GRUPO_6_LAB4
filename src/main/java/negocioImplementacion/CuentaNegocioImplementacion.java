package negocioImplementacion;

import java.util.List;

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
	public List<Cuenta> listarCuentas() {
		CuentaDaoImplementacion cuentas = new CuentaDaoImplementacion();
		return cuentas.listar();
	}
	
	@Override
	public List<Cuenta> listarCuentasPorDNI(int dni) {
		CuentaDaoImplementacion cuentas = new CuentaDaoImplementacion();
		return cuentas.listarPorDNI(dni);
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
