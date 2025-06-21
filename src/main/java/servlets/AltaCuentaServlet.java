package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daoImplementacion.ClienteDaoImplementacion;
import daoImplementacion.CuentaTipoDaoImplementacion;
import entidades.Cliente;
import entidades.CuentaTipo;

/**
 * Servlet implementation class AltaCuentaServlet
 */
@WebServlet("/AltaCuentaServlet")
public class AltaCuentaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AltaCuentaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		try {
			ClienteDaoImplementacion clienteDao = new ClienteDaoImplementacion();
			CuentaTipoDaoImplementacion tipoCuentaDao = new CuentaTipoDaoImplementacion();

			Cliente cliente = null;
			String btnBuscar = request.getParameter("btnBuscar");
			String dniFiltro = request.getParameter("txtDniCliente");
			
			// traer tipos de Cuentas
			ArrayList<CuentaTipo> tiposCuenta = tipoCuentaDao.listar();
			request.setAttribute("tiposCuenta", tiposCuenta);

			
			//buscar por DNI
			if (btnBuscar != null && dniFiltro != null && !dniFiltro.trim().isEmpty()) {

				    try {
				        int dni = Integer.parseInt(dniFiltro); //valido que sea un int
				        
				        cliente = clienteDao.clientePorDNI(dni);

				    } catch (NumberFormatException e) {
				        request.setAttribute("errorDni", "El DNI ingresado no es válido.");
				    }

			}

			request.setAttribute("cliente", cliente);
			
			System.out.println(tiposCuenta);
			request.getRequestDispatcher("/vistas/Admin/Cuentas/AltaCuentas.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("Error al obtener el cliente: " + e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
