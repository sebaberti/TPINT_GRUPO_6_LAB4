<%@ page import="java.util.List" %>
<%@ page import="entidades.Prestamo" %>
<%@ page import="utilidades.FormatterUtil" %>
<%@ page import="negocioImplementacion.Seguridad" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Mis Préstamos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css" rel="stylesheet">
     <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/estiloInicio.css">
</head>
<body>

<jsp:include page="/vistas/Header.jsp" />

<main class="container mt-5 mb-5">
    <h1 class="text-center mb-4">Mis Préstamos</h1>

    <%
        Object user = session.getAttribute("usuario");
        if (!Seguridad.sesionActiva(user) || Seguridad.esAdministrador(user)) {
            response.sendRedirect(request.getContextPath() + "/vistas/Login.jsp");
            return;
        }
    %>

    	<form class="d-flex flex-row-reverse" method="post" action="vistas/Inicio.jsp">
			<button type="submit" name="btnVolverALInicio" class="btn btn-secondary btn-abml mb-3">
				<i class="bi bi-arrow-return-right me-2"></i>Volver menú
			</button>
		</form>

    <div class="table-responsive">
        <table id="tabla_prestamos" class="table table-bordered table-hover text-center">
            <thead class="table-light">
                <tr>
                    <th>Nº Prestamo</th>
                    <th>Cuenta</th>
                    <th>Fecha Alta</th>
                    <th>Importe</th>
                    <th>Cantidad de cuotas</th>
                    <th>Estado</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Prestamo> prestamos = (List<Prestamo>) request.getAttribute("prestamosCliente");
                    if (prestamos != null && !prestamos.isEmpty()) {
                        for (Prestamo p : prestamos) {
                %>
                <tr>
                    <td><%= p.getId() %></td>
                    <td><%= p.getCuenta().getNumeroCuenta() %></td>
                    <td>
					    <%
					        int estado = p.getEstado();
					        if (estado == 0 || estado == 2 || p.getFechaAlta() == null) {
					            out.print("-");
					        } else {
					            out.print(new java.text.SimpleDateFormat("dd/MM/yyyy").format(
					                java.sql.Date.valueOf(p.getFechaAlta())
					            ));
					        }
					    %>
					</td>
                    <td>$ <%= FormatterUtil.formatearMiles(p.getImportePedido().doubleValue()) %></td>
                    <td><%= p.getPlazo().getCantidadCuotas() %></td>
                    <td>
                        <span class="badge bg-<%= p.getEstado() == 1 ? "success" : (p.getEstado() == 2 ? "danger" : "warning") %>">
                            <%= p.getEstado() == 1 ? "Aprobado" : (p.getEstado() == 2 ? "Rechazado" : "Pendiente") %>
                        </span>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr><td colspan="6">No tenés préstamos registrados.</td></tr>
                <% } %>
            </tbody>
        </table>
    </div>
</main>

<jsp:include page="/vistas/Footer.jsp" />

<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>
  <script>
        $(document).ready(function () {
            $('#tabla_prestamos').DataTable({
                order: [],
                pageLength: 5,
                lengthMenu: [5, 10, 25, 50],
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