package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidades.Cliente;
import entidades.Cuenta;
import entidades.Cuota;
import negocioImplementacion.CuentaNegocioImplementacion;
import negocioImplementacion.CuotaNegocioImplementacion;

/**
 * Servlet implementation class PagoCuotasServlet
 */
@WebServlet("/PagoCuotasServlet")
public class PagoCuotasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PagoCuotasServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
	    Cliente cliente = (Cliente) session.getAttribute("clienteActivo");

	    if (cliente != null) {
	        int idCliente = cliente.getId();

	        // Obtener cuotas pendientes del cliente
	        CuotaNegocioImplementacion cuotaNeg = new CuotaNegocioImplementacion();
	        List<Cuota> cuotasPendientes = cuotaNeg.cuotasPendientesPorCliente(idCliente);

	        // Obtener cuentas activas del cliente
	        CuentaNegocioImplementacion cuentaNeg = new CuentaNegocioImplementacion();
	        List<Cuenta> cuentasActivas = cuentaNeg.listarCuentasPorClienteId(idCliente, true);

	        // Enviar datos al JSP
	        request.setAttribute("cuotasPendientes", cuotasPendientes);
	        request.setAttribute("cuentasCliente", cuentasActivas);
	    }

	    request.getRequestDispatcher("/vistas/PagoCuotas.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
