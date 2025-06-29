package servlets;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import dao.CuentaDao;
import dao.MovimientoDao;
import dao.CuotaDao;
import daoImplementacion.CuotaDaoImplementacion;
import daoImplementacion.CuentaDaoImplementacion;
import daoImplementacion.MovimientoDaoImplementacion;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CuentaDao cuentaDAO = new CuentaDaoImplementacion();
		MovimientoDao movimientoDAO = new MovimientoDaoImplementacion();
		CuotaDao cuotaDAO = new CuotaDaoImplementacion();

		int cuotasPagas = cuotaDAO.contarCuotasPagas();
		int cuotasImpagas = cuotaDAO.contarCuotasImpagas();


		int totalCuentas = cuentaDAO.contarCuentas();
		double totalIngresos = movimientoDAO.sumarMovimientosPorTipo("INGRESO");
		double totalEgresos = movimientoDAO.sumarMovimientosPorTipo("EGRESO");
		double saldosImpagos = cuotaDAO.sumarSaldosImpagos();
		
		NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));
		String saldosImpagosFormateado = formatoMoneda.format(saldosImpagos);


		request.setAttribute("saldosImpagos", saldosImpagosFormateado);
		request.setAttribute("cuotasPagas", cuotasPagas);
		request.setAttribute("cuotasImpagas", cuotasImpagas);
		request.setAttribute("totalCuentas", totalCuentas);
		request.setAttribute("totalIngresos", totalIngresos);
		request.setAttribute("totalEgresos", totalEgresos);

		request.getRequestDispatcher("/vistas/Admin/Reportes/reportes.jsp").forward(request, response);
	}
}
