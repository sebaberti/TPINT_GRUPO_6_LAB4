package daoImplementacion;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import dao.CuentaDao;
import entidades.Cliente;
import entidades.Cuenta;
import entidades.CuentaTipo;

public class CuentaDaoImplementacion implements CuentaDao {

	@Override
	public boolean insertarCuenta(Cuenta cuenta) {
		String query ="INSERT INTO cuentas (id_cliente, fecha_creacion, numero_de_cuenta, id_tipo_cuenta, cbu, saldo, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		Connection conexion = null;
        PreparedStatement statement= null;
        
		try {
			conexion = Conexion.getConexion().getSQLConexion();
			statement = conexion.prepareStatement(query);

			statement.setInt(1, cuenta.getCliente().getId());
			statement.setString(2, cuenta.getFechaCreacion());
			statement.setString(3, cuenta.getNumeroCuenta());
			statement.setInt(4, cuenta.getTipoCuenta().getId());
			statement.setString(5, cuenta.getCBU());
			statement.setDouble(6, cuenta.getSaldo());
			statement.setBoolean(7, cuenta.isEstado());
			
			
			if (statement.executeUpdate() > 0) {
				conexion.commit();
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
            try {
                if (statement != null) statement.close();
                if (conexion != null) conexion.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

		return false;
	}
	
	@Override
    public ArrayList<Cuenta> listar() {
        ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
        
        Connection conexion = null;
        PreparedStatement statement= null;
   	 	ResultSet rs= null;
       
        String query = "SELECT c.id AS cuenta_id, c.fecha_creacion, c.numero_de_cuenta, "+
                "c.id_tipo_cuenta AS tipo_cuenta_id, c.cbu, c.saldo, c.estado, "+
        		"cl.nombre, cl.apellido, cl.dni, "+
                "t.descripcion AS tipo_descripcion FROM cuentas c"+
                "INNER JOIN Cliente cl ON c.id_cliente = cl.id";
        
        try {
        	conexion = Conexion.getConexion().getSQLConexion();
        	 statement = conexion.prepareStatement(query);
        	 rs = statement.executeQuery();

            while (rs.next()) {
                Cuenta cuenta = new Cuenta();
                Cliente cliente = new Cliente();
                CuentaTipo tipoCuenta = new CuentaTipo();

                cuenta.setId(rs.getInt("cuenta_id"));
                cuenta.setSaldo(rs.getDouble("saldo"));
                cuenta.setCBU(rs.getString("cbu"));
                cuenta.setFechaCreacion(rs.getString("fecha_creacion"));
                cuenta.setEstado(rs.getBoolean("estado"));

                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setDNI(rs.getString("dni"));
                cuenta.setCliente(cliente);

                tipoCuenta.setDescripcion(rs.getString("tipo_descripcion"));
                cuenta.setTipoCuenta(tipoCuenta);

                cuentas.add(cuenta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
                if (conexion != null) conexion.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return cuentas;
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
