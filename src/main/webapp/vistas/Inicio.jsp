<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page session="true"%>
<%
//Variables para manejar por ahora. Despues habría que conectarlo 
String usuarioNombre = "Cliente Prueba";
String tipoUsuario = "cliente";

//Clase para poder iterar luego y poder renderizar los datos. Hermosa clase auxiliar. 
class Tarjeta {
	String link;
	String icono;
	String color;
	String titulo;

	Tarjeta(String link, String icono, String color, String titulo) {
		this.link = link;
		this.icono = icono;
		this.color = color;
		this.titulo = titulo;
	}
}

List<Tarjeta> tarjetas = new ArrayList<>();

//Rellenamos la lista con esto asi no estamos tocando en el layout si necesitamos hacer cambios.. 

if (tipoUsuario  == "administrador") {
	tarjetas.add(new Tarjeta("Clientes.jsp", "bi-people-fill", "text-primary", "Gestionar Clientes"));
	tarjetas.add(new Tarjeta("Cuentas.jsp", "bi-bank2", "text-success", "Gestionar Cuentas"));
	tarjetas.add(new Tarjeta("Usuarios.jsp", "bi-person-lock", "text-info", "Usuarios"));
	tarjetas.add(new Tarjeta("AutorizarPrestamos.jsp", "bi-check2-circle", "text-warning", "Autorizar Préstamos"));
	tarjetas.add(new Tarjeta("Reportes.jsp", "bi-bar-chart-line", "text-danger", "Reportes"));
} else if (tipoUsuario  == "cliente") {
	tarjetas.add(new Tarjeta("Cuentas.jsp", "bi-wallet2", "text-primary", "Mis Cuentas"));
	tarjetas.add(new Tarjeta("Movimientos.jsp", "bi-journal-text", "text-success", "Movimientos"));
	tarjetas.add(new Tarjeta("Transferencias.jsp", "bi-arrow-left-right", "text-info", "Transferencias"));
	tarjetas.add(new Tarjeta("SolicitarPrestamo.jsp", "bi-cash-stack", "text-warning", "Solicitar Préstamo"));
	tarjetas.add(new Tarjeta("PagarCuota.jsp", "bi-credit-card-2-back", "text-danger", "Pagar Cuotas"));
}
%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Inicio - Banco SIX</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/estiloInicio.css">
</head>
<body>

	<jsp:include page="Header.jsp" />

	<main>
		<%
		if (usuarioNombre != null && tipoUsuario == "administrador") {
		%>
		<section class="container py-4">
			<div class="px-3 px-md-5">
				<h2 class="fw-bold mb-3">
					¡Bienvenido,
					<%=usuarioNombre%>!
				</h2>
				<p class="text-muted fs-5">
					Estás logueado como <strong>Administrador</strong>.
				</p>

				<div class="row g-4 mt-4">
					<%
					for (Tarjeta t : tarjetas) {
					%>
					<div class="col-md-4">
						<a href="<%=t.link%>"
							class="card text-decoration-none text-dark shadow-sm border-0 h-100 hover-shadow">
							<div class="card-body text-center">
								<i class="bi <%=t.icono%> display-4 <%=t.color%>"></i>
								<h5 class="card-title mt-3"><%=t.titulo%></h5>
							</div>
						</a>
					</div>
					<%
					}
					%>
				</div>
		</section>
		<%
		} else if (usuarioNombre != null && tipoUsuario == "cliente") {
		%>
		<section class="container py-4">
			<div class="px-3 px-md-5">
				<h2 class="fw-bold mb-3">
					¡Hola,
					<%=usuarioNombre%>!
				</h2>
				<p class="text-muted fs-5">
					Estás logueado como <strong>Cliente</strong>.
				</p>

				<div class="row g-4 mt-4">
					<%
					for (Tarjeta t : tarjetas) {
					%>
					<div class="col-md-4">
						<a href="<%=t.link%>"
							class="card text-decoration-none text-dark shadow-sm border-0 h-100 hover-shadow">
							<div class="card-body text-center">
								<i class="bi <%=t.icono%> display-4 <%=t.color%>"></i>
								<h5 class="card-title mt-3"><%=t.titulo%></h5>
							</div>
						</a>
					</div>
					<%
					}
					%>
				</div>
		</section>
		<%
		} else {
		%>
		<section
			class="hero d-flex align-items-center justify-content-center text-center bg-light min-vh-100">
			<div class="container">
				<h1 class="display-4">Bienvenido a SIX</h1>
				<p class="lead">Tu banco de confianza para operaciones seguras,
					préstamos personales y más.</p>
				<a href="Login.jsp" class="btn btn-primary btn-lg mt-3">Accedé
					ahora</a>
			</div>
		</section>
		<%
		}
		%>
	</main>
	<jsp:include page="Footer.jsp" />

	<!-- Bootstrap icons y JS -->
	<link rel="stylesheet"
		href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
