package daoImplementacion;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.CuentasDao;
import entidades.Cuentas;


public class CuentasImplementacion implements CuentasDao {

	private static final String insert = "INSERT INTO cuentass (...) VALUES (?, ?, ?, ?)"; //completar

	public boolean insertarCuenta(Cuentas cuenta) {
		try {
			Connection conexion = Conexion.getConexion().getSQLConexion();
			PreparedStatement statement = conexion.prepareStatement(insert);

			//completar

			if (statement.executeUpdate() > 0) {
				conexion.commit();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public List<Cuentas> listarCuentas() {
		List<Cuentas> lista = new ArrayList<Cuentas>();

		//completar
		
		return lista;
	}

	public String manejarCaracterEspecial(String texto) {
		try {
			texto = new String(texto.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return texto;
	}


}
