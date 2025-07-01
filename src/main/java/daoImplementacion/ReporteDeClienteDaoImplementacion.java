package daoImplementacion;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dao.ReporteDeClienteDao;
import entidades.ReporteDeCliente;


public class ReporteDeClienteDaoImplementacion implements ReporteDeClienteDao{

	@Override
	public ReporteDeCliente obtenerReporteDeCliente(String dni) {
		ReporteDeCliente rdc = new ReporteDeCliente();

	    String query = """
	        SELECT
	            -- Total en saldos
	            (SELECT SUM(ctas.saldo)
	             FROM cuentas ctas
	             INNER JOIN clientes c ON c.id = ctas.id_cliente
	             WHERE ctas.estado = true AND c.dni = ?) AS total_saldos,

	            -- Préstamos incompletos
	            (SELECT COUNT(DISTINCT p.id)
	             FROM Prestamos p
	             INNER JOIN Cuotas cuot ON cuot.id_prestamo = p.id
	             INNER JOIN Clientes c ON p.id_cliente = c.id
	             WHERE c.dni = ? AND cuot.estado = 0 AND p.estado = 1) AS prestamos_incompletos,

	            -- Préstamos completos
	            (SELECT COUNT(DISTINCT p.id)
	             FROM Prestamos p
	             INNER JOIN Clientes c ON p.id_cliente = c.id
	             WHERE c.dni = ? AND p.estado = 1
	               AND p.id NOT IN (SELECT id_prestamo FROM cuotas WHERE estado = 0)) AS prestamos_completos,

	            -- Cuotas pagadas
	            (SELECT SUM(CASE WHEN cu.estado = 1 THEN cu.importe ELSE 0 END)
	             FROM prestamos p
	             INNER JOIN cuotas cu ON cu.id_prestamo = p.id
	             INNER JOIN clientes c ON p.id_cliente = c.id
	             WHERE c.dni = ? AND p.estado = 1) AS cuotas_pagadas,

	            -- Cuotas impagas vencidas
	            (SELECT SUM(CASE WHEN cu.estado = 0 AND cu.fecha_vencimiento < CURRENT_DATE THEN cu.importe ELSE 0 END)
	             FROM prestamos p
	             INNER JOIN cuotas cu ON cu.id_prestamo = p.id
	             INNER JOIN clientes c ON p.id_cliente = c.id
	             WHERE c.dni = ? AND p.estado = 1) AS cuotas_vencidas,

	            -- Cuotas impagas por vencer
	            (SELECT SUM(CASE WHEN cu.estado = 0 AND cu.fecha_vencimiento >= CURRENT_DATE THEN cu.importe ELSE 0 END)
	             FROM prestamos p
	             INNER JOIN cuotas cu ON cu.id_prestamo = p.id
	             INNER JOIN clientes c ON p.id_cliente = c.id
	             WHERE c.dni = ? AND p.estado = 1) AS cuotas_por_vencer
	    """;

	    try (
	         Connection conexion = Conexion.getConexion().getSQLConexion();
	         PreparedStatement stmt = conexion.prepareStatement(query)) {

	        for (int i = 1; i <= 6; i++) {
	            stmt.setString(i, dni);
	        }

	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	        	rdc.setTotalSaldos(rs.getBigDecimal("total_saldos") != null ? rs.getBigDecimal("total_saldos") : BigDecimal.ZERO);
	            rdc.setPrestamosIncompletos(rs.getInt("prestamos_incompletos"));
	            rdc.setPrestamosCompletos(rs.getInt("prestamos_completos"));
	            rdc.setCuotasPagadas(rs.getBigDecimal("cuotas_pagadas") != null ? rs.getBigDecimal("cuotas_pagadas") : BigDecimal.ZERO);
	            rdc.setCuotasVencidas(rs.getBigDecimal("cuotas_vencidas") != null ? rs.getBigDecimal("cuotas_vencidas") : BigDecimal.ZERO);
	            rdc.setCuotasPorVencer(rs.getBigDecimal("cuotas_por_vencer") != null ? rs.getBigDecimal("cuotas_por_vencer") : BigDecimal.ZERO);
	        }
	    }  catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return rdc;
	}
	
	@Override
	public ReporteDeCliente obtenerResumenIngresosEgresosPorDniYAnio(String dni, int anio) {
		ReporteDeCliente resumen = new ReporteDeCliente();

	    String query = """
	        SELECT
	            SUM(CASE WHEN m.id_tipo_movimiento IN (1, 2, 4) THEN m.importe ELSE 0 END) AS ingresos,
	            SUM(CASE WHEN m.id_tipo_movimiento IN (3, 5) THEN m.importe ELSE 0 END) AS egresos
	        FROM movimientos m
	        INNER JOIN cuentas ctas ON ctas.id = m.id_cuenta
	        INNER JOIN clientes cl ON cl.id = ctas.id_cliente
	        WHERE cl.dni = ?
	          AND YEAR(m.fecha_movimiento) = ?
	        """;

	    try (
	    	Connection conexion = Conexion.getConexion().getSQLConexion();
	         PreparedStatement ps = conexion.prepareStatement(query)) {

	        ps.setString(1, dni);
	        ps.setInt(2, anio);

	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                BigDecimal ingresos = rs.getBigDecimal("ingresos");
	                BigDecimal egresos = rs.getBigDecimal("egresos");

	                resumen.setIngresos(ingresos != null ? ingresos : BigDecimal.ZERO);
	                resumen.setEgresos(egresos != null ? egresos : BigDecimal.ZERO);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); 
	    }

	    return resumen;
	}

}
