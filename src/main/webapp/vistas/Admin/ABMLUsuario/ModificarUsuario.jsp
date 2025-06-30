<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
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

String nombreUsuario = request.getParameter("nombreUsuario");
String estadoParam = request.getParameter("estado");
boolean estado = "true".equals(estadoParam);
%>

<html>
<head>
<meta charset="UTF-8">
<title>Modificar Usuario</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/estiloInicio.css">
	</head>
<body>
	<jsp:include page="../../Header.jsp" />
	
		<main>
		<div class="container mt-5">
			<h2 class="text-center mb-4">Modificar Usuario</h2>
			<div class="row justify-content-center">
				<div class="col-md-6">
					<form id="frmModificar" action="${pageContext.request.contextPath}/ModificarUsuarioServlet" method="post">
						
						<div class="mb-3">
							<label for="lblUsuario" class="form-label">Usuario</label> 
							<div class="d-flex gap-2">
								<input	type="text" class="form-control" name="lblUsuario" value="<%= nombreUsuario %>" required disabled>
								<input type="hidden" name="nombreUsuario" value="<%= nombreUsuario %>">
								 <button type="button" class="btn btn-warning btn-sm">
                                	<i class="bi bi-pencil-square" style="font-size: 1rem;"></i>
                            	</button>
							</div>
						</div>
						<div class="col mb-3">
						    <label for="selectEstado" class="form-label">Estado</label>
						   <select id="selectEstado" name="estado" class="form-select" required>
							    <option value="true" <%= estado ? "selected" : "" %>>Activo</option>
							    <option value="false" <%= !estado ? "selected" : "" %>>Inactivo</option>
							</select>						   
						</div>
						<div class="d-grid">
							<button type="button" class="btn btn-warning w-100" data-bs-toggle="modal" data-bs-target="#modalConfirmarModificacion"> Modificar usuario</button>							   							
						</div>
					</form>
				</div>
			</div>
		</div>
	</main>

	<!-- Modal Confirmación -->
<div class="modal fade" id="modalConfirmarModificacion" tabindex="-1" aria-labelledby="tituloModal" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
    
      <div class="modal-header bg-warning">
        <h5 class="modal-title" id="tituloModal">Confirmar modificación</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
      </div>

      <div class="modal-body">
        <p>Una vez confirmado se modificara el estado de este usuario. ¿Esta seguro?</p>
      </div>

      <div class="modal-footer">
        <button type="submit" class="btn btn-warning" form="frmModificar">Confirmar</button>
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
      </div>

    </div>
  </div>
</div>
	<jsp:include page="../../Footer.jsp" />
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>