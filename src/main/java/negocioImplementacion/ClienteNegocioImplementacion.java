package negocioImplementacion;

import java.util.ArrayList;
import java.util.List;

import daoImplementacion.ClienteDaoImplementacion;
import daoImplementacion.CuentaDaoImplementacion;
import entidades.Cliente;
import negocio.ClienteNegocio;

public class ClienteNegocioImplementacion implements ClienteNegocio {
	
	private ClienteDaoImplementacion clienteNegocio;
	
	public ClienteNegocioImplementacion() {
		clienteNegocio = new ClienteDaoImplementacion();
	}
	
	public Boolean insertar(Cliente cliente) {
		
			ClienteDaoImplementacion clienteNegocio = new ClienteDaoImplementacion();
			return clienteNegocio.insertar(cliente);
	}

	public Boolean modificar(Cliente cliente) {
		return true;
	}

	public Boolean bajaLogica(String dni) {
		return clienteNegocio.bajaLogica(dni);
	}

	public List<Cliente> listar() {
		List<Cliente> lista = new ArrayList<Cliente>();
		return lista;
	}

	public Boolean existeDNI(String DNI) {
		
		return clienteNegocio.existeDNI(DNI);
	}
	
	public Boolean existeCUIL(String CUIL) {
		return clienteNegocio.existeCUIL(CUIL);
	}

	public Cliente clientePorDNI(String dni) {
		return clienteNegocio.clientePorDNI(dni);
	}
	
	public Cliente clientePorDNI(int dni) {
		return clienteNegocio.clientePorDNI(dni);
	}
	
	
	public Cliente obtenerClientePorIdUsuario(int idUsuario) {
	    return clienteNegocio.obtenerClientePorIdUsuario(idUsuario);
	}
	
	@Override
	public boolean admiteNuevaCuenta(int idCliente) {
	    CuentaDaoImplementacion cuentas = new CuentaDaoImplementacion();
	    int cuentasActivas = cuentas.cuentasActivas(idCliente);
	    return cuentasActivas < 3;
	}
}
