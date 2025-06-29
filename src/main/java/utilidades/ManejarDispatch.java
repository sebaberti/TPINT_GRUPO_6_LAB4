package utilidades;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidades.Cliente;

public abstract class ManejarDispatch {
	public static void redirigir(HttpServletRequest request, HttpServletResponse response, String nombreAtributo,
			String msjError, String ruta) throws ServletException, IOException {
		request.setAttribute(nombreAtributo, msjError);
		RequestDispatcher dispatcher = request.getRequestDispatcher(ruta);
		dispatcher.forward(request, response);
	}
}
