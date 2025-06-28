package utilidades;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidades.Cliente;

public abstract class ruta {
	public static void redirigir(HttpServletRequest request, HttpServletResponse response, String nombreAtributo,
			String msjError, String ruta) throws ServletException, IOException {
		request.setAttribute(nombreAtributo, msjError);
		request.getRequestDispatcher(ruta).forward(request, response);
	}

	public static void redirigir(HttpServletRequest request, HttpServletResponse response, String nombreAtributo,
			Cliente cliente, String ruta) throws ServletException, IOException {
		request.setAttribute(nombreAtributo, cliente);
		request.getRequestDispatcher(ruta).forward(request, response);
	}
}
