package dao;

import entidades.Usuario;

public interface UsuarioDao {
    public Usuario validarUsuario(String nombreUsuario, String contrasenia);
}
