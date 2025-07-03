package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daoImplementacion.ClienteDaoImplementacion;
import entidades.Cliente;

@WebServlet("/ListarUsuariosServlet")
public class ListarUsuariosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ListarUsuariosServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ClienteDaoImplementacion clienteDao = new ClienteDaoImplementacion();

			String filtro = request.getParameter("txtDniClientes");

    		System.out.println("Filtro: " + filtro);
			List<Cliente> listaClientes;
			
			if (filtro != null && !filtro.trim().isEmpty()) {
				listaClientes = clienteDao.buscarGenerico(filtro.trim());
			} else {
				listaClientes = clienteDao.listar();
			}

			request.setAttribute("listaClientes", listaClientes);
			request.setAttribute("filtro", filtro); 
			request.getRequestDispatcher("/vistas/Admin/ABMLUsuario/ListarUsuario.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("Error al filtrar usuarios: " + e.getMessage());
		}

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);

	}

}
