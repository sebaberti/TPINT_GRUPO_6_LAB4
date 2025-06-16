package entidades;

import java.util.Random;

import entidades.Clientes;

public class Cuentas {
	
    private Clientes cliente; 
    private String fechaCreacion;
    private String tipoCuenta;
    private String numeroCuenta;
    private String CBU;
    private double saldo;
    private boolean estado;
    
    private static final double montoInicial=10000;

    
	//opcion completa
    public Cuentas(Clientes cliente, String fechaCreacion, String tipoCuenta, String numeroCuenta, String CBU, double saldo, boolean estado) {
	  this.cliente = cliente;
	  this.fechaCreacion = fechaCreacion;
	  this.tipoCuenta = tipoCuenta;
	  this.numeroCuenta = numeroCuenta;
	  this.CBU = CBU;
	  this.saldo = saldo;
	  this.estado = estado;
	}
    
    //opcion con saldo iniciaal fijo
    public Cuentas(Clientes cliente, String fechaCreacion, String tipoCuenta, String numeroCuenta, String CBU) {
    	this.cliente = cliente;
  	  this.fechaCreacion = fechaCreacion;
  	  this.tipoCuenta = tipoCuenta;
  	  this.numeroCuenta = numeroCuenta;
  	  this.CBU = CBU;
  	  this.saldo = montoInicial;
  	  this.estado = true;
	}

	public Clientes getCliente() {
		return cliente;
	}

	public void setCliente(Clientes cliente) {
		this.cliente = cliente;
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getCBU() {
		return CBU;
	}

	public void setCBU(String cBU) {
		CBU = cBU;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	@Override
	 public String toString() {
		return "Numero de Cuenta: " + numeroCuenta + ",\n cliente: " + cliente + ",\n tipo de Cuenta: " + tipoCuenta
                + ",\n fecha_Creacion: " + fechaCreacion + ",\n CBU: " + CBU + ",\n saldo: $" + saldo;  }
   }
}
