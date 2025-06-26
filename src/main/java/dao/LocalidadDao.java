package dao;

import java.util.List;

import entidades.Localidad;
import entidades.Provincia;

public interface LocalidadDao {
	 public List<Localidad> listarLocalidadesBuenosAires();
	 public List<Localidad> listarLocalidades();
	 public Localidad obtenerLocalidadPorID(int id);
}
