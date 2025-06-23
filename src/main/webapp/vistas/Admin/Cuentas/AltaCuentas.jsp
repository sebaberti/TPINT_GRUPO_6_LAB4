<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="negocioImplementacion.CuentaTipoNegocioImplementacion" %>
<%@ page import="entidades.Cliente"%>
<%@ page import="entidades.CuentaTipo"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Alta de Cuenta</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/estiloInicio.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/Cuentas/estiloAltaCuentas.css">
</head>
<body>

	<jsp:include page="/vistas/Header.jsp" />

	<%
		Cliente cliente = (Cliente) request.getAttribute("cliente");
		%>


	<!-- Formulario -->
	<div class="container my-5">
		<form action="${pageContext.request.contextPath}/AltaCuentaServlet"
		id="formAltaCuenta"
			method="post" class="form-section">
			<h5 class="form-title">Buscar Cliente</h5>
			<div class="input-group mb-4">
				<input type="text" class="form-control"
					placeholder="Ingrese DNI del cliente"
					value="<%= (cliente != null) ? cliente.getDNI() : "" %>"
					name="DniCliente" required>
				<button class="btn btn-success" name="btnBuscar" type="submit">&#128269;</button>
			</div>
			 <% String error = (String) request.getAttribute("error");
			 String  exito = (String) request.getAttribute("exito");

   if (error != null) {
%>
<div class="mb-3">
   <p style="color:red;"><%= error %></p>
   </div>
<% } else if(exito!=null){%>
<div class="mb-3">
   <p style="color:green;"><%= exito %></p>
   </div>
   <% }%>
			<div class="mb-3">
				<label for="usuario" class="form-label">Usuario:</label> <input
					type="text" class="form-control bg-light" id="usuario"
					name="usuario"
					value="<%= (cliente != null && cliente.getUsuario() != null) ? cliente.getUsuario().getNombreUsuario() : "" %>"
					readonly>
			</div>

			<div class="row g-3">
			<!-- Campo oculto con ID cliente -->
    		<input type="hidden" name="idCliente" value="<%=(cliente != null) ? cliente.getId() :""%>">
				<div class="col-md-6">
					<label for="nombre" class="form-label">Nombre:</label> <input
						type="text" class="form-control bg-light" id="nombre"
						name="nombre"
						value="<%= (cliente != null) ? cliente.getNombre() : "" %>"
						readonly>
				</div>
				<div class="col-md-6">
					<label for="apellido" class="form-label">Apellido:</label> <input
						type="text" class="form-control bg-light" id="apellido"
						name="apellido"
						value="<%= (cliente != null) ? cliente.getApellido() : "" %>"
						readonly>
				</div>
			</div>

			<div class="mt-4 mb-3">
				<label for="tipoCuenta" class="form-label">Tipo de cuenta:</label> 
				<select class="form-select" id="tipoCuenta" name="tipoCuenta">
					<option selected disabled>Seleccione una opción</option>
					<% 
					 CuentaTipoNegocioImplementacion tsi= new CuentaTipoNegocioImplementacion();
					  ArrayList<CuentaTipo> tiposCuenta= tsi.listarTiposCuenta();
					if(tiposCuenta!=null){
					for (CuentaTipo t : tiposCuenta) {%>
					<option value="<%=t.getId()%>"><%=t.getDescripcion()%></option>
					<%}}%>
				</select>
			<%	String errorTipoCuenta = (String) request.getAttribute("errorTipoCuenta");
			if (errorTipoCuenta != null) {%>
<div class="mb-3">
   <p style="color:red;"><%= errorTipoCuenta %></p>
   </div>
<% } %>

			<%	String mensajeError = (String) request.getAttribute("mensajeError");
			if (mensajeError != null) {%>
<div class="mb-3">
   <p style="color:red;"><%= mensajeError %></p>
   </div>
<% } %>

			</div>

			<div class="d-flex justify-content-end gap-2">
				<!-- <button type="button" class="btn btn-primary" onclick="validarYCrearCuenta()">Crear Cuenta</button> -->
				<input type="submit" class="btn-crear" name="btnCrear" value="Crear Cuenta">
			</div>
		</form>

	</div>
	
	<!-- Modal si el usuario está vacío -->
<div class="modal fade" id="usuarioModal" tabindex="-1" aria-labelledby="usuarioModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="usuarioModalLabel">Atención</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
      </div>
      <div class="modal-body">
        El campo "Usuario" está vacío. Por favor, busque un cliente antes de crear la cuenta.
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
      </div>
    </div>
  </div>
</div>
	
	<script>
  function validarYCrearCuenta() {
    var usuario = document.getElementById("usuario").value.trim();

    if (usuario === "") {
      // Abrir modal si el campo está vacío
      var modal = new bootstrap.Modal(document.getElementById('usuarioModal'));
      modal.show();
    } else {
      // Enviar formulario si está todo bien
      document.getElementById("formAltaCuenta").submit();
    }
  }
</script>
	

	<jsp:include page="/vistas/Footer.jsp" />
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
