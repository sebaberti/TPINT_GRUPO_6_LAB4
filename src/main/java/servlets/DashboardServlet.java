package servlets;

import java.io.IOException;
import java.text.DecimalFormat;
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

		LocalDate desde;
		LocalDate hasta;

		// Si alguno es null → usar fecha actual
		if (fechaDesde == null || fechaHasta == null) {
		    LocalDate hoy = LocalDate.now();
		    desde = hoy;
		    hasta = hoy;
		}
		// Si llegan vacíos (""), mostrar mensaje de error
		else if (fechaDesde.trim().isEmpty() || fechaHasta.trim().isEmpty()) {
		    request.setAttribute("error", "Debe completar ambas fechas para generar el reporte.");
		    request.getRequestDispatcher("/vistas/Admin/Reportes/reportes.jsp").forward(request, response);
		    return;
		}
		// Si todo está OK, parsear normalmente
		else {
		    desde = LocalDate.parse(fechaDesde);
		    hasta = LocalDate.parse(fechaHasta);
		}
		 //Resumen promedio cuentas por cliente
		 int totalCuentas = cuentaNegocio.contarCuentas(desde, hasta);
		 int totalClientes= cuentaNegocio.cantidadClientesxPeriodo(desde, hasta);
		 double promedioCuentasXcliente=cuentaNegocio.promedioCuentasXCliente(desde, hasta);
		 
		 //Ingresos Egresos
		 double totalIngresos= movNegocio.obtenerIngresos(desde, hasta);
		 double totalEgresos = movNegocio.obtenerEgresos(desde, hasta);
		 
		 //Caja y rentabilidad
		 double caja=cuentaNegocio.cajaActual();
		 
		 String rentabilidad=formatearCampos(calcularRentabilidad(totalIngresos,totalEgresos));
		 String cajaActual=formatearCampos(calcularFlujoCaja(caja,totalIngresos,totalEgresos));
		
		request.setAttribute("totalCuentas", totalCuentas);
		request.setAttribute("totalClientes", totalClientes);
		request.setAttribute("promedioCuentasXcliente",promedioCuentasXcliente);
		
		String ingresosFmt=formatearCampos(totalIngresos);
		String EgresosFmt=formatearCampos(totalEgresos);
		
		request.setAttribute("totalIngresos",ingresosFmt);
		request.setAttribute("totalEgresos",EgresosFmt);
		//Rentabilidad y caja
		request.setAttribute("rentabilidad",rentabilidad);
		request.setAttribute("caja",cajaActual);
		
		request.getRequestDispatcher("/vistas/Admin/Reportes/reportes.jsp").forward(request, response);
	}
	
	private double calcularRentabilidad (double ingresos, double egresos) {
		
		double GananciasNetas=0.0;
		
			 if (egresos == 0) {
			        
			   return ingresos > 0 ? Double.POSITIVE_INFINITY : 0;

			 }
		
		return ((ingresos-egresos)/egresos)*100;		
	}
	
	private double calcularFlujoCaja(double cajaActual,double ingresos,double egresos) {
		
		double FlujoCaja=0.0;
		
		FlujoCaja= cajaActual+ingresos-egresos;
		
		return FlujoCaja;
	}
	
	private String formatearCampos(double valor) {
			
		DecimalFormat df = new DecimalFormat("#,###.00");
		String totalFormateado = df.format(valor);
		
		return totalFormateado;
	}			
}
