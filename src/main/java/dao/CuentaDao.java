package dao;

import java.math.BigInteger;
import java.util.List;

import entidades.Cuenta;

public interface CuentaDao {
	public boolean insertarCuenta(Cuenta cuenta);
	public List<Cuenta> listar();
	public List<Cuenta> listarPorDNI(int dni);
	public Cuenta obtenerCuentaPorCBU(String cbu);
	public Cuenta obtenerCuentaPorId(int id);
	public List<Cuenta> listarCuentasPorClienteId(int clienteId,boolean soloActivas);
	public int cuentasActivas(int idCliente);
	public boolean existeCbu(BigInteger cbu);
	public String obtenerUltimoNumeroCuenta();
	boolean bajaLogica(int idCuenta);
	public boolean modificarCuenta(Cuenta cuenta);
	public int contarCuentas();
	public Boolean tienePrestamoActivo(int idCuenta);
	public boolean actualizarEstado(int idCuenta, boolean nuevoEstado);
	public List<Cuenta> listarPorNro(String nro);
	public List<Cuenta> listarPorCBU(BigInteger cbu);
}
