package cl.at.bussines;

public class PuntoEncuentro extends Punto {

	public GrupoFamiliar grupoFamiliar;

	public PuntoEncuentro(){
		
	}

	public void finalize() throws Throwable {
		super.finalize();
		System.out.println();
	}

	public GrupoFamiliar getGrupoFamiliar(){
		return grupoFamiliar;
	}
	
	public void setGrupoFamiliar(GrupoFamiliar grupoFamiliar){
		this.grupoFamiliar = grupoFamiliar;
	}

}