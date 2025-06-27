package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daoImplementacion.ClienteDaoImplementacion;
import entidades.Cliente;
import negocioImplementacion.UsuarioNegocioImpl;

/**
 * Servlet implementation class AltaUsuarioServlet
 */
@WebServlet("/AltaUsuarioServlet")
public class AltaUsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
   
    public AltaUsuarioServlet() {
        super();
        UsuarioNegocioImpl usuarioNegocio =new UsuarioNegocioImpl() ;
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		
	}
}
/*
	    
	    if (request.getParameter("btnBuscarCliente") != null) {
	        String dni = request.getParameter("dniClienteBuscar");
	        ClienteDaoImplementacion clienteDao = new ClienteDaoImplementacion();
	       Cliente cliente = clienteDao.buscarPorDNI(dni);

	        if (cliente != null && cliente.getUsuario() == null) {
	            request.setAttribute("clienteEncontrado", cliente);
	        } else {
	            request.setAttribute("error", "Cliente no encontrado o ya tiene usuario.");
	        }

	        request.getRequestDispatcher("/altaUsuario.jsp").forward(request, response);
	        return;
	    }

	   
	    if (request.getParameter("btnCrearUsuario") != null) {
	       
	        String nombreUsuario = request.getParameter("lblUsuario");
	        String clave = request.getParameter("lblClave");
	        int idCliente = Integer.parseInt(request.getParameter("idCliente"));

	       
	        Usuario nuevoUsuario = new Usuario();
	        nuevoUsuario.setNombreUsuario(nombreUsuario);
	        nuevoUsuario.setContrasenia(clave);
	        
	        Rol rolCliente = new Rol();
	        rolCliente.setId(2); 
	        nuevoUsuario.setRol(rolCliente);

	        UsuarioDao usuarioDao = new UsuarioDaoImplementacion();
	        boolean usuarioCreado = usuarioDao.insertar(nuevoUsuario);

	        if (usuarioCreado) {
	           
	            Usuario usuarioInsertado = usuarioDao.buscarPorNombre(nombreUsuario);

	           
	            ClienteDao clienteDao = new ClienteDaoImplementacion();
	            clienteDao.asignarUsuarioACliente(idCliente, usuarioInsertado.getId());

	            request.setAttribute("exito", "Usuario creado y vinculado correctamente.");
	        } else {
	            request.setAttribute("error", "No se pudo crear el usuario.");
	        }

	        request.getRequestDispatcher("/altaUsuario.jsp").forward(request, response);
	    }
	}
*/


