<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page session="true"%>
<%
String usuarioNombre = "Cliente Prueba";
String tipoUsuario = "cliente";

//Clase para poder iterar luego y poder renderizar los datos. Hermosa clase auxiliar.     
class Dato {
	String campo;
	String valor;

	Dato(String campo, String valor) {
		this.campo = campo;
		this.valor = valor;
	}
}

List<Dato> datos = new ArrayList<>();
datos.add(new Dato("Correo electrónico", "cliente@utnpatxeco.com"));
datos.add(new Dato("Teléfono", "11-1234-5678"));
datos.add(new Dato("Dirección", "Calle Falsa 123, Patxeco"));
datos.add(new Dato("Fecha de nacimiento", "1990-01-01"));
datos.add(new Dato("Nacionalidad", "Argentina"));
datos.add(new Dato("Sexo", "M"));
%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Mi Perfil - Banco SIX</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>
<body>

	<jsp:include page="Header.jsp" />

	<div class="container mt-5 mb-5">
		<h2 class="mb-4">Mi Perfil</h2>
		<div class="row g-4">
			<!-- Columna izquierda, para el perfil -->
			<div class="col-md-4 text-center">
				<img src="https://cdn-icons-png.flaticon.com/512/3135/3135715.png"
					class="rounded-circle img-thumbnail mb-3" width="150"
					alt="Foto de perfil">
				<form>
					<label for="foto" class="form-label">Cambiar foto</label> <input
						class="form-control" type="file" id="foto" disabled>
				</form>
			</div>

			<!-- Columna derecha para cambiar passwords -->
			<div class="col-md-8">
				<div class="card shadow-sm">
					<div class="card-body">
						<h5 class="card-title">Cambiar contraseña</h5>
						<form
							action="${pageContext.request.contextPath}/CambiarPasswordServlet"
							method="post">

							<div class="mb-3">
								<label for="actual" class="form-label">Contraseña actual</label>
								<input type="password" class="form-control" id="actual"
									name="actual">
							</div>
							<div class="mb-3">
								<label for="nueva" class="form-label">Nueva contraseña</label> <input
									type="password" class="form-control" id="nueva" name="nueva">
							</div>
							<div class="mb-3">
								<label for="confirmar" class="form-label">Confirmar
									nueva contraseña</label> <input type="password" class="form-control"
									id="confirmar" name="confirmar">
							</div>
							<button type="submit" class="btn btn-primary">Guardar
								cambios</button>

							<%
							String mensajeError = (String) request.getAttribute("mensajeError");
							String mensajeInformativo = (String) request.getAttribute("mensajeInformativo");

							if (mensajeError != null) {
							%>
							<div class="alert alert-danger mt-3"><%=mensajeError%></div>
							<%
							}
							if (mensajeInformativo != null) {
							%>
							<div class="alert alert-success mt-3"><%=mensajeInformativo%></div>
							<%
							}
							%>
						</form>
					</div>
				</div>
			</div>
		</div>

		<div class="card mt-5 shadow-sm">
			<div class="card-body">
				<h5 class="card-title">Datos personales</h5>
				<div class="row">
					<div class="col-md-6 mb-3">
						<strong>Nombre:</strong> <span class="text-muted"><%=usuarioNombre%></span>
					</div>
					<!-- Iteramos con los datos de arriba -->
					<%
					for (Dato d : datos) {
					%>
					<div class="col-md-6 mb-3">
						<strong><%=d.campo%>:</strong> <span class="text-muted"><%=d.valor%></span>
					</div>
					<%
					}
					%>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="Footer.jsp" />

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
