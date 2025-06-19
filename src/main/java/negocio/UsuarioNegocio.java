package negocio;

import entidades.Usuario;

public interface UsuarioNegocio {
    public Usuario validarUsuario(String nombreUsuario, String contrasenia);
}
