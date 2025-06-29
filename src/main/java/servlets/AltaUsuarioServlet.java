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
import negocioImplementacion.UsuarioNegocioImpl;

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
	
	if(session.getAttribute("nuevoCliente")!=null) {
		nuevoCliente= (Cliente) session.getAttribute("nuevoCliente");
		request.setAttribute("TipoUserElegido", 2);
	} else {
		request.setAttribute("TipoUserElegido", 1);
	}	
	
	RequestDispatcher dispatcher = request.getRequestDispatcher("/vistas/Admin/ABMLUsuario/AltaUsuario.jsp");
	dispatcher.forward(request, response);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuario;
		UsuarioNegocioImpl uni= new UsuarioNegocioImpl();
		boolean exito=false;
		
		if(request.getParameter("btnCrearUsuario")!=null) {			
		    usuario= new Usuario();
		    int idRol=0;
		    
		    if(request.getParameter("TipoUser")!=null) {
				idRol = Integer.parseInt(request.getParameter("TipoUser"));
				request.setAttribute("TipoUserElegido", idRol);
				usuario.setIdRol(idRol);
			}
		    
			String nombreUsuario= request.getParameter("Usuario");
			request.setAttribute("nombreUsuario", nombreUsuario);
			usuario.setNombreUsuario(nombreUsuario);				
			
		    if(uni.existeUsuarioActivo(nombreUsuario)) {
		    	request.setAttribute("mensajeError", "Ya existe un usuario con ese nombre.<br> Intente nuevamente.");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/vistas/Admin/ABMLUsuario/AltaUsuario.jsp");
				dispatcher.forward(request, response);
				return;
		    }
			
			String clave= request.getParameter("Clave");
			String claveRep= request.getParameter("RepetirClave");	
				
			if(clave.equals(claveRep)) {
				usuario.setContrasenia(clave);
			} else {
				request.setAttribute("mensajeError", "Las contraseñas deben coincidir");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/vistas/Admin/ABMLUsuario/AltaUsuario.jsp");
				dispatcher.forward(request, response);
				return;
			}				
				
			usuario.setEstado(true);
			
			if(idRol==2) {
			nuevoCliente.setUsuario(usuario);	
			ClienteNegocioImplementacion cni= new ClienteNegocioImplementacion();
			if(cni.insertar(nuevoCliente)) {
				exito=true;
			} 
			}
			
			if(idRol==1){
			    if(uni.agregarUsuarioAdministrador(usuario)) {
			    exito=true;
			    }
			}
			
			if(exito) {
				request.setAttribute("mensajeInformativo", "El usuario fue registrado exitosamente");
			} else {
				request.setAttribute("mensajeInformativo", "Ocurrió un error. El usuario no se puedo registrar");
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/vistas/MensajesInformativos.jsp");
			dispatcher.forward(request, response);
			return;
		}		
	}

}
