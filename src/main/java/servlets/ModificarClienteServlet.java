package servlets;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daoImplementacion.ClienteDaoImplementacion;
import daoImplementacion.LocalidadDaoImplementacion;
import daoImplementacion.PaisDaoImplementacion;
import daoImplementacion.ProvinciaDaoImplementacion;
import entidades.Cliente;
import entidades.Direccion;
import entidades.Localidad;
import entidades.Pais;
import entidades.Provincia;
import negocioImplementacion.ClienteNegocioImplementacion;
import negocioImplementacion.LocalidadNegocioImplementacion;
import negocioImplementacion.ProvinciaNegocioImplementacion;

@WebServlet("/ModificarClienteServlet")
public class ModificarClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ModificarClienteServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Filtro localidades según provincia elegida
		if (request.getParameter("btnCargarLocalidadesFiltradas") != null) {
			int provinciaID = 0;
			List<Localidad> localidades = new ArrayList<Localidad>();
			List<Localidad> localidadesAux = new ArrayList<Localidad>();
			
			if (request.getParameter("provincia") == null) {
				request.setAttribute("error", "Eliga una provincia");
				request.getRequestDispatcher("/vistas/Admin/ABMLCliente/ModificarCliente.jsp").forward(request,
						response);
				return;
			} else {
				provinciaID = Integer.parseInt(request.getParameter("provincia"));
				request.setAttribute("provinciaElegida", provinciaID);
			}

			if (request.getAttribute("localidades") != null) {
				localidades = (ArrayList<Localidad>) request.getAttribute("localidades");
			} else {
				LocalidadDaoImplementacion localidadDao = new LocalidadDaoImplementacion();
				localidades = localidadDao.listarLocalidades();
			}

			for (Localidad localidad : localidades) {
				if (localidad.getProvincia().getId() == provinciaID)
					localidadesAux.add(localidad);
			}

			ClienteNegocioImplementacion clienteNegocio = new ClienteNegocioImplementacion();
			Cliente clienteAux = (Cliente) request.getSession().getAttribute("cliente");
			Cliente cliente = clienteNegocio.getCliente(clienteAux.getDNI(), clienteAux.getCUIL());
			request.setAttribute("cliente", capturarCampos(request));
			request.setAttribute("provincias", new ProvinciaDaoImplementacion().listar());
			request.setAttribute("localidades", localidadesAux);
			request.getRequestDispatcher("/vistas/Admin/ABMLCliente/ModificarCliente.jsp").forward(request, response);
			return;
		}

		if (request.getParameter("btnModificar") != null) {
			ClienteDaoImplementacion clienteDao = new ClienteDaoImplementacion();

			try {

				int idCliente = Integer.parseInt(request.getParameter("idCliente"));
				String sexo = request.getParameter("sexo");
				String nombre = request.getParameter("nombre");
				String apellido = request.getParameter("apellido");
				String email = request.getParameter("email");
				String telefono = request.getParameter("telefono");
				int idProvincia = Integer.parseInt(request.getParameter("provincia"));
				int idLocalidad = Integer.parseInt(request.getParameter("localidad"));
				String direccion = request.getParameter("direccion");

				Cliente cliente = new Cliente();
				cliente.setId(idCliente);
				cliente.setSexo(sexo);
				cliente.setNombre(nombre);
				cliente.setApellido(apellido);
				cliente.setEmail(email);
				cliente.setTelefono(telefono);
				cliente.getDomicilio().setDireccion(direccion);
				
				// Localidad
				cliente.getDomicilio().getLocalidad().setId(idLocalidad);
				LocalidadNegocioImplementacion localidadNegocio = new LocalidadNegocioImplementacion();
				String nombreLocalidad = localidadNegocio.obtenerLocalidadPorID(idLocalidad).getNombre();
				cliente.getDomicilio().getLocalidad().setNombre(nombreLocalidad);
				// Provincia
				cliente.getDomicilio().getProvincia().setId(idProvincia);
				ProvinciaNegocioImplementacion provinciaNegocio = new ProvinciaNegocioImplementacion();
				String nombreProvincia = provinciaNegocio.obtenerProvinciaPorID(idProvincia).getNombre();
				cliente.getDomicilio().getProvincia().setNombre(nombreProvincia);

				boolean exito = clienteDao.modificar(cliente);

				if (exito) {
					List<Pais> listaPaises = new PaisDaoImplementacion().listar();
					request.setAttribute("nacionalidades", listaPaises);
					request.setCharacterEncoding("UTF-8");
					response.sendRedirect("ListarClientesServlet");
				} else {
					request.setAttribute("nacionalidades", new PaisDaoImplementacion().listar());
					request.setAttribute("error", "No se pudo modificar el cliente.");
					request.getRequestDispatcher("/vistas/Admin/ABMLCliente/ModificarCliente.jsp").forward(request,
							response);
				}
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("nacionalidades", new PaisDaoImplementacion().listar());
				request.setAttribute("error", "Ocurrió un error inesperado.");
				request.getRequestDispatcher("/vistas/Admin/ABMLCliente/ModificarCliente.jsp").forward(request,
						response);
			}
		}
	}
	
	public Cliente capturarCampos(HttpServletRequest request) {
		
		Cliente clienteAux = (Cliente)request.getSession().getAttribute("cliente");
		int idCliente = Integer.parseInt(request.getParameter("idCliente"));
		String dni = clienteAux.getDNI();
		String cuil = clienteAux.getCUIL();
		String sexo = request.getParameter("sexo");
		String nombre = request.getParameter("nombre");
		String apellido = request.getParameter("apellido");
		String email = request.getParameter("email");
		String telefono = request.getParameter("telefono");
		String nacionalidad = request.getParameter("nacionalidad");
		int idProvincia = Integer.parseInt(request.getParameter("provincia"));
		int idLocalidad = Integer.parseInt(request.getParameter("localidad"));
		String direccion = request.getParameter("direccion");

		Cliente cliente = new Cliente();
		cliente.setId(idCliente);
		cliente.setDNI(dni);
		cliente.setCUIL(cuil);
		cliente.setSexo(sexo);
		cliente.setNombre(nombre);
		cliente.setApellido(apellido);
		cliente.setFecha_nacimiento(clienteAux.getFecha_nacimiento());
		cliente.setEmail(email);
		cliente.setTelefono(telefono);
		cliente.getNacionalidad().setNombre(nacionalidad);
		cliente.getDomicilio().setDireccion(direccion);
		cliente.getDomicilio().getProvincia().setId(idProvincia);
		cliente.getDomicilio().getLocalidad().setId(idLocalidad);
		
		return cliente;
	}
}
