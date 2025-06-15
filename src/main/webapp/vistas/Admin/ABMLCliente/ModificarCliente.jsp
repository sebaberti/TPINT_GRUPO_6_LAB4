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
<title>Modificar Cliente</title>

</head>
<body>


<jsp:include page="../../Header.jsp" />
	
	<main class="container py-4 mb-4 d-flex flex-column justify-content-center">
			<div class="row text-center">
			<h2 class="fw-semibold">Modificar Cliente</h2>
		</div>
		
		<div class="row justify-content-center mb-3">
			<form class="" method="POST" action="">
			
			<div class="row row-cols-sm-1 row-cols-md-2 row-cols-lg-3">
				<div class="col mb-3">
						<label for="lblDNI" class="form-label">Número de documento</label> 
						<input type="text" class="form-control" id="lblDNI" placeholder="Ingrese su número de DNI" required pattern="^\d+$" title="Solo se permiten números">
					</div>
					<div class="col mb-3">
						<label for="lblCUIL" class="form-label">Número de CUIL</label>
						<input type="text" class="form-control" id="lblCUIL" placeholder="Ingrese su número de CUIl" required pattern="^\d+$" title="Solo se permiten números">
					</div>
					<div class="col mb-3 d-flex flex-column justify-content-end">
						<button type="submit" class="btn btn-secondary btn-md w-25 .btn-abml">Buscar</button>
    				</div>
					<div class="col mb-3">
						<label for="lblNombre" class="form-label" >Nombre/s</label>
						<input type="text" class="form-control" id="lblNombre" placeholder="Ingrese su nombre" required pattern="[A-Za-zÁÉÍÓÚáéíóúÑñ\s]+" title="Solo se permiten letras" disabled>
					</div>
					<div class="col mb-3">
						<label for="lblApellido" class="form-label" >Apellido/s</label>
						<input type="text" class="form-control" id="lblApellido" placeholder="Ingrese su apellido" required pattern="[A-Za-zÁÉÍÓÚáéíóúÑñ\s]+" title="Solo se permiten letras" disabled>
					</div>
			
			 		<div class="col mb-3">
      					<label for="lblSelectSexo" class="form-label">Sexo</label>
      					<select id="lblSelectSexo" class="form-select" required disabled>
        					<option value="" disabled selected>Seleccione un sexo</option>
        					<option>Femenino</option>
        					<option>Masculino</option>
      					</select>
      				</div>
    				<div class="col mb-3">
						<label for="lblNacionalidad" class="form-label" >Nacionalidad</label>
						<select id="lblNacionalidad" class="form-select" required disabled>
        					<option value="" disabled selected>Seleccionar país</option>
        					<!-- Cargar desde DB -->
      					</select>
					</div>
					<div class="col mb-3">
						<label for="lblFechaNacimiento" class="form-label" >Fecha Nacimiento</label>
						<input type="date" class="form-control" id="lblFechaNacimiento" required disabled>
					</div>
			
					<div class="col mb-3">
						<label for="lblDireccion" class="form-label" >Dirección</label>
						<input type="text" class="form-control" id="lblDireccion" placeholder="Ej: Calle 123" required disabled>
					</div>
					<div class="col mb-3">
						<label for="lblProvincia" class="form-label" >Provincia</label>
						<select id="lblProvincia" class="form-select" required disabled>
        					<option value="" disabled selected>Seleccionar provincia</option>
        					<!-- Cargar desde DB -->
      					</select>
					</div>
					<div class="col mb-3">
						<label for="lblLocalidad" class="form-label" >Localidad</label>
						<select id="lblLocalidad" class="form-select" required disabled>
        					<option value="" disabled selected>Seleccionar localidad</option>
        					<!-- Cargar desde DB -->
      					</select>
					</div>
			
					<div class="col mb-3">
						<label for="lblEmail" class="form-label" >Correo electronico</label>
						<input type="email" class="form-control" id="lblEmail" placeholder="Ej: ejemplo@proveedor.com" required disabled>
					</div>
					<div class="col mb-3">
						<label for="lblTelefono" class="form-label" >Teléfono</label>
						<input type="text" class="form-control" id="lblTelefono" placeholder="Ej: 1126440749" required pattern="^\d+$" title="Solo se permiten números" disabled>
					</div>
			
			</div>
    			
    			<div class="row">
    				<div class="col mb-3">
						<button type="submit" class="btn btn-warning btn-md w-100 .btn-abml">Modificar</button>
    				</div>
    			</div>
			</form>
		</div>
	</main>
<jsp:include page="../../Footer.jsp" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>