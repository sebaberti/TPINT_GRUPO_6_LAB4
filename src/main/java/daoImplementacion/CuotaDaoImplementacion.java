package daoImplementacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.CallableStatement;

import dao.CuotaDao;
import entidades.Cuota;

public class CuotaDaoImplementacion implements CuotaDao {
	
	@Override
	public Cuota cuotaPorId(int idCuota) {
		String query = "SELECT c.* FROM Cuotas c " +
                "WHERE c.id = ? ";

		 Connection conexion = null;
		 PreparedStatement statement = null;
		 ResultSet rs = null;
		 Cuota cuota = null;
		 
		    try {
		        conexion = Conexion.getConexion().getSQLConexion();
		        statement = conexion.prepareStatement(query);
		        statement.setInt(1, idCuota);
		        rs = statement.executeQuery();

		        while (rs.next()) {
		        	mapearCuota(rs);
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return cuota;
	}

	@Override
	public List<Cuota> cuotasPorClienteYEstado(int idCliente, boolean estado) {
	    List<Cuota> lista = new ArrayList<>();

	    String query = "SELECT c.* FROM Cuotas c " +
	                   "INNER JOIN Prestamos p ON c.id_prestamo = p.id " +
	                   "WHERE p.id_cliente = ? AND c.estado = ?";

	    try (Connection conexion = Conexion.getConexion().getSQLConexion();
	         PreparedStatement stmt = conexion.prepareStatement(query)) {

	        stmt.setInt(1, idCliente);
	        stmt.setBoolean(2, estado);

	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Cuota cuota = mapearCuota(rs);
	                lista.add(cuota);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return lista;
	}

	@Override
	public List<Cuota> cuotasPorCliente(int idCliente) {
	    List<Cuota> lista = new ArrayList<>();

	    String query = "SELECT c.* FROM Cuotas c " +
	                   "INNER JOIN Prestamos p ON c.id_prestamo = p.id " +
	                   "WHERE p.id_cliente = ?";

	    try (Connection conexion = Conexion.getConexion().getSQLConexion();
	         PreparedStatement stmt = conexion.prepareStatement(query)) {

	        stmt.setInt(1, idCliente);

	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Cuota cuota = mapearCuota(rs);
	                lista.add(cuota);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return lista;
	}

	private Cuota mapearCuota(ResultSet rs) throws SQLException {
	    Cuota cuota = new Cuota();
	    cuota.setId(rs.getInt("id"));
	    cuota.setPrestamoId(rs.getInt("id_prestamo"));
	    cuota.setNumeroCuota(rs.getInt("nro_cuota"));
	    cuota.setImporte(rs.getDouble("importe"));
	    cuota.setFechaPago(rs.getDate("fecha_pago"));
	    cuota.setFechaVencimiento(rs.getDate("fecha_vencimiento"));
	    cuota.setEstado(rs.getBoolean("estado"));
	    return cuota;
	}


	@Override
	public boolean pagarCuota(int idCuota, int idCuenta) {
	    Connection conexion = null;
	    CallableStatement callable = null;

	    try {
	        conexion = Conexion.getConexion().getSQLConexion();
	        conexion.setAutoCommit(true); // para hacer commit
	        String spCall = "{CALL pagar_cuota(?, ?, ?)}";
	        callable = (CallableStatement) conexion.prepareCall(spCall);

	        callable.setInt(1, idCuota);
	        callable.setInt(2, idCuenta);
	        callable.registerOutParameter(3, Types.BOOLEAN);

	        callable.execute();

	        return callable.getBoolean(3);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
}
