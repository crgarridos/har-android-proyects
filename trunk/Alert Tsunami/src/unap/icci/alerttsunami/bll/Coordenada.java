package unap.icci.alerttsunami.bll;

public class Coordenada {

	private float latitud;
	private float longitud;

	//constructor
	public Coordenada(float latitud, float longitud){
		setLatitud(latitud);
		setLongitud(longitud);
	}

	//get y set latitud
	public Float getLatitud(){
		return latitud;
	}
	
	public void setLatitud(float latitud){
		this.latitud = latitud;
	}
	
	//get y set longitud
	public Float getLongitud(){
		return longitud;
	}
	
	public void setLongitud(float longitud){
		this.longitud = longitud;
	}
	
	//destructor
	public void finalize() throws Throwable {

	}	

}