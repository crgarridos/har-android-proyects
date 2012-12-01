package unap.icci.alerttsunami.bll;

public class Dispositivo {

	private Boolean estadoDeRiesgo;
	public Ciudad ciudad;
	public Usuario usuario;
	public Coordenada coordenada;

	public Dispositivo(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param c
	 */
	public void actualizarPosicion(Coordenada c){

	}

	public Boolean estaEnRiego(){
		return false;
	}

	public int getIntervaloActualizacion(){
		return 0;
	}

	public Coordenada getPosicion(){
		return null;
	}

	/**
	 * 
	 * @param enRiesgo
	 */
	public void setEstadoRiego(boolean enRiesgo){

	}

	/**
	 * 
	 * @param intervalo
	 */
	public void setIntervaloActualizacion(int intervalo){

	}

	/**
	 * 
	 * @param c
	 */
	private void setPosicion(Coordenada c){

	}

}