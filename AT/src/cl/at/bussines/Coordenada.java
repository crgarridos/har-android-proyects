package cl.at.bussines;

public class Coordenada {

	private double latitud;
	private double longitud;

	//constructor
	public Coordenada(double d, double e){
		setLatitud(d);
		setLongitud(e);
	}


	//get y set latitud
	public double getLatitud(){
		return latitud;
	}
	
	public void setLatitud(double latitud){
		this.latitud = latitud;
	}
	
	//get y set longitud
	public double getLongitud(){
		return longitud;
	}
	
	public void setLongitud(double longitud){
		this.longitud = longitud;
	}
	
	//destructor
	public void finalize() throws Throwable {

	}	

}