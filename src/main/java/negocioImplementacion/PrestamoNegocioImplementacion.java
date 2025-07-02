package negocioImplementacion;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import daoImplementacion.MovimientoDaoImplementacion;
import daoImplementacion.PrestamoDaoImplementacion;
import entidades.Cliente;
import entidades.Movimiento;
import entidades.MovimientoTipo;
import entidades.Prestamo;
import excepciones.MontoInvalidoException;
import negocio.PrestamoNegocio;

public class PrestamoNegocioImplementacion implements PrestamoNegocio{

	@Override
	public boolean solicitarPrestamo(int idCliente, int idCuenta, BigDecimal monto, int idPlazo) {
		PrestamoDaoImplementacion pdi= new PrestamoDaoImplementacion();
		return pdi.ejecutarSPsolicitarPrestamo(idCliente, idCuenta, monto, idPlazo);
	}
	
	@Override
	public boolean autorizarPrestamo(int id) {
		PrestamoDaoImplementacion pdi= new PrestamoDaoImplementacion();
		boolean autorizado = pdi.autorizarPrestamo(id);

	    if (autorizado) {
	        // Buscar el préstamo recién autorizado
	        Prestamo prestamoAutorizado = null;
	        for (Prestamo p : pdi.listar()) {
	            if (p.getId() == id) {
	                prestamoAutorizado = p;
	                break;
	            }
	        }

	        if (prestamoAutorizado == null) {
	            System.out.println("Préstamo no encontrado");
	            return false;
	        }

	        
	        Movimiento movimiento = new Movimiento();
	        movimiento.setCuenta(prestamoAutorizado.getCuenta());
	        movimiento.setCliente(prestamoAutorizado.getCliente());
	        movimiento.setFecha(LocalDateTime.now());
	        movimiento.setImporte(prestamoAutorizado.getImportePedido().doubleValue());
	        movimiento.setDetalle("Préstamo acreditado");

	        MovimientoTipo tipo = new MovimientoTipo();
	        tipo.setId(2); 
	        movimiento.setTipoMovimiento(tipo);

	        MovimientoDaoImplementacion mdao = new MovimientoDaoImplementacion();
	        return mdao.insertarMovimiento(movimiento);
	    }

	    return false;
	}
	

	@Override
	public boolean rechazarPrestamo(int id) {
		PrestamoDaoImplementacion pdi= new PrestamoDaoImplementacion();
		return pdi.rechazarPrestamo(id);
	}

	@Override
	public BigDecimal calcularCuota(BigDecimal monto, int idPlazo) {
		PrestamoDaoImplementacion pdi= new PrestamoDaoImplementacion();
		return pdi.obtenerCuotaDesdeBD(monto, idPlazo);
	}

	@Override
	public List<Prestamo> listarPrestamos() {
		PrestamoDaoImplementacion pdi= new PrestamoDaoImplementacion();
		return pdi.listar();
	}
	
	@Override
	public List<Prestamo> listarPrestamosPorCliente(int idCliente){
		PrestamoDaoImplementacion pdi= new PrestamoDaoImplementacion();
		return pdi.listarPrestamosPorCliente(idCliente);
	}
	
	public void validarMonto(BigDecimal monto) throws MontoInvalidoException {
	    BigDecimal MONTO_MINIMO = new BigDecimal("500000");
	    BigDecimal MONTO_MAXIMO = new BigDecimal("150000000");

	    if (monto.compareTo(MONTO_MINIMO) < 0) {
	        throw new MontoInvalidoException("El monto mínimo es $500.000.");
	    }

	    if (monto.compareTo(MONTO_MAXIMO) > 0) {
	        throw new MontoInvalidoException("El monto no puede superar los $150.000.000.");
	    }
	}
	
}
