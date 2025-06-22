package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidades.Cliente;
import negocioImplementacion.ClienteNegocioImplementacion;

/**
 * Servlet implementation class BajaClienteServlet
 */
@WebServlet("/BajaClienteServlet")
public class BajaClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

		if (request.getParameter("btnBuscar") != null) {
			obtenerRegistro(request, response);
		}

		if (request.getParameter("btnEliminar") != null) {
			eliminarRegistro(request, response);
		}
	}

	private String capturarCamposBusqueda(HttpServletRequest request) {

		String DNI = "";
		DNI = request.getParameter("DNICliente");
		return DNI;
	}

	private void obtenerRegistro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String DNI = capturarCamposBusqueda(request);

		if (DNI.isEmpty() || DNI == null) {
			redirigir(request, response, "error", "Error al capturar el DNI", rutaEliminarJSP);
		}

		if (!clienteNegocio.existeDNI(DNI)) {
			redirigir(request, response, "error", "Cliente no fue encontrado", rutaEliminarJSP);
		}
		
		cliente = clienteNegocio.clientePorDNI(DNI);
		
		if(cliente == null) 
			redirigir(request, response, "error", "Error al buscar el cliente", rutaEliminarJSP);
		
		request.getSession().setAttribute("dni", DNI);
		request.getSession().setAttribute("estado", cliente.getEstado());
		redirigir(request, response, "cliente", cliente, rutaEliminarJSP);
	}

	private void eliminarRegistro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("btnEliminar") != null) {

			String dni = (String) request.getSession().getAttribute("dni");
			Boolean estado = (Boolean) request.getSession().getAttribute("estado");
			
			if (dni.isEmpty() || dni == null) {
				redirigir(request, response, "error", "No fue posible encontrar el cliente", rutaEliminarJSP);
			}
			
			if(!estado) {
				redirigir(request, response, "error", "El cliente ya se encuentra inactivo", rutaEliminarJSP);
			}

			if (!clienteNegocio.bajaLogica(dni)) {
				redirigir(request, response, "error", "No fue posible eliminar el CLIENTE", rutaEliminarJSP);
			}
			
			redirigir(request, response, "procesoExitoso", "El cliente se elimino correctamente", rutaEliminarJSP);
		}
	}
	
	private void redirigir(HttpServletRequest request, HttpServletResponse response, String nombreAtributo,
			String msjError, String ruta) throws ServletException, IOException {
		request.setAttribute(nombreAtributo, msjError);
		request.getRequestDispatcher(ruta).forward(request, response);
	}
	
	private void redirigir(HttpServletRequest request, HttpServletResponse response, String nombreAtributo,
			Cliente cliente, String ruta) throws ServletException, IOException {
		request.setAttribute(nombreAtributo, cliente);
		request.getRequestDispatcher(ruta).forward(request, response);
	}

}
