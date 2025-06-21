package daoImplementacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.CuentaTipoDao;
import entidades.CuentaTipo;
import utilidades.ManejoCaractEspecial;

public class CuentaTipoDaoImplementacion implements CuentaTipoDao {

	@Override
	public ArrayList<CuentaTipo> listar() {
		ArrayList<CuentaTipo> listaTiposCuentas = new ArrayList<CuentaTipo>();

		Connection conexion = null;
        PreparedStatement statement= null;
   	 	ResultSet rs= null;
       
        String query ="select id, descripcion from Tipos_Cuentas";
        
		try {
			conexion = Conexion.getConexion().getSQLConexion();
			statement = conexion.prepareStatement(query);
			rs = statement.executeQuery();

			while (rs.next()) {
				CuentaTipo tipoCuenta = new CuentaTipo();
				tipoCuenta.setId(rs.getInt(1));
				tipoCuenta.setDescripcion(ManejoCaractEspecial.manejarCaracterEspecial(rs.getString(2)));
				listaTiposCuentas.add(tipoCuenta);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaTiposCuentas;
	}
}
