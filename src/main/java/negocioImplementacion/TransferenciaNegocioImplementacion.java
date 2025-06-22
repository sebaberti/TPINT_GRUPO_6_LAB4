package negocioImplementacion;

import java.time.LocalDateTime;
import java.util.List;

import daoImplementacion.Conexion;
import daoImplementacion.CuentaDaoImplementacion;
import daoImplementacion.TransferenciaDaoImplementacion;
import entidades.Cuenta;
import entidades.Transferencia;
import negocio.TransferenciaNegocio;

public class TransferenciaNegocioImplementacion implements TransferenciaNegocio {

    private TransferenciaDaoImplementacion transferenciaDao = new TransferenciaDaoImplementacion();
    private CuentaDaoImplementacion cuentaDao = new CuentaDaoImplementacion();

    @Override
    public boolean realizarTransferencia(Transferencia transferencia) {
        // Validaciones básicas:
        if (transferencia.getMonto() <= 0) {
            return false; // monto inválido
        }

        Cuenta cuentaOrigen = transferencia.getCuentaOrigen();
        Cuenta cuentaDestino = transferencia.getCuentaDestino();

        // Obtener datos actualizados de cuentas (opcional, para validar saldos)
        // Podés implementar métodos para obtener cuenta por id con saldo actualizado.

        // Validar fondos disponibles
        if (cuentaOrigen.getSaldo() < transferencia.getMonto()) {
            return false; // fondos insuficientes
        }

        // Actualizar saldo de ambas cuentas
        cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - transferencia.getMonto());
        cuentaDestino.setSaldo(cuentaDestino.getSaldo() + transferencia.getMonto());

        // Guardar la transferencia
        transferencia.setFecha(LocalDateTime.now());

        // Importante: el proceso debe ser transaccional (commit/rollback)
        boolean insertOk = transferenciaDao.insertarTransferencia(transferencia);
        if (!insertOk) {
            return false;
        }

        // Actualizar saldos en base de datos
        boolean actualizoOrigen = actualizarSaldoCuenta(cuentaOrigen);
        boolean actualizoDestino = actualizarSaldoCuenta(cuentaDestino);

        return actualizoOrigen && actualizoDestino;
    }

    private boolean actualizarSaldoCuenta(Cuenta cuenta) {
        // Podés implementar en CuentaDao un método para actualizar el saldo
        // Ejemplo simple aquí:
        String query = "UPDATE cuentas SET saldo = ? WHERE id = ?";
        try {
            var conexion = Conexion.getConexion().getSQLConexion();
            var ps = conexion.prepareStatement(query);
            ps.setDouble(1, cuenta.getSaldo());
            ps.setInt(2, cuenta.getId());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                conexion.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Transferencia> listarTransferenciasPorCuenta(int cuentaId) {
        return transferenciaDao.listarTransferenciasPorCuenta(cuentaId);
    }
}
