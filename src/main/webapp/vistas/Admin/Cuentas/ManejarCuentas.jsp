<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Manejar Cuenta</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/estiloInicio.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/estiloManejarCuentas.css">

<style>
/* Contenedor principal centrado */
.container {
	max-width: 800px;
	margin: 40px auto;
	padding: 30px;
}

/* Tarjeta de formulario */
.form-card {
	background-color: #ffffff;
	border-radius: 12px;
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
	padding: 30px 40px;
}

/* Título del formulario */
.form-title {
	font-size: 1.8em;
	font-weight: 600;
	color: #333;
	text-align: center;
	margin-bottom: 25px;
}

/* Grupo de elementos del formulario */
.form-group {
	margin-bottom: 20px;
}

.form-group label {
	display: block;
	font-weight: 500;
	margin-bottom: 6px;
	color: #555;
}

.form-group input[type="text"], .form-group select {
	width: 100%;
	padding: 10px 14px;
	border: 1px solid #ccc;
	border-radius: 8px;
	font-size: 1em;
	background-color: #f9f9f9;
	transition: border-color 0.3s ease;
}

.form-group input:focus, .form-group select:focus {
	border-color: #007bff;
	outline: none;
	background-color: #fff;
}

/* Botones de acción */
.form-buttons {
	display: flex;
	justify-content: space-between;
	margin-top: 30px;
	gap: 15px;
}

.center-container {
	justify-content: center;
}

/* Botones */
.btn {
	padding: 10px 20px;
	border: none;
	border-radius: 8px;
	font-weight: 500;
	cursor: pointer;
	transition: background-color 0.2s ease;
	font-size: 1em;
}

/* Colores por tipo */
.btn-danger {
	background-color: #dc3545;
	color: white;
}

.btn-danger:hover {
	background-color: #c82333;
}

.btn-light {
	background-color: #f8f9fa;
	color: #333;
	border: 1px solid #ccc;
}

.btn-light:hover {
	background-color: #e2e6ea;
}

.btn-secondary {
	background-color: #6c757d;
	color: white;
}

.btn-secondary:hover {
	background-color: #5a6268;
}

/* Responsive */
@media ( max-width : 600px) {
	.form-buttons {
		flex-direction: column;
		gap: 10px;
	}
}
</style>

</head>
<body>
	<jsp:include page="/vistas/Header.jsp" />

	<main class="container">
		<div class="form-card">
			<h2 class="form-title">Modificar/Eliminar Cuenta</h2>

			<!-- Busqueda por DNI -->
			<form method="post" action="#">
				<div class="form-group">
					<label for="dniCliente">DNI del Cliente</label> <input type="text"
						id="dniCliente" name="dniCliente" placeholder="Ingrese el DNI"
						required />
					<button type="submit" name="btnBuscarCliente"
						class="btn btn-secondary">Buscar</button>
				</div>
			</form>

			<!-- Resultado de la búsqueda y selección de cuenta -->
			<div class="form-section">
				<form method="post" action="#">
					<div class="form-group">
						<label for="cliente">Cliente</label> <input type="text"
							id="cliente" name="cliente" readonly
							placeholder="Nombre y Apellido del Cliente" />
					</div>

					<div class="form-group">
						<label for="cuenta">Cuenta a Modificar/Eliminar</label> <select
							id="cuenta" name="cuenta" required>
							<option disabled selected>Seleccione una cuenta</option>
							<!-- Aquí se listarían las cuentas del cliente -->
						</select>
					</div>

					<div class="form-group">
						<label for="cbu">Cbu</label> <input type="text" id="cbu"
							name="cbu" readonly placeholder="CBU" />
					</div>

					<div class="form-group">
						<label for="tipoCuenta">Tipo de Cuenta</label> <input type="text"
							id="tipoCuenta" name="tipoCuenta" readonly
							placeholder="Caja de ahorro / Cuenta corriente" />
					</div>

					<div class="form-group">
						<label for="saldo">Saldo Actual</label> <input type="text"
							id="saldo" name="saldo" readonly placeholder="$0.00" />
					</div>

					<!-- Botones de acción -->
					<div class="form-buttons center-container">
						<button type="submit" name="btnEliminarCuenta"
							class="btn btn-danger">Modificar/Eliminar Cuenta</button>
						<button type="button" class="btn btn-secondary">Cancelar</button>
					</div>
				</form>
			</div>
		</div>
	</main>

	<jsp:include page="/vistas/Footer.jsp" />
</body>
</html>
