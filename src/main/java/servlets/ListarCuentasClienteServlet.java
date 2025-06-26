package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daoImplementacion.CuentaDaoImplementacion;
import entidades.Cliente;
import entidades.Cuenta;

/**
 * Servlet implementation class ListarCuentasClienteServlet
 */
@WebServlet("/ListarCuentasClienteServlet")
public class ListarCuentasClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    Cliente cliente = (Cliente) request.getSession().getAttribute("clienteActivo");

	    if (cliente == null) {
	        response.sendRedirect(request.getContextPath() + "/vistas/Login.jsp");
	        return;
	    }

	    int clienteId = cliente.getId();

	    CuentaDaoImplementacion cuentaDao = new CuentaDaoImplementacion();
	    List<Cuenta> cuentas = cuentaDao.listarCuentasPorClienteId(clienteId,false);

	    request.setAttribute("cuentasCliente", cuentas);
	    request.getRequestDispatcher("/vistas/CuentasCliente.jsp").forward(request, response);
	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
