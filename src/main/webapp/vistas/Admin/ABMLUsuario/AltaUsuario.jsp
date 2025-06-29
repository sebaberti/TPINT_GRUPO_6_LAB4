<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Alta de usuario</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/estiloInicio.css">
</head>
<body>
	<jsp:include page="../../Header.jsp" />

	
		<main>
		<div class="container mt-5">
			<h2 class="text-center mb-4">Crear nuevo usuario</h2>
			<div class="row justify-content-center">
				<div class="col-md-6">		
    				<form action="${pageContext.request.contextPath}/AltaUsuarioServlet" method="post">
						<div class="mb-3">
							<label for="Usuario" class="form-label">Usuario</label> 
							<input type="text" class="form-control" name="Usuario" 
							required placeholder="Ingrese el nombre de usuario" required>
						</div>
						<div class="mb-3">
							<label for="Clave" class="form-label">Contraseña</label>
							<input type="password" class="form-control" name="Clave" 
							required placeholder="Ingrese la contraseña" required>
						</div>
						<div class="mb-3">
							<label for="RepetirClave" class="form-label">Repetir contraseña</label> 
							<input type="password" class="form-control" name="RepetirClave"
								required placeholder="Repita la contraseña" required>
						</div>
							<%
							String tipoSeleccionado = String.valueOf(request.getAttribute("TipoUserElegido"));
							%>
						<div class="mb-3">
							<label for="TipoUser" class="form-label">Seleccionar Tipo
								De Usuario</label> <select name="TipoUser" class="form-select" required>
								<option value="" disabled
									<%=(tipoSeleccionado == null) ? "selected" : ""%>>Seleccionar
									tipo usuario</option>
								<option value="1"
									<%="1".equals(tipoSeleccionado) ? "selected" : ""%>>Administrador</option>
								<option value="2"
									<%="2".equals(tipoSeleccionado) ? "selected" : ""%>>Cliente</option>
							</select>
						</div>
						<div class="d-grid">
							<input type="submit" class="btn btn-primary" name="btnCrearUsuario" value="Crear Usuario" />
						</div>
					</form>
				</div>
			</div>
		</div>
	</main>


	<jsp:include page="../../Footer.jsp" />
</body>
</html>