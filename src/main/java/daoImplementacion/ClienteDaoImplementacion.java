package daoImplementacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import dao.ClienteDao;
import entidades.Cliente;

public class ClienteDaoImplementacion implements ClienteDao {
	
	private String insertQuery = "INSERT INTO Clientes(dni, cuil ,nombre, apellido, sexo, id_nacionalidad, fecha_nacimiento, id_domicilio, correo_electronico, telefono, id_usuario, estado) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
	private String validarDNIQuery = "SELECT * FROM Clientes WHERE DNI = ?";
	private String validarCUILQuery = "SELECT * FROM Clientes WHERE CUIL = ?";
	
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
		return true;
	}

	public Boolean bajaLogica(int idCliente) {
		return true;
	}

	public List<Cliente> listar() {
		List<Cliente> lista = new ArrayList<Cliente>();
		return lista;
	}

	public Boolean existeDNI(String DNI) {

		Connection conexion = Conexion.getConexion().getSQLConexion();
		try {

			PreparedStatement statement = conexion.prepareStatement(validarDNIQuery);
			statement.setString(1, DNI);
			ResultSet resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
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
			
			if(resultSet.next()) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
    public Cliente clientePorDNI(int dni) {
		Cliente cliente = null;
		Connection conexion = null;
		try {
			conexion = Conexion.getConexion().getSQLConexion();
			String query = "SELECT c.nombre, c.apellido, c.dni" +
                    "FROM Clientes c " +
                    "JOIN Usuario u ON c.id_usuario = u.id " +
                    "WHERE c.DNI = ? AND u.Activo = 1";
			PreparedStatement statement = conexion.prepareStatement(query);
			statement.setInt(1, dni);
			ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                cliente = new Cliente();
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setDNI(rs.getString("dni"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }
}
