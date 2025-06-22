package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daoImplementacion.ClienteDaoImplementacion;
import entidades.Cliente;

@WebServlet("/ModificarClienteServlet")
public class ModificarClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ModificarClienteServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ClienteDaoImplementacion clienteDao = new ClienteDaoImplementacion();

		try {
			int idCliente = Integer.parseInt(request.getParameter("idCliente"));
			String cuil = request.getParameter("cuil");
			String nombre = request.getParameter("nombre");
			String apellido = request.getParameter("apellido");
			String email = request.getParameter("email");
			String telefono = request.getParameter("telefono");
			String dni = request.getParameter("dni");

			
			System.out.println(cuil + " <- ");
			System.out.println(nombre + " <- ");
			System.out.println(apellido + " <- ");
			System.out.println(email + " <- ");
			System.out.println(telefono + " <- ");

			Cliente cliente = new Cliente();
			cliente.setId(idCliente);
			cliente.setCUIL(cuil);
			cliente.setNombre(nombre);
			cliente.setApellido(apellido);
			cliente.setEmail(email);
			cliente.setTelefono(telefono);
			cliente.setDNI(dni);

			boolean exito = clienteDao.modificar(cliente);

			if (exito) {
				response.sendRedirect("ListarClientesServlet");
			} else {
				request.setAttribute("error", "No se pudo modificar el cliente.");
				request.getRequestDispatcher("/vistas/Admin/ABMLCliente/ModificarCliente.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Ocurrió un error inesperado.");
			request.getRequestDispatcher("/vistas/Admin/ABMLCliente/ModificarCliente.jsp").forward(request, response);
		}
	}
}
