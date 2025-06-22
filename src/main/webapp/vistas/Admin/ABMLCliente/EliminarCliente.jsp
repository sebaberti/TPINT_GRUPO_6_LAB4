<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="entidades.Cliente" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/estiloRegistro.css">
<title>Eliminar cliente</title>

</head>
<body>
		
		<%!
			public String manejarNull(String valor) {
				if(valor == null || valor.isEmpty()) 
					return "";
				
				return valor;
			}
		
			public Boolean manejarNull(Boolean valor) {
				if(valor == null) 
					return false;
			
				return true;
		}
		
		%>

<jsp:include page="../../Header.jsp" />
	
	<main class="container py-4 mb-4 d-flex flex-column justify-content-center">
		<div class="row text-center">
			<h2 class="fw-semibold">Eliminar Cliente</h2>
		</div>
		
		<% 
			Cliente cliente = new Cliente(); 
			String error = (String) request.getAttribute("error");
			String procesoExitoso = (String) request.getAttribute("procesoExitoso");
		%>
		
		<%
	    
	    if (error != null) { %>
	    <div class="alert alert-danger text-center mt-3">
	        <%= error %>
	    </div> <%
	    	request.removeAttribute("error");
	    } 
	    
	    %>
	    	
	    <%
	    	if (procesoExitoso != null) { %>
	    		<div class="alert alert-success text-center mt-3">
	       	 		<%= procesoExitoso %>
	  	  		</div> <%
		    	request.removeAttribute("procesoExitoso");
	    	} 
	    
	    %>
			
		<% 
			if(request.getAttribute("cliente") != null) {
				cliente = (Cliente)request.getAttribute("cliente");
			} 
		%>
		
		<div class="row justify-content-center mb-3">
			<form class="" method="POST" action="${pageContext.request.contextPath}/BajaClienteServlet">
			
			<div class="row">
				<div class="col-12 col-md-9 mb-3">
					<label for="DNICliente" class="form-label">Número de documento</label> 
					<input type="text" class="form-control" id="DNICliente" name="DNICliente" placeholder="Ingrese su número de DNI" required pattern="^\d{8}$" title="Solo se permiten hasta 8 digitos" value="<%= manejarNull(cliente.getDNI()) %>">
				</div>
    			<div class="col d-flex flex-column justify-content-end mb-3">
					<button type="submit" name="btnBuscar" class="btn btn-secondary btn-md w-100 .btn-abml">Buscar</button>
    			</div>
			</div>
			
			<div class="row row-cols-sm-1 row-cols-md-2 row-cols-lg-3">
					<div class="col mb-3">
						<label for="CUILCliente" class="form-label">Número de CUIL</label>
						<input type="text" class="form-control" id="CUILCliente" name="CUILCliente" value="<%=  manejarNull(cliente.getCUIL()) %>" disabled>
					</div>
					<div class="col mb-3">
						<label for="lblNombre" class="form-label" >Nombre/s</label>
						<input type="text" class="form-control" id="lblNombre" value="<%= manejarNull(cliente.getNombre())%>"  disabled>
					</div>
					<div class="col mb-3">
						<label for="lblApellido" class="form-label" >Apellido/s</label>
						<input type="text" class="form-control" id="lblApellido" value="<%= manejarNull(cliente.getApellido()) %>" disabled>
					</div>
			
			 		<div class="col mb-3">
      					<label for="lblSelectSexo" class="form-label">Sexo</label>
      					<input type="text" class="form-control" id="lblSelectSexo" value="<%= manejarNull(cliente.getSexo()) %>" disabled>
      				</div>
    				<div class="col mb-3">
						<label for="lblNacionalidad" class="form-label" >Nacionalidad</label>
						<input type="text" class="form-control" id="lblNacionalidad" value="<%= cliente.getNacionalidad() != null ? cliente.getNacionalidad() : "" %>" disabled>
					</div>
					<div class="col mb-3">
						<label for="lblFechaNacimiento" class="form-label" >Fecha Nacimiento</label>
						<input type="date" class="form-control" id="lblFechaNacimiento" value="" disabled>
					</div>
			
					<div class="col mb-3">
						<label for="lblDireccion" class="form-label" >Dirección</label>
						<input type="text" class="form-control" id="lblDireccion" value="agregar codigo" disabled>
					</div>
					<div class="col mb-3">
						<label for="lblProvincia" class="form-label" >Provincia</label>
						<input type="text" class="form-control" id="lblProvincia" value="<%= cliente.getNacionalidad() != null ? cliente.getNacionalidad() : "-" %>" disabled>
					</div>
					<div class="col mb-3">
						<label for="lblLocalidad" class="form-label" >Localidad</label>
						<input type="text" class="form-control" id="lblLocalidad" value="<%= cliente.getNacionalidad() != null ? cliente.getNacionalidad() : "" %>" disabled>
					</div>
			
					<div class="col mb-3">
						<label for="lblEmail" class="form-label" >Correo electronico</label>
						<input type="email" class="form-control" id="lblEmail" value="<%= manejarNull(cliente.getEmail()) %>" disabled>
					</div>
					<div class="col mb-3">
						<label for="lblTelefono" class="form-label" >Teléfono</label>
						<input type="text" class="form-control" id="lblTelefono" value="<%= manejarNull(cliente.getTelefono()) %>" disabled>
					</div>
					<div class="col mb-3">
						<label for="lblEstado" class="form-label" >Estado</label>
						<input type="text" class="form-control" id="lblEstado" value="<%= manejarNull(cliente.getEstado()) ? "Activo" : "Inactivo" %>" disabled>
					</div>
			</div>
    			
    			<div class="row">
    				<div class="col mb-3">
						<button type="submit" name="btnEliminar" class="btn btn-danger btn-md w-100 .btn-abml">Eliminar Cliente</button>
    				</div>
    			</div>
			</form>
		</div>
	</main>
<jsp:include page="../../Footer.jsp" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
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
</body>
</html>