package servlets;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daoImplementacion.CuentaDaoImplementacion;
import entidades.Cuenta;
/**
 * Servlet implementation class ListarCuentasServlet
 */
@WebServlet("/ListarCuentasServlet")
public class ListarCuentasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListarCuentasServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			CuentaDaoImplementacion cuentaDao = new CuentaDaoImplementacion();

			List<Cuenta> listaCuentas = cuentaDao.listar();

			String btnBuscar = request.getParameter("btnBuscar");

			// Filtrar
			String valorBusqueda = request.getParameter("valorBusqueda");
			if (btnBuscar != null && !valorBusqueda.trim().isEmpty()) {
				String criterio = request.getParameter("criterioBusqueda");
				String valor = request.getParameter("valorBusqueda");

				switch (criterio) {
				case "dni":
					try {
						int dni = Integer.parseInt(valor); // valido que sea un int
						
						listaCuentas= cuentaDao.listarPorDNI(dni);
						if (listaCuentas==null || listaCuentas.isEmpty()) {
							request.setAttribute("errorBusqueda", "No se encontraron cuentas con el DNI ingresado.");
						}
						
					} catch (NumberFormatException e) {
						request.setAttribute("errorBusqueda", "El DNI ingresado no es válido.");
					}
					break;
				case "nroCuenta":
					try {
						listaCuentas = cuentaDao.listarPorNro(valor);
						if (listaCuentas==null || listaCuentas.isEmpty()) {
							request.setAttribute("errorBusqueda", "No se encontraron cuentas con el Número de cuenta ingresado.");
						}
					} catch (Exception e) {
						request.setAttribute("errorBusqueda", "Error al buscar el Nro de cuenta ingresado");
					}
					break;
				case "cbu":
					try {
						BigInteger cbu = new BigInteger(valor); //Valido que sean nros
						
						listaCuentas= cuentaDao.listarPorCBU(cbu);
						if (listaCuentas==null || listaCuentas.isEmpty()) {
							request.setAttribute("errorBusqueda", "No se encontraron cuentas con el CBU ingresado.");
						}
						
					} catch (NumberFormatException e) {
						request.setAttribute("errorBusqueda", "El CBU ingresado no es válido.");
					}
					break;
				}

			}

			request.setAttribute("listaCuentas", listaCuentas);

			request.getRequestDispatcher("/vistas/Admin/Cuentas/ListarCuentas.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("Error al obtener la lista de cuentas: " + e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
