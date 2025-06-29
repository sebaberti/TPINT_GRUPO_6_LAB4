<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="negocioImplementacion.Seguridad"%>
<%@ page import="negocioImplementacion.PrestamoNegocioImplementacion"%>
<%@ page import="entidades.Prestamo"%>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.text.DecimalFormatSymbols" %>
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

DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
simbolos.setDecimalSeparator(',');
simbolos.setGroupingSeparator('.');
DecimalFormat formato = new DecimalFormat("#,##0.00", simbolos);

%>	
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Autorizar préstamos</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloInicio.css">

<style>
	.table-responsive {
		overflow-x: auto;
	}
	#tabla_prestamos {
		width: 100% !important;
		table-layout: fixed;
		word-wrap: break-word;
	}
</style>
</head>
<body>
<jsp:include page="/vistas/Header.jsp" />

<% 
	ArrayList<Prestamo> listaPrestamos = null;
	if (request.getAttribute("listaPrestamos") != null) {
		listaPrestamos = (ArrayList<Prestamo>) request.getAttribute("listaPrestamos");
	}
%>

<main class="container mt-5 mb-5">
	<h1 class="text-center mb-4">Listado de Préstamos Solicitados</h1>
	<div class="table-responsive mt-5">

	<% if (request.getAttribute("mensaje") != null) { %>
		<div class="alert alert-success" role="alert">
			<%=request.getAttribute("mensaje")%>
		</div>
	<% } %>

	<table id="tabla_prestamos" class="table table-bordered text-center align-middle">
		<thead class="table-dark">
			<tr>
				<th class="d-none">IDPrestamo</th>
				<th>Cliente</th>
				<th>Cuenta</th>
				<th>Importe pedido</th>
				<th>Plazo (en meses)</th>
				<th>Importe Mensual</th>
				<th>Estado</th>
				<th>Reportes</th>
				<th>Aprobar</th>
				<th>Rechazar</th>
			</tr>
		</thead>
		<tbody>
		<%
			PrestamoNegocioImplementacion pni = new PrestamoNegocioImplementacion();

			if (listaPrestamos != null)
			for (Prestamo p : listaPrestamos) {
				BigDecimal importeMensual = pni.calcularCuota(p.getImportePedido(), p.getPlazo().getId());
		%>
			<tr>
				<form method="post" action="AutorizarPrestamosServlet">
					<td class="d-none">
						<%= p.getId() %>
						<input type="hidden" name="idPrestamo" value="<%=p.getId()%>">
					</td>
					<td><%=p.getCliente().getApellido() + ", " + p.getCliente().getNombre()%></td>
					<td><%=p.getCuenta().getNumeroCuenta()%></td>
					<td>$<%=formato.format(p.getImportePedido())%></td>
					<td><%=p.getPlazo().getCantidadCuotas()%></td>
					<td>$<%=formato.format(importeMensual)%></td>
					<td><%=p.getEstadoTexto()%></td>
					<% if (p.getEstado() == 0) { %>
						<td><input type="submit" name="btnReportes" value="Ver Reporte" class="btn btn-sm btn-outline-primary"></td>
						<td><input type="submit" name="btnAprobar" value="Aprobar" class="btn btn-sm btn-success"></td>
						<td><input type="submit" name="btnRechazar" value="Rechazar" class="btn btn-sm btn-danger"></td>
					<% } else { %>
						<td></td>
						<td></td>
						<td></td>
					<% } %>
				</form>
			</tr>
		<% } %>
		</tbody>
	</table>
	</div>
</main>

<jsp:include page="/vistas/Footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>

<script>
	$(document).ready(function () {
		$('#tabla_prestamos').DataTable({
			order: [],
			language: {
				url: "https://cdn.datatables.net/plug-ins/1.13.6/i18n/es-ES.json"
			},
			autoWidth: false,
			responsive: true
		});
	});
</script>

</body>
</html>
