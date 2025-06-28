package servlets;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import entidades.Cliente;
import entidades.Direccion;
import entidades.Localidad;
import entidades.Pais;
import entidades.Provincia;
import negocioImplementacion.ClienteNegocioImplementacion;
import negocioImplementacion.LocalidadNegocioImplementacion;
import negocioImplementacion.PaisNegocioImplementacion;
import negocioImplementacion.ProvinciaNegocioImplementacion;

@WebServlet("/AltaClienteServlet")
public class AltaClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ClienteNegocioImplementacion clienteNegocio;
	HttpSession session;

	private String rutaAltaClienteJSP = "/vistas/Admin/ABMLCliente/AltaCliente.jsp";

	public AltaClienteServlet() {
		super();
		clienteNegocio = new ClienteNegocioImplementacion();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
			cargarDesplegables(request, response);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/vistas/Admin/ABMLCliente/AltaCliente.jsp");
			dispatcher.forward(request, response);
			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		cargarDesplegables(request, response);
		
		session = request.getSession();
		
		Cliente cliente = new Cliente();
		cliente = capturarCampos(request);
				
		if(cliente != null) {
			request.setAttribute("cliente", cliente);
			existeCliente(request, response, cliente);
			
			if (request.getParameter("btnAltaCliente")!=null) {
				session.setAttribute("cliente", cliente);
				response.sendRedirect(request.getContextPath() + "/vistas/Admin/ABMLUsuario/AltaUsuario.jsp");
				return;
			}
			
			}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/vistas/Admin/ABMLCliente/AltaCliente.jsp");
		dispatcher.forward(request, response);
	};

	private Cliente capturarCampos(HttpServletRequest request) {

		Cliente cliente = null;
			try {
				cliente= new Cliente();
				if (request.getParameter("DNICliente") != null && !request.getParameter("DNICliente").isEmpty()) {
				    cliente.setDNI(request.getParameter("DNICliente"));
				}
				if (request.getParameter("CUILCliente") != null && !request.getParameter("CUILCliente").isEmpty()) {
				    cliente.setCUIL(request.getParameter("CUILCliente"));
				}
				if (request.getParameter("nombreCliente") != null && !request.getParameter("nombreCliente").isEmpty()) {
				    cliente.setNombre(request.getParameter("nombreCliente"));
				}
				if (request.getParameter("apellidoCliente") != null && !request.getParameter("apellidoCliente").isEmpty()) {
				    cliente.setApellido(request.getParameter("apellidoCliente"));
				}
				if (request.getParameter("selectSexo") != null && !request.getParameter("selectSexo").isEmpty()) {
				    cliente.setSexo(request.getParameter("selectSexo"));
				}	      
		            
		        Pais pais = null;
		    	if (request.getParameter("selectNacionalidad") != null && !request.getParameter("selectNacionalidad").isEmpty()) {
		    		int idPais= Integer.parseInt(request.getParameter("selectNacionalidad"));
		    		PaisNegocioImplementacion pni= new PaisNegocioImplementacion();
		    		pais= pni.obtenerPaisPorID(idPais);
		    		cliente.setNacionalidad(pais);
		    	}
		        		      		            
		            String fechaNac = request.getParameter("fechaNacimientoCliente");
		            if (fechaNac != null && !fechaNac.isEmpty()) {
		                Date fecha = Date.valueOf(fechaNac); 
		                cliente.setFecha_nacimiento(fecha);
		            }
		            
		            
		            Direccion direccion = new Direccion();	
		            if (request.getParameter("direccionCliente") != null && !request.getParameter("direccionCliente").isEmpty()) { 
		            	direccion.setDireccion(request.getParameter("direccionCliente"));
		            }
		            
		            Provincia provincia = null;
		            if (request.getParameter("selectProvincia") != null && !request.getParameter("selectProvincia").isEmpty()) {
		            Provincia p= new Provincia();		  
		            p.setId(Integer.parseInt(request.getParameter("selectProvincia")));	
		            direccion.setProvincia(p);
		            }		            
		        
		            Localidad localidad = null;		            
		            if (request.getParameter("selectLocalidad") != null && !request.getParameter("selectLocalidad").isEmpty()) {
		            localidad= new Localidad();
		            localidad.setId(Integer.parseInt(request.getParameter("selectLocalidad")));
		            direccion.setLocalidad(localidad);		           
		            }
		            
		            cliente.setDomicilio(direccion);
		            
		            
		            if (request.getParameter("emailCliente") != null && !request.getParameter("emailCliente").isEmpty()) {
		            cliente.setEmail(request.getParameter("emailCliente"));
		            }
		            if (request.getParameter("telefonoCliente") != null && !request.getParameter("telefonoCliente").isEmpty()) {
		            cliente.setTelefono(request.getParameter("telefonoCliente"));
		            }
		            
		            cliente.setEstado(true);

			} catch (Exception e) {
				e.printStackTrace();
			}

		return cliente;
	}

	private void redirigir(HttpServletRequest request, HttpServletResponse response, String nombreAtributo,
		String msjError, String ruta) throws ServletException, IOException {
		request.setAttribute(nombreAtributo, msjError);
		request.getRequestDispatcher(ruta).forward(request, response);
	}
	
	private void existeCliente(HttpServletRequest request, HttpServletResponse response, Cliente cliente) throws ServletException, IOException {
		
		Boolean dni = clienteNegocio.existeDNI(cliente.getDNI());
		Boolean cuil = clienteNegocio.existeCUIL(cliente.getCUIL());
		
		if (dni && cuil) {
			redirigir(request, response, "error", "El DNI " + cliente.getDNI() + " y el CUIL " + cliente.getCUIL() + " ya existen", rutaAltaClienteJSP);
		}

		if (dni) {
			redirigir(request, response, "error", "El DNI " + cliente.getDNI() + " ya existe", rutaAltaClienteJSP);
		}
		
		if (cuil) {
			redirigir(request, response, "error", "El CUIL " + cliente.getCUIL() + " ya existe", rutaAltaClienteJSP);
		}
	}
	
	public void cargarDesplegables(HttpServletRequest request, HttpServletResponse response){
		ProvinciaNegocioImplementacion pni= new ProvinciaNegocioImplementacion();
		LocalidadNegocioImplementacion lni= new LocalidadNegocioImplementacion();
		PaisNegocioImplementacion pani= new PaisNegocioImplementacion();
		String nacionalidadParam;
		String provinciaParam;
		String localidadParam;
				
		List<Pais> listaPaises= pani.listar();
		request.setAttribute("listaPaises", listaPaises);
		
		try {
	    	if (request.getParameter("selectNacionalidad") != null && !request.getParameter("selectNacionalidad").isEmpty()) {
	    		nacionalidadParam = request.getParameter("selectNacionalidad");		
			request.setAttribute("nacionalidadElegida", nacionalidadParam);			
	    	}
	    	//Se puede agregar un else con un mensajito q este campo debe ser seleccionado
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		
		List<Provincia> listaProvincias= pni.listar();
		request.setAttribute("listaProvincias", listaProvincias);
		
		try {
	    	if (request.getParameter("selectProvincia") != null && !request.getParameter("selectProvincia").isEmpty()) {
	    	provinciaParam = request.getParameter("selectProvincia");		
			request.setAttribute("provinciaElegida", provinciaParam);			
	    	}
	    	//Se puede agregar un else con un mensajito q este campo debe ser seleccionado
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }		
		
		if(request.getAttribute("provinciaElegida")!=null){
			int idProvincia= Integer.parseInt((String) request.getAttribute("provinciaElegida"));
			List<Localidad> listaLocalidades= lni.listarLocalidadesPorProvincia(idProvincia);
			request.setAttribute("listaLocalidades", listaLocalidades);
		}
			
		try {
		   	if (request.getParameter("selectLocalidad") != null && !request.getParameter("selectLocalidad").isEmpty()) {
		   	localidadParam = request.getParameter("selectLocalidad");		
			request.setAttribute("localidadElegida", localidadParam);		
		    }
		    	//Se puede agregar un else con un mensajito q este campo debe ser seleccionado
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
	}
}