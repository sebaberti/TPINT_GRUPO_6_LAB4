package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daoImplementacion.UsuarioDaoImpl;
import negocio.UsuarioNegocio;
import negocioImplementacion.ClienteNegocioImplementacion;
import negocioImplementacion.UsuarioNegocioImpl;


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
			UsuarioNegocio usuarioNegocio = new UsuarioNegocioImpl();
			ClienteNegocioImplementacion cni= new ClienteNegocioImplementacion();
			int idCliente=0;
			String dni = request.getParameter("dni");
		    String cuil = request.getParameter("cuil");
		    
		    if(request.getParameter("idCliente")!=null){
		    	idCliente= Integer.parseInt(request.getParameter("idCliente"));
		    }
		    boolean actualizado=false;
		    
		    if(!estado) {
		    	if(cni.tienePrestamoActivo(idCliente)) {
		    		request.setAttribute("mensajeInformativo", "El usuario no puede ser dado de baja porque tiene préstamos activos.");
		    		RequestDispatcher dispatcher = request.getRequestDispatcher("/vistas/MensajesInformativos.jsp");
		    		dispatcher.forward(request, response); 
		    		return;
		    	}
		    	actualizado= cni.bajaLogica(dni, cuil);	        
		    }else {
		    	actualizado= cni.reactivarCliente(dni, cuil);
		    }
		
			System.out.println("🔎 nombreUsuario recibido: " + nombreUsuario);
			System.out.println("🔎 estado recibido: " + estado);
									
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
