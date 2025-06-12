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
<title>Insert title here</title>

</head>
<body>


<jsp:include page="./Header.jsp" />
	
	<main class="container py-4 mb-4">
			<div class="row text-center">
			<h2 class="fw-semibold">Convertite en cliente Six</h2>
			<p class="text-muted fs-6">Completa el formulario y empeza a operar</p>
		</div>
		
		<div class="row justify-content-center mb-3">
			<form class="" method="POST" action="">
			
			<div class="row row-cols-sm-1 row-cols-md-2 row-cols-lg-3">
				<div class="col mb-3">
						<label for="lblDNI" class="form-label">Número de documento</label> 
						<input type="text" class="form-control" id="lblDNI" placeholder="Ingrese su número de DNI" required>
					</div>
					<div class="col mb-3">
						<label for="lblCUIL" class="form-label">Número de CUIL</label>
						<input type="text" class="form-control" id="lblCUIL" placeholder="Ingrese su número de CUIl" required>
					</div>
					<div class="col mb-3">
						<label for="lblNombre" class="form-label" >Nombre/s</label>
						<input type="text" class="form-control" id="lblNombre" placeholder="Ingrese su nombre" required>
					</div>
					<div class="col mb-3">
						<label for="lblApellido" class="form-label" >Apellido/s</label>
						<input type="text" class="form-control" id="lblApellido" placeholder="Ingrese su apellido" required>
					</div>
			
			 		<div class="col mb-3">
      					<label for="lblSelectSexo" class="form-label">Sexo</label>
      					<select id="lblSelectSexo" class="form-select" required>
        					<option value="" disabled selected>Seleccione un sexo</option>
        					<option>Femenino</option>
        					<option>Masculino</option>
      					</select>
      				</div>
    				<div class="col mb-3">
						<label for="lblNacionalidad" class="form-label" >Nacionalidad</label>
						<input type="text" class="form-control" id="lblNacionalidad" placeholder="Ingrese su nacionalidad" required>
					</div>
					<div class="col mb-3">
						<label for="lblFechaNacimiento" class="form-label" >Fecha Nacimiento</label>
						<input type="date" class="form-control" id="lblNacionalidad" required>
					</div>
			
					<div class="col mb-3">
						<label for="lblDireccion" class="form-label" >Dirección</label>
						<input type="text" class="form-control" id="lblDireccion" placeholder="Ej: Calle 123" required>
					</div>
					<div class="col mb-3">
						<label for="lblProvincia" class="form-label" >Provincia</label>
						<input type="text" class="form-control" id="lblProvincia" placeholder="Ingrese su provincia" required>
					</div>
					<div class="col mb-3">
						<label for="lblLocalidad" class="form-label" >Localidad</label>
						<input type="text" class="form-control" id="lblLocalidad" placeholder="Ingrese su localidad" required>
					</div>
			
					<div class="col mb-3">
						<label for="lblEmail" class="form-label" >Correo electronico</label>
						<input type="email" class="form-control" id="lblLocalidad" placeholder="Ej: ejemplo@proveedor.com" required>
					</div>
					<div class="col mb-3">
						<label for="lblTelefono" class="form-label" >Teléfono</label>
						<input type="text" class="form-control" id="lblLocalidad" placeholder="Ej: 1126440749" required>
					</div>
			
			</div>
					
				
				<div class="row">
					<div class="col mb-3">
      					<label for="lblSelectCuenta" class="form-label">Tipo de cuenta</label>
      					<select id="lblSelectCuenta" class="form-select" required>
        					<option value="" disabled selected>Seleccione un tipo de cuenta</option>
        					<!-- Cargar desde DB -->
      					</select>
    				</div>
				</div>
			 	
    			
    			<div class="row">
    				<div class="col mb-3">
						<button type="submit" class="btn btn-success btn-sm w-100 .btn-abml">Registrarme</button>
    				</div>
    			</div>
			</form>
		</div>
	</main>
<jsp:include page="./Footer.jsp" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>