<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="entidades.Provincia"%>
<%@ page import="entidades.Pais"%>
<%@ page import="entidades.Cliente"%>
<%@ page import="entidades.Localidad"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%
	// Guardo en session el cliente que viene de ListarClientes -> Botón modificar -> CargarClienteServlet.
	Cliente cliente = null;
	if(request.getAttribute("cliente")  != null) {
		cliente = (Cliente) request.getAttribute("cliente");
	} 
	else
	{
		// No se encontro el cliente o se intenta compilar desde Modificar directamente.
	}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Modificar Cliente</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/estiloInicio.css">
</head>
<body>
	<jsp:include page="../../Header.jsp" />

	<main class="container py-4 mb-4">
		<div class="row text-center mb-4">
			<h2 class="fw-semibold">Modificar Cliente</h2>
		</div>

		<!-- Formulario de modificación -->
		<div class="border rounded p-4 bg-white shadow-sm">
			<div class="d-flex flex-row-reverse w-100">
				<form method="GET"
				action="${pageContext.request.contextPath}/ModificarClienteServlet">
					<button type="submit" name="btnVolverAListar" class="btn btn-secondary btn-abml mb-3">
					<i class="bi bi-arrow-return-right me-2"></i>Volver al listado
						</button>
				</form>
			</div>
			<form method="POST"
				action="${pageContext.request.contextPath}/ModificarClienteServlet"
				class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-3">

				<input type="hidden" name="idCliente" value="<%=cliente.getId()%>">

				<div class="col">
					<label class="form-label">DNI</label> <input type="text" id="DNICliente" name="DNICliente"
						class="form-control" value="<%=cliente != null ? cliente.getDNI() : ""%>" readonly>
				</div>

				<div class="col">
					<label class="form-label">CUIL</label> <input type="text" id="CUILCliente"
						name="CUILCliente" class="form-control" readonly
						value="<%=cliente != null ? cliente.getCUIL() : ""%>">
				</div>

				<div class="col">
					<label class="form-label">Nombre/s</label> <input type="text"
						name="nombreCliente" class="form-control" required
						pattern="[A-Za-zÁÉÍÓÚáéíóúÑñ\s]+" value="<%=cliente != null ? cliente.getNombre() : ""%>">
				</div>

				<div class="col">
					<label class="form-label">Apellido/s</label> <input type="text"
						name="apellidoCliente" class="form-control" required
						pattern="[A-Za-zÁÉÍÓÚáéíóúÑñ\s]+"
						value="<%=cliente != null ? cliente.getApellido() : ""%>">
				</div>

				<div class="col">
					<label class="form-label">Sexo</label> <select name="selectSexo"
						class="form-select" required>
						<option value="" disabled>Seleccione un sexo</option>
						<option value="M"
							<%=cliente != null ? "M".equals(cliente.getSexo()) ? "selected" : "" : ""%>>Masculino</option>
						<option value="F"
							<%=cliente != null ? "F".equals(cliente.getSexo()) ? "selected" : "" : ""%>>Femenino</option>
					</select>
				</div>

				<div class="col">
					<label class="form-label">Fecha de Nacimiento</label> 
					<input
						type="date" id="fechaNacimientoCliente" name="fechaNacimientoCliente" class="form-control"
						value="<%=cliente != null ? cliente.getFecha_nacimiento() != null ? cliente.getFecha_nacimiento().toString() : "" : ""%>" required>
				</div>
			
				<div class="col">
					<label class="form-label">Dirección</label> 
					<!-- Mantengo el id de direccion entre peticiones para hacer el Update -->
					<input type="hidden" name="idDireccion" value="<%=(cliente != null) ? cliente.getDomicilio().getId() : ""%>">
					<input type="text"
						name="direccionCliente" class="form-control" required
						value="<%=cliente.getDomicilio() != null ? cliente.getDomicilio().getDireccion() != null ? cliente.getDomicilio().getDireccion() : "" : ""%>">
				</div>
				
				<div class="col">
					<label for="selectProvincia" class="form-label">Provincia</label>
						<select name="selectProvincia" class="form-select"
							onchange="this.form.submit()" required>
							<option value="">Seleccionar provincia</option>
								<%
									int provinciaElegida=-1;
									List<Provincia> provincias = (ArrayList<Provincia>) request.getAttribute("listaProvincias");
									if (request.getAttribute("provinciaElegida") != null) {
										provinciaElegida = Integer.parseInt(String.valueOf(request.getAttribute("provinciaElegida")));
									}						
									if (provincias != null) {
		            					for (Provincia p : provincias) {
		                				boolean esSeleccionada = (p.getId() == provinciaElegida);
		    						%>
									<option value="<%= p.getId() %>"
									<%= esSeleccionada ? "selected" : "" %>>
									<%= p.getNombre() %>
									</option>
								<%}
	            			}%>
						</select>
				</div>

				<div class="col">
					<label for="selectLocalidad" class="form-label">Localidad</label>
						<select name="selectLocalidad" class="form-select" required>
								<option value="">Seleccionar localidad</option>
								<%
									int localidadElegida=-1;
									List<Localidad> localidades = (ArrayList<Localidad>) request.getAttribute("listaLocalidades");;
									
									if (request.getAttribute("localidadElegida") != null) {
								           localidadElegida = Integer.parseInt(String.valueOf(request.getAttribute("localidadElegida")));
								    }
									if (localidades != null) {							
			            				for (Localidad l : localidades) {
			                				boolean esSeleccionada = (l.getId() == localidadElegida);
			    						%>
										<option value="<%= l.getId() %>"
											<%= esSeleccionada ? "selected" : "" %>>
											<%= l.getNombre() %>
										</option>
										<%}
			            			}%>
							</select>
				</div>

				<div class="col">
					<label class="form-label">Teléfono</label> <input type="text"
						name="telefonoCliente" class="form-control" pattern="^\d{10,15}$" title="Ingrese entre 10 y 15 números sin espacios ni símbolos" required
						value="<%=cliente != null ? cliente.getTelefono() : ""%>">
				</div>

				<div class="col">
					<label class="form-label">Correo electrónico</label> <input
						type="email" name="emailCliente" class="form-control" required
						value="<%=cliente != null ? cliente.getEmail() : ""%>">
				</div>

				<div class="col">
					<label for="selectNacionalidad" class="form-label">Nacionalidad</label>
						<select name="selectNacionalidad" class="form-select" required>
							<option value="">Seleccionar nacionalidad</option>
							<%
								int nacionalidadElegida=-1;
								List<Pais> nacionalidades = (ArrayList<Pais>) request.getAttribute("listaPaises");
								if (request.getAttribute("nacionalidadElegida") != null) {
								nacionalidadElegida = Integer.parseInt(String.valueOf(request.getAttribute("nacionalidadElegida")));
								}						
								if (nacionalidades != null) {
							   		 for (Pais p : nacionalidades) {
	                				boolean esSeleccionada = (p.getId() == nacionalidadElegida);
	    						%>
									<option value="<%= p.getId() %>"
										<%= esSeleccionada ? "selected" : "" %>>
										<%= p.getNombre() %>
									</option>
								<%}
            			}%>
						</select>
				</div>
				<div class="col w-100 d-flex justify-content-center mt-4">
					<button type="submit" name="btnModificar" class="btn btn-warning btn-md w-100 btn-abml">
						<i class="bi bi-pencil-square me-2"></i>Modificar
					</button>
				</div>
			</form>
		</div>
	</main>
	
	<!-- Mostrar modal modificacion éxitosa -->
	<%
	Boolean mostrarModal = (Boolean) request.getAttribute("mostrarModalClienteModificado");
	if (mostrarModal != null && mostrarModal) {
	%>
	<script>
		window.onload = function() {
			var modal = new bootstrap.Modal(document
					.getElementById('modalClienteModificado'));
			modal.show();
		};
	</script>
	<%
	}
	%>

	<!-- Modal modificacion éxitosa -->
	<div class="modal fade" id="modalClienteModificado" tabindex="-1"
		aria-labelledby="modalClienteModificado" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">

				<div class="modal-header">
					<h5 class="modal-title" id="modalClienteModificado">Modificación éxitosa!</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Cerrar"></button>
				</div>

				<div class="modal-body">
					<%
					cliente = (Cliente) request.getAttribute("cliente");
					if (cliente != null) {
					%>
					<p> El cliente <%=cliente.getNombre()%>, <%=cliente.getApellido()%> con DNI <%=cliente.getDNI()%> y CUIL <%=cliente.getCUIL()%> fue modificado correctamente.</p>
					<%
					}
					%>
				</div>
				<form method="GET"
				action="${pageContext.request.contextPath}/ModificarClienteServlet">
					<div class="modal-footer">
						<button type="submit" name="btnModalClienteModificado" class="btn btn-secondary"
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
	
	<!-- Limito el calendario del input hasta la fecha actaul -->
	<script>
	    const hoy = new Date().toISOString().split('T')[0];
	    document.getElementById('fechaNacimientoCliente').max = hoy;
	</script>
	
	<!-- Formatea el campo CUIL a NN-NNNNNNNN-NN -->
	<script>
		document.getElementById('CUILCliente').addEventListener('input', function (e) {
	    	let input = e.target;
	    	let value = input.value;
	
	   		value = value.replace(/\D/g, '');
	
	    	if (value.length > 11) value = value.slice(0, 11);
	
	    	let formatted = '';
	    	if (value.length > 0) {
	       		formatted += value.substring(0, Math.min(2, value.length));
	    	}
	    	if (value.length >= 3) {
	       		 formatted += '-' + value.substring(2, Math.min(10, value.length));
	    	}
	    	if (value.length > 10) {
	       	 formatted += '-' + value.substring(10);
	   		 }
	    input.value = formatted;
		});
	</script>

	<!-- Limita el número de DNI a 8 dígitos -->
	<script>
		document.getElementById("DNICliente").addEventListener("input", function (e) {
	    let digitsOnly = e.target.value.replace(/\D/g, '');
	    
	    if (digitsOnly.length > 8) {
	        digitsOnly = digitsOnly.slice(0, 8);
	    }
	
	    e.target.value = digitsOnly;
	});
	</script>
	
	<jsp:include page="../../Footer.jsp" />
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
