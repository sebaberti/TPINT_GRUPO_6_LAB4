package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import entidades.Cliente;
import utilidades.ManejarDispatch;
import negocioImplementacion.ClienteNegocioImplementacion;

/**
 * Servlet implementation class BajaClienteServlet
 */
@WebServlet("/BajaClienteServlet")
public class BajaClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	HttpSession session;
	private ClienteNegocioImplementacion clienteNegocio;
	private Cliente cliente;
	private String rutaEliminarJSP = "/vistas/Admin/ABMLCliente/EliminarCliente.jsp";

	public BajaClienteServlet() {
		super();
		clienteNegocio = new ClienteNegocioImplementacion();
		cliente = null;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if(request.getParameter("ConfirmarEliminacion") != null && Boolean.parseBoolean(request.getParameter("ConfirmarEliminacion")) == true) {
			if(eliminarCliente(request, response)) {
				request.setAttribute("mostrarModalClienteEliminado", true);
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(rutaEliminarJSP);
			dispatcher.forward(request, response);
			return;
		}

		// Redirigo a listar
		if (request.getParameter("btnVolverAListar") != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/ListarClientesServlet");
			dispatcher.forward(request, response);
			return;
		}

		HttpSession session = request.getSession();

		String dni = (String) request.getParameter("dni");
		String cuil = (String) request.getParameter("cuil");
		cliente = clienteNegocio.getCliente(dni, cuil);

		if (cliente == null) {
			request.setAttribute("modalError", true);
			ManejarDispatch.redirigir(request, response, "error",
					"Hubo un error al buscar el cliente. Intentelo manualmente", rutaEliminarJSP);
			return;
		}

		session.setAttribute("objCliente", cliente);
		response.sendRedirect(request.getContextPath() + rutaEliminarJSP);
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if (request.getParameter("btnEliminar") != null) {
			request.setAttribute("ModalConfirmarEliminacion", true);
			RequestDispatcher dispatcher = request.getRequestDispatcher(rutaEliminarJSP);
			dispatcher.forward(request, response);
		}

	}

	private Boolean eliminarCliente(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();

		if (session.getAttribute("objCliente") != null) {
			cliente = (Cliente) session.getAttribute("objCliente");
		} else {
			request.setAttribute("modalError", true);
			request.setAttribute("error", "Ocurrio un error al eliminar");
			return false;
		}

		if (!cliente.getEstado()) {
			request.setAttribute("modalError", true);
			request.setAttribute("error", "El cliente ya se encuentra inactivo");
			return false;
		}

		if (clienteNegocio.tienePrestamoActivo(cliente.getId())) {
			request.setAttribute("modalError", true);
			request.setAttribute("error","No es posible eliminar el cliente. Posee prestamos activos");
			return false;
		}

		if (!clienteNegocio.bajaLogica(cliente.getDNI(), cliente.getCUIL())) {
			request.setAttribute("modalError", true);
			request.setAttribute("error", "No fue posible eliminar el Cliente " + cliente.getNombre() + ", " + cliente.getApellido());
			return false;
		}
		return true;
	}
}
