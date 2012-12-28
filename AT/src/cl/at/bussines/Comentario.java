package cl.at.bussines;

import java.util.Date;

import cl.at.data.ComentarioSQL;

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
	
	public Comentario(Usuario usuario){
		remitente = usuario;
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
	
	public void setFecha(Date fecha){
		this.fecha = fecha;
	}
	
	//get y set usuario
	public Usuario getUsuario() {
		return remitente;
	}
	
	public void setUsuario(Usuario remitente) {
		this.remitente = remitente;
	}

	public void persistir() throws Exception{
		ComentarioSQL comentarioSQL = new ComentarioSQL();
		if(!comentarioSQL.persistir(this))
			throw new Exception("Error");
	}
	
}