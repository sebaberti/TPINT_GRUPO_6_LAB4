package servlets;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import daoImplementacion.ClienteDaoImplementacion;
import daoImplementacion.PaisDaoImplementacion;
import entidades.Cliente;
import entidades.Localidad;
import entidades.Pais;
import entidades.Provincia;
import negocioImplementacion.ClienteNegocioImplementacion;
import negocioImplementacion.LocalidadNegocioImplementacion;
import negocioImplementacion.PaisNegocioImplementacion;
import negocioImplementacion.ProvinciaNegocioImplementacion;
import utilidades.ClienteHelper;
import utilidades.ManejarDispatch;

@WebServlet("/ModificarClienteServlet")
public class ModificarClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ClienteNegocioImplementacion clienteNegocio;
	LocalidadNegocioImplementacion localidadNegocio;
	PaisNegocioImplementacion paisNegocio;
	ProvinciaNegocioImplementacion provinciaNegocio;
	ArrayList<Localidad> localidades;
	ArrayList<Provincia> provincias;
	ArrayList<Pais> paises;
	Cliente cliente;
	private String rutaModificarClienteJSP = "/vistas/Admin/ABMLCliente/ModificarCliente.jsp";
	private String rutaListarClienteJSP = "/vistas/Admin/ABMLCliente/ListarCliente.jsp";

	public ModificarClienteServlet() {
		super();
		localidadNegocio = new LocalidadNegocioImplementacion();
		provinciaNegocio = new ProvinciaNegocioImplementacion();
		paisNegocio = new PaisNegocioImplementacion();
		localidades = new ArrayList<Localidad>();
		provincias = new ArrayList<Provincia>();
		paises = new ArrayList<Pais>();
		clienteNegocio = new ClienteNegocioImplementacion();
		cliente = null;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Redirigo a listar
		if (request.getParameter("btnVolverAListar") != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/ListarClientesServlet");
			dispatcher.forward(request, response);
			return;
		}

		// Si el cliente se modifica con exito, redirigo a listar nuevamente al cerrar
		// el modal.
		if (request.getParameter("btnModalClienteModificado") != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(rutaListarClienteJSP);
			dispatcher.forward(request, response);
		}

		// Capturo el cliente que viene de ListarClientes -> BtnModificar
		String dni = request.getParameter("dni");
		String cuil = request.getParameter("cuil");

		try {
			if (!estaNullVacio(dni) && !estaNullVacio(cuil)) {
				request.setAttribute("modalError", true);
				ManejarDispatch.redirigir(request, response, "error",
						"Ocurrio un error al cargar el cliente, intentelo nuevamente.", rutaModificarClienteJSP);
				return;
			}

			cliente = clienteNegocio.getCliente(dni, cuil);

			if (cliente != null) {
				request.setAttribute("cliente", cliente);
				// Guardo los datos originales para completar campos vacios al generar una
				// peticion del select Provincia o recargar los datos si intenta modificar un
				// cliente sin datos nuevos.
				request.getSession().setAttribute("clienteOriginal", cliente);
			} else {
				request.setAttribute("modalError", true);
				ManejarDispatch.redirigir(request, response, "error",
						"Ocurrio un error al intentar capturar los campos. Intentelo nuevamente",
						rutaModificarClienteJSP);
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("modalError", true);
			ManejarDispatch.redirigir(request, response, "error",
					"Ocurrio un error al intentar capturar los campos DNI y CUIL. Intentelo nuevamente",
					rutaModificarClienteJSP);
			return;
		}

		// Seteo los valores originales de nacionalidad, localidad y provincia del
		// cliente para capturar en <select>
		request.setAttribute("nacionalidadElegida", cliente.getNacionalidad().getId());
		request.setAttribute("localidadElegida", cliente.getDomicilio().getLocalidad().getId());
		request.setAttribute("provinciaElegida", cliente.getDomicilio().getLocalidad().getProvincia().getId());

		// Se cargan los desplegables.
		ClienteHelper.cargarDesplegables(request, response);

		RequestDispatcher dispatcher = request.getRequestDispatcher(rutaModificarClienteJSP);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// En cada petición generada por el select Provincia recarga los desplegables
		// para filtrar el listado de localidades.
		ClienteHelper.cargarDesplegables(request, response);
		// Guardo los datos del form por cada post generado por el desplegable
		// Provincia.
		cliente = ClienteHelper.capturarCamposModificar(request);
		if (cliente != null) {
			request.setAttribute("cliente", cliente);
		}

		if (request.getParameter("btnModificar") != null) {

			if (cliente != null) {

				// Evaluo que el los datos hayan cambiado antes de modificar
				Cliente clienteAux = (Cliente) request.getSession().getAttribute("clienteOriginal");
				if (clienteAux.equals(cliente)) {
					request.setAttribute("cliente", clienteAux);
					request.setAttribute("modalError", true);
					ManejarDispatch.redirigir(request, response, "error", "Realice cambios para modificar el cliente.",
							rutaModificarClienteJSP);
					return;
				}

				try {

					request.setAttribute("cliente", cliente);
					boolean exito = clienteNegocio.modificar(cliente);

					if (exito) {
						request.setAttribute("mostrarModalClienteModificado", true);
						request.getRequestDispatcher("/vistas/Admin/ABMLCliente/ModificarCliente.jsp").forward(request,
								response);
						return;
					} else {
						request.setAttribute("modalError", true);
						ManejarDispatch.redirigir(request, response, "error",
								"Ocurrio un error al modificar el cliente.", rutaModificarClienteJSP);
					}
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("modalError", true);
					ManejarDispatch.redirigir(request, response, "error",
							"Ocurrió un error inesperado al intentar modificar el cliente.", rutaModificarClienteJSP);
				}
			}
		}

		request.getRequestDispatcher("/vistas/Admin/ABMLCliente/ModificarCliente.jsp").forward(request, response);
		return;
	}

	private boolean estaNullVacio(String valor) {
		return valor != null && !valor.isEmpty();
	}
}
