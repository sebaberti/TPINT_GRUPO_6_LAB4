package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidades.Cliente;
import entidades.Prestamo;
import negocio.PrestamoNegocio;
import negocioImplementacion.PrestamoNegocioImplementacion;

@WebServlet("/MisPrestamosServlet")
public class MisPrestamosServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private PrestamoNegocio prestamoNegocio = new PrestamoNegocioImplementacion();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	Cliente cliente = (Cliente) request.getSession().getAttribute("clienteActivo");

        if (cliente == null) {
            response.sendRedirect("vistas/Login.jsp");
            return;
        }

        List<Prestamo> prestamos = prestamoNegocio.listarPrestamosPorCliente(cliente.getId());
        request.setAttribute("prestamosCliente", prestamos);

        RequestDispatcher dispatcher = request.getRequestDispatcher("vistas/MisPrestamos.jsp");
        dispatcher.forward(request, response);
    }
}
