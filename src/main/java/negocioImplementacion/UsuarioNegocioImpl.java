package negocioImplementacion;

import dao.UsuarioDao;
import daoImplementacion.UsuarioDaoImpl;
import entidades.Usuario;
import negocio.UsuarioNegocio;

public class UsuarioNegocioImpl implements UsuarioNegocio {
    private UsuarioDao usuarioDao = new UsuarioDaoImpl();

    @Override
    public Usuario validarUsuario(String nombreUsuario, String contrasenia) {
        return usuarioDao.validarUsuario(nombreUsuario, contrasenia);
    }
}
