package unap.icci.alerttsunami.bll;

public class Lider extends Usuario {

	public Lider(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	public Lider(Usuario u){
		super(u.getNombreCompleto(),
				u.getNombreUsuario(),
				u.getPassword(),
				u.getEmail());
	}

	
	
	public void abandonarGrupoFamiliar(){
	}

	public void definirPuntoEncuentro(){

	}

	public void persistir(){

	}

}