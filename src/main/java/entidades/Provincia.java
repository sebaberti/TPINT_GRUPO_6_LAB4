package entidades;

public class Provincia {
	int id;
	String nombre;
	Pais pais;
	
	public Provincia() {
		pais = new Pais();
	}
	
	public Provincia(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
		pais = new Pais();
	}
	
	public Provincia(int id, String nombre, Pais pais) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.pais = pais;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Pais getPais() {
		return pais;
	}
	public void setPais(Pais pais) {
		this.pais = pais;
	}

	@Override
	public String toString() {
		return "Provincia [id=" + id + ", nombre=" + nombre + ", pais=" + pais + "]";
	}
	
}
