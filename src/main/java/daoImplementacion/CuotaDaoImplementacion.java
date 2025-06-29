package daoImplementacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.CuotaDao;
import entidades.Cuota;

public class CuotaDaoImplementacion implements CuotaDao {
	
	@Override
	public List<Cuota> cuotasPendientesPorCliente(int idCliente) {
		 List<Cuota> lista = new ArrayList<>();
		 
		 String query = "SELECT c.* FROM Cuotas c " +
                 "INNER JOIN Prestamos p ON c.id_prestamo = p.id " +
                 "WHERE p.id_cliente = ? AND c.estado = 0";

		 Connection conexion = null;
		 PreparedStatement statement = null;
		 ResultSet rs = null;
		 
		    try {
		        conexion = Conexion.getConexion().getSQLConexion();
		        statement = conexion.prepareStatement(query);
		        statement.setInt(1, idCliente);
		        rs = statement.executeQuery();

		        while (rs.next()) {
		            Cuota cuota = new Cuota();
		            cuota.setId(rs.getInt("id"));
		            cuota.setPrestamoId(rs.getInt("id_prestamo"));
		            cuota.setNumeroCuota(rs.getInt("nro_cuota"));
		            cuota.setImporte(rs.getDouble("importe"));
		            cuota.setFechaPago(rs.getDate("fecha_pago"));
		            cuota.setFechaVencimiento(rs.getDate("fecha_vencimiento"));
		            cuota.setEstado(rs.getBoolean("estado"));

		            lista.add(cuota);
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return lista;
	}

}
