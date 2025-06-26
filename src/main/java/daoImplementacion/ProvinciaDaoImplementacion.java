package daoImplementacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.ProvinciaDao;
import entidades.Provincia;
import entidades.Pais;

public class ProvinciaDaoImplementacion implements ProvinciaDao {

	@Override
	public List<Provincia> listar() {
		List<Provincia> lista = new ArrayList<>();
		String query = "SELECT p.id, p.nombre, pa.id as id_pais, pa.nombre as nombre_pais " + "FROM provincias p "
				+ "INNER JOIN paises pa ON pa.id = p.id_pais";

		try {
			Connection conexion = Conexion.getConexion().getSQLConexion();
			PreparedStatement stmt = conexion.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Pais pais = new Pais(rs.getInt("id_pais"), rs.getString("nombre_pais"));
				Provincia provincia = new Provincia(rs.getInt("id"), rs.getString("nombre"), pais);
				lista.add(provincia);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}
	
	public Provincia obtenerProvinciaPorID(int id) {
		
		String query = "SELECT * FROM Provincias WHERE id = ?";
		

		try {
			Connection conexion = Conexion.getConexion().getSQLConexion();
			PreparedStatement statement = conexion.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				return new Provincia(rs.getInt(1), rs.getString(2));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Provincia(-1, "vacio");
	}
}
