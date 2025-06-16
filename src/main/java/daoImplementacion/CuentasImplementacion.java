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

	private static final String insert = "INSERT INTO cuentas (id_cliente, fecha_creacion, numero_de_cuenta, id_tipo_cuenta, cbu, saldo, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";

	public boolean insertarCuenta(Cuentas cuenta) {
		try {
			Connection conexion = Conexion.getConexion().getSQLConexion();
			PreparedStatement statement = conexion.prepareStatement(insert);

			//statement.setString(1, cuenta.getCliente().getID());
			statement.setString(2, cuenta.getFechaCreacion());
			statement.setString(3, cuenta.getNumeroCuenta());
			//statement.setInt(4, cuenta.getTipoCuenta().getID());
			statement.setString(5, cuenta.getCBU());
			statement.setDouble(6, cuenta.getSaldo());
			statement.setBoolean(7, cuenta.isEstado());
			
			
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

		Connection conexion = Conexion.getConexion().getSQLConexion();

		String query = "SELECT c.id, c.id_cliente, c.fecha_creacion, c.numero_de_cuenta, c.id_tipo_cuenta, c.cbu, c.saldo, c.estado, t.descripcion FROM cuentas c"+
		"INNER JOIN Tipos_Cuentas t ON c.id_tipo_cuenta=t.id";

		try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			//completar

		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
