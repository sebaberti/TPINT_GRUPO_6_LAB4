package daoImplementacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dao.CuentaDao;
import entidades.Cliente;
import entidades.Cuenta;
import entidades.CuentaTipo;
import utilidades.ManejoCaractEspecial;

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
		}
		return false;
	}
	
	@Override
    public List<Cuenta> listar() {
        List<Cuenta> cuentas = new ArrayList<Cuenta>();
        
        Connection conexion = null;
        PreparedStatement statement= null;
   	 	ResultSet rs= null;
       
        String query = "SELECT c.id AS cuenta_id, c.fecha_creacion, c.numero_de_cuenta, "+
                "c.id_tipo_cuenta AS tipo_cuenta_id, c.cbu, c.saldo, c.estado, "+
        		"cl.nombre, cl.apellido, cl.dni, "+
                "t.descripcion AS tipo_descripcion FROM cuentas c "+
                "INNER JOIN Clientes cl ON c.id_cliente = cl.id "+
                "INNER JOIN Tipos_Cuentas t ON c.id_tipo_cuenta = t.id";
        
        try {
        	conexion = Conexion.getConexion().getSQLConexion();
        	 statement = conexion.prepareStatement(query);
        	 rs = statement.executeQuery();

            while (rs.next()) {
                Cuenta cuenta = new Cuenta();
                Cliente cliente = new Cliente();
                CuentaTipo tipoCuenta = new CuentaTipo();

                cuenta.setId(rs.getInt("cuenta_id"));
                cuenta.setFechaCreacion(rs.getString("fecha_creacion"));
                cuenta.setNumeroCuenta(rs.getString("numero_de_cuenta"));

                tipoCuenta.setDescripcion(ManejoCaractEspecial.manejarCaracterEspecial(rs.getString("tipo_descripcion")));
                cuenta.setTipoCuenta(tipoCuenta);
                
                cuenta.setCBU(rs.getString("cbu"));
                cuenta.setSaldo(rs.getDouble("saldo"));
                cuenta.setEstado(rs.getBoolean("estado"));

                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setDNI(rs.getString("dni"));
                cuenta.setCliente(cliente);

                cuentas.add(cuenta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cuentas;
    }

	@Override
    public List<Cuenta> listarPorDNI(int dni) {
        List<Cuenta> cuentas = new ArrayList<Cuenta>();
        
        Connection conexion = null;
        PreparedStatement statement= null;
   	 	ResultSet rs= null;
       
        String query = "SELECT c.id AS cuenta_id, c.fecha_creacion, c.numero_de_cuenta, "+
                "c.id_tipo_cuenta AS tipo_cuenta_id, c.cbu, c.saldo, c.estado, "+
        		"cl.nombre, cl.apellido, cl.dni, "+
                "t.descripcion AS tipo_descripcion FROM cuentas c "+
                "INNER JOIN Clientes cl ON c.id_cliente = cl.id "+
                "INNER JOIN Tipos_Cuentas t ON c.id_tipo_cuenta = t.id "+
                "WHERE cl.dni LIKE ?";
        
        try {
        	conexion = Conexion.getConexion().getSQLConexion();
        	 statement = conexion.prepareStatement(query);
        	 statement.setString(1, "%" + dni + "%");
        	 rs = statement.executeQuery();
        	 
            while (rs.next()) {
                Cuenta cuenta = new Cuenta();
                Cliente cliente = new Cliente();
                CuentaTipo tipoCuenta = new CuentaTipo();

                cuenta.setId(rs.getInt("cuenta_id"));
                cuenta.setFechaCreacion(rs.getString("fecha_creacion"));
                cuenta.setNumeroCuenta(rs.getString("numero_de_cuenta"));

                tipoCuenta.setDescripcion(ManejoCaractEspecial.manejarCaracterEspecial(rs.getString("tipo_descripcion")));
                cuenta.setTipoCuenta(tipoCuenta);
                
                cuenta.setCBU(rs.getString("cbu"));
                cuenta.setSaldo(rs.getDouble("saldo"));
                cuenta.setEstado(rs.getBoolean("estado"));

                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setDNI(rs.getString("dni"));
                cuenta.setCliente(cliente);

                cuentas.add(cuenta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cuentas;
    }
	
}
