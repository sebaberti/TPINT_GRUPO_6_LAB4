package utilidades;

import java.text.NumberFormat;
import java.util.Locale;

public class FormatterUtil {
    public static String formatearMiles(double valor) {
        NumberFormat formato = NumberFormat.getInstance(new Locale("es", "AR"));
        return formato.format(valor);
    }
}
