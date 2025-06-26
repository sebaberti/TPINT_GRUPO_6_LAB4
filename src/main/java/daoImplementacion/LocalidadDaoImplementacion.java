package daoImplementacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.LocalidadDao;
import entidades.Localidad;
import entidades.Provincia;

public class LocalidadDaoImplementacion implements LocalidadDao {

	private String listarQuery = "SELECT id, nombre FROM Localidades WHERE id_provincia = 1";
	private String listar = "SELECT id, nombre, id_provincia FROM Localidades";

	@Override
	public List<Localidad> listarLocalidadesBuenosAires() {
		List<Localidad> localidades = new ArrayList<>();

		try {
			Connection conexion = Conexion.getConexion().getSQLConexion();
			PreparedStatement stmt = conexion.prepareStatement(listarQuery);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Localidad l = new Localidad();
				l.setId(rs.getInt("id"));
				l.setNombre(rs.getString("nombre"));
				localidades.add(l);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return localidades;
	}

	public List<Localidad> listarLocalidades() {
		List<Localidad> localidades = new ArrayList<>();

		try {
			Connection conexion = Conexion.getConexion().getSQLConexion();
			PreparedStatement stmt = conexion.prepareStatement(listar);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Localidad l = new Localidad();
				l.setId(rs.getInt("id"));
				l.setNombre(rs.getString("nombre"));
				l.getProvincia().setId(rs.getInt("id_provincia"));
				localidades.add(l);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return localidades;
	}

	public Localidad obtenerLocalidadPorID(int id) {
		String query = "SELECT * FROM Localidades WHERE id = ?";

		try {
			Connection conexion = Conexion.getConexion().getSQLConexion();
			PreparedStatement statement = conexion.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				return new Localidad(rs.getInt(1), rs.getString(2));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Localidad(-1, "vacio");
	}
}
