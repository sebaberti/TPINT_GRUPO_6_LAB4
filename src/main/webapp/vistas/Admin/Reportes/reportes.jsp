<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="negocioImplementacion.Seguridad"%>
<%@ page import="entidades.Usuario"%>
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
<title>Reportes Generales - Banco Six</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/estiloInicio.css">
</head>
<body>
	<jsp:include page="/vistas/Header.jsp" />
 <form class="container bg-light p-4 rounded shadow-sm" action=${pageContext.request.contextPath}/DashboardServlet method="GET">
    <h4 class="fw-bold text-center mb-4">Filtrar Reportes</h4>

    <div class="row g-3 justify-content-center">
      <div class="col-md-4">
        <label for="fechaDesde" class="form-label">Desde</label>
        <input type="date" class="form-control" id="fechaDesde"
               name="fechaDesde" required 
               value="${param.fechaDesde}" />
      </div>

      <div class="col-md-4">
        <label for="fechaHasta" class="form-label">Hasta</label>
        <input type="date" class="form-control" id="fechaHasta"
               name="fechaHasta" required 
               value="${param.fechaHasta}" />
      </div>
    </div>
    	<%
  			String error = (String) request.getAttribute("error");
		%>

		<% if (error != null) { %>
  	<div class="alert alert-danger mt-3" role="alert">
    		<%= error %>
  	</div>
		<% } %>

    <div class="text-center mt-4">
  <input type="submit" class="btn btn-primary" value="Buscar Reporte" />
	</div>
  </form>
	<main class="container py-4">
    <h2 class="fw-bold text-center mb-4">Panel de Reportes General</h2>

    <div class="row row-cols-1 row-cols-md-3 g-4">

        <!-- Total de cuentas -->
        <div class="col">
            <div class="card text-center shadow-sm h-100">
                <div class="card-body d-flex flex-column justify-content-center">
                    <i class="bi bi-people-fill display-4 text-primary mb-2"></i>
                    <h5 class="card-title">Total de cuentas creadas en el periodo</h5>
                    <p class="fs-4 fw-bold mb-0">${totalCuentas}</p>
                </div>
            </div>
        </div>

        <!-- Total de clientes únicos -->
        <div class="col">
            <div class="card text-center shadow-sm h-100">
                <div class="card-body d-flex flex-column justify-content-center">
                    <i class="bi bi-people-fill display-4 text-primary mb-2"></i>
                    <h5 class="card-title">Total de clientes del periodo</h5>
                    <p class="fs-4 fw-bold mb-0">${totalClientes}</p>
                </div>
            </div>
        </div>

        <!-- Promedio de cuentas por cliente -->
        <div class="col">
            <div class="card text-center shadow-sm h-100">
                <div class="card-body d-flex flex-column justify-content-center">
                    <i class="bi bi-people-fill display-4 text-primary mb-2"></i>
                    <h5 class="card-title">Promedio de cuentas por cliente</h5>
                    <p class="fs-4 fw-bold mb-0">${promedioCuentasXcliente}</p>
                </div>
            </div>
        </div>
          </div>

   		  <h2 class="fw-bold text-center mb-4 mt-5">Ingresos y Egresos</h2>
<div class="row justify-content-center row-cols-1 row-cols-md-3 g-4">
  <!-- Ingresos -->
  <div class="col-md-4 d-flex justify-content-center">
    <div class="card text-center shadow-sm h-100">
      <div class="card-body d-flex flex-column justify-content-center">
        <i class="bi bi-cash-stack display-4 text-success mb-2"></i>
        <h5 class="card-title">Total ingresos del periodo</h5>
        <p class="p-3 border rounded bg-light text-success fw-bold">${totalIngresos}</p>
      </div>
    </div>
  </div>

  <!-- Egresos -->
  <div class="col-md-4 d-flex justify-content-center">
    <div class="card text-center shadow-sm h-100">
      <div class="card-body d-flex flex-column justify-content-center">
        <i class="bi bi-cash display-4 text-danger mb-2"></i>
        <h5 class="card-title">Total de egresos del periodo</h5>
        <p class="p-3 border rounded bg-light text-danger fw-bold">${totalEgresos}</p>
      </div>
    </div>
  </div>
</div>

<h2 class="fw-bold text-center mb-4 mt-5">Caja y Rentabilidad</h2>
<div class="row justify-content-center row-cols-1 row-cols-md-3 g-4">
  <!-- Rentabilidad -->
  <div class="col-md-4 d-flex justify-content-center">
    <div class="card text-center shadow-sm h-100">
      <div class="card-body d-flex flex-column justify-content-center">
        <i class="bi bi-bar-chart-line display-4 text-primary mb-2"></i>
        <h5 class="card-title">Rentabilidad</h5>
        <p class="fs-4 fw-bold mb-0">${rentabilidad}</p>
      </div>
    </div>
  </div>

  <!-- Caja Banco -->
  <div class="col-md-4 d-flex justify-content-center">
    <div class="card text-center shadow-sm h-100">
      <div class="card-body d-flex flex-column justify-content-center">
        <i class="bi bi-bank2 display-4 text-primary mb-2"></i>
        <h5 class="card-title">Total Caja</h5>
        <p class="fs-4 fw-bold mb-0">${caja}</p>
      </div>
    </div>
  </div>
</div>
      
</main>

	<jsp:include page="/vistas/Footer.jsp" />
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
		
</body>
</html>
