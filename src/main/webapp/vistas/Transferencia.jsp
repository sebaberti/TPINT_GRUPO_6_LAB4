<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Transferencias</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/estiloInicio.css">
</head>
<body>
	<jsp:include page="Header.jsp" />
	
	<main>
		
		<div class="container py-4">
		
			<div class="text-center">
				<h2>Transferencias</h2>
			</div>
			
			<div class="d-flex justify-content-center">
				<form method="POST" action="">
					
					<div class="card w-auto">
	  					<div class="card-body">
	  					
	    					<div class="mb-3">
	    						<label for="lblCuentaOrig" class="form-label">Seleccionar cuenta de Origen</label>
	    						<select id="lblCuentaOrig" class="form-select">
	    							<option value="" disabled selected>Seleccionar cuenta</option>
	    							<!-- cargar todas la cuentas -->
	    						</select>
	    					</div>
	    					
	    					<div class="mb-3">
	    						<label for="lblCBU" class="form-label">Ingresar CBU de destino</label>
	    						<input type="text" id="lblCBU" class="form-control">
	    					</div>
	    					
	    					<div class="mb-3">
	    						<label for="lblMonto" class="form-label">Ingrese el monto a transferir</label>
	    						<p class="text-muted">Monto disponible: <%=1000 %></p>
	    						<input type="number" id="lblMonto" class="form-control">
	    					</div>
	    					
	    					<div class="mb-3">
	    						<button type="submit" class="btn btn-success btn-sm w-100">Transferir</button>
	    						<!-- validar que la cuenta tenga fondos-->
	    						<!-- validar que el CBU exista-->
	    						<!-- descontar saldo del cliente -->
	    						<!-- aumentar saldo de cuenta destino -->
	    					</div>
	    					
	  					</div>
					</div>
				</form>
			</div>
		
		
		</div>
	</main>
	
	<jsp:include page="Footer.jsp" />
</body>
</html>