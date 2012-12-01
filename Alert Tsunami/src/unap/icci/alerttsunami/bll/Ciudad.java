package unap.icci.alerttsunami.bll;

import java.util.ArrayList;

public class Ciudad {

	public Dispositivo dispositivo;
	public Coordenada coordenada;
	public Punto puntoSeguridad;
	public ArrayList<Coordenada> areaInundacion;
	public ArrayList<PuntoRiesgo> puntosRiesgo;
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
	public Punto determinarPuntoSeguridad(Coordenada c){
		return null;
	}

	public ArrayList<Coordenada> getAreaInundacion(){
		return null;
	}

	public Dispositivo getDispositivo(){
		return null;
	}

	public ArrayList<Coordenada> getLineaSeguridad(){
		return null;
	}

	public PuntoEncuentro getPuntoEncuentro(){
		return null;
	}

	public ArrayList<PuntoRiesgo> getPuntosDeRiesgo(){
		return null;
	}

}