package daoImplementacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.PaisDao;
import entidades.Pais;

public class PaisDaoImplementacion implements PaisDao {

	@Override
	public List<Pais> listar() {
		List<Pais> lista = new ArrayList<>();
		String query = "SELECT id, nombre FROM paises";

		try {
			Connection conexion = Conexion.getConexion().getSQLConexion();
			PreparedStatement stmt = conexion.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Pais pais = new Pais();
				pais.setId(rs.getInt("id"));
				pais.setNombre(rs.getString("nombre"));
				lista.add(pais);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}
	
	public Pais obtenerPaisPorID(int id) {
		String query = "SELECT * FROM paises WHERE id = ?";
		

		try {
			Connection conexion = Conexion.getConexion().getSQLConexion();
			PreparedStatement statement = conexion.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				return new Pais(rs.getInt(1), rs.getString(2));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Pais(-1, "vacio");
	}
}

