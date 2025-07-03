<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="entidades.Cliente" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Listar Usuarios</title>

    <!-- Bootstrap y DataTables -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
    <link href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css" rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloInicio.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloListarCuentas.css">
</head>

<body>
<jsp:include page="/vistas/Header.jsp" />

<main class="container mt-5 mb-5">
    <h1 class="text-center mb-4">Listado de Usuarios</h1>
	 <form method="GET" action="${pageContext.request.contextPath}/vistas/Admin/ABMLUsuario/InicioABMLUsuario.jsp" class="d-flex flex-row-reverse">
			<button type="submit" name="btnVolverALInicio" class="btn btn-secondary btn-abml mb-3">
				<i class="bi bi-arrow-return-right me-2"></i>Volver al inicio
			</button>
	</form>
    <!-- Botón Nuevo Usuario -->
    <div class="mb-4 d-flex justify-content-end">
        <button type="button" class="btn btn-primary"
                onclick="location.href='${pageContext.request.contextPath}/vistas/Admin/ABMLUsuario/AltaUsuario.jsp'">
            <i class="bi bi-plus-circle me-2"></i> Nuevo Usuario
        </button>
    </div>

    <!-- Formulario de búsqueda -->
    <div class="row justify-content-center">
        <div class="col-md-6">
            <form method="POST" action="${pageContext.request.contextPath}/ListarUsuariosServlet">
                <div class="mb-3">
                    <label for="nombreUsuario" class="form-label">Nombre de Usuario</label>
                    <input type="text" class="form-control shadow-sm" id="nombreUsuario"
                           name="txtDniClientes" placeholder="Ingrese nombre de usuario">
                </div>
                <div class="d-grid gap-2 d-md-flex justify-content-md-between">
                    <button type="submit" name="btnBuscar" class="btn btn-success">
                        <i class="bi bi-search me-1"></i> Buscar
                    </button>
                    <button type="button" class="btn btn-secondary"
                            onclick="location.href='${pageContext.request.contextPath}/ListarUsuariosServlet?verTodo=true'">
                        <i class="bi bi-eye me-1"></i> Ver Todo
                    </button>
                </div>
            </form>
        </div>
    </div>

    <!-- Tabla de usuarios -->
    <div class="table-responsive mt-5">
        <table id="tabla_usuarios" class="table table-bordered table-hover align-middle text-center">
            <thead class="table-dark">
                <tr>
                    <th>Usuario</th>
                    <th>DNI Cliente</th>
                    <th>CUIL</th>
                    <th>Nombre/s</th>
                    <th>Apellido/s</th>
                    <th>Estado de usuario</th>
                    <th>Modificar</th>
                </tr>
            </thead>
            <tbody>
            
            <%
                List<Cliente> listaClientes = (List<Cliente>) request.getAttribute("listaClientes");
                if (listaClientes != null && !listaClientes.isEmpty()) {
                    for (Cliente c : listaClientes) {
            %>
                <tr>
                    <td><%= c.getUsuario().getNombreUsuario() %></td>
                    <td><%= c.getDNI() %></td>
                    <td><%= c.getCUIL() %></td>
                    <td><%= c.getNombre() %></td>
                    <td><%= c.getApellido() %></td>
                    <td> <%= (c.getUsuario() != null && c.getUsuario().isEstado()) ? "Activo" : "Inactivo" %></td>
                    <td>
            		<button type="button" class="btn btn-warning btn-sm"
    					onclick="location.href='${pageContext.request.contextPath}/vistas/Admin/ABMLUsuario/ModificarUsuario.jsp?nombreUsuario=<%=c.getUsuario().getNombreUsuario()%>&estado=<%=c.getEstado()%>&dni=<%=c.getDNI()%>&cuil=<%=c.getCUIL()%>&idCliente=<%=c.getId()%>'">
   						 <i class="bi bi-pencil-square"></i>
					</button>
                    </td>
                </tr>
            <%
                    }
                } else {
            %>
                <tr>
                    <td colspan="7">No hay usuarios para mostrar.</td>
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
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>

<script>
    $(document).ready(function () {
    	$.fn.dataTable.ext.errMode = 'none';
        $('#tabla_usuarios').DataTable({
            order: [],  // sin orden inicial
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


