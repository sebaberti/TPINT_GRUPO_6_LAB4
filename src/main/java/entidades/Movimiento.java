package entidades;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Movimiento {
	 private int id;
	    private Cuenta cuenta;
	    private Cliente cliente;
	    private MovimientoTipo tipoMovimiento;// 1=AltaCuenta, 2=AltaPrestamo, 3=PagoPrestamo, 4=Transferencia
	    private LocalDateTime fecha;
	    private double importe;
	    private String detalle;
		private Integer idTransferencia;
	    
	    public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public Cuenta getCuenta() {
			return cuenta;
		}
		public void setCuenta(Cuenta cuenta) {
			this.cuenta = cuenta;
		}
		public Cliente getCliente() {
			return cliente;
		}
		public void setCliente(Cliente cliente) {
			this.cliente = cliente;
		}
		
		public MovimientoTipo getTipoMovimiento() {
			return tipoMovimiento;
		}
		public void setTipoMovimiento(MovimientoTipo tipoMovimiento) {
			this.tipoMovimiento = tipoMovimiento;
		}
		public LocalDateTime getFecha() {
			return fecha;
		}
		public void setFecha(LocalDateTime fecha) {
			this.fecha = fecha;
		}
		public double getImporte() {
			return importe;
		}
		public void setImporte(double importe) {
			this.importe = importe;
		}
		public String getDetalle() {
			return detalle;
		}
		public void setDetalle(String detalle) {
			this.detalle = detalle;
		}
		public Integer getIdTransferencia() {
			return idTransferencia;
		}
		public void setIdTransferencia(Integer idTransferencia) {
			this.idTransferencia = idTransferencia;
		}
	
    
	
    
}
