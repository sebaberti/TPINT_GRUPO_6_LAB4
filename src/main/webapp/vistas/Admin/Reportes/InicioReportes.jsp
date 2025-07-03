<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="negocioImplementacion.Seguridad" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Inicio Reportes</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/estiloInicio.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/estiloABML.css">
</head>
<body>
	
	<%
		Object user = session.getAttribute("usuario");
		
		if (!Seguridad.sesionActiva(user) || !Seguridad.esAdministrador(user)) {
			response.sendRedirect(request.getContextPath() + "/vistas/Login.jsp");
			return;
			}
	%>

	<jsp:include page="/vistas/Header.jsp" />

	<main class="container py-4">
		<div class="text-center mb-3">
			<h4 class="fw-semibold">Reportes</h4>
			<p class="text-muted small">Seleccione el tipo de reporte que desea ver</p>
		</div>

		<div class="row row-cols-1 row-cols-md-2 g-3 mb-4">
			<div class="col">
				<div class="card text-center card-abml h-100">
					<div class="card-body d-flex flex-column justify-content-between">
						<div>
							<div class="icono text-primary">
								<i class="bi bi-list-ul"></i>
							</div>
							<h6 class="card-title mb-2">Reporte General</h6>
						</div>
						<a href="${pageContext.request.contextPath}/DashboardServlet"
							class="btn btn-primary btn-sm w-100 btn-abml">Seleccionar </a>
					</div>
				</div>
			</div>

			<div class="col">
				<div class="card text-center card-abml h-100">
					<div class="card-body d-flex flex-column justify-content-between">
						<div>
							<div class="icono text-primary">
								<i class="bi bi-list-ul"></i>
							</div>
							<h6 class="card-title mb-2">Reporte de cliente</h6>
						</div>
						<a href="${pageContext.request.contextPath}/ReporteDeClienteServlet"
							class="btn btn-primary btn-sm w-100 btn-abml">Seleccionar</a>
						</div>
				</div>
			</div>
		</div>
		<div class="d-flex justify-content-center">
    	<form method="GET" action="${pageContext.request.contextPath}/vistas/Inicio.jsp">
        <button type="submit" name="btnVolverALInicio" class="btn btn-secondary btn-abml mb-3">
            <i class="bi bi-arrow-return-right me-2"></i>Volver al inicio
        </button>
    	</form>
		</div>
	</main>

	<jsp:include page="/vistas/Footer.jsp" />
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>