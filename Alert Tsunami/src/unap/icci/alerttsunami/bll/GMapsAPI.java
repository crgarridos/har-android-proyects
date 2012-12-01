package unap.icci.alerttsunami.bll;

import java.util.ArrayList;

public class GMapsAPI {

	private Float zoom;
	private Coordenada centro;

	public GMapsAPI(){

	}
	
	public GMapsAPI(Coordenada centro, float zoom){

	}

	public GMapsAPI(Coordenada centro){

	}

	public void setZoom(Float zoom) {
		this.zoom = zoom;
	}
	
	public Float getZoom() {
		return zoom;
	}

	public void setCentro(Coordenada centro) {
		this.centro = centro;
	}
	
	public Coordenada getCentro() {
		return centro;
	}

	public void finalize() throws Throwable {

	}

	public boolean compararPunto(Coordenada origen, Punto destino){
		return false;
	}

	public Coordenada derminarCiudad(Coordenada c){
		return null;
	}

	public void desplegarMapa(){

	}

	public void dibujarPoligono(ArrayList<Coordenada> dots){

	}

	public void dibujarPolilinea(ArrayList<Coordenada> dots){

	}

	public void dibujarPosicion(Coordenada c, float radio){

	}

	public void dibujarPunto(Coordenada c, int color){

	}

	public void dibujarPunto(Coordenada c, String titulo, int color){

	}

	public boolean estaEnElMedio(Coordenada c, ArrayList<Coordenada> zona){
		return false;
	}

	public Coordenada getCoordenadaMasCercana(Coordenada origen, ArrayList<Coordenada> destinos){
		return null;
	}


}