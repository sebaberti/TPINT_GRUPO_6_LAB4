<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/estiloRegistro.css">
<title>Alta Cliente</title>

</head>
<body>


<jsp:include page="../../Header.jsp" />
	
	<main class="container py-4 mb-4 d-flex flex-column justify-content-center">
			<div class="row text-center">
			<h2 class="fw-semibold">Alta nuevo Cliente</h2>
		</div>
		
		<% if (request.getAttribute("error") != null) { %>
    		<div class="alert alert-danger text-center mt-3">
        		<%= request.getAttribute("error") %>
    		</div>
			<% }  %>
		
		<% if (request.getAttribute("clienteInsertado") != null) { %>
    		<div class="alert alert-success text-center mt-3">
        		<%= request.getAttribute("clienteInsertado") %>
    		</div>
			<% }  %>
		
		<div class="row justify-content-center mb-3">
			<form method="POST" action="${pageContext.request.contextPath}/AltaClienteServlet">
			
			<div class="row row-cols-sm-1 row-cols-md-2 row-cols-lg-3">
				<div class="col mb-3">
						<label for="DNICliente" class="form-label">Número de documento</label> 
						<input type="text" class="form-control" id="DNICliente" name="DNICliente" placeholder="Ingrese su número de DNI" required pattern="^\d+$" title="Solo se permiten números">
					</div>
					<div class="col mb-3">
						<label for="CUILCliente" class="form-label">Número de CUIL</label>
						<input type="text" class="form-control" id="CUILCliente" name="CUILCliente" placeholder="Ingrese su número de CUIl" required pattern="^\d+$" title="Solo se permiten números">
					</div>
					<div class="col mb-3">
						<label for="nombreCliente" class="form-label" >Nombre/s</label>
						<input type="text" class="form-control" id="nombreCliente" name="nombreCliente" placeholder="Ingrese su nombre" required pattern="[A-Za-zÁÉÍÓÚáéíóúÑñ\s]+" title="Solo se permiten letras">
					</div>
					<div class="col mb-3">
						<label for="apellidoCliente" class="form-label" >Apellido/s</label>
						<input type="text" class="form-control" id="apellidoCliente" name="apellidoCliente" placeholder="Ingrese su apellido" required pattern="[A-Za-zÁÉÍÓÚáéíóúÑñ\s]+" title="Solo se permiten letras">
					</div>
			
			 		<div class="col mb-3">
      					<label for="selectSexo" class="form-label">Sexo</label>
      					<select id="selectSexo" name="selectSexo" class="form-select" required>
        					<option value="" disabled selected>Seleccione un sexo</option>
        					<option>Femenino</option>
        					<option>Masculino</option>
      					</select>
      				</div>
    				<div class="col mb-3">
						<label for="selectNacionalidad" class="form-label" >Nacionalidad</label>
						<select id="selectNacionalidad" name="selectNacionalidad" class="form-select" required>
        					<option value="" disabled selected>Seleccionar país</option>
        					<option>Argentina</option>
      					</select>
					</div>
					<div class="col mb-3">
						<label for="fechaNacimientoCliente" class="form-label" >Fecha Nacimiento</label>
						<input type="date" class="form-control" id="fechaNacimientoCliente" name="fechaNacimientoCliente" required>
					</div>
			
					<div class="col mb-3">
						<label for="direccionCliente" class="form-label" >Dirección</label>
						<input type="text" class="form-control" id="direccionCliente" name="direccionCliente"  placeholder="Ej: Calle 123" required>
					</div>
					<div class="col mb-3">
						<label for="selectProvincia" class="form-label" >Provincia</label>
						<select id="selectProvincia" name="selectProvincia" class="form-select" required>
        					<option value="" disabled selected>Seleccionar provincia</option>
        					<option>Buenos Aires</option>
      					</select>
					</div>
					<div class="col mb-3">
						<label for="selectLocalidad" class="form-label" >Localidad</label>
						<select id="selectLocalidad" name="selectLocalidad" class="form-select" required>
        					<option value="" disabled selected>Seleccionar localidad</option>
        					<option>CABA</option>
      					</select>
					</div>
			
					<div class="col mb-3">
						<label for="emailCliente" class="form-label" >Correo electronico</label>
						<input type="email" class="form-control" id="emailCliente" name="emailCliente" placeholder="Ej: ejemplo@proveedor.com" required>
					</div>
					<div class="col mb-3">
						<label for="telefonoCliente" class="form-label" >Teléfono</label>
						<input type="text" class="form-control" id="telefonoCliente" name="telefonoCliente" placeholder="Ej: 1126440749" required pattern="^\d+$" title="Solo se permiten números">
					</div>
			
			</div>
    			
    			<div class="row">
    				<div class="col mb-3">
						<button type="submit" id="btnAltaCliente" name="btnAltaCliente" class="btn btn-success btn-md w-100 .btn-abml">Registrar Cliente</button>
    				</div>
    			</div>
			</form>
		</div>
	</main>
	
	
		
		
<jsp:include page="../../Footer.jsp" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>