package dao;

import java.util.List;

import entidades.Cliente;

public interface ClienteDao {
	public Boolean insertar(Cliente cliente);
	public Boolean modificar(Cliente cliente);
	public Boolean bajaLogica(int idCliente);
	public List<Cliente> listar();
	public Boolean existe(int idCliente);
}
