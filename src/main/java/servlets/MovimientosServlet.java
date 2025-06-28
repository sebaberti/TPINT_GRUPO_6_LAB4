package servlets;

import entidades.Cuenta;
import entidades.Movimiento;
import negocio.CuentaNegocio;
import negocio.MovimientoNegocio;
import negocioImplementacion.CuentaNegocioImplementacion;
import negocioImplementacion.MovimientoNegocioImplementacion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/MovimientosServlet")
public class MovimientosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    private CuentaNegocio cuentaNegocio = new CuentaNegocioImplementacion();
    private MovimientoNegocio movimientoNegocio = new MovimientoNegocioImplementacion();
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession();
        Integer clienteId = (Integer) sesion.getAttribute("usuarioId");

        if (clienteId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<Cuenta> cuentas = cuentaNegocio.listarCuentasPorClienteId(clienteId, true);
        request.setAttribute("cuentasCliente", cuentas);

        // Si viene una cuenta seleccionada:
        String cuentaIdParam = request.getParameter("cuentaId");
        String fechaInicioParam = request.getParameter("fechaInicio");
        String fechaFinParam = request.getParameter("fechaFin");
        Integer cuentaSeleccionada = null;
        
        if (cuentaIdParam != null && !cuentaIdParam.isEmpty()) {
            try {
                cuentaSeleccionada = Integer.parseInt(cuentaIdParam);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // Si no se envió cuentaId y hay cuentas, seleccionar la primera
        if (cuentaSeleccionada == null && cuentas != null && !cuentas.isEmpty()) {
            cuentaSeleccionada = cuentas.get(0).getId();
        }

        request.setAttribute("cuentaSeleccionada", cuentaSeleccionada);
        request.setAttribute("fechaInicio", fechaInicioParam);
        request.setAttribute("fechaFin", fechaFinParam);

        List<Movimiento> movimientos = null;
        if (cuentaSeleccionada != null) {
            movimientos = movimientoNegocio.obtenerMovimientosPorCuenta(cuentaSeleccionada);
            if ((fechaInicioParam != null && !fechaInicioParam.isEmpty()) ||
                    (fechaFinParam != null && !fechaFinParam.isEmpty())) {

                    LocalDate fechaInicio = null;
                    LocalDate fechaFin = null;

                    try {
                        if (fechaInicioParam != null && !fechaInicioParam.isEmpty()) {
                            fechaInicio = LocalDate.parse(fechaInicioParam);
                        }
                        if (fechaFinParam != null && !fechaFinParam.isEmpty()) {
                            fechaFin = LocalDate.parse(fechaFinParam);
                        }

                        LocalDate finalFechaInicio = fechaInicio;
                        LocalDate finalFechaFin = fechaFin;

                        movimientos = movimientos.stream().filter(mov -> {
                            LocalDate fechaMovimiento = mov.getFecha().toLocalDate();
                            boolean cumple = true;

                            if (finalFechaInicio != null) {
                                cumple = cumple && !fechaMovimiento.isBefore(finalFechaInicio);
                            }
                            if (finalFechaFin != null) {
                                cumple = cumple && !fechaMovimiento.isAfter(finalFechaFin);
                            }

                            return cumple;
                        }).collect(Collectors.toList());

                    } catch (Exception e) {
                        e.printStackTrace(); 
                    }
                }
            if (movimientos != null) {
            	movimientos.sort((m1, m2) -> m2.getFecha().compareTo(m1.getFecha()));
            	}
            }
        
        
        request.setAttribute("movimientos", movimientos);

        request.getRequestDispatcher("/vistas/Movimientos.jsp").forward(request, response);
    }
}