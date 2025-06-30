<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
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

String nombreUsuario = request.getParameter("nombreUsuario");
String estadoParam = request.getParameter("estado");
boolean estado = "true".equals(estadoParam);
%>

<html>
<head>
<meta charset="UTF-8">
<title>Modificar Usuario</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/estiloInicio.css">
	</head>
<body>
	<jsp:include page="../../Header.jsp" />
	
		<main>
		<div class="container mt-5">
			<h2 class="text-center mb-4">Modificar Usuario</h2>
			<div class="row justify-content-center">
				<div class="col-md-6">
					<form action="${pageContext.request.contextPath}/AltaUsuarioServlet" method="post">
						
						<div class="mb-3">
							<label for="lblUsuario" class="form-label">Usuario</label> 
							<div class="d-flex gap-2">
								<input	type="text" class="form-control" name="lblUsuario" value="<%= nombreUsuario %>" required disabled>
								 <button type="button" class="btn btn-warning btn-sm">
                                	<i class="bi bi-pencil-square" style="font-size: 1rem;"></i>
                            	</button>
							</div>
						</div>
						<div class="col mb-3">
						    <label for="selectEstado" class="form-label">Estado</label>
						   <select id="selectEstado" name="estado" class="form-select" required>
							    <option value="true" <%= estado ? "selected" : "" %>>Activo</option>
							    <option value="false" <%= !estado ? "selected" : "" %>>Inactivo</option>
							</select>						   
						</div>
						<div class="d-grid">
							<button type="submit" class="btn btn-warning">Modificar usuario</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</main>


	<jsp:include page="../../Footer.jsp" />
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>