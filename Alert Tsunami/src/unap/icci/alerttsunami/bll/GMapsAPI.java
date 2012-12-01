package unap.icci.alerttsunami.bll;

import java.util.ArrayList;

public class GMapsAPI {

	private Float zoom;
	public Coordenada coordenada;

	public GMapsAPI(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param centro
	 * @param zoom
	 */
	public GMapsAPI(Coordenada centro, float zoom){

	}

	/**
	 * 
	 * @param centro
	 */
	public GMapsAPI(Coordenada centro){

	}

	/**
	 * 
	 * @param origen
	 * @param destino
	 */
	public boolean compararPunto(Coordenada origen, Punto destino){
		return false;
	}

	/**
	 * 
	 * @param c
	 */
	public Coordenada derminarCiudad(Coordenada c){
		return null;
	}

	public void desplegarMapa(){

	}

	/**
	 * 
	 * @param dots
	 */
	public void dibujarPoligono(ArrayList<Coordenada> dots){

	}

	/**
	 * 
	 * @param dots
	 */
	public void dibujarPolilinea(ArrayList<Coordenada> dots){

	}

	/**
	 * 
	 * @param c
	 * @param radio
	 */
	public void dibujarPosicion(Coordenada c, float radio){

	}

	/**
	 * 
	 * @param c
	 * @param color
	 */
	public void dibujarPunto(Coordenada c, int color){

	}

	/**
	 * 
	 * @param c
	 * @param titulo
	 * @param color
	 */
	public void dibujarPunto(Coordenada c, String titulo, int color){

	}

	/**
	 * 
	 * @param c
	 * @param zona
	 */
	public boolean estaEnElMedio(Coordenada c, ArrayList<Coordenada> zona){
		return false;
	}

	/**
	 * 
	 * @param origen
	 * @param destinos
	 */
	public Coordenada getCoordenadaMasCercana(Coordenada origen, ArrayList<Coordenada> destinos){
		return null;
	}

	/**
	 * 
	 * @param c
	 */
	public void setCenter(Coordenada c){

	}

	/**
	 * 
	 * @param z
	 */
	public void setZoom(float z){

	}

}