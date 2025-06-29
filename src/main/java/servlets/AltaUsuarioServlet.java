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
        nuevoCliente= new Cliente();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	session= request.getSession();
	
	if(session.getAttribute("cliente")!=null) {
		nuevoCliente= (Cliente) session.getAttribute("cliente");
		request.setAttribute("TipoUserElegido", 2);
	}
	
	RequestDispatcher dispatcher = request.getRequestDispatcher("/vistas/Admin/ABMLUsuario/AltaUsuario.jsp");
	dispatcher.forward(request, response);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuario;
		if(request.getParameter("btnCrearUsuario")!=null) {			
		    usuario= new Usuario();
			String nombreUsuario= request.getParameter("Usuario");
			usuario.setNombreUsuario(nombreUsuario);				
			String clave= request.getParameter("Clave");
			String claveRep= request.getParameter("RepetirClave");
			
			if(clave.equals(claveRep)) {
				usuario.setContrasenia(clave);
			} else {
				request.setAttribute("MensajeError", "Las contraseñas deben coincidir");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/vistas/Admin/ABMLUsuario/AltaUsuario.jsp");
				dispatcher.forward(request, response);
				return;
			}
			
			int idRol= Integer.parseInt(request.getParameter("TipoUser"));
			usuario.setIdRol(idRol);			
			usuario.setEstado(true);
			
			if(session.getAttribute("cliente")!=null) {
			nuevoCliente.setUsuario(usuario);	
			ClienteNegocioImplementacion cni= new ClienteNegocioImplementacion();
			if(cni.insertar(nuevoCliente)) {
				request.setAttribute("mensajeInformativo", "El usuario fue registrado exitosamente");
			} else {
				request.setAttribute("mensajeInformativo", "Ocurrió un error. El usuario no pudo ser registrado.");
			}			
			}		
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/vistas/MensajesInformativos.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
	
	}

}
