<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="entidades.Cuenta"%>
<%@ page import="entidades.Cuota"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Pagar Préstamos - Banco SIX</title>
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

	<jsp:include page="/vistas/Header.jsp" />

	<main class="container py-4">
		<div class="text-center mb-4">
			<h4 class="fw-semibold">Pago de Préstamos</h4>
			<p class="text-muted">Seleccione una cuota pendiente y la cuenta
				desde la cual se desea pagar.</p>
		</div>

		<!-- Formulario del filtro -->
		<form method="get" action="PagoCuotasServlet" class="row mb-3">
			<div class="col-md-4">
				<label for="estadoCuota" class="form-label">Filtrar por
					estado</label> <select class="form-select" name="estadoCuota"
					id="estadoCuota">
					<option value="todas"
						<%="todas".equals(request.getParameter("estadoCuota")) ? "selected" : ""%>>Todas</option>
					<option value="pagas"
						<%="pagas".equals(request.getParameter("estadoCuota")) ? "selected" : ""%>>Pagas</option>
					<option value="impagas"
						<%="impagas".equals(request.getParameter("estadoCuota")) || request.getParameter("estadoCuota") == null
		? "selected"
		: ""%>>Impagas</option>
				</select>
			</div>
			<div class="col-md-2 d-flex align-items-end">
				<button type="submit" name="btnFiltrar" class="btn btn-primary">Filtrar</button>
			</div>
		</form>


		<form method="post" action="PagoCuotasServlet">
		<input type="hidden" name="estadoCuota" value="<%= request.getAttribute("estadoFiltro") != null ? request.getAttribute("estadoFiltro") : "impagas" %>">
			<!-- Tabla de cuotas -->
			<div class="mb-4">
				<h6><%=request.getAttribute("tituloCuotas") != null ? request.getAttribute("tituloCuotas") : "Todas las Cuotas"%></h6>
				<div class="table-responsive">
					<table
						class="table table-bordered table-hover align-middle text-center">
						<thead class="table-dark">
							<tr>
								<th>Seleccionar</th>
								<th>Nro de Cuota</th>
								<th>Monto</th>
								<th>Vencimiento</th>
								<th>Estado</th>
								<th>Fecha de Pago</th>
							</tr>
						</thead>
						<tbody>
							<tr>

								<%
								List<Cuota> cuotas = (List<Cuota>) request.getAttribute("cuotasFiltradas");
								List<Cuenta> cuentas = (List<Cuenta>) request.getAttribute("cuentasCliente");
								SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
								%>
							
						<tbody>
							<%
							if (cuotas != null) {
								for (Cuota c : cuotas) {
							%>
							<tr>
								<td>
								<input class="form-check-input" type="radio" name="cuotaId"
									value="<%=c.getId()%>" <%=c.getEstado() ? "disabled" : ""%>>
								</td>
								<td><%=c.getNumeroCuota()%></td>
								<td>$<%=c.getImporte()%></td>
								<td><%=sdf.format(c.getFechaVencimiento())%></td>
								<td><span
									class="badge <%=c.getEstado() ? "bg-success" : "bg-warning text-dark"%>">
										<%=c.getEstado() ? "Pago" : "Pendiente"%>
								</span></td>
								<td><%=c.getEstado() ? c.getFechaPago() : "-"%></td>
							</tr>
							<%
							}
							} else {
							%>
							<tr>
								<td colspan="100%">No hay cuotas para mostrar.</td>
							</tr>
							<%
							}
							%>
						</tbody>
						</tr>
						</tbody>
					</table>
				</div>
			</div>

			<!-- Selección de cuenta -->
			<div class="mb-4">
				<h6>Cuenta de origen</h6>
				<select class="form-select" name="cuentaId" required>
					<option value="">Seleccione una cuenta...</option>
					<%
					if (cuentas != null) {
						for (Cuenta cuenta : cuentas) {
					%>
					<option value="<%=cuenta.getId()%>">
						<%=cuenta.getTipoCuenta().getDescripcion()%> - $<%=cuenta.getSaldo()%>
					</option>
					<%
					}
					}
					%>
				</select>
			</div>

			<!-- Botón de pago -->
			<div class="text-end">
				<button type="submit" name="btnPagar" value="pagar"
					class="btn btn-success">
					<i class="bi bi-cash-coin me-1"></i> Pagar cuota seleccionada
				</button>
			</div>
		</form>

		<%
		Boolean mostrarModal = (Boolean) request.getAttribute("mostrarModalMsj");
		String titulo = (String) request.getAttribute("titulo");
		String mensaje = (String) request.getAttribute("mensaje");
		if (mensaje != null && mostrarModal) {
		%>
		<script>
			window.onload = function() {
				var modal = new bootstrap.Modal(document
						.getElementById('modalRespuesta'));
				modal.show();
			};
		</script>
		<div class="modal fade" id="modalRespuesta" tabindex="-1"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header bg-primary text-white">
						<h5 class="modal-title"><%=titulo%></h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Cerrar"></button>
					</div>
					<div class="modal-body">
						<p><%=mensaje%></p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal">Cerrar</button>
					</div>
				</div>
			</div>
		</div>
		<%
		}
		%>
	</main>

	<jsp:include page="/vistas/Footer.jsp" />
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
