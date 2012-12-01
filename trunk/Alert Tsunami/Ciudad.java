public class Ciudad {

	public Dispositivo dispositivo;
	public Coordenada coordenada;
	public PuntoSeguridad puntoSeguridad;
	public AreaInundacion areaInundacion;
	public ArrayList<PuntoRiesgo> puntosRiesgo;
	public PuntoEnuentro puntoEnuentro;
	public PuntoEncuentro puntoEncuentro;
	public GMapsAPI gMapsAPI;

	public Ciudad(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param c
	 */
	public PuntoSeguridad determinarPuntoSeguridad(Coordenada c){
		return null;
	}

	public ArrayList<Coordenadas> getAreaInundacion(){
		return null;
	}

	public Dispositivo getDispositivo(){
		return null;
	}

	public ArrayList<Coordenadas> getLineaSeguridad(){
		return null;
	}

	public PuntoEncuentro getPuntoEncuentro(){
		return null;
	}

	public ArrayList<PuntoRiesgo> getPuntosDeRiesgo(){
		return null;
	}

}