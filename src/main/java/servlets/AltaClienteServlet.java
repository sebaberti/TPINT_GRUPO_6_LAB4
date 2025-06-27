package servlets;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import entidades.Cliente;
import entidades.Direccion;
import entidades.Localidad;
import entidades.Pais;
import entidades.Provincia;
import negocioImplementacion.ClienteNegocioImplementacion;

@WebServlet("/AltaClienteServlet")
public class AltaClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ClienteNegocioImplementacion clienteNegocio;
	HttpSession session;

	private String rutaAltaClienteJSP = "/vistas/Admin/ABMLCliente/AltaCliente.jsp";

	public AltaClienteServlet() {
		super();
		clienteNegocio = new ClienteNegocioImplementacion();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		session = request.getSession();
		
		Cliente cliente = new Cliente();
		cliente = capturarCampos(request);
		
		if(cliente != null) 
			session.setAttribute("cliente", cliente);
		
		request.setAttribute("cliente", cliente);

		existeCliente(request, response, cliente);
		
		if (cliente.getId() != 1) {
			redirigir(request, response, "error", "Error al capturar los campos", rutaAltaClienteJSP);
		}
		
		response.sendRedirect(request.getContextPath() + "/vistas/Admin/ABMLUsuario/AltaUsuario.jsp");
	};

	private Cliente capturarCampos(HttpServletRequest request) {

		Cliente cliente = new Cliente();

		if (request.getParameter("btnAltaCliente") != null) {

			try {
				 	cliente.setDNI(request.getParameter("DNICliente"));
		            cliente.setCUIL(request.getParameter("CUILCliente"));
		            cliente.setNombre(request.getParameter("nombreCliente"));
		            cliente.setApellido(request.getParameter("apellidoCliente"));
		            cliente.setSexo(request.getParameter("selectSexo"));		           
		            Pais pais = new Pais();
		            pais.setId(Integer.parseInt(request.getParameter("selectNacionalidad")));
		            cliente.setNacionalidad(pais);		           
		            String fechaNac = request.getParameter("fechaNacimientoCliente");
		            if (fechaNac != null && !fechaNac.isEmpty()) {
		                Date fecha = Date.valueOf(fechaNac); 
		                cliente.setFecha_nacimiento(fecha);
		            }		          
		            Direccion direccion = new Direccion();
		            direccion.setDireccion(request.getParameter("direccionCliente"));
		            Provincia provincia = new Provincia();
		            provincia.setId(Integer.parseInt(request.getParameter("selectProvincia")));
		            direccion.setProvincia(provincia);
		            Localidad localidad = new Localidad();
		            localidad.setId(Integer.parseInt(request.getParameter("selectLocalidad")));
		            direccion.setLocalidad(localidad);
		            cliente.setDomicilio(direccion);		     
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
