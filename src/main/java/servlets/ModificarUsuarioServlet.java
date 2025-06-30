package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daoImplementacion.UsuarioDaoImpl;


@WebServlet("/ModificarUsuarioServlet")
public class ModificarUsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public ModificarUsuarioServlet() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nombreUsuario=request.getParameter("nombreUsuario");
		boolean estado = Boolean.parseBoolean(request.getParameter("estado"));
		
		try {
			UsuarioDaoImpl usuarioDaoImpl= new UsuarioDaoImpl();
			
			Boolean actualizado=usuarioDaoImpl.modificarEstado(estado);
			
			
			if(actualizado) 
			 {
				request.setAttribute("mensajeInformativo", "El usuario fue modificado exitosamente");
			 } else {
					request.setAttribute("mensajeInformativo", "Ocurrió un error. El usuario no se puedo registrar");
			 }
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/vistas/MensajesInformativos.jsp");
				dispatcher.forward(request, response);
				return;
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}

}
