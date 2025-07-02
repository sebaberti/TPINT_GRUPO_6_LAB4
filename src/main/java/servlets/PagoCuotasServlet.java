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

			// cargar informacion
			if (cliente != null) {
				idCliente = cliente.getId();

				// Cuentas activas
				List<Cuenta> cuentasActivas = cuentaNeg.listarCuentasPorClienteId(idCliente, true);

				// Enviar datos al JSP
				request.setAttribute("cuentasCliente", cuentasActivas);

				// filtrar
				String btnFiltrar = request.getParameter("btnFiltrar");
				String estadoFiltro = request.getParameter("estadoCuota");
				request.getSession().setAttribute("estadoCuota", estadoFiltro); //lo guardo en session
				
				if (estadoFiltro == null)
					estadoFiltro = "impagas"; // default

				List<Cuota> cuotasFiltradas;

				if (btnFiltrar != null) {
					switch (estadoFiltro) {
					case "pagas":
						cuotasFiltradas = cuotaNeg.cuotasPorClienteYEstado(idCliente, true);
						break;
					case "impagas":
						cuotasFiltradas = cuotaNeg.cuotasPorClienteYEstado(idCliente, false);
						break;
					default:
						cuotasFiltradas = cuotaNeg.cuotasPorCliente(idCliente);
						break;
					}
				}

				String tituloCuotas;

				switch (estadoFiltro) {
				case "pagas":
					cuotasFiltradas = cuotaNeg.cuotasPorClienteYEstado(idCliente, true);
					tituloCuotas = "Cuotas pagadas";
					break;
				case "impagas":
					cuotasFiltradas = cuotaNeg.cuotasPorClienteYEstado(idCliente, false);
					tituloCuotas = "Cuotas pendientes";
					break;
				default:
					cuotasFiltradas = cuotaNeg.cuotasPorCliente(idCliente);
					tituloCuotas = "Todas las cuotas";
					break;
				}

				request.setAttribute("estadoFiltro", estadoFiltro);
				request.setAttribute("cuotasFiltradas", cuotasFiltradas);
				request.setAttribute("tituloCuotas", tituloCuotas);

			}

			// si presionan "pagar"
			if (btnPagar != null) {
				String idCuotaStr = request.getParameter("cuotaId");
				String idCuentaStr = request.getParameter("cuentaId");
				if (idCuotaStr != null && idCuentaStr != null) {
					int idCuota = Integer.parseInt(request.getParameter("cuotaId"));
					int idCuenta = Integer.parseInt(request.getParameter("cuentaId"));

					//Recupero nro de cuota antes del pago
			        Cuota cuotaSeleccionada = cuotaNeg.obtenerCuotaPorId(idCuota);
			        int nroCuota = cuotaSeleccionada.getNumeroCuota();
			        
//					boolean pagado = cuotaNeg.pagarCuota(idCuota, idCuenta);
//
//					if (pagado) {
//						request.setAttribute("titulo", "Operación realizada");
//						request.setAttribute("mensaje",  "La cuota N° " + nroCuota + " fue abonada correctamente.");
//					} else {
//						request.setAttribute("titulo", "Operación no permitida");
//						request.setAttribute("mensaje", "La cuenta no tiene saldo suficiente para abonar la cuota.");
//					}
			        

			        int idPrestamo = cuotaSeleccionada.getPrestamoId();

			        // Verificamos si hay cuotas anteriores impagas
			        if (cuotaNeg.adeudaCuotaPrevia(idPrestamo, nroCuota)) {
			            request.setAttribute("titulo", "Operación no permitida");
			            request.setAttribute("mensaje", "No puede pagar esta cuota hasta abonar las que tienen un vencimiento anterior.");
			        } else {
			            boolean pagado = cuotaNeg.pagarCuota(idCuota, idCuenta);

			            if (pagado) {
			                request.setAttribute("titulo", "Operación realizada");
			                request.setAttribute("mensaje", "La cuota N° " + nroCuota + " fue abonada correctamente.");
			            } else {
			                request.setAttribute("titulo", "Operación no permitida");
			                request.setAttribute("mensaje", "La cuenta no tiene saldo suficiente para abonar la cuota.");
			            }
			        }


				} else {
					request.setAttribute("titulo", "Atención!");
					request.setAttribute("mensaje","Debe seleccionar una cuota y una cuenta desde la cuál hará el pago");
					request.setAttribute("mostrarModalMsj", true);
				}
				// Refrescar vista
				List<Cuenta> cuentasActivas = cuentaNeg.listarCuentasPorClienteId(idCliente, true);

				request.setAttribute("cuentasCliente", cuentasActivas);
				request.setAttribute("mostrarModalMsj", true);
				String estadoFiltro = (session.getAttribute("estadoCuota") != null)  //recupero de memoria si se filtró antes, o mantengo valor por defecto
					    ? session.getAttribute("estadoCuota").toString()
					    : "impagas";
				

				List<Cuota> cuotasActualizadas;
				switch (estadoFiltro) {
					case "pagas":
						cuotasActualizadas = cuotaNeg.cuotasPorClienteYEstado(idCliente, true);
						break;
					case "impagas":
						cuotasActualizadas = cuotaNeg.cuotasPorClienteYEstado(idCliente, false);
						break;
					default:
						cuotasActualizadas = cuotaNeg.cuotasPorCliente(idCliente);
						break;
				}

				request.setAttribute("cuotasFiltradas", cuotasActualizadas);
				request.setAttribute("estadoFiltro", estadoFiltro);
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
