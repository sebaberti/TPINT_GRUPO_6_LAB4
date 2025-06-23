package daoImplementacion;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.List;

import dao.PrestamoDao;
import entidades.Cliente;
import entidades.Cuenta;
import entidades.Prestamo;

public class PrestamoDaoImplementacion implements PrestamoDao{

	@Override
	public boolean ejecutarSPsolicitarPrestamo(Prestamo prestamo, Cliente cliente) {
	boolean estado= false;
		 try
		  {
			 Connection conexion = Conexion.getConexion().getSQLConexion();
			 CallableStatement cst = conexion.prepareCall("CALL SolicitarPrestamo(?,?,?,?)");
			 cst.setInt(1, cliente.getId());  
			 Cuenta aux= prestamo.getCuenta();
		     cst.setInt(2, aux.getId());             
		     cst.setBigDecimal(3, prestamo.getImportePedido()); 
		     cst.setInt(4, prestamo.getPlazo().getId());  
			 estado=cst.execute();		
			 
			 conexion.commit();

		    return estado;
		  }
		  catch (Exception e) {
			e.printStackTrace();
			return false;
		  }
	}
/*
	@Override
	public boolean modificar(Prestamo prestamo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean bajaLogica(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Prestamo> listarPrestamos() {
		// TODO Auto-generated method stub
		return null;
	}
*/



}
