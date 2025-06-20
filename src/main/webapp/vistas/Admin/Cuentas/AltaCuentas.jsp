<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="entidades.Cliente"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Alta de Cuenta</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/estiloInicio.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/Cuentas/estiloAltaCuentas.css">
</head>
<body>

	<jsp:include page="/vistas/Header.jsp" />

		<%
		Cliente cliente = (Cliente) request.getAttribute("cliente");
		%>


	<!-- Formulario -->
	<div class="container my-5">
		<form action="#" method="post" class="form-section">
			<h5 class="form-title">Buscar Cliente</h5>
			<div class="input-group mb-4">
				<input type="text" class="form-control"
					placeholder="Ingrese DNI del cliente" name="txtDniCliente" required>
				<button class="btn btn-success" name="btnBuscar" type="submit">&#128269;</button>
			</div>

			<div class="mb-3">
				<label for="usuario" class="form-label">Usuario:</label> 
				<input
					type="text" class="form-control" id="usuario" name="usuario" 
					value="<%= (cliente != null && cliente.getUsuario() != null) ? cliente.getUsuario().getNombreUsuario() : "" %>"
					readonly>
			</div>

			<div class="row g-3">
				<div class="col-md-6">
					<label for="nombre" class="form-label">Nombre:</label> <input
						type="text" class="form-control bg-light" id="nombre"
						name="nombre" readonly>
				</div>
				<div class="col-md-6">
					<label for="apellido" class="form-label">Apellido:</label> <input
						type="text" class="form-control bg-light" id="apellido"
						name="apellido" readonly>
				</div>
			</div>

			<div class="mt-4 mb-3">
				<label for="tipoCuenta" class="form-label">Tipo de cuenta:</label> <select
					class="form-select" id="tipoCuenta" name="tipoCuenta">
					<option selected disabled>Seleccione una opción</option>
					<option value="1">Caja de Ahorro</option>
					<option value="2">Cuenta Corriente</option>
				</select>
			</div>

			<div class="d-flex justify-content-end gap-2">
				<button type="submit" class="btn btn-primary">Crear Cuenta</button>
			</div>
		</form>
	</div>

	<jsp:include page="/vistas/Footer.jsp" />
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
