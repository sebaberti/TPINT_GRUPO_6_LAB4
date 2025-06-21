package negocioImplementacion;

import java.util.ArrayList;

import daoImplementacion.CuentaTipoDaoImplementacion;
import entidades.CuentaTipo;
import negocio.CuentaTipoNegocio;

public class CuentaTipoNegocioImplementacion implements CuentaTipoNegocio{

	@Override
	public ArrayList<CuentaTipo> listarTiposCuenta() {
		CuentaTipoDaoImplementacion tsi= new CuentaTipoDaoImplementacion();
		return tsi.listar();
	}

}
