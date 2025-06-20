package entidades;

public class Cuenta {
	
	private int id;
	private Cliente cliente; 
    private String fechaCreacion;
    //private Date FechaDeCreacion;   otra opcion
    private CuentaTipo tipoCuenta;
    private String numeroCuenta;
    private String CBU;
    private double saldo;
    private boolean estado;
    
    private static final double montoInicial=10000;

    public Cuenta() {
    	this.cliente = new Cliente();
    	this.tipoCuenta = new CuentaTipo();
    }
    
	//opcion completa
    public Cuenta(Cliente cliente, String fechaCreacion, CuentaTipo tipoCuenta, String numeroCuenta, String CBU, double saldo, boolean estado) {
	  this.cliente = cliente;
	  this.fechaCreacion = fechaCreacion;
	  this.tipoCuenta = tipoCuenta;
	  this.numeroCuenta = numeroCuenta;
	  this.CBU = CBU;
	  this.saldo = saldo;
	  this.estado = estado;
	}
    
    //opcion con saldo iniciaal fijo
    public Cuenta(Cliente cliente, String fechaCreacion, CuentaTipo tipoCuenta, String numeroCuenta, String CBU) {
    	this.cliente = cliente;
  	  this.fechaCreacion = fechaCreacion;
  	  this.tipoCuenta = tipoCuenta;
  	  this.numeroCuenta = numeroCuenta;
  	  this.CBU = CBU;
  	  this.saldo = montoInicial;
  	  this.estado = true;
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

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public CuentaTipo getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(CuentaTipo tipoCuenta) {
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
		return "Numero de Cuenta: " + numeroCuenta + ",\n cliente: " + cliente.toString() + ",\n tipo de Cuenta: " + tipoCuenta.toString()
                + ",\n fecha_Creacion: " + fechaCreacion + ",\n CBU: " + CBU + ",\n saldo: $" + saldo;  
		}
   
}
