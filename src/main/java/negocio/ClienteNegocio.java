package negocio;

import java.util.List;

import entidades.Cliente;

public interface ClienteNegocio {
	public Boolean insertar(Cliente cliente);
	public Boolean modificar(Cliente cliente);
	public Boolean bajaLogica(int idCliente);
	public List<Cliente> listar();
	public Boolean existe(int idCliente);
}
