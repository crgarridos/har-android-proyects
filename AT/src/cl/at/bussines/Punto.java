package cl.at.bussines;


public class Punto{

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