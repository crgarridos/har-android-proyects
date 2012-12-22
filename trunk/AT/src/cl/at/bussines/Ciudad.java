package cl.at.bussines;

import java.util.ArrayList;

import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import cl.at.data.DispositivoSQL;

import com.google.android.maps.MapView;

public class Ciudad {

	private static final String TAG = Ciudad.class.getName();
	private Dispositivo dispositivo;
	private Coordenada coordenada;
	private Punto puntoSeguridad;
	private ArrayList<Coordenada> areaInundacion;
	private ArrayList<PuntoRiesgo> puntosRiesgo;
	private PuntoEncuentro puntoEncuentro;
	private GMapsAPI gMapsAPI;
	private LocationListener locListener;

	//constructor
//	public Ciudad(){
//		areaInundacion = new ArrayList<Coordenada>();
//		puntosRiesgo = new ArrayList<PuntoRiesgo>();
//	}
	
	public Ciudad(MapView mapView, Dispositivo disp, PuntoEncuentro ptoEncuentro) {
		dispositivo = disp;
		puntoSeguridad = new Punto();//TODO Calcular punto seguridad???
		areaInundacion = new ArrayList<Coordenada>();//cargar desde la bd
		puntosRiesgo = new ArrayList<PuntoRiesgo>();//cargar desde la bd
		puntoEncuentro = ptoEncuentro;
		gMapsAPI = new GMapsAPI(mapView);
//		gMapsAPI.setCentro(disp.getPosicion());
		locListener = new LocationListener() {
	    	public void onLocationChanged(Location location) {
	    		dispositivo.getPosicion().setLatitud(location.getLatitude());
	    		dispositivo.getPosicion().setLongitud(location.getLongitude());
	    		Log.i(TAG, "Localización: "+dispositivo.getPosicion().getLatitud()+" - "+dispositivo.getPosicion().getLongitud());
	    		new AsyncMapa().execute();
	    	}
	    	public void onProviderDisabled(String provider){
	    		Log.i(TAG, "GPS Status: OFF");
	    	}
	    	public void onProviderEnabled(String provider){
	    		Log.i(TAG, "GPS Status: ON");
	    	}
	    	public void onStatusChanged(String provider, int status, Bundle extras){
	    		Log.i(TAG, "Provider Status: " + status);
	    	}
    	};
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
	
	//get y set Area de inundaciï¿½n
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
	
//	//get y set GMapsAPI
//	public GMapsAPI getgMapsAPI() {
//		return gMapsAPI;
//	}
//
//	public void setgMapsAPI(GMapsAPI gMapsAPI) {
//		this.gMapsAPI = gMapsAPI;
//	}
//
//	//Destructor?
//	public void finalize() throws Throwable {
//
//	}

	//Otros
	public Punto determinarPuntoSeguridad(Coordenada c){
		return puntoSeguridad;
	}


	public void obtenerCiudad() {
		Coordenada c = dispositivo.getPosicion();
		gMapsAPI.determinarCiudad();
		gMapsAPI.desplegarMapa(c);
	}

	public LocationListener getLocationListener() {
		return this.locListener;
	}
	
	class AsyncMapa extends AsyncTask<String, String , String>{

		@Override
		protected String doInBackground(String... params) {
			dispositivo.actualizarPosicion();
			int i = 1;
			while(i < dispositivo.getUsuario().getGrupoFamiliar().getIntegrantes().size()){
				gMapsAPI.dibujarPunto(dispositivo.getUsuario().getGrupoFamiliar().getIntegrantes().get(i).getDispositivo().getPosicion(), 1);
				i++;
			}
    		DispositivoSQL dSQL = new DispositivoSQL();
    		dSQL.actualizarPosicion(dispositivo);
			return null;
		}
		
	}
}