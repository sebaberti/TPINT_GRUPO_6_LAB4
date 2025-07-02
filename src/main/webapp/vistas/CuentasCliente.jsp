<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="entidades.Cuenta"%>
<%@ page import="entidades.CuentaTipo"%>
<%@ page import="java.math.BigInteger"%>
<%@ page import="utilidades.FormatterUtil" %>
<%@ page import="negocioImplementacion.Seguridad"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Mis Cuentas</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">

    <!-- DataTables CSS -->
    <link href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css" rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloInicio.css">
</head>
<body>

    <jsp:include page="/vistas/Header.jsp" />

    <main class="container mt-5 mb-5">
    
        <h1 class="text-center mb-4">Mis Cuentas</h1>
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
            <table id="tabla_cuentas" class="table table-bordered table-hover text-center">
                <thead class="table-light">
                    <tr>
                        <th>Nro Cuenta</th>
                        <th>CBU</th>
                        <th>Tipo de Cuenta</th>
                        <th>Saldo</th>
                        <th>Fecha de Creación</th>
                        <th>Estado</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Cuenta> cuentas = (List<Cuenta>) request.getAttribute("cuentasCliente");
                        if (cuentas != null && !cuentas.isEmpty()) {
                            for (Cuenta c : cuentas) {
                    %>
                    <tr>
                        <td><%= c.getNumeroCuenta() %></td>
                        <td><%= c.getCBU() %></td>
                        <td><%= c.getTipoCuenta().getDescripcion() %></td>
                        <td>$ <%= FormatterUtil.formatearMiles(c.getSaldo()) %></td>
                        <td><%= new java.text.SimpleDateFormat("dd/MM/yyyy").format(c.getFechaCreacion()) %></td>
                        <td>
                            <span class="badge bg-<%= c.isEstado() ? "success" : "danger" %>">
                                <%= c.isEstado() ? "Activa" : "Inactiva" %>
                            </span>
                        </td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="6">No tenés cuentas asociadas activas.</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
    </main>

    <jsp:include page="/vistas/Footer.jsp" />

    <!-- Scripts -->
    <script src="https://code.jquery.com/jquery-3.7.0.min.js"></script> <!-- jQuery primero -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <!-- DataTables JS -->
    <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>

    <script>
        $(document).ready(function () {
            $('#tabla_cuentas').DataTable({
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
