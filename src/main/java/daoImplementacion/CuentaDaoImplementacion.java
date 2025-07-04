package daoImplementacion;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import dao.CuentaDao;
import entidades.Cliente;
import entidades.Cuenta;
import entidades.CuentaTipo;
import utilidades.ManejoCaractEspecial;

public class CuentaDaoImplementacion implements CuentaDao {

	@Override
	public boolean insertarCuenta(Cuenta cuenta) {
		String query = "INSERT INTO cuentas (id_cliente, fecha_creacion, numero_de_cuenta, id_tipo_cuenta, cbu, saldo, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";

		Connection conexion = null;
		PreparedStatement statement = null;

		try {
			conexion = Conexion.getConexion().getSQLConexion();
			statement = conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			conexion.setAutoCommit(false);
			
			statement.setInt(1, cuenta.getCliente().getId());
			statement.setDate(2, new java.sql.Date(cuenta.getFechaCreacion().getTime()));
			statement.setString(3, cuenta.getNumeroCuenta());
			statement.setInt(4, cuenta.getTipoCuenta().getId());
			statement.setString(5, cuenta.getCBU().toString());
			statement.setDouble(6, cuenta.getSaldo());
			statement.setBoolean(7, cuenta.isEstado());
			
			if (statement.executeUpdate() > 0) {
	            ResultSet generatedKeys = statement.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                cuenta.setId(generatedKeys.getInt(1)); // Obtener ID generado
	            }
	            conexion.commit();
	            return true;
	        }		
		} catch (SQLException e) {
			System.err.println("Error al insertar cuenta: " + e.getMessage());
		    e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Cuenta> listar() {
		List<Cuenta> cuentas = new ArrayList<Cuenta>();

		Connection conexion = null;
		PreparedStatement statement = null;
		ResultSet rs = null;

		String query = "SELECT c.id AS cuenta_id, c.fecha_creacion, c.numero_de_cuenta, "
		        + "c.id_tipo_cuenta AS tipo_cuenta_id, c.cbu, c.saldo, c.estado, "
		        + "cl.id AS cliente_id, cl.nombre, cl.apellido, cl.dni, "
		        + "t.descripcion AS tipo_descripcion "
		        + "FROM cuentas c "
		        + "INNER JOIN Clientes cl ON c.id_cliente = cl.id "
		        + "INNER JOIN Tipos_Cuentas t ON c.id_tipo_cuenta = t.id";
		try {
			conexion = Conexion.getConexion().getSQLConexion();
			statement = conexion.prepareStatement(query);
			rs = statement.executeQuery();

			while (rs.next()) {
				cuentas.add(mapearCuenta(rs));
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
	       		"cl.id AS cliente_id,cl.nombre, cl.apellido, cl.dni, "+
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
	        	cuentas.add(mapearCuenta(rs));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return cuentas;
	}

	@Override
	public List<Cuenta> listarCuentasPorClienteId(int clienteId, boolean soloActivas) {
	    List<Cuenta> cuentas = new ArrayList<>();
	    
	    Connection conexion = null;
	    PreparedStatement statement= null;
	  	 	ResultSet rs= null;
	   
	  	 	String query = """
	  	 			SELECT 
	  	 				c.id as cuenta_id, c.cbu, c.saldo, c.numero_de_cuenta, 
	  	 				c.fecha_creacion, c.estado, 
	  	 				tc.id AS id_tipo_cuenta, tc.descripcion AS tipo_descripcion,
	  	 				cl.id AS cliente_id, cl.nombre, cl.apellido, cl.dni
	  	 			FROM cuentas c
	  	 			INNER JOIN tipos_cuentas tc ON c.id_tipo_cuenta = tc.id
	  	 			INNER JOIN Clientes AS cl ON cl.id = c.id_cliente
	  	 			WHERE c.id_cliente = ? AND tc.descripcion IN ('Caja de ahorro', 'Cuenta corriente')
	  	 			""";
	  	 	
	  	 		if (soloActivas) {
  	 				query += " AND c.estado = 1";
  	 			}


	    try {
	    	conexion = Conexion.getConexion().getSQLConexion();
	    	statement = conexion.prepareStatement(query);
	    	statement.setInt(1, clienteId);
	    	rs = statement.executeQuery();

	        while (rs.next()) {
	        	cuentas.add(mapearCuenta(rs));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return cuentas;
	}

	@Override
	public Cuenta obtenerCuentaPorCBU(String cbu) {
	    Cuenta cuenta = null;
	    Connection conexion = null;
	    PreparedStatement statement= null;
	  	 	ResultSet rs= null;
	  	 	
	    String query = "SELECT c.id as cuenta_id, c.fecha_creacion, c.numero_de_cuenta, c.id_tipo_cuenta, c.cbu, c.saldo, c.estado, t.descripcion as tipo_descripcion ,cl.id AS cliente_id, cl.nombre, cl.apellido, cl.dni " +
	    	    "FROM cuentas c " +
	    	    "JOIN clientes cl ON c.id_cliente = cl.id " +
	    	    "INNER JOIN Tipos_Cuentas t ON c.id_tipo_cuenta = t.id " +
	    	    "WHERE c.cbu = ?";

	    try { 
	    	conexion = Conexion.getConexion().getSQLConexion();
	    	statement = conexion.prepareStatement(query);
	    	statement.setString(1, cbu);
	    	rs = statement.executeQuery();

	        if (rs.next()) {
	        	cuenta = mapearCuenta(rs);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return cuenta;
	}

	@Override
	public Cuenta obtenerCuentaPorId(int id) {
	    Cuenta cuenta = null;
	    Connection conexion = null;
        PreparedStatement statement= null;
   	 	ResultSet rs= null;
   	 	
	    String query = "SELECT c.id AS cuenta_id, c.fecha_creacion, c.numero_de_cuenta, "+
	            "c.id_tipo_cuenta AS tipo_cuenta_id, c.cbu, c.saldo, c.estado, "+
	       		"cl.id AS cliente_id,cl.nombre, cl.apellido, cl.dni, "+
	            "t.descripcion AS tipo_descripcion FROM cuentas c "+
	            "INNER JOIN Clientes cl ON c.id_cliente = cl.id "+
	            "INNER JOIN Tipos_Cuentas t ON c.id_tipo_cuenta = t.id WHERE c.id = ?";

	    try{ 
	    	conexion = Conexion.getConexion().getSQLConexion();
	    	statement = conexion.prepareStatement(query);
	        
	    	statement.setInt(1, id);
	    	rs = statement.executeQuery();

	        if (rs.next()) {
	        	cuenta = mapearCuenta(rs);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return cuenta;
	}

	@Override
	public int cuentasActivas(int idCliente) {
		int cuentasActivas = 0;
		Connection conexion = null;
		PreparedStatement statement = null;
		ResultSet rs = null;

		String query = "SELECT COUNT(*) AS cantidad_cuentas FROM Cuentas WHERE id_cliente = ? AND estado = 1";

		try {
			conexion = Conexion.getConexion().getSQLConexion();
			statement = conexion.prepareStatement(query);
			statement.setInt(1, idCliente);
			rs = statement.executeQuery();

			if (rs.next()) {
				cuentasActivas = rs.getInt("cantidad_cuentas");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cuentasActivas;
	}
	
	@Override
	public List<Cuenta> listarPorNro(String nro) {
	    List<Cuenta> cuentas= new ArrayList<>();
	    Connection conexion = null;
	    PreparedStatement statement= null;
	  	 	ResultSet rs= null;
	  	 	
	    String query = "SELECT c.id AS cuenta_id, c.fecha_creacion, c.numero_de_cuenta, "+
	            "c.id_tipo_cuenta AS tipo_cuenta_id, c.cbu, c.saldo, c.estado, "+
	       		"cl.id as cliente_id, cl.nombre, cl.apellido, cl.dni, "+
	            "t.descripcion AS tipo_descripcion FROM cuentas c "+
	            "INNER JOIN Clientes cl ON c.id_cliente = cl.id "+
	            "INNER JOIN Tipos_Cuentas t ON c.id_tipo_cuenta = t.id WHERE c.numero_de_cuenta LIKE ?";

	    try{ 
	    	conexion = Conexion.getConexion().getSQLConexion();
	    	statement = conexion.prepareStatement(query);
	        
	    	statement.setString(1,"%"+ nro+"%");
	    	rs = statement.executeQuery();

	        while (rs.next()) {
	        	cuentas.add(mapearCuenta(rs));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return cuentas;
	}

	@Override
	public List<Cuenta> listarPorCBU(BigInteger cbu) {
	    List<Cuenta> cuentas = new ArrayList<>();
	    Connection conexion = null;
	    PreparedStatement statement= null;
	  	ResultSet rs= null;
	  	 	
	    String query = "SELECT c.id AS cuenta_id, c.fecha_creacion, c.numero_de_cuenta, "+
	            "c.id_tipo_cuenta AS tipo_cuenta_id, c.cbu, c.saldo, c.estado, "+
	       		"cl.id AS cliente_id, cl.nombre, cl.apellido, cl.dni, "+
	            "t.descripcion AS tipo_descripcion FROM cuentas c "+
	            "INNER JOIN Clientes cl ON c.id_cliente = cl.id "+
	            "INNER JOIN Tipos_Cuentas t ON c.id_tipo_cuenta = t.id WHERE c.cbu LIKE ?";

	    try{ 
	    	conexion = Conexion.getConexion().getSQLConexion();
	    	statement = conexion.prepareStatement(query);
	        
	    	statement.setString(1,"%"+ cbu+"%");
	    	rs = statement.executeQuery();

	        if (rs.next()) {
	        	cuentas.add(mapearCuenta(rs));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return cuentas;
	}

	@Override
	public boolean existeCbu(BigInteger cbu) {
		Connection conexion = null;
		PreparedStatement statement = null;
		ResultSet rs = null;

		String query = "SELECT COUNT(*) AS coincidencias FROM Cuentas WHERE cbu = ?";

		try {
			conexion = Conexion.getConexion().getSQLConexion();
			statement = conexion.prepareStatement(query);
			statement.setString(1, cbu.toString());
			rs = statement.executeQuery();

			if (rs.next()) {
				int count = rs.getInt("coincidencias");
				return count > 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public String obtenerUltimoNumeroCuenta() {
		String nroCta = "";
		Connection conexion = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		String query = "SELECT numero_de_cuenta FROM Cuentas WHERE numero_de_cuenta LIKE '12345678-%' ORDER BY numero_de_cuenta DESC LIMIT 1";
		try {
			conexion = Conexion.getConexion().getSQLConexion();
			statement = conexion.prepareStatement(query);
			rs = statement.executeQuery();

			if (rs.next()) {
				nroCta = rs.getString("numero_de_cuenta");
				return nroCta;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nroCta;
	}

	@Override
	public boolean bajaLogica(int idCuenta) {
		Connection conexion = null;
		PreparedStatement statement = null;

		String query = "UPDATE cuentas SET estado = 0 WHERE id = ? AND estado = 1";

		try {
			conexion = Conexion.getConexion().getSQLConexion();
			conexion.setAutoCommit(true); // para hacer commit

			statement = conexion.prepareStatement(query);
			statement.setInt(1, idCuenta);

			int filasAfectadas = statement.executeUpdate();

			return filasAfectadas > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean modificarCuenta(Cuenta cuenta) {
		Connection conexion = null;
		PreparedStatement statement = null;

		String query = "UPDATE cuentas SET id_tipo_cuenta = ?, saldo = ?, estado = ? WHERE id = ?";

		try {
			conexion = Conexion.getConexion().getSQLConexion();
			conexion.setAutoCommit(false);
			statement = conexion.prepareStatement(query);

			statement.setInt(1, cuenta.getTipoCuenta().getId());
			statement.setDouble(2, cuenta.getSaldo());
			statement.setBoolean(3, cuenta.isEstado());
			statement.setInt(4, cuenta.getId());

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
	public Boolean tienePrestamoActivo(int idCuenta) {
		Connection conexion = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		String query = "SELECT COUNT(id_cuenta) FROM Prestamos WHERE id_cuenta = ? AND estado = 1";

		int count = 0;
		try {
			conexion = Conexion.getConexion().getSQLConexion();
			statement = conexion.prepareStatement(query);
			statement.setInt(1, idCuenta);
			rs = statement.executeQuery();

			if (!rs.next())
				return false;

			count = rs.getInt(1);
			return count > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean actualizarEstado(int idCuenta, boolean nuevoEstado) {
		Connection conexion = null;
		PreparedStatement statement = null;
	    String query = "UPDATE Cuentas SET estado = ? WHERE id = ?";
	    
	    try {
	    	conexion = Conexion.getConexion().getSQLConexion();
	        statement = conexion.prepareStatement(query);
	        conexion.setAutoCommit(true); // para hacer commit
	        statement.setBoolean(1, nuevoEstado);
	        statement.setInt(2, idCuenta);
	        int filas = statement.executeUpdate();
	        return filas > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	@Override
	public int contarCuentas(LocalDate desde, LocalDate hasta) {
	    int total = 0;
	    String query = "SELECT COUNT(*) AS cuentas FROM cuentas " +
	                   "WHERE estado = 1 AND fecha_creacion BETWEEN ? AND ?";

	    try (Connection conexion = Conexion.getConexion().getSQLConexion();
	         PreparedStatement statement = conexion.prepareStatement(query)) {

	        statement.setDate(1, java.sql.Date.valueOf(desde));
	        statement.setDate(2, java.sql.Date.valueOf(hasta));

	        try (ResultSet rs = statement.executeQuery()) {
	            if (rs.next()) {
	                total = rs.getInt("cuentas");
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return total;
	}

	
	private Cuenta mapearCuenta(ResultSet rs) throws SQLException {
	    Cuenta cuenta = new Cuenta();
	    Cliente cliente = new Cliente();
        CuentaTipo tipoCuenta = new CuentaTipo();

        cuenta.setId(rs.getInt("cuenta_id"));
        cuenta.setFechaCreacion(rs.getDate("c.fecha_creacion"));
        cuenta.setNumeroCuenta(rs.getString("c.numero_de_cuenta"));

        tipoCuenta.setDescripcion(ManejoCaractEspecial.manejarCaracterEspecial(rs.getString("tipo_descripcion")));
        cuenta.setTipoCuenta(tipoCuenta);
        
        cuenta.setCBU(new BigInteger(rs.getString("cbu")));
        cuenta.setSaldo(rs.getDouble("saldo"));
        cuenta.setEstado(rs.getBoolean("estado"));

        
        cliente.setId(rs.getInt("cliente_id")); 
        cliente.setNombre(rs.getString("cl.nombre"));
        cliente.setApellido(rs.getString("cl.apellido"));
        cliente.setDNI(rs.getString("cl.dni"));
        cuenta.setCliente(cliente);

	    return cuenta;
	}

	@Override
	public int cantidadCuentasXcliente(LocalDate desde, LocalDate hasta) {
		
		 int total = 0;
		 
		    String query ="""
		    	    SELECT COUNT(DISTINCT id_cliente) AS clientes_activos
		    	    FROM cuentas
		    	    WHERE fecha_creacion BETWEEN ? AND ?
		    	      AND estado = 1
		    	""";

		    try (Connection conexion = Conexion.getConexion().getSQLConexion();
			         PreparedStatement statement = conexion.prepareStatement(query)) {

			        statement.setDate(1, java.sql.Date.valueOf(desde));
			        statement.setDate(2, java.sql.Date.valueOf(hasta));

			        try (ResultSet rs = statement.executeQuery()) {
			            if (rs.next()) {
			                total = rs.getInt("clientes_activos");
			            }
			        }

			    } catch (Exception e) {
			        e.printStackTrace();
			    }

			    return total;	
	}

	@Override
	public double promedioCuentasporCliente(LocalDate desde, LocalDate hasta) {
		 double promedio = 0.0;
		    String query = """
		        SELECT 
		            COUNT(*) * 1.0 / NULLIF(COUNT(DISTINCT id_cliente), 0) AS promedio
		        FROM 
		            cuentas
		        WHERE 
		            fecha_creacion BETWEEN ? AND ?
		    """;

		    try (Connection conexion =  Conexion.getConexion().getSQLConexion();
		         PreparedStatement stmt = conexion.prepareStatement(query)) {

		        stmt.setDate(1, java.sql.Date.valueOf(desde));
		        stmt.setDate(2, java.sql.Date.valueOf(hasta));

		        ResultSet rs = stmt.executeQuery();
		        if (rs.next()) {
		            promedio = rs.getDouble("promedio");
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return promedio;
	}
		@Override
	public double cajaActual() {
		
		double cajaActual=0;
		
		String query="""
				SELECT SUM(saldo) AS caja_actual 
				FROM cuentas""";
                       
        try(Connection conexion=Conexion.getConexion().getSQLConexion();
        PreparedStatement stmt= conexion.prepareStatement(query)) {
        	
		     
		     ResultSet rs = stmt.executeQuery();
		        if (rs.next()) {
		            cajaActual = rs.getDouble("caja_actual");
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		
			return cajaActual;
	}

	@Override
	public double cajaBancoXporPeriodo(LocalDate desde, LocalDate hasta) { 
	double caja_por_periodo=0;
		
		String query= """
		        SELECT SUM(saldo) AS caja_periodo
		        FROM cuentas
		        WHERE fecha_creacion BETWEEN ? AND ?
		    """;                
        try(Connection conexion=Conexion.getConexion().getSQLConexion();
        PreparedStatement stmt= conexion.prepareStatement(query)) {	     
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
		        caja_por_periodo = rs.getDouble("caja_por_periodo");
		        }
		    } 
	catch (SQLException e) {
		 e.printStackTrace();
	}
		
	return caja_por_periodo;
	}
}
