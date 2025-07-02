package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidades.Cliente;
import entidades.Cuenta;
import entidades.CuentaTipo;
import entidades.Usuario;
import negocioImplementacion.ClienteNegocioImplementacion;
import negocioImplementacion.CuentaNegocioImplementacion;
import negocioImplementacion.CuentaTipoNegocioImplementacion;
import validaciones.ExcepLimiteCtasActivas;
import validaciones.ValidacionesCuentas;

/**
 * Servlet implementation class AltaCuentaServlet
 */
@WebServlet("/AltaCuentaServlet")
public class AltaCuentaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ValidacionesCuentas validarCampo = new ValidacionesCuentas();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AltaCuentaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	try {
    		ClienteNegocioImplementacion clienteNeg = new ClienteNegocioImplementacion();
    		
    		Cliente cliente = null;
    		String btnBuscar = request.getParameter("btnBuscar");
    		String dniFiltro = request.getParameter("DniCliente");
    		String btnCrear = request.getParameter("btnCrear");

    		// Si se clickeo para buscar por DNI:
    		if (btnBuscar != null && dniFiltro != null && !dniFiltro.trim().isEmpty()) {
    			try {
    				int dni = Integer.parseInt(dniFiltro); // valido que hayan completado con un int
    				cliente = clienteNeg.clientePorDNI(dni);
    				if(cliente!=null) {
        				request.setAttribute("cliente", cliente);
        				request.getSession().setAttribute("idCliente", cliente.getId()); //lo guardo en session para no perderlo
    				}else {
    					request.setAttribute("error", "El DNI ingresado no es válido.");
    				}
    			} catch (NumberFormatException e) {
    				request.setAttribute("error", "El DNI ingresado no es válido.");
    			}
    		}

    		// si clickeo la opcion "Crear cuenta":  		
    		if (btnCrear != null) {
    		    try {
    		        if (!validarCampo.campoVacio(request.getParameter("usuario"))) {
    		            request.setAttribute("error", "Primero debe seleccionar un Cliente activo");
    		        } else {
    		            request.getSession().setAttribute("nombreUsuarioAlta", request.getParameter("usuario"));
    		            request.getSession().setAttribute("dniCliente", request.getParameter("DniCliente"));

    		            if (request.getParameter("tipoCuenta") != null) {
    		                CuentaNegocioImplementacion cuentaNeg = new CuentaNegocioImplementacion();
    		                Cuenta ctaNueva = new Cuenta();
    		                ctaNueva.setId(0); //seteo id en 0, si se crea la cta este dato se pisa, lo uso como validacion
    		                
    		                ctaNueva = (Cuenta) cuentaNeg.crearCuentaNueva(request);

    		                if (ctaNueva.getId()!=0) {
    		                	request.setAttribute("cuentaNueva", ctaNueva);
    		                    request.setAttribute("mostrarModalCuentaCreada", true);
    		                } else {    		                	
    		                    request.setAttribute("error", "Error al crear la cuenta.");
    		                }
    		            } else {
    		                request.setAttribute("errorTipoCuenta", "Debe seleccionar un tipo de Cuenta");
    		            }
    		        }
    		    } catch (ExcepLimiteCtasActivas ex) {
    		    	request.setAttribute("mostrarModalLimiteAlcanzado", true);
    		        request.setAttribute("errorLimite", ex.getMessage());
    		        System.out.println("Excepción personalizada capturada: " + ex.getMessage());
    		        ex.printStackTrace();
    		        request.getRequestDispatcher("/vistas/Admin/Cuentas/AltaCuentas.jsp").forward(request, response);
    		    	return;
    		    } catch (NumberFormatException e) {
    		        e.printStackTrace();
    		        request.setAttribute("error", "Error en el tipo de datos ingresados para el DNI");
    		    } catch (Exception e) {
    		        e.printStackTrace();
    		        request.setAttribute("error", "Error no esperado");
    		    }
    		}

            request.getRequestDispatcher("/vistas/Admin/Cuentas/AltaCuentas.jsp").forward(request, response);
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		response.getWriter().println("Error al obtener el cliente: " + e.getMessage());
    	}
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
			doGet(request, response);
    }

}