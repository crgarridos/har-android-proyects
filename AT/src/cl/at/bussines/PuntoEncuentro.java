package cl.at.bussines;

public class PuntoEncuentro extends Punto {

	private GrupoFamiliar grupoFamiliar;
	private String referencia;

	public PuntoEncuentro() {

	}
	
	public PuntoEncuentro(Coordenada c){
		super(c);
		//TODO notificar
	}
	public PuntoEncuentro(GrupoFamiliar grupoFamiliar) {
		this.grupoFamiliar=grupoFamiliar;
	}
	public void finalize() throws Throwable {
		super.finalize();
		System.out.println();
	}

	public GrupoFamiliar getGrupoFamiliar() {
		return grupoFamiliar;
	}
	
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;

	}

}