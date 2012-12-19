package cl.at.bussines;

import java.io.Serializable;

public class Punto implements Serializable{

	public static final float RADIO = 10;
	public Coordenada coordenada;

	public Punto(){
	}
	
	public Punto(Coordenada coordenada){
		this.setCoordenada(coordenada);
	}

	public void finalize() throws Throwable {
	}

	public Coordenada getCoordenada(){
		return coordenada;
	}

	public void setCoordenada(Coordenada coordenada) {
		this.coordenada = coordenada;
	}
}