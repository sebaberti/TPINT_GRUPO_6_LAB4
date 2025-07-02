package negocio;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import entidades.Cuenta;
import validaciones.ExcepLimiteCtasActivas;

public interface CuentaNegocio {
	public boolean insertarCuenta(Cuenta cuenta);
	public List<Cuenta> listarCuentas();
	public boolean existeCuenta(Cuenta cuenta);
	List<Cuenta> listarCuentasPorDNI(int dni);
	public Cuenta obtenerCuentaPorCBU(String cbu);
	public Cuenta obtenerCuentaPorId(int id);
	List<Cuenta> listarCuentasPorClienteId(int clienteId, boolean soloActivas);
	public boolean bajaLogica(int idCuenta);
	public boolean modificarCuenta(Cuenta cuenta);
	public Boolean tienePrestamoActivo(int idCuenta);
	public int cuentasActivas(int idCliente);
	public boolean actualizarEstado(int idCuenta, boolean nuevoEstado);
	public List<Cuenta> listarPorNro(String nro);
	public List<Cuenta> listarPorCBU(BigInteger cbu);
	public int contarCuentas(LocalDate desde,LocalDate hasta);
	public int cantidadClientesxPeriodo(LocalDate desde,LocalDate hasta);
	public double promedioCuentasXCliente(LocalDate desde,LocalDate hasta);
}


