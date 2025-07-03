<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="entidades.Cuenta" %>
<%@ page import="entidades.Cliente" %>
<%@ page import="entidades.CuentaTipo" %>
<%@ page import="negocioImplementacion.Seguridad"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="utilidades.FormatterUtil" %>

<%
    Cuenta cuenta = (Cuenta) request.getAttribute("cuenta");
%>
<%
    ArrayList<CuentaTipo> listaTiposCuenta = (ArrayList<CuentaTipo>) request.getAttribute("listaTiposCuenta");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Manejar Cuentas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Cuentas/estiloManejarCuentas.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloInicio.css">
</head>

<body>
	<jsp:include page="/vistas/Header.jsp" />

	<main class="container">
	<%
	Object user = session.getAttribute("usuario");
	
 	if (!Seguridad.sesionActiva(user) || !Seguridad.esAdministrador(user)) {
	response.sendRedirect(request.getContextPath() + "/vistas/Login.jsp");
    return;
}

	%>
		<div class="form-card">
			<h2 class="form-title">Modificar Cuenta</h2>
				<form class="d-flex flex-row-reverse" method="post" action="${pageContext.request.contextPath}/ListarCuentasServlet">
				<button type="submit" name="btnVolverALInicio" class="btn btn-secondary btn-abml mb-3">
					<i class="bi bi-arrow-return-right me-2"></i>Volver al listado
				</button>
		</form>

			<div class="form-section">
				<form method="post" action="ModificarCuentaServlet">
    <input type="hidden" name="idCuenta" value="<%= cuenta.getId() %>">

	 <div class="mb-3">
        <label for="cbu">Numero de Cuenta</label>
        <input type="text" id="Nro_cuenta" name="Nro_cuenta" class="form-control" value="<%= cuenta.getNumeroCuenta() %>" readonly>
    </div>
    
    <div class="mb-3">
        <label for="dni">DNI Cliente</label>
        <input type="text" id="dni" name="dni" class="form-control" value="<%= cuenta.getCliente().getDNI() %>" readonly>
    </div>

    <div class="mb-3">
        <label for="cbu">CBU</label>
        <input type="text" id="cbu" name="cbu" class="form-control" value="<%= cuenta.getCBU() %>" readonly>
    </div>

    <div class="mb-3">
    <label for="tipoCuenta">Tipo de Cuenta</label>
    <select id="tipoCuenta" name="idTipoCuenta" class="form-select">
        <% for (CuentaTipo tipo : listaTiposCuenta) { %>
            <option value="<%= tipo.getId() %>" <%= (cuenta.getTipoCuenta().getId() == tipo.getId()) ? "selected" : "" %>>
                <%= tipo.getDescripcion() %>
            </option>
        <% } %>
    </select>
	</div>

    <div class="mb-3">
        <label for="saldo">Saldo</label>
        <input type="text" id="saldo" name="saldo" class="form-control" value="<%= FormatterUtil.formatearMiles(cuenta.getSaldo()) %>">
       
    </div>

    <div class="mb-3">
        <label for="estado">Estado</label>
        <select id="estado" name="estado" class="form-select">
            <option value="true" <%= cuenta.isEstado() ? "selected" : "" %>>Activo</option>
            <option value="false" <%= !cuenta.isEstado() ? "selected" : "" %>>Inactivo</option>
        </select>
    </div>

    <button type="submit" class="btn btn-success">Guardar cambios</button>
    <a href="ListarCuentasServlet" class="btn btn-secondary">Cancelar</a>
</form>
			</div>
		</div>
	</main>

	<jsp:include page="../../Footer.jsp" />
	 <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
