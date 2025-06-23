package daoImplementacion;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import dao.ClienteDao;
import entidades.Cliente;
import entidades.Direccion;
import entidades.Localidad;
import entidades.Pais;
import entidades.Usuario;

public class ClienteDaoImplementacion implements ClienteDao {

	private String insertQuery = "INSERT INTO Clientes(dni, cuil ,nombre, apellido, sexo, id_nacionalidad, fecha_nacimiento, id_domicilio, correo_electronico, telefono, id_usuario, estado) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
	private String validarDNIQuery = "SELECT * FROM Clientes WHERE DNI = ?";
	private String validarCUILQuery = "SELECT * FROM Clientes WHERE CUIL = ?";
	private String bajaQuery = "UPDATE Clientes SET estado = 0 WHERE id=?";

	public Boolean insertar(Cliente cliente) {
		int rows = 0;

		try {
			Connection conexion = Conexion.getConexion().getSQLConexion();
			PreparedStatement statement = conexion.prepareStatement(insertQuery);
			System.out.println(cliente.getDNI());
			statement.setString(1, cliente.getDNI());
			statement.setString(2, cliente.getCUIL());
			statement.setString(3, cliente.getNombre());
			statement.setString(4, cliente.getApellido());
			statement.setString(5, "M"); // Sexo - Modificar por el valor del objeto.
			statement.setInt(6, 1); // ID Nacionalidad - Modificar por el valor del objeto.
			statement.setDate(7, java.sql.Date.valueOf(LocalDate.of(1999, 10, 10))); // Capturar el valor del objeto
			statement.setInt(8, 1); // ID Domiclio Capturar el valor del objeto
			statement.setString(9, cliente.getEmail());
			statement.setString(10, cliente.getTelefono());
			// El usuario deberia poder omitirse al crear el cliente?
			statement.setInt(11, 3); // ID Usuario - Deberia ser nulo si se esta creando.
			statement.setBoolean(12, true);

			if (statement.executeUpdate() > 0) {
				conexion.commit();
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Boolean modificar(Cliente cliente) {
		String query = """
					UPDATE Clientes
					SET cuil = ?, nombre = ?, apellido = ?, correo_electronico = ?, telefono = ?
					WHERE id = ?
				""";

		try {
			Connection conexion = Conexion.getConexion().getSQLConexion();
			PreparedStatement stmt = conexion.prepareStatement(query);

			stmt.setString(1, cliente.getCUIL());
			stmt.setString(2, cliente.getNombre());
			stmt.setString(3, cliente.getApellido());
			stmt.setString(4, cliente.getEmail());
			stmt.setString(5, cliente.getTelefono());
			stmt.setInt(6, cliente.getId());

			if (stmt.executeUpdate() > 0) {
				conexion.commit();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Boolean bajaLogica(String dni) {

		try {
			Connection conexion = Conexion.getConexion().getSQLConexion();
			CallableStatement preparedCall;
			preparedCall = conexion.prepareCall("CALL SP_BAJA_CLIENTE(?)");
			preparedCall.setString(1, dni);
			preparedCall.execute();

			ResultSet resultSet = preparedCall.getResultSet();

			if (!resultSet.next())
				return false;

			int resultado = resultSet.getInt(1);
			
			if(!(resultado == 2))
				return false;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public List<Cliente> listar() {
		List<Cliente> lista = new ArrayList<>();
		String query = "SELECT c.id, c.dni, c.cuil, c.nombre, c.apellido, c.estado, " + "u.nombre_usuario "
				+ "FROM Clientes c " + "INNER JOIN Usuarios u ON c.id_usuario = u.id";

		try {
			Connection conexion = Conexion.getConexion().getSQLConexion();
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				Cliente c = new Cliente();
				c.setId(rs.getInt("id"));
				c.setDNI(rs.getString("dni"));
				c.setCUIL(rs.getString("cuil"));
				c.setNombre(rs.getString("nombre"));
				c.setApellido(rs.getString("apellido"));
				c.setEstado(rs.getBoolean("estado"));

				Usuario usuario = new Usuario();
				usuario.setNombreUsuario(rs.getString("nombre_usuario"));
				c.setUsuario(usuario);

				lista.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<Cliente> buscar(String dni, String cuil) {
		List<Cliente> lista = new ArrayList<>();

		String queryBase = """
				    SELECT c.id AS cliente_id, c.dni, c.cuil, c.nombre, c.apellido, c.sexo,
				           c.id_nacionalidad, c.fecha_nacimiento, c.correo_electronico,
				           c.telefono, c.estado,
				           d.id AS domicilio_id, d.direccion,
				           l.id AS localidad_id, l.nombre AS nombre_localidad
				    FROM clientes c
				    INNER JOIN domicilios d ON c.id_domicilio = d.id
				    INNER JOIN localidades l ON d.id_localidad = l.id
				""";

		String query = queryBase;
		List<String> condiciones = new ArrayList<>();

		if (dni != null && !dni.trim().isEmpty()) {
			condiciones.add("c.dni = ?");
		}
		if (cuil != null && !cuil.trim().isEmpty()) {
			condiciones.add("REPLACE(c.cuil, '-', '') = ?");
		}

		if (!condiciones.isEmpty()) {
			query += " WHERE " + String.join(" AND ", condiciones);
		}

		try {
			Connection conexion = Conexion.getConexion().getSQLConexion();
			PreparedStatement stmt = conexion.prepareStatement(query);

			int index = 1;
			if (dni != null && !dni.trim().isEmpty()) {
				stmt.setString(index++, dni.trim());
			}
			if (cuil != null && !cuil.trim().isEmpty()) {
				stmt.setString(index++, cuil.trim().replace("-", ""));
			}

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Cliente c = new Cliente();
				c.setId(rs.getInt("cliente_id"));
				c.setDNI(rs.getString("dni"));
				c.setCUIL(rs.getString("cuil"));
				c.setNombre(rs.getString("nombre"));
				c.setApellido(rs.getString("apellido"));
				c.setSexo(rs.getString("sexo"));
				c.setFecha_nacimiento(rs.getDate("fecha_nacimiento"));
				c.setEmail(rs.getString("correo_electronico"));
				c.setTelefono(rs.getString("telefono"));
				c.setEstado(rs.getBoolean("estado"));

				// Domicilio
				Direccion d = new Direccion();
				d.setId(rs.getInt("domicilio_id"));
				d.setDireccion(rs.getString("direccion"));

				// Localidad
				Localidad l = new Localidad();
				l.setId(rs.getInt("localidad_id"));
				l.setNombre(rs.getString("nombre_localidad"));

				d.setLocalidad(l);
				c.setDomicilio(d);

				lista.add(c);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	public Boolean existeDNI(String DNI) {

		Connection conexion = Conexion.getConexion().getSQLConexion();
		try {

			PreparedStatement statement = conexion.prepareStatement(validarDNIQuery);
			statement.setString(1, DNI);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Boolean existeCUIL(String CUIL) {
		Connection conexion = Conexion.getConexion().getSQLConexion();
		try {

			PreparedStatement statement = conexion.prepareStatement(validarCUILQuery);
			statement.setString(1, CUIL);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override

	public Cliente clientePorDNI(String dni) {
		Cliente cliente = null;
		Connection conexion = null;
		try {
			conexion = Conexion.getConexion().getSQLConexion();
			// Obtengo todos los clientes sin importar su estado
			String query = "SELECT c.id, c.nombre, c.apellido, c.dni, c.id_usuario, c.estado, u.nombre_usuario FROM Clientes c JOIN Usuarios u ON c.id_usuario = u.id WHERE c.dni = ?";
			PreparedStatement statement = conexion.prepareStatement(query);
			statement.setString(1, dni);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				cliente = new Cliente();
				cliente.setId(rs.getInt("c.id"));
				cliente.setNombre(rs.getString("nombre"));
				cliente.setApellido(rs.getString("apellido"));
				cliente.setDNI(rs.getString("dni"));
				cliente.setEstado(rs.getBoolean("c.estado"));
				Usuario usuario = new Usuario();
				usuario.setNombreUsuario(rs.getString("nombre_usuario"));
				cliente.setUsuario(usuario);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cliente;
	}
	
	@Override
	public Cliente clientePorDNI(int dni) {
		// Obtengo el cliente solo si está activo (este es apra alta de cuenta)
		Cliente cliente = null;
		Connection conexion = null;
		try {
			conexion = Conexion.getConexion().getSQLConexion();
			String query = "SELECT c.id, c.nombre, c.apellido, c.dni, c.id_usuario, u.nombre_usuario " + "FROM Clientes c "
					+ "JOIN Usuarios u ON c.id_usuario = u.id " + "WHERE c.dni = ? AND u.estado = 1";
			PreparedStatement statement = conexion.prepareStatement(query);
			statement.setInt(1, dni);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				cliente = new Cliente();
				cliente.setId(rs.getInt("id"));
				cliente.setNombre(rs.getString("nombre"));
				cliente.setApellido(rs.getString("apellido"));
				cliente.setDNI(rs.getString("dni"));
				Usuario usuario = new Usuario();
				usuario.setNombreUsuario(rs.getString("nombre_usuario"));
				cliente.setUsuario(usuario);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cliente;
	}
			
	
	@Override
	public Cliente obtenerClientePorIdUsuario(int idUsuario) {
	    Cliente cliente = null;
	    Connection conexion = null;
	   

	    try {
	    	conexion = Conexion.getConexion().getSQLConexion();
	    	String query = "SELECT c.nombre, c.apellido, c.dni, c.cuil, c.telefono,c.id_usuario, c.correo_electronico, c.sexo, c.fecha_nacimiento, p.id AS id_nacionalidad, p.nombre AS nacionalidad " +
	                   "FROM Clientes c " +
	                   "JOIN Paises p ON c.id_nacionalidad = p.id " +
	                   "WHERE c.id_usuario = ?";
	    	PreparedStatement statement = conexion.prepareStatement(query);
	    	statement.setInt(1, idUsuario);
	    	ResultSet rs = statement.executeQuery();
	      

	        if (rs.next()) {
	            cliente = new Cliente();
	            cliente.setNombre(rs.getString("nombre"));
	            cliente.setApellido(rs.getString("apellido"));
	            cliente.setDNI(rs.getString("dni"));
	            cliente.setCUIL(rs.getString("cuil"));
	            cliente.setTelefono(rs.getString("telefono"));
	            cliente.setEmail(rs.getString("correo_electronico"));
	            cliente.setSexo(rs.getString("sexo"));
	            cliente.setFecha_nacimiento(rs.getDate("fecha_nacimiento"));
	            Pais nacionalidad = new Pais();
	            nacionalidad.setId(rs.getInt("id_nacionalidad"));
	            nacionalidad.setNombre(rs.getString("nacionalidad"));
	            cliente.setNacionalidad(nacionalidad);
	            
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return cliente;
	}
}
