package Negocio;

import java.util.ArrayList;

public class Ciudad {

	private Dispositivo dispositivo;
	private Coordenada coordenada;
	private Punto puntoSeguridad;
	private ArrayList<Coordenada> areaInundacion;
	private ArrayList<PuntoRiesgo> puntosRiesgo;
	private PuntoEncuentro puntoEncuentro;
	private GMapsAPI gMapsAPI;

	//constructor
	public Ciudad(){
		areaInundacion = new ArrayList<Coordenada>();
		puntosRiesgo = new ArrayList<PuntoRiesgo>();
	}
	
	
	//get y set Dispositivo
	public Dispositivo getDispositivo(){
		return dispositivo;
	}
	
	public void setDispositivo(Dispositivo dispositivo) {
		this.dispositivo = dispositivo;
	}
	
	//get y set Coordenada
	public Coordenada getCoordenada() {
		return coordenada;
	}

	public void setCoordenada(Coordenada coordenada) {
		this.coordenada = coordenada;
	}
	
	//get y set Punto de seguridad
	public Punto getPuntoSeguridad() {
		return puntoSeguridad;
	}
	
	public void setPuntoSeguridad(Punto puntoSeguridad) {
		this.puntoSeguridad = puntoSeguridad;
	}
	
	//get y set Area de inundaci�n
	public ArrayList<Coordenada> getAreaInundacion(){
		return areaInundacion;
	}
	
	public void setAreaInundacion(ArrayList<Coordenada> areaInundacion) {
		this.areaInundacion = areaInundacion;
	}
	
	//get y set Puntos de riesgo
	public ArrayList<PuntoRiesgo> getPuntosRiesgo() {
		return puntosRiesgo;
	}

	public void setPuntosRiesgo(ArrayList<PuntoRiesgo> puntosRiesgo) {
		this.puntosRiesgo = puntosRiesgo;
	}
	
	//get y set Punto de encuentro
	public PuntoEncuentro getPuntoEncuentro(){
		return puntoEncuentro;
	}

	public void setPuntoEncuentro(PuntoEncuentro puntoEncuentro) {
		this.puntoEncuentro = puntoEncuentro;
	}
	
	//get y set GMapsAPI
	public GMapsAPI getgMapsAPI() {
		return gMapsAPI;
	}

	public void setgMapsAPI(GMapsAPI gMapsAPI) {
		this.gMapsAPI = gMapsAPI;
	}

	//Destructor?
	public void finalize() throws Throwable {

	}

	//Otros
	public Punto determinarPuntoSeguridad(Coordenada c){
		return puntoSeguridad;
	}
	
}