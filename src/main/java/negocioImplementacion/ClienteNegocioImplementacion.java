package negocioImplementacion;

import java.util.ArrayList;
import java.util.List;

import daoImplementacion.ClienteDaoImplementacion;
import entidades.Cliente;
import negocio.ClienteNegocio;

public class ClienteNegocioImplementacion implements ClienteNegocio {
	public Boolean insertar(Cliente cliente) {
		
			ClienteDaoImplementacion clienteAux = new ClienteDaoImplementacion();
			return clienteAux.insertar(cliente);
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

	public Boolean existe(int idCliente) {
		return true;
	}
}
