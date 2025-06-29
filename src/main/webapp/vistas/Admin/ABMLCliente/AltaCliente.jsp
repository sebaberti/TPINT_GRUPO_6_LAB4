<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="entidades.Cliente"%>
<%@ page import="entidades.Pais"%>
<%@ page import="entidades.Provincia"%>
<%@ page import="entidades.Localidad"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="negocioImplementacion.Seguridad"%>
<%
Object user = session.getAttribute("usuario");

if (!Seguridad.sesionActiva(user)) {
	response.sendRedirect(request.getContextPath() + "/vistas/Login.jsp");
    return;
} 
if (!Seguridad.esAdministrador(user)) {
	response.sendRedirect(request.getContextPath() + "/vistas/Login.jsp");
    return;
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/estiloInicio.css">
<title>Alta Cliente</title>
</head>
<body>


	<jsp:include page="../../Header.jsp" />

	<main
		class="container py-4 mb-4 d-flex flex-column justify-content-center">
		<div class="row text-center">
			<h2 class="fw-semibold">Alta nuevo Cliente</h2>
		</div>
<%
    Cliente cliente = null;
    Object objCliente = request.getAttribute("nuevoCliente");
    if (objCliente != null && objCliente instanceof Cliente) {
        cliente = (Cliente) objCliente;
    }
%>

		<div class="row justify-content-center mb-3">
			<%if(request.getAttribute("mensajeError")!=null){%>
				<div class="alert alert-danger" role="alert">
  					<%= request.getAttribute("mensajeError") %>
				</div>								
				<%}%>
			<form method="POST"
				action="${pageContext.request.contextPath}/AltaClienteServlet">
				<div class="row row-cols-sm-1 row-cols-md-2 row-cols-lg-3">
					<div class="col mb-3">
						<label for="DNICliente" class="form-label">Número de
							documento</label> <input type="text" class="form-control" id="DNICliente"
							name="DNICliente" placeholder="Ingrese su número de DNI" required
							pattern="^\d{8}$" title="Solo se permiten números"
							value="<%= cliente != null && cliente.getDNI()!= null ? cliente.getDNI() :"" %>">
					</div>
					<div class="col mb-3">
						<label for="CUILCliente" class="form-label">Número de CUIL</label>
						<input type="text" class="form-control" id="CUILCliente"
							name="CUILCliente" placeholder="20-43158994-6" required
							pattern="\d{2}-\d{8}-\d" title="Formato esperado XX-XXXXXXXX-XX"
							value="<%= cliente != null && cliente.getCUIL() != null ? cliente.getCUIL() :"" %>">

					</div>
					<div class="col mb-3">
						<label for="nombreCliente" class="form-label">Nombre/s</label> <input
							type="text" class="form-control" id="nombreCliente"
							name="nombreCliente" placeholder="Ingrese su nombre" required
							pattern="[A-Za-zÁÉÍÓÚáéíóúÑñ\s]+" title="Solo se permiten letras"
							value="<%= cliente != null && cliente.getNombre() !=null ? cliente.getNombre() :"" %>">
					</div>
					<div class="col mb-3">
						<label for="apellidoCliente" class="form-label">Apellido/s</label>
						<input type="text" class="form-control" id="apellidoCliente"
							name="apellidoCliente" placeholder="Ingrese su apellido" required
							pattern="[A-Za-zÁÉÍÓÚáéíóúÑñ\s]+" title="Solo se permiten letras"
							value="<%= cliente != null && cliente.getApellido() != null ? cliente.getApellido() :"" %>">
					</div>

					<div class="col mb-3">
						<label for="selectSexo" class="form-label">Sexo</label> <select
							id="selectSexo" name="selectSexo" class="form-select" required>
							<option value="" disabled
								<%= (cliente == null || cliente.getSexo() == null) ? "selected" : "" %>>Seleccione
								un sexo</option>
							<option value="F"
								<%= (cliente != null && "F".equals(cliente.getSexo())) ? "selected" : "" %>>Femenino</option>
							<option value="M"
								<%= (cliente != null && "M".equals(cliente.getSexo())) ? "selected" : "" %>>Masculino</option>
						</select>
					</div>

		
					<div class="col mb-3">
						<label for="fechaNacimientoCliente" class="form-label">Fecha
							Nacimiento</label>
						<%
    					String fechaFormateada = "";
    					if (cliente != null && cliente.getFecha_nacimiento() != null) {
        				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        				fechaFormateada = sdf.format(cliente.getFecha_nacimiento());
    					}
						%>
						<input type="date" class="form-control"
							id="fechaNacimientoCliente" name="fechaNacimientoCliente"
							required value="<%= fechaFormateada %>">
					</div>
					<div class="col mb-3">
						<label for="selectNacionalidad" class="form-label">Nacionalidad</label>
						<%
						int nacionalidadElegida = -1;
						List<Pais> nacionalidades = (ArrayList<Pais>) request.getAttribute("listaPaises");
						if (request.getAttribute("nacionalidadElegida") != null) {
							nacionalidadElegida = Integer.parseInt(String.valueOf(request.getAttribute("nacionalidadElegida")));
						}
						%>
						<select name="selectNacionalidad" id="selectNacionalidad"
							class="form-select" required>
							<option value="" disabled
								<%=(nacionalidadElegida == -1) ? "selected" : ""%>>Seleccionar
								nacionalidad</option>
							<%
							if (nacionalidades != null) {
								for (Pais p : nacionalidades) {
									boolean esSeleccionada = (p.getId() == nacionalidadElegida);
							%>
							<option value="<%=p.getId()%>"
								<%=esSeleccionada ? "selected" : ""%>><%=p.getNombre()%></option>
							<%
		    				}
							}
						%>
						</select>
					</div>

					<div class="col mb-3">
						<label for="direccionCliente" class="form-label">Dirección</label>
						<input type="text" class="form-control" id="direccionCliente"
							name="direccionCliente" placeholder="Ej: Calle 123"
							value="<%= cliente != null && cliente.getDomicilio().getDireccion()!=null ? cliente.getDomicilio().getDireccion() :"" %>"
							required>
					</div>

					<div class="col mb-3">
						<label name="selectProvincia" class="form-label">Provincia</label>
						<select name="selectProvincia" class="form-select"
							onchange="this.form.submit()" required>
							<option value="">Seleccionar provincia</option>
							<%
						int provinciaElegida=-1;
						List<Provincia> provincias = (ArrayList<Provincia>) request.getAttribute("listaProvincias");
						if (request.getAttribute("provinciaElegida") != null) {
							provinciaElegida = Integer.parseInt(String.valueOf(request.getAttribute("provinciaElegida")));
						}						
						if (provincias != null) {
            				for (Provincia p : provincias) {
                			boolean esSeleccionada = (p.getId() == provinciaElegida);
    						%>
							<option value="<%= p.getId() %>"
								<%= esSeleccionada ? "selected" : "" %>>
								<%= p.getNombre() %>
							</option>
							<%}
            			}%>
						</select>
					</div>

					<div class="col mb-3">
						<label for="selectLocalidad" class="form-label">Localidad</label>
						<select name="selectLocalidad" id="selectLocalidad" class="form-select" required>
							<option value="">Seleccionar localidad</option>
							<%
						int localidadElegida=-1;
						List<Localidad> localidades=null;
						
						if (request.getAttribute("provinciaElegida") != null && request.getAttribute("listaLocalidades")!=null) {
							localidades= (ArrayList<Localidad>) request.getAttribute("listaLocalidades");
						}	
						
						if (request.getAttribute("localidadElegida") != null) {
					           localidadElegida = Integer.parseInt(String.valueOf(request.getAttribute("localidadElegida")));
					            }
						if (localidades != null) {							
            				for (Localidad l : localidades) {
                			boolean esSeleccionada = (l.getId() == localidadElegida);
    						%>
							<option value="<%= l.getId() %>"
								<%= esSeleccionada ? "selected" : "" %>>
								<%= l.getNombre() %>
							</option>
							<%}
            			}%>
						</select>
					</div>
					<div class="col mb-3">
						<label for="emailCliente" class="form-label">Correo
							electronico</label> <input type="email" class="form-control"
							id="emailCliente" name="emailCliente"
							placeholder="Ej: ejemplo@proveedor.com"
							value="<%= cliente != null && cliente.getEmail()!=null ? cliente.getEmail() :"" %>" required>
					</div>
					<div class="col mb-3">
						<label for="telefonoCliente" class="form-label">Teléfono</label>
						<input type="text" class="form-control" id="telefonoCliente"
							name="telefonoCliente" placeholder="Ej: 1126440749" required
							pattern="^\d+$" title="Solo se permiten números"
							value="<%= cliente != null && cliente.getTelefono()!=null ? cliente.getTelefono() :"" %>">
					</div>
				</div>
				<div class="row">
					<div class="col mb-3">
						<input type="submit" id="btnAltaCliente" name="btnAltaCliente"
							class="btn btn-success btn-md w-100 btn-abml"
							value="Registrar Cliente" />
					</div>
				</div>
			</form>
		</div>
	</main>

	<!-- Formatea el campo CUIL a NN-NNNNNNNN-NN -->
	<script>
		document.getElementById('CUILCliente').addEventListener('input', function (e) {
	    	let input = e.target;
	    	let value = input.value;
	
	   		value = value.replace(/\D/g, '');
	
	    	if (value.length > 11) value = value.slice(0, 11);
	
	    	let formatted = '';
	    	if (value.length > 0) {
	       		formatted += value.substring(0, Math.min(2, value.length));
	    	}
	    	if (value.length >= 3) {
	       		 formatted += '-' + value.substring(2, Math.min(10, value.length));
	    	}
	    	if (value.length > 10) {
	       	 formatted += '-' + value.substring(10);
	   		 }
	    input.value = formatted;
		});
	</script>

	<script>
		document.getElementById("DNICliente").addEventListener("input", function (e) {
	    let digitsOnly = e.target.value.replace(/\D/g, '');
	    
	    if (digitsOnly.length > 8) {
	        digitsOnly = digitsOnly.slice(0, 8);
	    }
	
	    e.target.value = digitsOnly;
	});
	</script>

	<jsp:include page="../../Footer.jsp" />
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>