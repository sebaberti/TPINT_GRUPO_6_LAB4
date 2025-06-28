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
import entidades.Usuario;
import negocio.ClienteNegocio;
import negocioImplementacion.ClienteNegocioImplementacion;

@WebServlet("/AltaUsuarioServlet")
public class AltaUsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session;
	private Cliente nuevoCliente;
	
    public AltaUsuarioServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	session= request.getSession();
	nuevoCliente= new Cliente();
	if(session.getAttribute("cliente")!=null) {
		nuevoCliente= (Cliente) session.getAttribute("cliente");
		request.setAttribute("DNI", nuevoCliente.getDNI());
		request.setAttribute("CUIL", nuevoCliente.getCUIL());
	}
	
	RequestDispatcher dispatcher = request.getRequestDispatcher("/vistas/Admin/ABMLUsuario/AltaUsuario.jsp");
	dispatcher.forward(request, response);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("btnBuscar")!=null) {			
			request.setAttribute("DNI", nuevoCliente.getDNI());
			request.setAttribute("CUIL", nuevoCliente.getCUIL());
			if(existeCliente(request, response)) {
				request.setAttribute("mensajeValidacion", "El cliente ha sido validado");
			}			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/vistas/Admin/ABMLUsuario/AltaUsuario.jsp");
			dispatcher.forward(request, response);	
			return;
		}
		
		if(request.getParameter("btnCrearUsuario")!=null) {
			Usuario usuario= new Usuario();
			Boolean claveConfirmada=false;
			
			String nombreUsuario= request.getParameter("Usuario");
			usuario.setNombreUsuario(nombreUsuario);	
			
			String clave= request.getParameter("Clave");
			String claveRep= request.getParameter("RepetirClave");
			if(clave.equals(claveRep)) {
				usuario.setContrasenia(clave);
			}
		
			int idRol= Integer.parseInt(request.getParameter("TipoUser"));
			usuario.setIdRol(idRol);
			
			usuario.setEstado(true);
			//
			nuevoCliente.setUsuario(usuario);
			
			ClienteNegocioImplementacion cni= new ClienteNegocioImplementacion();
			cni.insertar(nuevoCliente);
			
			request.setAttribute("mensajeInformativo", "El usuario fue registrado exitosamente");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/vistas/MensajesInformativos.jsp");
			dispatcher.forward(request, response);
			 return;
		}
		
	
	}
	
	private boolean existeCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ClienteNegocioImplementacion cni= new ClienteNegocioImplementacion();
		Boolean dni = cni.existeDNI(nuevoCliente.getDNI());
		Boolean cuil = cni.existeCUIL(nuevoCliente.getCUIL());
		String msj="";
		Boolean datosValidos=true;
		
		if (dni) {
			msj+="Ya se encuentra un cliente registrado con este DNI <br>";
			datosValidos=false;
		}
		
		if (cuil) {
			msj+="Ya se encuentra un cliente registrado con este CUIL <br>";
			datosValidos=false;
		}
		
		if (!datosValidos) {
		    request.setAttribute("mensajeError", msj);
		}
		
		return dni && cuil;
		
	}

}
