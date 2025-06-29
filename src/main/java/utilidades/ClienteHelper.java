package utilidades;

import java.sql.Date;	
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entidades.Cliente;
import entidades.Localidad;
import entidades.Pais;
import entidades.Provincia;
import negocioImplementacion.LocalidadNegocioImplementacion;
import negocioImplementacion.PaisNegocioImplementacion;
import negocioImplementacion.ProvinciaNegocioImplementacion;

public class ClienteHelper {

	public static void cargarDesplegables(HttpServletRequest request, HttpServletResponse response) {
		ProvinciaNegocioImplementacion pni = new ProvinciaNegocioImplementacion();
		LocalidadNegocioImplementacion lni = new LocalidadNegocioImplementacion();
		PaisNegocioImplementacion pani = new PaisNegocioImplementacion();
		String nacionalidadParam;
		String provinciaParam;
		String localidadParam;

		List<Pais> listaPaises = pani.listar();
		request.setAttribute("listaPaises", listaPaises);

		try {
			if (request.getParameter("selectNacionalidad") != null
					&& !request.getParameter("selectNacionalidad").isEmpty()) {
				nacionalidadParam = request.getParameter("selectNacionalidad");
				request.setAttribute("nacionalidadElegida", nacionalidadParam);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<Provincia> listaProvincias = pni.listar();
		request.setAttribute("listaProvincias", listaProvincias);

		try {
			if (request.getParameter("selectProvincia") != null && !request.getParameter("selectProvincia").isEmpty()) {
				provinciaParam = request.getParameter("selectProvincia");
				request.setAttribute("provinciaElegida", provinciaParam);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (request.getAttribute("provinciaElegida") != null) {
			int idProvincia = Integer.parseInt((request.getAttribute("provinciaElegida").toString()));
			List<Localidad> listaLocalidades = lni.listarLocalidadesPorProvincia(idProvincia);
			request.setAttribute("listaLocalidades", listaLocalidades);
		}

		try {
			if (request.getParameter("selectLocalidad") != null && !request.getParameter("selectLocalidad").isEmpty()) {
				localidadParam = request.getParameter("selectLocalidad");
				request.setAttribute("localidadElegida", localidadParam);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Cliente capturarCamposModificar(HttpServletRequest request) {
		
		Cliente cliente = new Cliente();
		Cliente clienteAux = new Cliente();
		clienteAux = (Cliente)request.getSession().getAttribute("clienteOriginal");

		try {

			if (request.getParameter("idCliente") != null) {
				cliente.setId(Integer.parseInt(request.getParameter("idCliente")));
			}

			if (request.getParameter("DNICliente") != null && !request.getParameter("DNICliente").isEmpty()) {
				cliente.setDNI(request.getParameter("DNICliente"));
			}

			if (request.getParameter("CUILCliente") != null && !request.getParameter("CUILCliente").isEmpty()) {
				cliente.setCUIL(request.getParameter("CUILCliente"));
			}

			if (request.getParameter("nombreCliente") != null && !request.getParameter("nombreCliente").isEmpty()) {
				cliente.setNombre(ManejoCaractEspecial.manejarCaracterEspecial(request.getParameter("nombreCliente").trim().replaceAll("\\s{2,}", " ")));
			} else {
				cliente.setNombre(ManejoCaractEspecial.manejarCaracterEspecial(clienteAux.getNombre()));
			}
			
			if (request.getParameter("apellidoCliente") != null && !request.getParameter("apellidoCliente").isEmpty()) {
				cliente.setApellido(ManejoCaractEspecial.manejarCaracterEspecial(request.getParameter("apellidoCliente").trim().replaceAll("\\s{2,}", " ")));
			} else {
				cliente.setApellido(ManejoCaractEspecial.manejarCaracterEspecial(clienteAux.getApellido()));
			}
			
			if (request.getParameter("selectSexo") != null && !request.getParameter("selectSexo").isEmpty()) {
				cliente.setSexo(request.getParameter("selectSexo"));
			}

			Pais pais = null;
			if (request.getParameter("selectNacionalidad") != null
					&& !request.getParameter("selectNacionalidad").isEmpty()) {
				int idPais = Integer.parseInt(request.getParameter("selectNacionalidad"));
				PaisNegocioImplementacion pni = new PaisNegocioImplementacion();
				pais = pni.obtenerPaisPorID(idPais);
				cliente.setNacionalidad(pais);
			}

			if (request.getParameter("idDireccion") != null) {
				cliente.getDomicilio().setId(Integer.parseInt(request.getParameter("idDireccion")));
			}

			if (request.getParameter("direccionCliente") != null
					&& !request.getParameter("direccionCliente").isEmpty()) {
				cliente.getDomicilio().setDireccion(ManejoCaractEspecial.manejarCaracterEspecial(request.getParameter("direccionCliente").trim().replaceAll("\\s{2,}", " ")));
			} else {
				cliente.getDomicilio().setDireccion(clienteAux.getDomicilio().getDireccion());
			}

			Localidad localidad = null;
			if (request.getParameter("selectLocalidad") != null && !request.getParameter("selectLocalidad").isEmpty()) {
				localidad = new Localidad();
				localidad.setId(Integer.parseInt(request.getParameter("selectLocalidad")));
				cliente.getDomicilio().setLocalidad(localidad);
			}

			Provincia p = null;
			if (request.getParameter("selectProvincia") != null && !request.getParameter("selectProvincia").isEmpty()) {
				p = new Provincia();
				p.setId(Integer.parseInt(request.getParameter("selectProvincia")));
				cliente.getDomicilio().getLocalidad().setProvincia(p);
				cliente.getDomicilio().getLocalidad().getProvincia().setPais(pais);
			}

			String fechaNac = request.getParameter("fechaNacimientoCliente");
			if (fechaNac != null && !fechaNac.isEmpty()) {
				Date fecha = Date.valueOf(fechaNac);
				cliente.setFecha_nacimiento(fecha);
			} else {
				cliente.setFecha_nacimiento(clienteAux.getFecha_nacimiento());
			}
				

			if (request.getParameter("emailCliente") != null && !request.getParameter("emailCliente").isEmpty()) {
				cliente.setEmail(ManejoCaractEspecial.manejarCaracterEspecial(request.getParameter("emailCliente").trim()));
			} else {
				cliente.setEmail(ManejoCaractEspecial.manejarCaracterEspecial(clienteAux.getEmail()));
			}
			
			
			if (request.getParameter("telefonoCliente") != null && !request.getParameter("telefonoCliente").isEmpty()) {
				cliente.setTelefono(request.getParameter("telefonoCliente").trim().replaceAll("\\s{2,}", " "));
			} else {
				cliente.setTelefono(clienteAux.getTelefono());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return cliente;
	}
}
