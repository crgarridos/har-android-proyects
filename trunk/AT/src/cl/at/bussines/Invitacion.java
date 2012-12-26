package cl.at.bussines;

import java.util.Date;

import cl.at.data.InvitacionSQL;

public class Invitacion{

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
	public Invitacion(Usuario invitado, Usuario remitente,Date fecha) {
		this.fecha = fecha;
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

	public void persistir() throws Exception{
		InvitacionSQL iSQL = new InvitacionSQL();
		if(!iSQL.persistir(this))
			throw new Exception("Error");
	}


}