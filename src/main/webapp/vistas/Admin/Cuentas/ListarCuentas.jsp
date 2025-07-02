<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="entidades.Cuenta" %>
<%@ page import="negocioImplementacion.Seguridad" %>
<%@ page import="utilidades.FormatterUtil" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Listar Cuentas</title>

    <!-- Bootstrap & DataTables -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloInicio.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Cuentas/estiloListarCuentas.css">
</head>
<body>

<jsp:include page="/vistas/Header.jsp" />

<main class="container mt-5 mb-5">
<%
    Object user = session.getAttribute("usuario");

    if (!Seguridad.sesionActiva(user) || !Seguridad.esAdministrador(user)) {
        response.sendRedirect(request.getContextPath() + "/vistas/Login.jsp");
        return;
    }

    List<Cuenta> listaCuentas = (List<Cuenta>) request.getAttribute("listaCuentas");
    String criterioSeleccionado = request.getParameter("criterioBusqueda") != null ? request.getParameter("criterioBusqueda") : "dni";
    String valorBusqueda = request.getParameter("valorBusqueda") != null ? request.getParameter("valorBusqueda") : "";
%>

    <h1 class="text-center mb-4">Listado de Cuentas</h1>

     <form method="GET" action="${pageContext.request.contextPath}/vistas/Admin/Cuentas/InicioABMLCuentas.jsp" class="d-flex flex-row-reverse">
		<button type="submit" name="btnVolverALInicio" class="btn btn-secondary btn-abml mb-3">
			<i class="bi bi-arrow-return-right me-2"></i>Volver al inicio
		</button>
	</form>
    <div class="mb-4 d-flex justify-content-end">
        <a href="${pageContext.request.contextPath}/AltaCuentaServlet" class="btn btn-primary">
            <i class="bi bi-plus-circle me-2"></i> Nueva Cuenta
        </a>
    </div>

    
    <div class="row justify-content-center">
        <div class="col-md-6">
            <form action="${pageContext.request.contextPath}/ListarCuentasServlet" method="post">
                <div class="mb-3">
                    <label for="criterioBusqueda" class="form-label">Filtrar por</label>
                    <select class="form-select" name="criterioBusqueda" id="criterioBusqueda">
                        <option value="dni" <%= "dni".equals(criterioSeleccionado) ? "selected" : "" %>>DNI del Cliente</option>
                        <option value="nroCuenta" <%= "nroCuenta".equals(criterioSeleccionado) ? "selected" : "" %>>Nro de Cuenta</option>
                        <option value="cbu" <%= "cbu".equals(criterioSeleccionado) ? "selected" : "" %>>CBU</option>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="valorBusqueda" class="form-label">Valor</label>
                    <input type="text" class="form-control" name="valorBusqueda" id="valorBusqueda" value="<%=valorBusqueda%>" placeholder="Ingrese el valor...">
                    <% if (request.getAttribute("errorBusqueda") != null) { %>
                        <div class="alert alert-danger mt-2"><%=request.getAttribute("errorBusqueda")%></div>
                    <% } %>
                </div>

                <div class="d-flex justify-content-between">
                    <button type="submit" name="btnBuscar" class="btn btn-success">
                        <i class="bi bi-search me-1"></i> Buscar
                    </button>
                    <button type="submit" name="btnVerTodo" class="btn btn-secondary">
                        <i class="bi bi-eye me-1"></i> Ver Todo
                    </button>
                </div>
            </form>
        </div>
    </div>

    
    <div class="table-responsive mt-5">
        <table id="tabla_cuentas" class="table table-striped table-bordered text-center align-middle">
            <thead class="table-dark">
                <tr>
                    <th>Nro de Cuenta</th>
                    <th>DNI Cliente</th>
                    <th>Tipo de Cuenta</th>
                    <th>CBU</th>
                    <th>Saldo</th>
                    <th>Estado</th>
                    <th>Modificar</th>
                    <th>Activar/Desactivar</th>
                </tr>
            </thead>
            <tbody>
            <%
                if (listaCuentas != null && !listaCuentas.isEmpty()) {
                    for (Cuenta c : listaCuentas) {
            %>
                <tr>
                    <td><%=c.getNumeroCuenta()%></td>
                    <td><%=c.getCliente().getDNI()%></td>
                    <td><%=c.getTipoCuenta().getDescripcion()%></td>
                    <td><%=c.getCBU()%></td>
                    <td>$ <%=FormatterUtil.formatearMiles(c.getSaldo())%></td>
                    <td><%=c.isEstado() ? "Activo" : "Inactivo"%></td>
                    <td>
                        <form method="get" action="ModificarCuentaServlet">
                            <input type="hidden" name="idCuenta" value="<%=c.getId()%>">
                            <button class="btn btn-warning btn-sm" type="submit"><i class="bi bi-pencil-square"></i></button>
                        </form>
                    </td>
                    <td>
                        <form method="post" action="ManejarCuentaServlet">
                            <input type="hidden" name="idCuenta" value="<%=c.getId()%>">
                            <% if (c.isEstado()) { %>
                                <button type="submit" name="btnEliminar" value="eliminar" class="btn btn-danger btn-sm" title="Eliminar">
                                    <i class="bi bi-trash"></i>
                                </button>
                            <% } else { %>
                                <button type="submit" name="btnReactivar" value="reactivar" class="btn btn-success btn-sm" title="Reactivar">
                                    <i class="bi bi-arrow-clockwise"></i>
                                </button>
                            <% } %>
                        </form>
                    </td>
                </tr>
            <%
                    }
                } else {
            %>
                <tr><td colspan="8">No hay cuentas cargadas.</td></tr>
            <% } %>
            </tbody>
        </table>
    </div>

   <!-- Modal si se quiere desactivar/reactivar-->
		<%
		Boolean mostrarModal = (Boolean) request.getAttribute("mostrarModalEliminar");
		Cuenta cuentaAEliminar = (Cuenta) request.getAttribute("cuentaAElim");

		boolean esReactivacion = cuentaAEliminar != null && !cuentaAEliminar.isEstado();
		String tituloModal = esReactivacion ? "Confirmar Reactivación" : "Confirmar Eliminación";
		String mensajeModal = esReactivacion
				? "Está por reactivar una cuenta previamente dada de baja."
				: "Está por eliminar una cuenta del sistema.";
		String botonTexto = esReactivacion ? "Reactivar Cuenta" : "Eliminar Cuenta";
		String botonClase = esReactivacion ? "btn-success" : "btn-danger";
		String botonIcono = esReactivacion ? "bi-arrow-clockwise" : "bi-trash";

		if (mostrarModal != null && mostrarModal && cuentaAEliminar != null) {
		%>
		<script>
			window.onload = function() {
				var modal = new bootstrap.Modal(document
						.getElementById('modalEliminar'));
				modal.show();
			};
		</script>
		<%
		}
		%>

		<%
		if (mostrarModal != null && mostrarModal && cuentaAEliminar != null) {
		%>
		<div class="modal fade" id="modalEliminar" tabindex="-1"
			aria-labelledby="modalEliminarLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<div class="modal-content shadow-lg">

					<div class="modal-header bg-primary text-white">
						<h5 class="modal-title" id="modalEliminarLabel">
							<i class="bi bi-exclamation-triangle-fill me-2"></i>
							<%=tituloModal%>
						</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Cerrar"></button>
					</div>

					<div class="modal-body">
						<p class="mb-4 text-danger fs-5">
							<strong>¡Atención!</strong>
							<%=mensajeModal%>
						</p>

						<ul class="list-group mb-4">
							<li class="list-group-item"><strong>DNI Cliente:</strong> <%=cuentaAEliminar.getCliente().getDNI()%></li>
							<li class="list-group-item"><strong>Tipo de Cuenta:</strong>
								<%=cuentaAEliminar.getTipoCuenta().getDescripcion()%></li>
							<li class="list-group-item"><strong>CBU:</strong> <%=cuentaAEliminar.getCBU().toString()%></li>
							<li class="list-group-item"><strong>N° Cuenta:</strong> <%=cuentaAEliminar.getNumeroCuenta()%></li>
							<li class="list-group-item"><strong>Saldo:</strong> $<%=cuentaAEliminar.getSaldo()%></li>
							<li class="list-group-item"><strong>Fecha de
									Creación:</strong> <%=cuentaAEliminar.getFechaCreacion().toString()%></li>
							<li class="list-group-item"><strong>Estado:</strong> <%=cuentaAEliminar.isEstado() ? "Activa" : "Inactiva"%></li>
						</ul>
					</div>

					<div class="modal-footer">
						<form method="post" action="ManejarCuentaServlet">
							<input type="hidden" name="idCuenta"
								value="<%=cuentaAEliminar.getId()%>">
							<%
							if (esReactivacion) {
							%>
							<button type="submit" name="btnReactivarConfirmado"
								class="<%=botonClase%> btn">
								<i class="bi <%=botonIcono%> me-1"></i>
								<%=botonTexto%>
							</button>
							<%
							} else {
							%>
							<button type="submit" name="btnEliminarConfirmado"
								class="<%=botonClase%> btn">
								<i class="bi <%=botonIcono%> me-1"></i>
								<%=botonTexto%>
							</button>
							<%
							}
							%>
							<button type="button" class="btn btn-primary"
								data-bs-dismiss="modal">Cancelar</button>
						</form>
					</div>

				</div>
			</div>
		</div>
		<%
		}
		%>
		<!-- Fin Modal -->


		<!-- Modal Mensaje-->
		<%
		Boolean mostrarModal2 = (Boolean) request.getAttribute("mostrarModalMsj");
		String mensaje = (String) request.getAttribute("mensaje");
		if (mostrarModal2 != null && mostrarModal2) {
		%>

		<script>
			window.onload = function() {
				var modal = new bootstrap.Modal(document
						.getElementById('modalMensaje'));
				modal.show();
			};
		</script>
		<%
		}
		%>

		<div class="modal fade" id="modalMensaje" tabindex="-1"
			aria-labelledby="modalMensajeLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">

					<div class="modal-header">
						<h5 class="modal-title" id="modalMensajeLabel">Resultado de
							operación:</h5>

						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Cerrar"></button>
					</div>

					<div class="modal-body">
						<h4><%=mensaje%></h4>
					</div>

					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal">Cerrar</button>
					</div>

				</div>
			</div>
		</div>

	</main>


<jsp:include page="/vistas/Footer.jsp" />

<!-- Scripts -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>

<script>
    $(document).ready(function () {
    	
    	$.fn.dataTable.ext.errMode = 'none';
    	
        $('#tabla_cuentas').DataTable({
            order: [],
            pageLength: 10,
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
</html>