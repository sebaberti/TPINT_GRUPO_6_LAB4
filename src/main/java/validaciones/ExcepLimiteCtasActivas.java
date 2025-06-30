package validaciones;

public class ExcepLimiteCtasActivas extends Exception{
	public ExcepLimiteCtasActivas() {
        super("El cliente ya tiene 3 cuentas activas.");
    }
}