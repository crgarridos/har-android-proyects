package cl.at.bussines;

import java.util.ArrayList;

public class GrupoFamiliar {

	private String nombre;
	private ArrayList<Comentario> comentarios;
	private PuntoEncuentro puntoEncuentro;
	private ArrayList<Usuario> integrantes;
	private Lider lider;
	
	public GrupoFamiliar() {
		comentarios = new ArrayList<Comentario>();
		integrantes = new ArrayList<Usuario>();
	}

	public GrupoFamiliar(String nombre, PuntoEncuentro puntoEncuentro, Lider lider) {
		lider.setGrupoFamiliar(this);	
		comentarios = new ArrayList<Comentario>();
		integrantes = new ArrayList<Usuario>();
		this.setNombre(nombre);
		this.setPuntoEncuentro(puntoEncuentro);
		this.setLider(lider);
		
	}	

	// Setter y getter
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void addComentario(Comentario comentario) {
		this.comentarios.add(comentario);
	}
	
	public ArrayList<Comentario> getComentarios() {
		return this.comentarios;
	}
	
	public void setPuntoEncuentro(PuntoEncuentro puntoEncuentro) {
		this.puntoEncuentro = puntoEncuentro;
	}
	
	public PuntoEncuentro getPuntoEncuentro() {
		return puntoEncuentro;
	}

	public void addIntegrante(Usuario usuario) {
		this.integrantes.add(usuario);
		usuario.setGrupoFamiliar(this);
	}

	public ArrayList<Usuario> getIntegrantes() {
		return this.integrantes;
	}

	public void eliminarIntegrante(Usuario usuario) {
		this.integrantes.remove(usuario);
	}
	
	private void setLider(Lider lider) {
		this.lider = lider;
	}
	
	public Lider getLider() {
		return lider;
	}
	
	public void finalize() throws Throwable {

	}

	public void eliminar() {

	}

	public void persistir() {

	}

}