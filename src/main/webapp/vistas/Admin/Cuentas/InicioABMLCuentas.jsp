<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="negocioImplementacion.Seguridad"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>ABML de Cuentas - Banco SIX</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/estiloInicio.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/Cuentas/estiloABML.css">
</head>
<body>

	<jsp:include page="/vistas/Header.jsp" />

	<main class="container py-4">
	<%
	Object user = session.getAttribute("usuario");
	
 	if (!Seguridad.sesionActiva(user) || !Seguridad.esAdministrador(user)) {
	response.sendRedirect(request.getContextPath() + "/vistas/Login.jsp");
    return;
}
	%>
	
		<div class="text-center mb-3">
			<h4 class="fw-semibold">Gestión de Cuentas</h4>
			<p class="text-muted small">Seleccione una opción para administrar las cuentas.</p>
		</div>

		<div class="row row-cols-1 row-cols-md-2 g-3 mb-4">
			<div class="col">
				<div class="card text-center card-abml h-100">
					<div class="card-body d-flex flex-column justify-content-between">
						<div>
							<div class="icono text-success">
								<i class="bi bi-plus-circle"></i>
							</div>
							<h6 class="card-title mb-2">Alta de Cuenta</h6>
						</div>
						<button class="btn btn-success btn-sm w-100 btn-abml"
							onclick="location.href='AltaCuentas.jsp'">Seleccionar</button>
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
							<h6 class="card-title mb-2">Listar y editar Cuentas</h6>
						</div>
						<a href="${pageContext.request.contextPath}/ListarCuentasServlet"
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
