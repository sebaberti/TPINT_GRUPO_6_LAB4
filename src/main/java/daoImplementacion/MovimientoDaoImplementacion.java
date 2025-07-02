package daoImplementacion;

import dao.MovimientoDao;
import entidades.Cuenta;
import entidades.Cliente;
import entidades.Movimiento;
import entidades.MovimientoTipo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovimientoDaoImplementacion implements MovimientoDao {

    @Override
    public boolean insertarMovimiento(Movimiento movimiento) {
    	Connection conexion = null;
        PreparedStatement stmt= null;
        String query = "INSERT INTO movimientos (id_cuenta, id_tipo_movimiento, fecha_movimiento, importe, detalle, id_transferencia) VALUES (?, ?, ?, ?, ?, ?)";

        try {
        	
        	conexion = Conexion.getConexion().getSQLConexion();
        	stmt = conexion.prepareStatement(query);
        	stmt.setInt(1, movimiento.getCuenta().getId());
        	stmt.setInt(2, movimiento.getTipoMovimiento().getId());
        	stmt.setTimestamp(3, Timestamp.valueOf(movimiento.getFecha()));
        	stmt.setDouble(4, movimiento.getImporte());
        	stmt.setString(5, movimiento.getDetalle());

        	if (movimiento.getIdTransferencia() != null) {
        	    stmt.setInt(6, movimiento.getIdTransferencia());
        	} else {
        	    stmt.setNull(6, Types.INTEGER);
        	}

            if (stmt.executeUpdate() > 0) {
                conexion.commit();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Movimiento> listarMovimientosPorCuenta(int cuentaId) {
        List<Movimiento> movimientos = new ArrayList<>();
        
        Connection conexion = null;
        PreparedStatement stmt= null;
        ResultSet rs= null;
        
        String query = """
                SELECT m.*, tm.descripcion AS tipo_descripcion, c.id_cliente
				FROM movimientos m
				INNER JOIN tipos_movimientos tm ON m.id_tipo_movimiento = tm.id
				INNER JOIN cuentas c ON m.id_cuenta = c.id
				WHERE m.id_cuenta = ?
				ORDER BY m.fecha_movimiento DESC
                """;

        try {
        	conexion = Conexion.getConexion().getSQLConexion();
        	stmt = conexion.prepareStatement(query);
        	stmt.setInt(1, cuentaId);
	       	 rs = stmt.executeQuery();

            while (rs.next()) {
                Movimiento mov = new Movimiento();

                // Cuenta
                Cuenta cuenta = new Cuenta();
                cuenta.setId(rs.getInt("id_cuenta"));
                mov.setCuenta(cuenta);

                // Cliente
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id_cliente"));
                mov.setCliente(cliente);

                // Tipo de Movimiento
                MovimientoTipo tipo = new MovimientoTipo();
                tipo.setId(rs.getInt("id_tipo_movimiento"));
                tipo.setDescripcion(rs.getString("tipo_descripcion"));
                mov.setTipoMovimiento(tipo);

                // Otros datos
                mov.setId(rs.getInt("id"));
                mov.setFecha(rs.getTimestamp("fecha_movimiento").toLocalDateTime());
                mov.setImporte(rs.getDouble("importe"));
                mov.setDetalle(rs.getString("detalle"));
                mov.setIdTransferencia(rs.getObject("id_transferencia", Integer.class));

                movimientos.add(mov);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movimientos;
    }
    
    @Override
    public double sumarMovimientosPorTipo(String tipoMovimiento) {
        double total = 0.0;

        String query = """
            SELECT SUM(m.importe) AS total
            FROM movimientos m
            INNER JOIN tipos_movimientos tm ON m.id_tipo_movimiento = tm.id
            WHERE tm.descripcion = ?
        """;

        try (Connection conexion = Conexion.getConexion().getSQLConexion();
             PreparedStatement stmt = conexion.prepareStatement(query)) {

            stmt.setString(1, tipoMovimiento.toUpperCase());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    total = rs.getDouble("total");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

}
