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
						<div class="col mb-3">
							<label for="lblDNI" class="form-label">Número de documento</label> 
							<input type="text" class="form-control" id="lblDNI" placeholder="Ingrese su número de DNI" required pattern="^\d+$" title="Solo se permiten números" value="<%= request.getAttribute("DNI")!=null? request.getAttribute("DNI"):""  %>" readonly>
						</div>
						<div class="col mb-3">
							<label for="lblCUIL" class="form-label">Número de CUIL</label>
							<input type="text" class="form-control" id="lblCUIL" placeholder="Ingrese su número de CUIl" required pattern="^\d+$" title="Solo se permiten números" value="<%= request.getAttribute("CUIL")!=null? request.getAttribute("CUIL"):"" %>" readonly>
						</div>
						<div class="col mb-3 d-flex flex-column justify-content-end">
							<input type="submit" class="btn btn-secondary btn-md w-25 .btn-abml" name="btnBuscar" value="Buscar" />
    					</div>
    					<%
							if (request.getAttribute("mensajeError") != null) {
							%>
							<div class="alert alert-danger" role="alert">
								<%=request.getAttribute("mensajeError")%>
							</div>
							<%
							}
						%>
						<%
							if (request.getAttribute("mensajeValidacion") != null) {
							%>
							<div class="lert alert-success " role="alert">
								<%=request.getAttribute("mensajeError")%>
							</div>
							<%
							}
						%>
    				</form>
    				<form action="${pageContext.request.contextPath}/AltaUsuarioServlet" method="post">
						<div class="mb-3">
							<label for="Usuario" class="form-label">Usuario</label> 
							<input type="text" class="form-control" name="Usuario" 
							required placeholder="Ingrese el nombre de usuario">
						</div>
						<div class="mb-3">
							<label for="Clave" class="form-label">Contraseña</label>
							<input type="password" class="form-control" name="Clave" 
							required placeholder="Ingrese la contraseña">
						</div>
						<div class="mb-3">
							<label for="RepetirClave" class="form-label">Repetir contraseña</label> 
							<input type="password" class="form-control" name="RepetirClave"
								required placeholder="Repita la contraseña">
						</div>
						<div class="mb-3">
	    						<label for="TipoUser" class="form-label">Seleccionar Tipo De Usuario</label>
	    						<select name="TipoUser" class="form-select" required>
	    							<option value="" disabled selected> Seleccionar tipo usuario</option>
	    							<option value="1">Administrador</option>
	    							<option value="2">Cliente</option>
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