package servlets;

import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import entidades.Movimiento;
import negocio.CuentaNegocio;
import negocioImplementacion.CuentaNegocioImplementacion;
import negocio.MovimientoNegocio;
import negocioImplementacion.MovimientoNegocioImplementacion;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CuentaNegocio cuentaNegocio = new CuentaNegocioImplementacion();
	MovimientoNegocio movNegocio=new MovimientoNegocioImplementacion();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		 String fechaDesde = request.getParameter("fechaDesde");
		 String fechaHasta = request.getParameter("fechaHasta");
		 
		 LocalDate desde = LocalDate.parse(fechaDesde);
		 LocalDate hasta = LocalDate.parse(fechaHasta);
		 
		 int totalCuentas = cuentaNegocio.contarCuentas(desde, hasta);
		 int totalClientes= cuentaNegocio.cantidadClientesxPeriodo(desde, hasta);
		 
		 double promedioCuentasXcliente=cuentaNegocio.promedioCuentasXCliente(desde, hasta);
		
		request.setAttribute("totalCuentas", totalCuentas);
		request.setAttribute("totalClientes", totalClientes);
		request.setAttribute("promedioCuentasXcliente",promedioCuentasXcliente);
		
		request.getRequestDispatcher("/vistas/Admin/Reportes/reportes.jsp").forward(request, response);
	}
		
}

