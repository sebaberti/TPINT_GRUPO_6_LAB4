<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="negocioImplementacion.Seguridad"%>
<%@ page import="entidades.Usuario"%>
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
				<%if(request.getAttribute("mensajeError")!=null){%>
				<div class="alert alert-danger" role="alert">
  					<%= request.getAttribute("mensajeError") %>
				</div>								
				<%}%>
			
    				<form action="${pageContext.request.contextPath}/AltaUsuarioServlet" method="post">
						<div class="mb-3">
							<label for="Usuario" class="form-label">Usuario</label> <input
								type="text" class="form-control" name="Usuario"
								placeholder="Ingrese el nombre de usuario"
								value="<%= request.getAttribute("nombreUsuario") != null ? request.getAttribute("nombreUsuario") : "" %>"
								required minlength="4" maxlength="50" pattern="[A-Za-z0-9_]+"
								title="Solo letras, números y guiones bajos.">
						</div>
						<div class="mb-3">
							<label for="Clave" class="form-label">Contraseña</label> <input
								type="password" class="form-control" name="Clave"
								placeholder="Ingrese la contraseña" required pattern=".*\S.*"
								title="La contraseña no puede ser solo espacios en blanco">
						</div>

						<div class="mb-3">
							<label for="RepetirClave" class="form-label">Repetir
								contraseña</label> <input type="password" class="form-control"
								name="RepetirClave" required placeholder="Repita la contraseña"
								pattern=".*\S.*"
								title="La contraseña no puede ser solo espacios en blanco">
						</div>
						<%
							String tipoSeleccionado = String.valueOf(request.getAttribute("TipoUserElegido"));
							%>
						<div class="mb-3">
							<label for="TipoUser" class="form-label">Seleccionar Tipo
								De Usuario</label> <select name="TipoUser" class="form-select" <%= tipoSeleccionado != null ? "disabled" : "" %> required>
								<option value="" disabled
									<%=(tipoSeleccionado == null) ? "selected" : ""%>>Seleccionar
									tipo usuario</option>
								<option value="1"
									<%="1".equals(tipoSeleccionado) ? "selected" : ""%>>Administrador</option>
								<option value="2"
									<%="2".equals(tipoSeleccionado) ? "selected" : ""%>>Cliente</option>
							</select>
									<% if (tipoSeleccionado != null) { %>
    								<input type="hidden" name="TipoUser" value="<%= tipoSeleccionado %>">
									<% } %>
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