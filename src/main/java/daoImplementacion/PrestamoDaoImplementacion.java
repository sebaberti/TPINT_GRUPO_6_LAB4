package daoImplementacion;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dao.PrestamoDao;
import entidades.Cliente;
import entidades.Cuenta;
import entidades.Plazo;
import entidades.Prestamo;
import entidades.Usuario;

public class PrestamoDaoImplementacion implements PrestamoDao{

	@Override
	public boolean ejecutarSPsolicitarPrestamo(int idCliente, int idCuenta, BigDecimal monto, int idPlazo) {
		 try
		  {
			 Connection conexion = Conexion.getConexion().getSQLConexion();
			 CallableStatement cst = conexion.prepareCall("CALL banco.SolicitarPrestamo(?,?,?,?)");
			 cst.setInt(1, idCliente);  		
		     cst.setInt(2, idCuenta);             
		     cst.setBigDecimal(3, monto); 
		     cst.setInt(4, idPlazo);  
			 cst.execute();		
			 conexion.commit();

		    return true;
		  }
		  catch (Exception e) {
			e.printStackTrace();
			return false;
		  }
	}
	@Override
	public boolean autorizarPrestamo(int idPrestamo) {
		 try
		  {
			 Connection conexion = Conexion.getConexion().getSQLConexion();
			 CallableStatement cst = conexion.prepareCall("CALL banco.AutorizarPrestamo(?)");
			 cst.setInt(1, idPrestamo);  		
			 cst.execute();		
			 conexion.commit();
		    return true;
		  }
		  catch (Exception e) {
			e.printStackTrace();
			return false;
		  }		
	}
	
	@Override
	public boolean rechazarPrestamo(int idPrestamo) {
		String query = "UPDATE prestamos SET estado=2 WHERE id=?";

		try {
			Connection conexion = Conexion.getConexion().getSQLConexion();
			PreparedStatement stmt = conexion.prepareStatement(query);
			stmt.setInt(1, idPrestamo);

			if (stmt.executeUpdate() > 0) {
				conexion.commit();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public BigDecimal obtenerCuotaDesdeBD(BigDecimal monto, int idPlazo) {
	    BigDecimal cuota = BigDecimal.ZERO;
	    
	    String sql = "SELECT CalcularCuotaPrestamo(?, ?) AS importeMensual";
	    try (Connection conexion = Conexion.getConexion().getSQLConexion();
	         PreparedStatement ps = conexion.prepareStatement(sql)) {
	        
	        ps.setBigDecimal(1, monto);
	        ps.setInt(2, idPlazo);
	        
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                cuota = rs.getBigDecimal("importeMensual");
	            }
	        }
	    }catch(SQLException e) {
	    	e.printStackTrace();
	        return BigDecimal.ZERO; 
	    }
	    return cuota;
	}

	@Override
	public List<Prestamo> listar() {
		List<Prestamo> lista = new ArrayList<>();
		String query = "SELECT"
				+ " id,"
				+ " id_cliente,"
				+ " id_cuenta,"
				+ " fecha_alta,"
				+ " importe_pedido,"
				+ " id_opcion_plazo,"
				+ " estado"
				+ " FROM prestamos";

		try {
			Connection conexion = Conexion.getConexion().getSQLConexion();
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				
				Prestamo p = new Prestamo();
				p.setId(rs.getInt("id"));
				
				Cliente cl= new Cliente();
				ClienteDaoImplementacion cldi= new ClienteDaoImplementacion();
				cl=cldi.obtenerClientePorId(rs.getInt("id_cliente"));
				p.setCliente(cl);
				
				Cuenta c= new Cuenta();
				CuentaDaoImplementacion cdi= new CuentaDaoImplementacion();
				c= cdi.obtenerCuentaPorId(rs.getInt("id_cuenta"));
				p.setCuenta(c);
				
				Date fecha = rs.getDate("fecha_alta");
				if (fecha != null) {
					p.setFechaAlta(fecha.toLocalDate());
				} else {
					p.setFechaAlta(null);
				}
				
				p.setImportePedido(rs.getBigDecimal("importe_pedido"));
				
				Plazo plazo= new Plazo();
				PlazoDaoImplementacion pdi= new PlazoDaoImplementacion();
				plazo= pdi.obtenerPlazoPorId(rs.getInt("id_opcion_plazo"));
				p.setPlazo(plazo);
				
				p.setEstado(rs.getInt("estado"));
				
				lista.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	
	}


}
