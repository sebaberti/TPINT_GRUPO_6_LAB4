package entidades;

import java.sql.Date;

public class Cuota {
	private int id;
	private int prestamoId;
	private int nroCuota;
	private double importe;
	private Date fechaPago;
	private Date fechaVto;
	private boolean estado;
	
	public Cuota() {
		this.id=0;
	}
	
	public Cuota(int id, int prestamoId, int numeroCuota, double monto, Date fechaPago,
			Date fechaVencimiento, boolean estado) {
			this.id = id;
			this.prestamoId = prestamoId;
			this.nroCuota = numeroCuota;
			this.importe = monto;
			this.fechaPago = fechaPago;
			this.fechaVto = fechaVencimiento;
			this.estado = estado;
		}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getPrestamoId() {
		return prestamoId;
	}
	public void setPrestamoId(int prestamoId) {
		this.prestamoId = prestamoId;
	}
	
	public int getNumeroCuota() {
		return nroCuota;
	}
	public void setNumeroCuota(int numeroCuota) {
		this.nroCuota = numeroCuota;
	}
	
	public double getImporte() {
		return importe;
	}
	public void setImporte(double monto) {
		this.importe = monto;
	}
	
	public Date getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}
	public Date getFechaVencimiento() {
		return fechaVto;
	}
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVto = fechaVencimiento;
	}
	
	public boolean getEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	@Override
	public String toString() {
		return "Id de Cuota: " + id + ",\n id de prestamo: " + prestamoId + ",\n numero de cuota: " + nroCuota + ",\n monto: $"
				+ importe + ",\n fecha de pago: " + fechaPago + ",\n fecha de vencimiento: " + fechaVto ;
	}
}
