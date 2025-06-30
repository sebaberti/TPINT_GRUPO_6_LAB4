<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="entidades.Cliente"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/estiloInicio.css">
<title>Eliminar cliente</title>

</head>
<body>
	<jsp:include page="../../Header.jsp" />

	<%! 
			public String manejarNull(String valor) {
				if(valor == null || valor.isEmpty()) 
					return "";
				
				return valor;
			}

			public Boolean manejarNull(Boolean valor) {
				if (valor == null)
					return false;

			return true;
		}%>

		<%
			Cliente cliente = new Cliente();
		%>
		
		<%
			if (session.getAttribute("objCliente") != null) {
				cliente = (Cliente) session.getAttribute("objCliente");
			}
		%>
		

	<main
		class="container py-4 mb-4">
		<div class="row text-center mb-4">
			<h2 class="fw-semibold">Eliminar Cliente</h2>
		</div>
	
		<div class="border rounded p-4 bg-white shadow-sm">
			<div class="d-flex flex-row-reverse w-100">
				<form method="GET"
				action="${pageContext.request.contextPath}/BajaClienteServlet">
					<button type="submit" name="btnVolverAListar" class="btn btn-secondary btn-abml mb-3">
					<i class="bi bi-arrow-return-right me-2"></i>Volver al listado
						</button>
				</form>
			</div>
			<form method="POST"
				action="${pageContext.request.contextPath}/BajaClienteServlet">
				<div class="row row-cols-sm-1 row-cols-md-2 row-cols-lg-3">
				<div class="col mb-3">
						<label for="DNICliente" class="form-label">Número de
							documento</label> <input type="text" class="form-control" id="DNICliente"
							name="DNICliente" placeholder="Ingrese el número de DNI" required disabled
							pattern="^\d{8}$" title="Solo se permiten hasta 8 digitos"
							value="<%=manejarNull(cliente.getDNI())%>">
					</div>
					<div class="col mb-3">
						<label for="CUILCliente" class="form-label">Número de CUIL</label>
						<input type="text" class="form-control" id="CUILCliente"
							name="CUILCliente" placeholder="20-43158994-6" required disabled
							pattern="\d{2}-\d{8}-\d" title="Formato esperado XX-XXXXXXXX-XX"
							value="<%=manejarNull(cliente.getCUIL())%>">
					</div>
					<div class="col mb-3">
						<label for="lblNombre" class="form-label">Nombre/s</label> <input
							type="text" class="form-control" id="lblNombre"
							value="<%= manejarNull(cliente.getNombre()) %>" disabled>
					</div>
					<div class="col mb-3">
						<label for="lblApellido" class="form-label">Apellido/s</label> <input
							type="text" class="form-control" id="lblApellido"
							value="<%=manejarNull(cliente.getApellido()) %>" disabled>
					</div>

					<div class="col mb-3">
						<label for="lblSelectSexo" class="form-label">Sexo</label> <input
							type="text" class="form-control" id="lblSelectSexo"
							value="<%=manejarNull(cliente.getSexo()).equals("F") ? "Femenino" : "Masculo"%>"
							disabled>
					</div>
					<div class="col mb-3">
						<label for="lblNacionalidad" class="form-label">Nacionalidad</label>
						<input type="text" class="form-control" id="lblNacionalidad"
							value="<%=cliente.getNacionalidad() != null ? cliente.getNacionalidad().getNombre() : ""%>"
							disabled>
					</div>
					<div class="col mb-3">
						<label for="lblFechaNacimiento" class="form-label">Fecha
							Nacimiento</label> <input type="date" class="form-control"
							id="lblFechaNacimiento"
							value="<%=cliente.getFecha_nacimiento() != null ? cliente.getFecha_nacimiento().toString() : ""%>" disabled>
					</div>

					<div class="col mb-3">
						<label for="lblDireccion" class="form-label">Dirección</label> <input
							type="text" class="form-control" id="lblDireccion"
							value="<%=cliente.getDomicilio() != null ? cliente.getDomicilio().getDireccion() : ""%>"
							disabled>
					</div>
					<div class="col mb-3">
						<label for="lblProvincia" class="form-label">Provincia</label> <input
							type="text" class="form-control" id="lblProvincia"
							value="<%=cliente.getDomicilio() != null
		? cliente.getDomicilio().getProvincia() != null ? cliente.getDomicilio().getProvincia().getNombre() : ""
		: ""%>"
							disabled>
					</div>
					<div class="col mb-3">
						<label for="lblLocalidad" class="form-label">Localidad</label> <input
							type="text" class="form-control" id="lblLocalidad"
							value="<%=cliente.getDomicilio() != null
		? cliente.getDomicilio().getLocalidad() != null ? cliente.getDomicilio().getLocalidad().getNombre() : ""
		: ""%>"
							disabled>
					</div>

					<div class="col mb-3">
						<label for="lblEmail" class="form-label">Correo
							electronico</label> <input type="email" class="form-control"
							id="lblEmail" value="<%=manejarNull(cliente.getEmail())%>"
							disabled>
					</div>
					<div class="col mb-3">
						<label for="lblTelefono" class="form-label">Teléfono</label> <input
							type="text" class="form-control" id="lblTelefono"
							value="<%=manejarNull(cliente.getTelefono())%>" disabled>
					</div>
					<div class="col mb-3">
						<label for="lblEstado" class="form-label">Estado</label> <input
							type="text" class="form-control" id="lblEstado"
							value="<%=manejarNull(cliente.getEstado()) ? cliente.getEstado() ? "Activo" : "Inactivo" : ""%>"
							disabled>
					</div>
				</div>
				<div class="row">
					<div class="col mb-3">
						<button type="submit" name="btnEliminar"
							class="btn btn-danger btn-md w-100 .btn-abml">Eliminar
							Cliente</button>
					</div>
				</div>
			</form>
			</div>
	</main>
	
	<!-- Mostrar modal modificacion éxitosa -->
	<%
	Boolean mostrarModal = (Boolean) request.getAttribute("mostrarModalClienteEliminado");
	if (mostrarModal != null && mostrarModal) {
	%>
	<script>
		window.onload = function() {
			var modal = new bootstrap.Modal(document
					.getElementById('mostrarModalClienteEliminado'));
			modal.show();
		};
	</script>
	<%
	}
	%>

	<!-- Modal modificacion éxitosa -->
	<div class="modal fade" id="mostrarModalClienteEliminado" tabindex="-1"
		aria-labelledby="mostrarModalClienteEliminado" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">

				<div class="modal-header">
					<h5 class="modal-title" id="mostrarModalClienteEliminado">Eliminación éxitosa!</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Cerrar"></button>
				</div>

				<div class="modal-body">
					<%
					if (cliente != null) {
					%>
					<p> El cliente <%=cliente.getNombre()%>, <%=cliente.getApellido()%> con DNI <%=cliente.getDNI()%> y CUIL <%=cliente.getCUIL()%> fue eliminado correctamente.</p>
					<%
					}
					%>
				</div>
				<form method="GET"
				action="${pageContext.request.contextPath}/ListarClientesServlet">
					<div class="modal-footer">
						<button type="submit" class="btn btn-secondary"
						data-bs-dismiss="modal">Cerrar</button>
					</div>
				</form>

			</div>
		</div>
	</div>
	
	<!-- Mostrar modal error -->
	<%
	Boolean mostrarModalError = (Boolean) request.getAttribute("modalError");
	if (mostrarModalError != null && mostrarModalError) {
	%>
	<script>
		window.onload = function() {
			var modal = new bootstrap.Modal(document
					.getElementById('modalError'));
			modal.show();
		};
	</script>
	<%
	}
	%>

	<!-- Modal error -->
	<div class="modal fade" id="modalError" tabindex="-1"
		aria-labelledby="modalError" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">

				<div class="modal-header">
					<h5 class="modal-title" id="modalClienteModificado">Ocurrio un error!</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Cerrar"></button>
				</div>

				<div class="modal-body">
				<% String mensajeError = (String) request.getAttribute("error"); %>
				<p><%= mensajeError != null ?  mensajeError :"Ocurrio un error" %></p>
				</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Cerrar</button>
					</div>
			</div>
		</div>
	</div>
	
	<!-- Mostrar modal confirmar eliminar -->
	<%
	Boolean mostrarModalEliminar = (Boolean) request.getAttribute("ModalConfirmarEliminacion");
	if (mostrarModalEliminar != null && mostrarModalEliminar) {
	%>
	<script>
		window.onload = function() {
			var modal = new bootstrap.Modal(document
					.getElementById('ModalConfirmarEliminacion'));
			modal.show();
		};
	</script>
	<%
	}
	%>

	<!-- Modal eliminar -->
	<div class="modal fade" id="ModalConfirmarEliminacion" tabindex="-1"
		aria-labelledby="ModalConfirmarEliminacion" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">

				<div class="modal-header bg-danger">
					<h5 class="modal-title" id="ModalConfirmarEliminacion">Confirmar eliminación</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Cerrar"></button>
				</div>

				<div class="modal-body">
					<%
					if (cliente != null) {
					%>
					<p> Esta seguro que desea eliminar el cliente? </p>
					<%
					}
					%>
					<div class="d-flex modal-footer justify-content-end">
						<div class="">
							<button type="button" name="btnConfirmarEliminacion" class="btn btn-danger" onclick="location.href='${pageContext.request.contextPath}/BajaClienteServlet?id=<%=cliente.getId()%>&ConfirmarEliminacion=<%=true%>'"
							data-bs-dismiss="modal">Confirmar</button>
						</div>
						<div class="">
							<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal">Cancelar</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/Validaciones/validaciones.js"></script>
	<script>
		document.addEventListener("DOMContentLoaded", function () {
			formatearDNI("DNICliente");
			formatearCUIL("CUILCliente");
		})
	</script>
<jsp:include page="../../Footer.jsp" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>