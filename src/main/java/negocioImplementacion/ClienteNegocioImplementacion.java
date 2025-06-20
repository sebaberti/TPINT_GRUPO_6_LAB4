package negocioImplementacion;

import java.util.ArrayList;
import java.util.List;

import daoImplementacion.ClienteDaoImplementacion;
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

	public Boolean bajaLogica(int idCliente) {
		return true;
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
}
