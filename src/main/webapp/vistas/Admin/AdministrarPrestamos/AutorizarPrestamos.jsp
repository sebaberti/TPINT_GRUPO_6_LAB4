<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Autorizar préstamos</title>
 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/vistas/Header.jsp" />

 <main class="container mt-5 mb-5">
        <h1 class="text-center mb-4">Listado de Préstamos Solicitados</h1>
		
		<form method="get" action="#" class="row g-3 mb-4">
   
    	<div class="col-md-3">
        	<label for="estado" class="form-label">Estado</label>
        		<select class="form-select" name="estado" id="estado">
           			<option value="todos">Todos</option>
            		<option value="pendiente">Pendientes</option>
            		<option value="aprobado">Aprobados</option>
            		<option value="rechazado">Rechazados</option>
        		</select>
    	</div>
   
    	<div class="col-md-3">
        	<label for="ordenarPor" class="form-label">Ordenar por</label>
        		<select class="form-select" name="ordenarPor" id="ordenarPor">
            		<option value="importePedido">Importe pedido</option>
            		<option value="importeMensual">Importe mensual</option>
            		<option value="plazo">Plazo</option>
        		</select>
    	</div>

    	<div class="col-md-3">
        	<label for="direccion" class="form-label">Dirección</label>
        		<select class="form-select" name="direccion" id="direccion">
            		<option value="asc">Ascendente</option>
            		<option value="desc">Descendente</option>
        		</select>
    	</div>

    	<div class="col-md-3 d-flex align-items-end">
        	<button type="submit" class="btn btn-primary w-100">
            	<i class="bi bi-funnel"></i> Filtrar
        	</button>
    	</div>
		</form>
    
        <div class="table-responsive mt-5">
            <table class="table table-bordered table-hover align-middle text-center tabla-cuentas">
                <thead class="table-light">
                    <tr>
                        <th>Cliente</th>
                        <th>Cuenta</th>
                        <th>Importe pedido</th>
                        <th>Plazo (en meses)</th>
                        <th>Importe Mensual</th>
                        <th>Estado</th>
                        <th>Estadisticas del cliente</th>
                        <th>Aprobar</th>
                        <th>Rechazar</th>                      
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>....</td>
                        <td>....</td>
                        <td>....</td>
                        <td>....</td>
                        <td>....</td>
                        <td>Pendiente/Aprobado/Rechazado</td>
                
                <td>
    			<a href="#">Ver...</a> 
    			</button>
				</td>
				
				<td>
    			<button type="submit" name="accion" value="aprobar" class="btn btn-primary btn-sm">
        		<i class="bi bi-check-circle"></i> Aprobar
    			</button>
				</td>
				
				<td>
    			<button type="submit" name="accion" value="rechazar" class="btn btn-danger btn-sm">
        		<i class="bi bi-x-circle"></i> Rechazar
    			</button>
				</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </main>


 <jsp:include page="/vistas/Footer.jsp" />
 <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>