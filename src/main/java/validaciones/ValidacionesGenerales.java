package validaciones;

public class ValidacionesGenerales {

	public boolean campoVacio(String campo) {
		if(campo.isEmpty() || campo == null || campo.trim().isEmpty())
		return false;
		
		return true;
	}
	
	public boolean soloLetras(String campo) {
	        return campo != null && campo.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
	    }

	public boolean soloNumeros(String campo) {
	        return campo != null && campo.matches("^\\d+$");
	    }
}

