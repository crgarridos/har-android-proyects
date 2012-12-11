package Negocio;

import java.util.Date;

public class Comentario {

	private String contenido;
	private Date fecha;
	private Usuario remitente;

	//constructor
	public Comentario(String contenido, Usuario usuario){
		setContenido(contenido);
		this.fecha = new Date();
		setUsuario(usuario);
	}
	
	//get y set contenido
	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	
	//get fecha
	public Date getFecha() {
		return fecha;
	}
	
	//get y set usuario
	public Usuario getUsuario() {
		return remitente;
	}
	
	public void setUsuario(Usuario remitente) {
		this.remitente = remitente;
	}

	//destructor
	public void finalize() throws Throwable {

	}
	
}