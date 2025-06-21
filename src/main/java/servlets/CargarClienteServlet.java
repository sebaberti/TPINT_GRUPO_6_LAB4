package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import daoImplementacion.ClienteDaoImplementacion;
import entidades.Cliente;

@WebServlet("/CargarClienteServlet")
public class CargarClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CargarClienteServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String dni = request.getParameter("dni");
		String cuil = request.getParameter("cuil");

		ClienteDaoImplementacion clienteDao = new ClienteDaoImplementacion();
		List<Cliente> lista = clienteDao.buscar(dni, cuil);

		if (lista != null && !lista.isEmpty()) {
			Cliente cliente = lista.get(0); 
			request.setAttribute("cliente", cliente);
			request.getRequestDispatcher("/vistas/Admin/ABMLCliente/ModificarCliente.jsp").forward(request, response);
		} else {
			request.setAttribute("error", "No se encontró ningún cliente con los datos ingresados.");
			request.getRequestDispatcher("/vistas/Admin/ABMLCliente/ModificarCliente.jsp").forward(request, response);
		}
	}
}
