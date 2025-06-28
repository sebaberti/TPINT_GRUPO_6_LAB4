package servlets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CuentaDao;
import entidades.Cuenta;
import entidades.CuentaTipo;
import negocio.CuentaNegocio;
import dao.CuentaTipoDao;
import daoImplementacion.CuentaTipoDaoImplementacion;
import negocioImplementacion.CuentaNegocioImplementacion;

@WebServlet("/ModificarCuentaServlet")
public class ModificarCuentaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    CuentaNegocio cuentaNegocio = new CuentaNegocioImplementacion();
    
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idCuenta = Integer.parseInt(request.getParameter("idCuenta"));

        CuentaNegocio cuentaNegocio = new CuentaNegocioImplementacion();
        Cuenta cuenta = cuentaNegocio.obtenerCuentaPorId(idCuenta);
        
        CuentaTipoDao tipoDao = new CuentaTipoDaoImplementacion();
        ArrayList<CuentaTipo> listaTipos = tipoDao.listar();

        if (cuenta != null) {
            request.setAttribute("cuenta", cuenta);
            request.setAttribute("listaTiposCuenta", listaTipos);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/vistas/Admin/Cuentas/ModificarCuentas.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("ListarCuentasServlet");
        }
    }

    

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int idCuenta = Integer.parseInt(request.getParameter("idCuenta"));
            double saldo = Double.parseDouble(request.getParameter("saldo"));
            boolean estado = Boolean.parseBoolean(request.getParameter("estado"));
            int idTipoCuenta = Integer.parseInt(request.getParameter("idTipoCuenta"));

            CuentaNegocio cuentaNegocio = new CuentaNegocioImplementacion();
            Cuenta cuenta = cuentaNegocio.obtenerCuentaPorId(idCuenta);

            CuentaTipoDao tipoDao = new CuentaTipoDaoImplementacion();
            CuentaTipo tipoCuenta = tipoDao.buscarPorId(idTipoCuenta);

            if (cuenta != null && tipoCuenta != null) {
                // Verificamos si se quiere reactivar una cuenta inactiva
                boolean seQuiereReactivar = !cuenta.isEstado() && estado;

                if (seQuiereReactivar) {
                    int cuentasActivas = cuentaNegocio.cuentasActivas(cuenta.getCliente().getId());

                    if (cuentasActivas >= 3) {
                        request.setAttribute("mostrarModalMsj", true);
                        request.setAttribute("mensaje", "No se puede reactivar la cuenta. El cliente ya tiene 3 cuentas activas.");

                        // Mostrar nuevamente la lista de cuentas
                        List<Cuenta> listaCuentas = cuentaNegocio.listarCuentas();
                        request.setAttribute("listaCuentas", listaCuentas);

                        RequestDispatcher dispatcher = request.getRequestDispatcher("/vistas/Admin/Cuentas/ListarCuentas.jsp");
                        dispatcher.forward(request, response);
                        return;
                    }
                }

                cuenta.setSaldo(saldo);
                cuenta.setEstado(estado);
                cuenta.setTipoCuenta(tipoCuenta);

                boolean exito = cuentaNegocio.modificarCuenta(cuenta);

                request.setAttribute("mostrarModalMsj", true);
                if (exito) {
                    request.setAttribute("mensaje", "Cuenta actualizada correctamente.");
                } else {
                    request.setAttribute("mensaje", "Error al actualizar la cuenta.");
                }

            } else {
                request.setAttribute("mostrarModalMsj", true);
                request.setAttribute("mensaje", "No se encontró la cuenta o el tipo de cuenta.");
            }

            // Refrescar lista de cuentas
            List<Cuenta> listaCuentas = cuentaNegocio.listarCuentas();
            request.setAttribute("listaCuentas", listaCuentas);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/vistas/Admin/Cuentas/ListarCuentas.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mostrarModalMsj", true);
            request.setAttribute("mensaje", "Ocurrió un error al modificar la cuenta: " + e.getMessage());

            List<Cuenta> listaCuentas = cuentaNegocio.listarCuentas();
            request.setAttribute("listaCuentas", listaCuentas);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/vistas/Admin/Cuentas/ListarCuentas.jsp");
            dispatcher.forward(request, response);
        }
    }
   
} 

