public class Invitacion {

	private Date fecha;
	private Usuario remitente;
	private Usuario invitado;

	public Invitacion(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param invitado
	 * @param remitente
	 */
	public Invitacion(Usuario invitado, Usuario remitente){

	}

	public void eliminar(){

	}

	public boolean estaAprobada(){
		return false;
	}

	public Date getFecha(){
		return null;
	}

	public Usuario getInvitado(){
		return null;
	}

	public Usuario getRemitente(){
		return null;
	}

	public void notificar(){

	}

	public void persistir(){

	}

}