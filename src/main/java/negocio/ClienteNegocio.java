package negocio;

import java.util.List;

import entidades.Cliente;

public interface ClienteNegocio {
	public Boolean insertar(Cliente cliente);
	public Boolean modificar(Cliente cliente);
	public Boolean bajaLogica(int idCliente);
	public List<Cliente> listar();
	public Boolean existeDNI(String DNI);
	public Boolean existeCUIL(String CUIL);
	public Cliente clientePorDNI(int dni);
}
