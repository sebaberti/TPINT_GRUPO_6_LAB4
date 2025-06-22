package servlets;

import entidades.Cuenta;
import entidades.Transferencia;
import negocio.CuentaNegocio;
import negocio.TransferenciaNegocio;
import negocioImplementacion.CuentaNegocioImplementacion;
import negocioImplementacion.TransferenciaNegocioImplementacion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/TransferenciaServlet")
public class TransferenciaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private TransferenciaNegocio transferenciaNegocio = new TransferenciaNegocioImplementacion();
    private CuentaNegocio cuentaNegocio = new CuentaNegocioImplementacion();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        Integer idCliente = (Integer) sesion.getAttribute("usuarioId");

        if (idCliente == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<Cuenta> cuentasCliente = cuentaNegocio.listarCuentasPorClienteId(idCliente);

        System.out.println("DEBUG: cuentasCliente.size() = " + (cuentasCliente != null ? cuentasCliente.size() : "null"));
        if (cuentasCliente != null) {
            for (Cuenta c : cuentasCliente) {
                System.out.println("Cuenta: ID=" + c.getId() + ", CBU=" + c.getCBU() + ", Saldo=" + c.getSaldo() +
                    ", Tipo=" + (c.getTipoCuenta() != null ? c.getTipoCuenta().getDescripcion() : "null"));
            }
        }

        request.setAttribute("cuentasCliente", cuentasCliente);
        request.getRequestDispatcher("/vistas/Transferencia.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        Integer idCliente = (Integer) sesion.getAttribute("usuarioId");
        System.out.println("DEBUG: idCliente desde sesión = " + idCliente);

        try {
            int cuentaOrigenId = Integer.parseInt(request.getParameter("cuentaOrigen"));
            String cbuDestino = request.getParameter("cbuDestino");
            double monto = Double.parseDouble(request.getParameter("monto"));

            if (monto <= 0) {
                request.setAttribute("error", "El monto debe ser mayor que 0.");
            } else {
                Cuenta cuentaDestino = cuentaNegocio.obtenerCuentaPorCBU(cbuDestino);

                if (cuentaDestino == null) {
                    request.setAttribute("error", "El CBU de destino no existe.");
                } else {
                    Cuenta cuentaOrigen = cuentaNegocio.obtenerCuentaPorId(cuentaOrigenId);

                    if (cuentaOrigen.getSaldo() < monto) {
                        request.setAttribute("error", "Saldo insuficiente.");
                    } else {
                        Transferencia transferencia = new Transferencia();
                        transferencia.setCuentaOrigen(cuentaOrigen);
                        transferencia.setCuentaDestino(cuentaDestino);
                        transferencia.setMonto(monto);

                        boolean resultado = transferenciaNegocio.realizarTransferencia(transferencia);

                        if (resultado) {
                            request.setAttribute("mensajeExito", "Transferencia realizada con éxito.");
                        } else {
                            request.setAttribute("error", "Error al realizar la transferencia.");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error en los datos ingresados.");
        }

        // Cargar las cuentas para mostrar en el desplegable (igual que en doGet)
        List<Cuenta> cuentasCliente = cuentaNegocio.listarCuentasPorClienteId(idCliente);

        System.out.println("DEBUG: cuentasCliente.size() = " + (cuentasCliente != null ? cuentasCliente.size() : "null"));
        if (cuentasCliente != null) {
            for (Cuenta c : cuentasCliente) {
                System.out.println("Cuenta: ID=" + c.getId() + ", CBU=" + c.getCBU() + ", Saldo=" + c.getSaldo() +
                    ", Tipo=" + (c.getTipoCuenta() != null ? c.getTipoCuenta().getDescripcion() : "null"));
            }
        }

        request.setAttribute("cuentasCliente", cuentasCliente);
        request.getRequestDispatcher("/vistas/Transferencia.jsp").forward(request, response);
    }
}

