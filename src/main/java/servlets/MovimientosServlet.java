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
import java.util.List;

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
        if (cuentaIdParam != null && !cuentaIdParam.isEmpty()) {
            try {
                int cuentaId = Integer.parseInt(cuentaIdParam);
                List<Movimiento> movimientos = movimientoNegocio.obtenerMovimientosPorCuenta(cuentaId);
                request.setAttribute("movimientos", movimientos);
                request.setAttribute("cuentaSeleccionada", cuentaId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        request.getRequestDispatcher("/vistas/Movimientos.jsp").forward(request, response);
    }
}