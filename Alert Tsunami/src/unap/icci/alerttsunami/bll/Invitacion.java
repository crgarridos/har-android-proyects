package unap.icci.alerttsunami.bll;

import java.util.Date;

public class Invitacion {

	private Date fecha;
	private Usuario invitado;
	private Usuario remitente;

	public Invitacion() {
		
	}

	public Invitacion(Usuario invitado, Usuario remitente) {
		this.fecha = new Date();
		this.setInvitado(invitado);
		this.setRemitente(remitente);
	}
	
	public Date getFecha() {
		return this.fecha;
	}

	private void setInvitado(Usuario invitado){
		this.invitado = invitado;
	}
	
	public Usuario getInvitado() {
		return this.invitado;
	}
	
	private void setRemitente(Usuario remitente){
		this.remitente = remitente;
	}
	
	public Usuario getRemitente() {
		return this.remitente;
	}
	
	public void finalize() throws Throwable {

	}

	public void eliminar() {

	}

	public boolean estaAprobada() {
		return false;
	}

	public void notificar() {

	}

	public void persistir() {

	}

}