package negocioImplementacion;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import daoImplementacion.CuentaDaoImplementacion;
import daoImplementacion.MovimientoDaoImplementacion;
import entidades.Cliente;
import entidades.Cuenta;
import entidades.CuentaTipo;
import entidades.Movimiento;
import entidades.MovimientoTipo;
import entidades.Usuario;
import negocio.CuentaNegocio;
import validaciones.ExcepLimiteCtasActivas;

public class CuentaNegocioImplementacion implements CuentaNegocio {
	private CuentaDaoImplementacion cuentaDao = new CuentaDaoImplementacion();
    private ClienteNegocioImplementacion clienteNeg = new ClienteNegocioImplementacion();
    private CuentaTipoNegocioImplementacion tipoDao = new CuentaTipoNegocioImplementacion();

	@Override
	public boolean insertarCuenta(Cuenta cuenta) {

		cuenta.setCBU(generarCBU()); // Generar el CBU único
		java.util.Date fechaActual = new java.util.Date();
		cuenta.setFechaCreacion(new java.sql.Date(fechaActual.getTime())); // seteo fecha actual
		cuenta.setNumeroCuenta(generarNroCta());
		cuenta.setEstado(true);
		
		boolean insertada = cuentaDao.insertarCuenta(cuenta);

	    if (insertada) {
	        // Crear movimiento por apertura
	        Movimiento movimiento = new Movimiento();
	        movimiento.setCuenta(cuenta);
	        movimiento.setCliente(cuenta.getCliente());
	        movimiento.setFecha(LocalDateTime.now());
	        movimiento.setImporte(cuenta.getSaldo());
	        movimiento.setDetalle("Alta de cuenta");

	        MovimientoTipo tipoMovimiento = new MovimientoTipo();
	        tipoMovimiento.setId(1); // Id 1 es "Alta de cuenta"
	        movimiento.setTipoMovimiento(tipoMovimiento);

	        MovimientoDaoImplementacion movDao = new MovimientoDaoImplementacion();
	        return movDao.insertarMovimiento(movimiento);
	    }

	    return false;
	}
	

	private BigInteger generarCBU() {
		
		// Generar un número de 22 dígitos
		BigInteger cbu = null;

		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		boolean yaExisteCBU = true;

		while (yaExisteCBU) { // si el cbu ya existia, crea otro
			for (int i = 0; i < 22; i++) {
				sb.append(random.nextInt(10));
			}
			cbu = new BigInteger(sb.toString());

			yaExisteCBU = validarCBU(cbu);
		}
		return cbu;
	}


	private boolean validarCBU(BigInteger cbu) {
		return cuentaDao.existeCbu(cbu);
	}


	private String generarNroCta() {
		String ultimoNumeroCuenta = cuentaDao.obtenerUltimoNumeroCuenta(); // ejemplo: "12345678-0004"

		String baseFija = "12345678";

		int nuevoNumero = 0;

		if (ultimoNumeroCuenta != null && ultimoNumeroCuenta.contains("-")) {
			String[] partes = ultimoNumeroCuenta.split("-");
			try {
				int numeroActual = Integer.parseInt(partes[1]);
				nuevoNumero = numeroActual + 1;
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		String sufijoFormateado = String.format("%04d", nuevoNumero); // Formatea con ceros a la izquierda

		return baseFija + "-" + sufijoFormateado;
	}

	@Override
	public List<Cuenta> listarCuentas() {
		return cuentaDao.listar();
	}
	
	@Override
	public List<Cuenta> listarCuentasPorDNI(int dni) {
		return cuentaDao.listarPorDNI(dni);
	}
	
	@Override
	public List<Cuenta> listarCuentasPorClienteId(int clienteId,boolean soloActivas) {
	    return cuentaDao.listarCuentasPorClienteId(clienteId,soloActivas); 
	}

	@Override
	public boolean existeCuenta(Cuenta cuenta) {
		List<Cuenta> cuentas = listarCuentas();
		
		for (Cuenta  cta : cuentas) {
			if(cta.equals(cuenta))
				return true;
		}
		return false;
	}	
	
	@Override
	public Cuenta obtenerCuentaPorCBU(String cbu) {
	    return cuentaDao.obtenerCuentaPorCBU(cbu);
	}

	@Override
	public Cuenta obtenerCuentaPorId(int id) {
	    return cuentaDao.obtenerCuentaPorId(id);
	}

	@Override
	public List<Cuenta> listarPorNro(String nro) {
		return cuentaDao.listarPorNro(nro);
	}

	@Override
	public List<Cuenta> listarPorCBU(BigInteger cbu) {
		return cuentaDao.listarPorCBU(cbu);
	}
	@Override
	public boolean bajaLogica(int idCuenta) {
	    return cuentaDao.bajaLogica(idCuenta);
	}
	
	@Override
	public boolean modificarCuenta(Cuenta cuenta) {
	    return cuentaDao.modificarCuenta(cuenta);
	}

	@Override
	public Boolean tienePrestamoActivo(int idCuenta) {
	    return cuentaDao.tienePrestamoActivo(idCuenta);
	}
	
	@Override
	public int cuentasActivas(int idCliente) {
	    return cuentaDao.cuentasActivas(idCliente);
	}
	
	@Override
	public boolean actualizarEstado(int idCuenta, boolean nuevoEstado) {
	    Cuenta cuenta = cuentaDao.obtenerCuentaPorId(idCuenta);

	    if (cuenta == null) {
	        return false;
	    }

	    
	    if (nuevoEstado) {  // Si estamos reactivando la cuenta, validamos que no haya 3 ctas activas del cliente
	        int idCliente = cuenta.getCliente().getId();
	        List<Cuenta> cuentasActivas = cuentaDao.listarCuentasPorClienteId(idCliente, true);

	        if (cuentasActivas.size() >= 3) {
	            return false;
	        }
	    }

	    return cuentaDao.actualizarEstado(idCuenta, nuevoEstado);
	}

}
