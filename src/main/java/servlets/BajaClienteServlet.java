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
	private String rutaListarClienteJSP = "/vistas/Admin/ABMLCliente/ListarCliente.jsp";

	public BajaClienteServlet() {
		super();
		clienteNegocio = new ClienteNegocioImplementacion();
		cliente = null;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Redirigo a listar
		if (request.getParameter("btnVolverAListar") != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(rutaListarClienteJSP);
			dispatcher.forward(request, response);
		}

		HttpSession session = request.getSession();

//		if (request.getParameter("vieneDeListar") == null || request.getParameter("dni") == null
//				|| request.getParameter("cuil") == null)
//			ruta.redirigir(request, response, "error", "Hubo un error al buscar el cliente. Intentelo manualmente",
//					rutaEliminarJSP);

		String dni = (String) request.getParameter("dni");
		String cuil = (String) request.getParameter("cuil");
		cliente = clienteNegocio.getCliente(dni, cuil);

		if (cliente == null) {
			request.setAttribute("modalError", true);
			ManejarDispatch.redirigir(request, response, "error", "Hubo un error al buscar el cliente. Intentelo manualmente",
					rutaEliminarJSP);
			return;
		}

		session.setAttribute("objCliente", cliente);
		response.sendRedirect(request.getContextPath() + rutaEliminarJSP);
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
//		String dni = null;
//		String cuil = null;
//
//		if (request.getParameter("btnBuscar") != null) {
//
//			if (request.getParameter("DNICliente") == null || request.getParameter("DNICliente") == null)
//				ruta.redirigir(request, response, "error", "Ingrese un número de DNI y CUIL valido", rutaEliminarJSP);
//
//			dni = request.getParameter("DNICliente");
//			cuil = request.getParameter("CUILCliente");
//
//			cliente = clienteNegocio.getCliente(dni, cuil);
//
//			if (cliente == null)
//				ruta.redirigir(request, response, "error", "No se encontro un registro asociado", rutaEliminarJSP);
//
//			session.setAttribute("objCliente", cliente);
//			response.sendRedirect(request.getContextPath() + rutaEliminarJSP);
//			return;
//		}

		if (request.getParameter("btnEliminar") != null) {

			if (session.getAttribute("objCliente") != null) {
				cliente = (Cliente) session.getAttribute("objCliente");
			} else {
				request.setAttribute("modalError", true);
				ManejarDispatch.redirigir(request, response, "error", "Ocurrio un error al eliminar", rutaEliminarJSP);
				return;
			}

			if (!cliente.getEstado()) {
				request.setAttribute("modalError", true);
				ManejarDispatch.redirigir(request, response, "error", "El cliente ya se encuentra inactivo", rutaEliminarJSP);
				return;
			}

			if (clienteNegocio.tienePrestamoActivo(cliente.getId())) {
				request.setAttribute("modalError", true);
				ManejarDispatch.redirigir(request, response, "error", "No es posible eliminar el cliente. Posee prestamos activos",
						rutaEliminarJSP);
				return;
			}

			if (!clienteNegocio.bajaLogica(cliente.getDNI(), cliente.getCUIL())) {
				request.setAttribute("modalError", true);
				ManejarDispatch.redirigir(request, response, "error",
						"No fue posible eliminar el Cliente " + cliente.getNombre() + ", " + cliente.getApellido(),
						rutaEliminarJSP);
				return;
			}

			request.setAttribute("mostrarModalClienteEliminado", true);
			ManejarDispatch.redirigir(request, response,
					"procesoExitoso", "El cliente se elimino correctamente", rutaEliminarJSP);
			return;
		}
	}
}
