package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entidades.Cliente;
import negocioImplementacion.ClienteNegocioImplementacion;

@WebServlet("/AltaClienteServlet")
public class AltaClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ClienteNegocioImplementacion clienteNegocio;

	private String rutaAltaClienteJSP = "/vistas/Admin/ABMLCliente/AltaCliente.jsp";

	public AltaClienteServlet() {
		super();
		clienteNegocio = new ClienteNegocioImplementacion();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Cliente cliente = new Cliente();
		cliente = capturarCampos(request);
		request.setAttribute("cliente", cliente);

		if (cliente.getId() != 1) {
			redirigir(request, response, "error", "Error al capturar los campos", rutaAltaClienteJSP);
		}
		
		existeCliente(request, response, cliente);

//		try {
//			clienteNegocio.insertar(cliente);
//			redirigir(request, response, "clienteInsertado",
//					"El cliente " + cliente.getNombre() + " " + cliente.getApellido() + " se inserto correctamente",
//					rutaAltaClienteJSP);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			redirigir(request, response, "error", "Error al insertar", rutaAltaClienteJSP);
//		}

		
	};

	private Cliente capturarCampos(HttpServletRequest request) {

		Cliente cliente = new Cliente();

		if (request.getParameter("btnAltaCliente") != null) {

			try {
				cliente.setId(1);
				cliente.setDNI(request.getParameter("DNICliente"));
				cliente.setCUIL(request.getParameter("CUILCliente"));
				cliente.setNombre(request.getParameter("nombreCliente"));
				cliente.setApellido(request.getParameter("apellidoCliente"));
				cliente.setSexo("M");
				// cliente.getNacionalidad.setPais("Argentino");
				// cliente.setFecha_nacimiento();
				// cliente.getDireccion.setDireccion(request.getParameter("lblDireccion"));
				// cliente.getDireccion.setLocalidad(request.getParameter("lblLocalidad"));
				// cliente.getDireccion.setProvincia(request.getParameter("lblProvincia"));
				cliente.setEmail(request.getParameter("emailCliente"));
				cliente.setTelefono(request.getParameter("telefonoCliente"));
				cliente.setEstado(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return cliente;
	}

	private void redirigir(HttpServletRequest request, HttpServletResponse response, String nombreAtributo,
			String msjError, String ruta) throws ServletException, IOException {
		request.setAttribute(nombreAtributo, msjError);
		request.getRequestDispatcher(ruta).forward(request, response);
	}
	
	private void existeCliente(HttpServletRequest request, HttpServletResponse response, Cliente cliente) throws ServletException, IOException {
		
		Boolean dni = clienteNegocio.existeDNI(cliente.getDNI());
		Boolean cuil = clienteNegocio.existeCUIL(cliente.getCUIL());
		
		if (dni && cuil) {
			redirigir(request, response, "error", "El DNI " + cliente.getDNI() + " y el CUIL " + cliente.getCUIL() + " ya existen", rutaAltaClienteJSP);
		}

		if (dni) {
			redirigir(request, response, "error", "El DNI " + cliente.getDNI() + " ya existe", rutaAltaClienteJSP);
		}
		
		if (cuil) {
			redirigir(request, response, "error", "El CUIL " + cliente.getCUIL() + " ya existe", rutaAltaClienteJSP);
		}
	}
}
