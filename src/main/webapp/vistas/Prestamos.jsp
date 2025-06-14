<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.time.*, java.time.format.*, java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/estiloPrestamos.css">
<title>Préstamos</title>
</head>
<body>
<jsp:include page="Header.jsp" />

 <div class="container mt-5 mb-5">
 <h2 class="mb-4">Simulá tu Prestamo Personal</h2> 
 

<div class="card">
  <div class="card-body">
    <div class="row justify-content-center">
      <div class="col-4 text-center">
        <label class="textoGris">Podes pedir hasta</label>
      </div>
      <div class="col-4 text-center">
        <label class="textoGris">Plazo máximo</label>
      </div>
    </div>
    <div class="row justify-content-center">
      <div class="col-4 text-center">
        <% String montomax = "$70.000.000"; %>	
        <p><strong><%= montomax %></strong></p>
      </div>
      <div class="col-4 text-center">
        <% String plazomax = "72 cuotas"; %>	
        <p><strong><%= plazomax %></strong></p>
      </div>
    </div>
  </div>
</div>
<br>

<div class="container">
  <div class="row">
    <div class="col-6">
		<div class="bordes">
		<form method="post" action="${pageContext.request.contextPath}/servletPrestamos">
		
        <div class="mb-3">        
          <label for="Monto" class="form-label">Monto</label>
          <div class="input-group mb-3">
            <span class="input-group-text">$</span>           
            <input type="text" class="form-control" name="monto" aria-label="Monto en pesos"
       		value="<%= request.getAttribute("monto") != null ? request.getAttribute("monto") : "" %>">
            <span class="input-group-text">.00</span>
          </div>
          <div id="avisoMonto" class="form-text">Podés pedir hasta <%=montomax %></div>
        </div>

        <div class="mb-3">
        
          <label for="" class="form-label">Plazo</label>
          <% String cuotasSeleccionadas = request.getAttribute("cuotas") != null ? request.getAttribute("cuotas").toString() : ""; %>
		<select name="cuotas" class="form-select">
		<option value="" <%= (cuotasSeleccionadas == null || cuotasSeleccionadas.isEmpty()) ? "selected" : "" %>>Seleccione una opción...</option>
  		<% for (int i = 1; i <= 72; i++) { %>
    		<option value="<%= i %>" <%= String.valueOf(i).equals(cuotasSeleccionadas) ? "selected" : "" %>>
      	<%= i %> cuota<%= i > 1 ? "s" : "" %>
    		</option>
  		<% } %>
		</select>
		
        <div id="avisoPlazo" class="form-text">El monto determina las opciones</div>
        
        </div>
		
		<% if (request.getAttribute("mensajeError") != null) { %>
  		<div class="alert alert-danger" role="alert">
  		  <%= request.getAttribute("mensajeError") %>
 		</div>
		<% } %>
        <input type="submit" class="btn btn-primary" name="btnSimular" value="Simular" />
          
      </form>
       </div>
    </div>

<div class="col-6">
  <div class="bordes">
  <form method="post" action="${pageContext.request.contextPath}/servletPrestamos">
  	<h3>Detalle de cuotas</h3>
  	<%
    	ArrayList<String> fechas = (ArrayList<String>) request.getAttribute("fechasCuotas");
    	if (fechas != null) {
  	%>
  	<p>Valor de cuota: </p>
 	<p>Intereses: </p>
  	<p>Fecha de cuotas:</p>
    <ul>
      <% for (String f : fechas) { %>
        <li><%= f %></li>
      <% } %>
    </ul>
  <% } else { %>
    <p>Simulá tu préstamo para ver el detalle.</p>
  <% } %>

		<div>
      <input type="submit" class="btn btn-primary" name="btnConfirmar" value="Confirmar" />
      </div>
      <div>
      <a href="Inicio.jsp" class="btn btn-link">Cancelar</a>
  	</div>
  	
  	</form>
  </div>
</div>
 
 
  </div>
</div>

</div>

<jsp:include page="Footer.jsp" /> 

<script>

document.getElementById("Monto").addEventListener("input", function (e) {
  let valor = e.target.value;

  valor = valor.replace(/[^0-9,]/g, '');

  let partes = valor.split(',');

  let entero = partes[0].replace(/\B(?=(\d{3})+(?!\d))/g, '.');

  let decimal = partes[1] ? ',' + partes[1].substring(0, 2) : '';

  e.target.value = entero + decimal;
});
</script>
  


</body>
</html>