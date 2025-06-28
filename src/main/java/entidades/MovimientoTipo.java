package entidades;

public class MovimientoTipo {
    private int id;
    private String descripcion;

    public MovimientoTipo() {}

    public MovimientoTipo(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "MovimientoTipo [id=" + id + ", descripcion=" + descripcion + "]";
	}
 
    
    
}
    