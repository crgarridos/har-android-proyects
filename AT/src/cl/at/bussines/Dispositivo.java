package cl.at.bussines;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import cl.at.util.Util;
import cl.at.view.AlertTsunamiApplication;

public class Dispositivo implements Serializable{
	
	private static final String TAG = Dispositivo.class.getName();
	private Boolean estadoDeRiesgo;
//	private Ciudad ciudad;
	private Usuario usuario;
	private Coordenada posicion;
	private Integer intervalo;
	private LocationManager locManager;
	private Location location;
//	private LocationListener locListener;
	private Context context;
	

	public Dispositivo(Usuario usuario){
		this.estadoDeRiesgo = false;//TODO Calcular la posicion e indicar si hay estado de riesgo
		this.context = AlertTsunamiApplication.getAppContext();
		this.intervalo = Util.getPreferencia("intervalo", context)!= null?Integer.parseInt(Util.getPreferencia("intervalo", context)):5000;//TODO cambiar tiempo default
		this.posicion = new Coordenada();
		setUsuario(usuario);
		this.locManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		actualizarPosicion();
//		locListener = new LocationListener() {
//	    	public void onLocationChanged(Location location) {
//	    		Log.i(TAG, "loc.Lat: "+location.getLatitude()+" - loc.Long:"+location.getLongitude());
//	    		posicion.setLatitud(location.getLatitude());
//	    		posicion.setLongitud(location.getLongitude());
//	    		Log.i(TAG, "c.Lat: "+posicion.getLatitud()+" - c.Lon: "+posicion.getLongitud());
//	    		actualizarPosicion();
//	    	}
//	    	public void onProviderDisabled(String provider){
//	    		Log.i(TAG, "GPS Status: OFF");
//	    	}
//	    	public void onProviderEnabled(String provider){
//	    		Log.i(TAG, "GPS Status: ON");
//	    	}
//	    	public void onStatusChanged(String provider, int status, Bundle extras){
//	    		Log.i(TAG, "Provider Status: " + status);
//	    	}
//    	};
    	
		Log.i(TAG, "Fin del constructor...");
		//actualizar posicion//setPosicion(null);//TODO calcular
	}
	
	//get estado de riesgo
	public Boolean getEstadoDeRiesgo(){
		return this.estadoDeRiesgo;
	}
	
	//get y set usuario
	public Usuario getUsuario(){
		return usuario;
	}
	
	public void setUsuario(Usuario usuario){
		this.usuario = usuario;
	}
	
	//get y set posici�n
	public Coordenada getPosicion(){
		return posicion;
	}
	
	public void setPosicion(Coordenada posicion){
		this.posicion = posicion;
	}

	//get y set intervalo en que se demora en actualizar la posici�n del dispositivo
	public Integer getIntervalo(){
		return intervalo;
	}
	
	public void setIntervalo(Integer intervalo){
		this.intervalo = intervalo;
	}
	
	//destructor
	public void finalize() throws Throwable {

	}
	
	//otros
	public void actualizarPosicion(){
		Double latitud;
		Double longitud;
		try{
			this.location = this.locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			latitud = this.location.getLatitude();
			longitud = this.location.getLongitude();
		}catch(Exception e){
			Log.e(TAG, e.toString());
			//TODO obtener ultima posicion desde la base de datos...
			latitud = -20.213839 * 1E6; longitud = -70.152500 * 1E6;
			}
		this.posicion.setLatitud(latitud);
		this.posicion.setLongitud(longitud);
		if(!estaSeguro()){
			
		}
	}

	public Boolean estaSeguro(){
		return estadoDeRiesgo;
		//podria tener un setter para que la ciudad lo haga cambiar de estado, asi evitarnos la relacion.
								// creo que para eso estaba :S, la relacion...
//		if(this.posicion == this.ciudad.getPuntoSeguridad().coordenada){
//			return true;
//		}
//		else
//			return false;
	}

	public void inicializar(LocationListener locListener) {
		 this.locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, this.intervalo, 0, locListener);
	}
}