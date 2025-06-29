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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			Cliente cliente = (Cliente) session.getAttribute("clienteActivo");
			String btnPagar = request.getParameter("btnPagar");
			request.setAttribute("mostrarModalMsj", false);
			int idCliente = 0;

			CuotaNegocioImplementacion cuotaNeg = new CuotaNegocioImplementacion();
			CuentaNegocioImplementacion cuentaNeg = new CuentaNegocioImplementacion();

			if (cliente != null) {
				idCliente = cliente.getId();

				// Cuotas pendientes
				List<Cuota> cuotasPendientes = cuotaNeg.cuotasPendientesPorCliente(idCliente, false);

				// Cuentas activas
				List<Cuenta> cuentasActivas = cuentaNeg.listarCuentasPorClienteId(idCliente, true);

				// Enviar datos al JSP
				request.setAttribute("cuotasPendientes", cuotasPendientes);
				request.setAttribute("cuentasCliente", cuentasActivas);
			}

			// si presionan "pagar"
			if (btnPagar != null) {
				String idCuotaStr = request.getParameter("cuotaId");
				String idCuentaStr = request.getParameter("cuentaId");
				if (idCuotaStr != null && idCuentaStr != null) {
					int idCuota = Integer.parseInt(request.getParameter("cuotaId"));
					int idCuenta = Integer.parseInt(request.getParameter("cuentaId"));

					boolean pagado = cuotaNeg.pagarCuota(idCuota, idCuenta);

					if (pagado) {
						request.setAttribute("titulo", "Operación realizada");
						request.setAttribute("mensaje", "La cuota fue pagada correctamente.");
					} else {
						request.setAttribute("titulo", "Operación no permitida");
						request.setAttribute("mensaje", "La cuenta no tiene saldo suficiente para abonar la cuota.");
					}

				} else {
					request.setAttribute("titulo", "Atención!");
					request.setAttribute("mensaje","Debe seleccionar una cuota y una cuenta desde la cuál hará el pago");
					request.setAttribute("mostrarModalMsj", true);
				}
				// Refrescar vista
				List<Cuota> cuotasPendientes = cuotaNeg.cuotasPendientesPorCliente(idCliente, false);
				List<Cuenta> cuentasActivas = cuentaNeg.listarCuentasPorClienteId(idCliente, true);

				request.setAttribute("mostrarModalMsj", true);
				request.setAttribute("cuotasPendientes", cuotasPendientes);
				request.setAttribute("cuentasCliente", cuentasActivas);
			}
			request.getRequestDispatcher("/vistas/PagoCuotas.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("titulo", "Error inesperado");
			request.setAttribute("mensaje", "Ocurrió un error al procesar el pago.");
			request.getRequestDispatcher("/vistas/PagoCuotas.jsp").forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
