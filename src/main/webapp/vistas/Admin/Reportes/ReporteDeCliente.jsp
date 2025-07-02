<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="entidades.ReporteDeCliente" %>
<%@ page import="java.time.Year" %>
<%@ page import="negocioImplementacion.Seguridad"%>
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
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Reporte de Cliente</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloInicio.css">
</head>
<body>

<jsp:include page="/vistas/Header.jsp" />

<form action="${pageContext.request.contextPath}/ReporteDeClienteServlet" method="get"
      class="container mt-5 p-4 border rounded shadow-sm bg-light" style="max-width: 500px;">
    <h4 class="mb-4 text-center">Consultar Reporte de Cliente</h4>
    <div class="mb-3">
        <label for="dniIngresado" class="form-label">DNI del cliente</label>
        <input type="text" name="dniIngresado" value="<%= request.getAttribute("dniIngresado") != null ? request.getAttribute("dniIngresado").toString() : "" %>" 
        id="dni" required class="form-control <%= request.getAttribute("verificacionDni") != null ? "is-invalid" : "" %>" placeholder="Ingrese el DNI del cliente">
        <% if (request.getAttribute("verificacionDni") != null) { %>
            <div class="invalid-feedback">
                <%= request.getAttribute("verificacionDni") %>
            </div>
        <% } %>
    </div>
    <div class="text-center">
        <input type="submit" name="btnReporte" class="btn btn-primary" value="Ver reporte" />        
    </div>
</form>

<%
    ReporteDeCliente reporte = (ReporteDeCliente) request.getAttribute("reporte");
    ReporteDeCliente reporteMovimientos = (ReporteDeCliente) request.getAttribute("reporteMovimientos");
    int anioActual = Year.now().getValue();
    int anioSeleccionado = anioActual;
    if (request.getAttribute("anioSeleccionado") != null) {
        anioSeleccionado = (Integer) request.getAttribute("anioSeleccionado");
    }
%>

<% if (reporte != null) { %>
<!-- Tabla de resumen del cliente -->
<div class="container mt-5">
    <h4 class="mb-4 text-center">Resumen del Reporte del Cliente</h4>
    <table class="table table-bordered table-hover table-striped">
        <tbody>
            <tr><th scope="row">Total en saldos</th><td>$<%= reporte.getTotalSaldos() %></td></tr>
            <tr><th scope="row">Préstamos completos</th><td><%= reporte.getPrestamosCompletos() %></td></tr>
            <tr><th scope="row">Préstamos incompletos</th><td><%= reporte.getPrestamosIncompletos() %></td></tr>
            <tr><th scope="row">Cuotas pagadas</th><td>$<%= reporte.getCuotasPagadas() %></td></tr>
            <tr><th scope="row">Cuotas impagas vencidas</th><td>$<%= reporte.getCuotasVencidas() %></td></tr>
            <tr><th scope="row">Cuotas impagas por vencer</th><td>$<%= reporte.getCuotasPorVencer() %></td></tr>
        </tbody>
    </table>
</div>

<!-- Gráfico + Selector año + resumen financiero -->
<div class="container mt-5">
    <div class="row g-4">
        <!-- Gráfico de barras -->
        <div class="col-md-6">
            <div class="p-4 border rounded shadow-sm bg-white">
                <h5 class="text-center mb-3">Estado de las Cuotas</h5>
                <canvas id="cuotasChart"></canvas>
            </div>
        </div>

        <div class="col-md-6">
            <form method="get" action="ReporteDeClienteServlet" class="mb-4">
                <div class="row g-2 align-items-end">
                    <div class="col-6">
                        <label for="anio" class="form-label">Año</label>
                        <select class="form-select" id="anio" name="anio" required>
                            <option disabled value="">-- Seleccionar año --</option>
                            <% for (int i = anioActual; i >= anioActual - 10; i--) { %>
                                <option value="<%= i %>" <%= (i == anioSeleccionado) ? "selected" : "" %>><%= i %></option>
                            <% } %>
                        </select>
                    </div>
                    <div class="col-6">
                        <input type="hidden" name="dniIngresado" value="<%= request.getAttribute("dniIngresado") != null ? request.getAttribute("dniIngresado").toString() : "" %>"/>
                        <input type="submit" class="btn btn-primary w-100" name="btnMovimientos" value="Ver Movimientos" />
                    </div>
                </div>
            </form>

            <h5 class="text-center mt-4">Resumen Financiero</h5>
            <% if (reporteMovimientos != null) { %>
                <div class="row text-center">
                    <div class="col-6">
                        <div class="p-3 border rounded bg-light text-success fw-bold">
                            Ingresos<br>$<%= reporteMovimientos.getIngresos() %>
                        </div>
                    </div>
                    <div class="col-6">
                        <div class="p-3 border rounded bg-light text-danger fw-bold">
                            Egresos<br>$<%= reporteMovimientos.getEgresos() %>
                        </div>
                    </div>
                </div>
            <% } else { %>
                <p class="text-center text-muted mt-3">No se pudo cargar el resumen financiero.</p>
            <% } %>
        </div>
    </div>
</div>
<% } %>

<jsp:include page="/vistas/Footer.jsp" />

<!-- Scripts -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<% if (reporte != null) { %>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    const ctx = document.getElementById('cuotasChart').getContext('2d');
    const cuotasChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ['Pagadas', 'Impagas Vencidas', 'Impagas por Vencer'],
            datasets: [{
                label: 'Monto en $',
                data: [
                    <%= reporte.getCuotasPagadas() %>,
                    <%= reporte.getCuotasVencidas() %>,
                    <%= reporte.getCuotasPorVencer() %>
                ],
                backgroundColor: ['#198754', '#dc3545', '#ffc107'],
                borderColor: ['#145c39', '#a71d2a', '#cc9a06'],
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Monto ($)'
                    }
                }
            },
            plugins: {
                legend: { display: false }
            }
        }
    });
</script>
<% } %>

</body>
</html>
