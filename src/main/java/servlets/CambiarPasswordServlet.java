package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/CambiarPasswordServlet")
public class CambiarPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CambiarPasswordServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String actual = request.getParameter("actual");
		String nueva = request.getParameter("nueva");
		String confirmar = request.getParameter("confirmar");

		boolean datosValidos = true;
		String mensajeError = "";

		// Validaciones básicas
		if (actual == null || actual.trim().isEmpty()) {
			mensajeError += "Debe ingresar su contraseña actual.<br>";
			datosValidos = false;
		}

		if (nueva == null || nueva.trim().isEmpty()) {
			mensajeError += "Debe ingresar la nueva contraseña.<br>";
			datosValidos = false;
		}

		if (confirmar == null || confirmar.trim().isEmpty()) {
			mensajeError += "Debe confirmar la nueva contraseña.<br>";
			datosValidos = false;
		}

		if (nueva != null && confirmar != null && !nueva.equals(confirmar)) {
			mensajeError += "La nueva contraseña y su confirmación no coinciden.<br>";
			datosValidos = false;
		}

		if (!datosValidos) {
			request.setAttribute("mensajeError", mensajeError);
			request.getRequestDispatcher("/vistas/Perfil.jsp").forward(request, response);
			return;
		}

		String mensajeInformativo = "Contraseña cambiada correctamente. NO FUE A LA DB - Prueba de Servlet";
		request.setAttribute("mensajeInformativo", mensajeInformativo);
		request.getRequestDispatcher("/vistas/Perfil.jsp").forward(request, response);

	}
}
