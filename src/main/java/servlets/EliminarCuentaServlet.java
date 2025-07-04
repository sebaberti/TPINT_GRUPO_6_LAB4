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
import excepciones.ExcepLimiteCtasActivas;
import negocioImplementacion.ClienteNegocioImplementacion;
import negocioImplementacion.CuentaNegocioImplementacion;

/**
 * Servlet implementation class ManejarCuentaServlet
 */
@WebServlet("/EliminarCuentaServlet")
public class EliminarCuentaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ClienteNegocioImplementacion clienteNegocio;
	private Cliente cliente;
	private String rutaEliminarJSP = "/vistas/Admin/ABMLCliente/EliminarCliente.jsp";

	public EliminarCuentaServlet() {
		super();
		clienteNegocio = new ClienteNegocioImplementacion();
		cliente = null;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			CuentaNegocioImplementacion cuentaNeg = new CuentaNegocioImplementacion();
			Cuenta cuenta = null;
			String btnEliminar = null;
			btnEliminar = request.getParameter("btnEliminar");
			String idCuentaStr = request.getParameter("idCuenta");
			int idCuenta = 0;
			if(request.getParameter("idCuenta")!=null){
				idCuenta = Integer.parseInt(idCuentaStr);
			}
			// Si se clickeo para eliminar:
			if (btnEliminar != null && idCuentaStr != null) {
				try {					
					cuenta = cuentaNeg.obtenerCuentaPorId(idCuenta);
					request.setAttribute("cuenta", cuenta);
					request.getSession().setAttribute("idCuenta", cuenta.getId()); // lo guardo en session para no
																					// perderlo
					request.setAttribute("mostrarModalEliminar", true);
					request.setAttribute("cuentaAElim", cuenta);
				} catch (NumberFormatException e) {
					request.setAttribute("errorCuenta", "Error en la cuenta seleccionada");
				}
			}

			// Si se confirma eliminar
				String btnEliminarConfirmado = request.getParameter("btnEliminarConfirmado");
				HttpSession session = request.getSession();			

				if (btnEliminarConfirmado != null) {
					request.setAttribute("mostrarModalEliminar", false);
					try {
						request.setAttribute("mostrarModalMsj", true);
						if (cuentaNeg.tienePrestamoActivo(idCuenta)) {
							request.setAttribute("mensaje",
									"No es posible eliminar la cuenta. Posee prestamos activos");
						} else {
							boolean eliminada = cuentaNeg.bajaLogica(idCuenta);

							if (eliminada) {
								request.setAttribute("mensaje", "La cuenta fue dada de baja correctamente.");
							} else {
								request.setAttribute("mensaje",
										"No se pudo dar de baja la cuenta. Posiblemente ya estaba inactiva.");
							}
						}
					} catch (Exception e) {
						request.setAttribute("mensajeError", "Error al eliminar la cuenta: " + e.getMessage());
					}
				}
			// Si se clickeó el botón de reactivar
			String btnReactivar = null;
			btnReactivar = request.getParameter("btnReactivar");

			if (btnReactivar != null && idCuentaStr != null) {
				idCuenta = Integer.parseInt(idCuentaStr);
				cuenta = cuentaNeg.obtenerCuentaPorId(idCuenta);
				request.setAttribute("cuenta", cuenta);
				request.getSession().setAttribute("idCuenta", cuenta.getId());
				request.setAttribute("mostrarModalEliminar", true);
				request.setAttribute("cuentaAElim", cuenta);
			}

			// Si se confirmó la reactivación
				String btnReactivarConfirmado = request.getParameter("btnReactivarConfirmado");

				if (btnReactivarConfirmado != null) {
					request.setAttribute("mostrarModalEliminar", false);
					try {
						request.setAttribute("mostrarModalMsj", true);
						boolean reactivada = cuentaNeg.actualizarEstado(idCuenta, true);
						
						if (reactivada) {
							request.setAttribute("mensaje", "La cuenta fue reactivada correctamente.");
						} else {
							request.setAttribute("mensaje",
									"No se pudo reactivar la cuenta, el usuario ya posee 3 cuentas activas");
							throw new ExcepLimiteCtasActivas();
						}
					} catch (ExcepLimiteCtasActivas ex) {
			            request.setAttribute("errorLimite", ex.getMessage());
			            System.out.println("Excepción personalizada capturada: " + ex.getMessage());
			            ex.printStackTrace();
			         }catch (Exception e) {
						request.setAttribute("mensajeError", "Error al reactivar la cuenta: " + e.getMessage());
					}
			}
			// Refresco la lista actualizada
			List<Cuenta> listaCuentas = cuentaNeg.listarCuentas();
			request.setAttribute("listaCuentas", listaCuentas);

			Cuenta cuentaAEliminar = cuentaNeg.obtenerCuentaPorId(idCuenta);
			request.setAttribute("cuentaAEliminar", cuentaAEliminar);

			request.getRequestDispatcher("/vistas/Admin/Cuentas/ListarCuentas.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("Error al obtener la cuenta: " + e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}