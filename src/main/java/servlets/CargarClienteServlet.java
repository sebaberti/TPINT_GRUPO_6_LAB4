package servlets;

import daoImplementacion.ProvinciaDaoImplementacion;
import entidades.Provincia;
import negocioImplementacion.ClienteNegocioImplementacion;
import negocioImplementacion.LocalidadNegocioImplementacion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import daoImplementacion.ClienteDaoImplementacion;
import daoImplementacion.LocalidadDaoImplementacion;
import daoImplementacion.PaisDaoImplementacion;
import entidades.Cliente;
import entidades.Localidad;

@WebServlet("/CargarClienteServlet")
public class CargarClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CargarClienteServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String dni = request.getParameter("dni");
		String cuil = request.getParameter("cuil");
		
		ClienteNegocioImplementacion clienteNegocio = new ClienteNegocioImplementacion();
		Cliente cliente = clienteNegocio.getCliente(dni, cuil);
		if (cliente != null) 
			request.setAttribute("cliente", cliente);
		else
			request.setAttribute("error", "No se encontró ningún cliente con los datos ingresados.");
			

		LocalidadNegocioImplementacion localidadDao = new LocalidadNegocioImplementacion();

		if ((dni == null || dni.trim().isEmpty()) && (cuil == null || cuil.trim().isEmpty())) {
			request.setAttribute("error", "Debe ingresar al menos un DNI o CUIL para buscar.");
			List<Localidad> localidades = localidadDao.listarLocalidades();
			request.setAttribute("localidades", localidades);
			request.setAttribute("nacionalidades", new PaisDaoImplementacion().listar());
			request.getRequestDispatcher("/vistas/Admin/ABMLCliente/ModificarCliente.jsp").forward(request, response);
			return;
		}


		List<Localidad> localidades = localidadDao.listarLocalidades();
		List<Localidad> localidadesFiltradas = new ArrayList<Localidad>();
		// Filtro por las localidades correspondientes a la provincia del Cliente
		for (Localidad localidad : localidades) {
			if(localidad.getProvincia().getId() == cliente.getDomicilio().getProvincia().getId())
				localidadesFiltradas.add(localidad);
		}
		request.setAttribute("provincias", new ProvinciaDaoImplementacion().listar());
		request.setAttribute("localidades", localidadesFiltradas);
		request.setAttribute("nacionalidades", new PaisDaoImplementacion().listar());

		request.getRequestDispatcher("/vistas/Admin/ABMLCliente/ModificarCliente.jsp").forward(request, response);
	}
}
