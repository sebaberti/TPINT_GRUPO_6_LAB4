package servlets;

import java.io.IOException;import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidades.Cliente;
import negocioImplementacion.ClienteNegocioImplementacion;

/**
 * Servlet implementation class AltaClienteServlet
 */
@WebServlet("/AltaClienteServlet")
public class AltaClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AltaClienteServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Cliente cliente = new Cliente();
		cliente = capturarCampos(request);
		
		if(cliente.getId() != 1) {
			request.setAttribute("error", "Error al capturar los campos");
			request.getRequestDispatcher("/vistas/Admin/ABMLCliente/AltaCliente.jsp").forward(request, response);
		}
		
		try {
			ClienteNegocioImplementacion clienteNegocio = new ClienteNegocioImplementacion();
			clienteNegocio.insertar(cliente);
			request.setAttribute("clienteInsertado", "El cliente " + cliente.getNombre() + " " + cliente.getApellido() + " se inserto correctamente");
			request.getRequestDispatcher("/vistas/Admin/ABMLCliente/AltaCliente.jsp").forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error al capturar los campos");
			request.getRequestDispatcher("/vistas/Admin/ABMLCliente/AltaCliente.jsp").forward(request, response);
		}
	}
	
	private Cliente capturarCampos(HttpServletRequest request) {
		
		Cliente cliente = new Cliente();
		
		if(request.getParameter("btnAltaCliente") != null) {
			
			try {
				cliente.setId(1);
				cliente.setDNI(request.getParameter("DNICliente"));
				cliente.setCUIL(request.getParameter("CUILCliente"));
				cliente.setNombre(request.getParameter("nombreCliente"));
				cliente.setApellido(request.getParameter("apellidoCliente"));
				cliente.setSexo("M");
				//cliente.getNacionalidad.setPais("Argentino");
				//cliente.setFecha_nacimiento();
				//cliente.getDireccion.setDireccion(request.getParameter("lblDireccion"));
				//cliente.getDireccion.setLocalidad(request.getParameter("lblLocalidad"));
				//cliente.getDireccion.setProvincia(request.getParameter("lblProvincia"));
				cliente.setEmail(request.getParameter("emailCliente"));
				cliente.setTelefono(request.getParameter("telefonoCliente"));
				cliente.setEstado(true);
				System.out.println("Capture el cliente");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return cliente;
	}
}
