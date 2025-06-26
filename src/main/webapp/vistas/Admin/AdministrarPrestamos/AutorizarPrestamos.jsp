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
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">

<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" charset="utf8"
	src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$('#table_id').DataTable();
	});
</script>
</head>
<body>
	<jsp:include page="/vistas/Header.jsp" />
<% 
	ArrayList<Prestamo> listaPrestamos = null;
	if(request.getAttribute("listaPrestamos")!=null)
	{
		listaPrestamos = (ArrayList<Prestamo>) request.getAttribute("listaPrestamos");
	}

 %>
	<main class="container mt-5 mb-5">
		<h1 class="text-center mb-4">Listado de Préstamos Solicitados</h1>
			<div class="table-responsive mt-5">
			<%
			if (request.getAttribute("mensaje") != null) {
			%>
			<div class="alert alert-success" role="alert">
				<%=request.getAttribute("mensaje")%>
			</div>
			<%
			}
			%>
			<table id="table_id" class="display">
					<thead class="table-light">
						<tr>
							<th>IDPrestamo</th>
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
						PrestamoNegocioImplementacion pni= new PrestamoNegocioImplementacion();
					    
						if(listaPrestamos!=null)
						for(Prestamo p : listaPrestamos) 
						{
					%>					
					<tr>
						<form name="tablaPrestamo" method="post"	action="AutorizarPrestamosServlet">
						<td><%=p.getId() %> <input type="hidden" name="idPrestamo" value="<%=p.getId()%>"></td>						
						<td><%=p.getCliente().getApellido() + ", " + p.getCliente().getNombre()  %></td>
						<td><%=p.getCuenta().getNumeroCuenta() %></td>
						<td>$<%=formato.format(p.getImportePedido()) %></td>						
						<td><%=p.getPlazo().getCantidadCuotas() %></td>
						<% BigDecimal importeMensual= pni.calcularCuota(p.getImportePedido(), p.getPlazo().getId()); %>
						<td>$<%=formato.format(importeMensual)%></td>
						<td><%=p.getEstadoTexto() %></td>
						<td><input type="submit" name="btnReportes" value="Ver Reporte"></td>
						<%if(p.getEstado()==0) {%>
						<td><input type="submit" name="btnAprobar" value="Aprobar"></td>
						<td><input type="submit" name="btnRechazar" value="Rechazar"></td>
						<%} else {
							%>
							<td></td>
							<td></td>
						<%} %>
						</form>
					</tr>
					<%  } %>
				</tbody>
			</table>
			</div>
	</main>

	<jsp:include page="/vistas/Footer.jsp" />
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>