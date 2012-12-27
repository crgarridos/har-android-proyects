package cl.at.bussines;

import cl.at.data.PuntoEncuentroSQL;

public class PuntoEncuentro extends Punto {

	private GrupoFamiliar grupoFamiliar;
	private String referencia;

	public PuntoEncuentro() {

	}

	public PuntoEncuentro(Coordenada c) {
		super(c);
		// TODO notificar
	}

	public PuntoEncuentro(GrupoFamiliar grupoFamiliar) {
		this.grupoFamiliar = grupoFamiliar;
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

	public void persistir() throws Exception {
		PuntoEncuentroSQL puntoEncuentroSQL = new PuntoEncuentroSQL(); 
		if (!puntoEncuentroSQL.persistir(this))
			throw new Exception("Error");
	}
	
	public void setGrupoFamiliar(GrupoFamiliar grupoFamiliar) {
		this.grupoFamiliar = grupoFamiliar;
	}

}