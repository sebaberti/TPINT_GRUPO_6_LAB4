package entidades;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Prestamo {
	private int id;
	private Cliente cliente;
	private Cuenta cuenta;
	private LocalDate fechaAlta;
	private BigDecimal importePedido;
	private Plazo plazo;
	private int estado;
	
	public Prestamo() {
	}
	
	public Prestamo(int id, Cliente cliente, Cuenta cuenta, LocalDate fechaAlta, BigDecimal importePedido, Plazo plazo,
			int estado) {	
		this.id = id;
		this.cuenta= cuenta;
		this.fechaAlta = fechaAlta;
		this.importePedido = importePedido;
		this.plazo = plazo;
		this.estado = estado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}
	
	public void setCuenta(Cuenta cuenta) {
		this.cuenta= cuenta;
	}
	
	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public BigDecimal getImportePedido() {
		return importePedido;
	}

	public void setImportePedido(BigDecimal importePedido) {
		this.importePedido = importePedido;
	}

	public Plazo getPlazo() {
		return plazo;
	}

	public void setPlazo(Plazo plazo) {
		this.plazo = plazo;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	public String getEstadoTexto() {
		switch (estado) {
			case 0: return "Pendiente";
			case 1: return "Aprobado";
			case 2: return "Rechazado";
			default: return "Desconocido";
		}
	}
}
