<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="entidades.Cuenta"%>
<%@ page import="entidades.CuentaTipo"%>
<!DOCTYPE html>
<html lang="es">
<head>
<!--  <meta charset="UTF-8"> -->
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Listar Cuentas</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/estiloInicio.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/Cuentas/estiloListarCuentas.css">
</head>

<body>
	<jsp:include page="/vistas/Header.jsp" />

	<main class="container mt-5 mb-5">
		<%
		List<Cuenta> listaCuentas = (List<Cuenta>) request.getAttribute("listaCuentas");
		%>

		<h1 class="text-center mb-4">Listado de Cuentas</h1>

		<!-- Botón Nueva Cuenta -->
		<div class="mb-4 d-flex justify-content-end">
			<button type="button" class="btn btn-primary btn-nueva-cuenta"
				onclick="location.href='${pageContext.request.contextPath}/vistas/Admin/Cuentas/AltaCuentas.jsp'">
				<i class="bi bi-plus-circle me-2"></i> Nueva Cuenta
			</button>
		</div>

		<!-- Formulario de búsqueda -->
		<div class="row justify-content-center">
			<div class="col-md-6">
				<form
					action="${pageContext.request.contextPath}/ListarCuentasServlet"
					method="post">

					<div class="mb-3">
						<label for="dniCliente" class="form-label">DNI del Cliente</label>
						<input type="text" class="form-control shadow-sm" id="dniCliente"
							name="txtDniClientes" placeholder="Ingrese DNI...">
						<%
						if (request.getAttribute("errorDni") != null) {
						%>
						<div class="alert alert-danger"><%=request.getAttribute("errorDni")%></div>
						<%
						}
						%>
					</div>
					<div class="d-grid gap-2 d-md-flex justify-content-md-between">
						<button type="submit" name="btnBuscar" class="btn btn-success">
							<i class="bi bi-search me-1"></i> Buscar
						</button>
						<button type="submit" name="btnVerTodo" class="btn btn-secondary">
							<i class="bi bi-eye me-1"></i> Ver Todo
						</button>
					</div>
				</form>
			</div>
		</div>

		<!-- Tabla de cuentas -->
		<div class="table-responsive mt-5">
			<table
				class="table table-bordered table-hover align-middle text-center tabla-cuentas">
				<thead class="table-light">
					<tr>
						<th>Nro de Cuenta</th>
						<th>DNI Cliente</th>
						<th>Tipo de Cuenta</th>
						<th>CBU</th>
						<th>Saldo</th>
						<th>Estado</th>
						<th>Modificar</th>
						<th>Eliminar</th>
					</tr>
				</thead>
				<tbody>
					<%
					if (listaCuentas != null && !listaCuentas.isEmpty()) {
						for (Cuenta c : listaCuentas) {
					%>
					<tr>
						<td><%=c.getNumeroCuenta()%></td>
						<td><%=c.getCliente().getDNI()%></td>
						<td><%=c.getTipoCuenta().getDescripcion()%></td>
						<td><%=c.getCBU()%></td>
						<td><%=c.getSaldo()%></td>
						<%
						if (c.isEstado()) {
						%>
						<td>Activo</td>
						<%
						} else {
						%>
						<td>Inactivo</td>
						<%
						}
						%>
						<td>
							<button type="button" class="btn btn-warning btn-sm">
								<i class="bi bi-pencil-square"></i>
							</button>
						</td>
						<td>
							<button type="button" class="btn btn-danger btn-sm">
								<i class="bi bi-trash"></i>
							</button>
						</td>
					</tr>
					<%
					}
					} else {
					%>
					<tr>
						<td colspan="5">No hay cuentas cargadas.</td>
					</tr>
					<%
					}
					%>
				</tbody>
			</table>
		</div>
	</main>

	<jsp:include page="/vistas/Footer.jsp" />
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

