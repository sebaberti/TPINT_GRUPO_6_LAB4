package daoImplementacion;

import java.sql.*;
import dao.UsuarioDao;
import entidades.Usuario;

public class UsuarioDaoImpl implements UsuarioDao {
    private static final String VALIDAR_USUARIO = 
        "SELECT * FROM usuarios WHERE nombre_usuario = ? AND contrasenia = ? AND estado = true";

    @Override
    public Usuario validarUsuario(String nombreUsuario, String contrasenia) {
    	Usuario usuario = null;

        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // OBTENER SIEMPRE UNA NUEVA CONEXIÓN
            conexion = Conexion.getConexion().getSQLConexion();

            statement = conexion.prepareStatement("SELECT * FROM usuarios WHERE nombre_usuario=? AND contrasenia=?");
            statement.setString(1, nombreUsuario);
            statement.setString(2, contrasenia);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                usuario = new Usuario();
                usuario.setId(resultSet.getInt("id"));
                usuario.setNombreUsuario(resultSet.getString("nombre_usuario"));
                usuario.setIdRol(resultSet.getInt("id_rol"));
                usuario.setEstado(resultSet.getBoolean("estado"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuario;
    }
}