<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, entidades.Movimiento, entidades.Cuenta" %>
<%@ page import="utilidades.FormatterUtil" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Movimientos - Banco SIX</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloInicio.css">
</head>
<body>
<jsp:include page="/vistas/Header.jsp" />

<main class="container my-5">
    <h1 class="text-center mb-4">Historial de Movimientos</h1>
	<form class="d-flex flex-row-reverse" method="post" action="vistas/Inicio.jsp">
			<button type="submit" name="btnVolverALInicio" class="btn btn-secondary btn-abml mb-3">
				<i class="bi bi-arrow-return-right me-2"></i>Volver menú
			</button>
	</form>
    <!-- Formulario de filtros -->
    <form method="get" action="MovimientosServlet">
        <div class="row mb-3">
            <div class="col-md-6">
                <label for="cuentaId" class="form-label">Cuenta:</label>
                <%
    List<Cuenta> cuentasCliente = (List<Cuenta>) request.getAttribute("cuentasCliente");
    Integer cuentaSeleccionada = (Integer) request.getAttribute("cuentaSeleccionada");
    if (cuentaSeleccionada == null) {
        cuentaSeleccionada = -1; // o null si querés manejar diferente
    }
	%>
	<select name="cuentaId" id="cuentaId" class="form-select" required>
    <option value="">Seleccione una cuenta...</option>
    <% for (Cuenta c : cuentasCliente) {
        boolean seleccionada = c.getId() == cuentaSeleccionada;
    %>
        <option value="<%= c.getId() %>" <%= seleccionada ? "selected" : "" %>>
            <%= c.getTipoCuenta().getDescripcion() %> - CBU: <%= c.getCBU() %> - Saldo: $<%= FormatterUtil.formatearMiles(c.getSaldo()) %>
        </option>
    <% } %>
	</select>
            </div>

            <div class="col-md-3">
                <label for="fechaInicio" class="form-label">Desde:</label>
                <input type="date" class="form-control" name="fechaInicio" value="">
                
            </div>

            <div class="col-md-3">
                <label for="fechaFin" class="form-label">Hasta:</label>
               <input type="date" class="form-control" name="fechaFin" value="">
            </div>
        </div>

        <div class="mb-4 text-end">
            <button type="submit" class="btn btn-primary">
                <i class="bi bi-search"></i> Ver movimientos
            </button>
        </div>
    </form>

    
    <div class="table-responsive">
        <table id="tabla_movimientos" class="table table-bordered text-center align-middle">
            <thead class="table-dark">
                <tr>
                    <th>Fecha</th>
                    <th>Tipo</th>
                    <th>Detalle</th>
                    <th>Monto</th>
                </tr>
            </thead>
            <tbody>
    <%
    	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        List<Movimiento> movimientos = (List<Movimiento>) request.getAttribute("movimientos");
        if (movimientos != null && !movimientos.isEmpty()) {
            for (Movimiento m : movimientos) {
    %>
    <tr>
        <td><%= sdf.format(java.sql.Date.valueOf(m.getFecha().toLocalDate())) %></td>
        <td><%= m.getTipoMovimiento().getDescripcion() %></td>
        <td><%= m.getDetalle() %></td>
       
		<td class="<%= m.getImporte() >= 0 ? "text-success" : "text-danger" %>">
   		 $<%= FormatterUtil.formatearMiles(m.getImporte()) %>
		</td>
    </tr>
    <%  }
        }
    %>
		</tbody>
	</table>
<%
    if (movimientos == null || movimientos.isEmpty()) {
%>
    <div class="alert alert-info text-center mt-3">No hay movimientos para mostrar.</div>
<%
    }
%>
            </tbody>
        </table>
    </div>
</main>

<jsp:include page="/vistas/Footer.jsp" />

<!-- Scripts Bootstrap y DataTables -->
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>


<script>
	$(document).ready(function() {$('#tabla_movimientos').DataTable({
		order: [],
	searching : false,
	language : { url : "https://cdn.datatables.net/plug-ins/1.13.6/i18n/es-ES.json"}
		});
	});
	
	  document.getElementById('cuentaId').addEventListener('change', function() {
	        this.form.submit();
	    });
</script>

</body>
</html>
