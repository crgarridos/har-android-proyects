package cl.at.bussines;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import cl.at.util.HelloItemizedOverlay;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class GMapsAPI {

	private static final String TAG = GMapsAPI.class.getName();
	private Float zoom;
	private GeoPoint centro;
	private MapView mapView;
	private MapController mapController;
	private LocationManager locManager;
	private LocationListener locListener;
	private Location location;

//	public GMapsAPI(){
//
//	}
//	
	public GMapsAPI(MapView m, Coordenada centro, float zoom){
		this.mapView = m;
		this.mapView.setBuiltInZoomControls(true);
		Double latitud;
		Double longitud;
		this.locManager = (LocationManager)m.getContext().getSystemService(Context.LOCATION_SERVICE);
		try{
			this.location = this.locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			latitud = this.location.getLatitude()*1E6;
			longitud = this.location.getLongitude()*1E6;
		}catch(Exception e){
			Log.e(TAG, e.toString());
			//TODO obtener ultima posicion desde la base de datos...
			latitud = -20.213839 * 1E6; longitud = -70.152500 * 1E6;
			}
		this.centro = new GeoPoint(latitud.intValue(), longitud.intValue());
		this.zoom = zoom;
		this.mapController = mapView.getController();
		mapController.setCenter(this.centro);
		mapController.setZoom(this.zoom.intValue());
		locListener = new LocationListener() {
	    	public void onLocationChanged(Location location) {
	    		mapView.getOverlays().clear();
	    		Log.i(TAG, "loc.Lat: "+location.getLatitude()+" - loc.Long:"+location.getLongitude());
	    		Coordenada c = new Coordenada(location.getLatitude(), location.getLongitude());
	    		Log.i(TAG, "c.Lat: "+c.getLatitud()+" - c.Lon: "+c.getLongitud());
	    		desplegarMapa(c);
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
    	this.locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this.locListener);
	}

	public GMapsAPI(MapView m, Coordenada centro){
		this(m,centro,15);
	}

	public GMapsAPI(MapView mapView) {
		this(mapView,new Coordenada(0, 0),15);
	}

	public void setZoom(Float zoom) {
		this.zoom = zoom;
	}
	
	public Float getZoom() {
		return zoom;
	}

	public void setCentro(Coordenada centro) {
		Log.i(TAG, "Lat Loc: "+centro.getLatitud()*1E6+" - Lon Loc: "+centro.getLongitud()*1E6);
		this.centro = new GeoPoint((int)(centro.getLatitud()*1E6),(int) (centro.getLongitud()*1E6));
		mapController.setCenter(this.centro);
	}
	
	public Coordenada getCentro() {
		Coordenada c= new Coordenada((double) centro.getLatitudeE6()/1E6,(double) centro.getLongitudeE6()/1E6);
		return c;
	}

	public void finalize() throws Throwable {

	}

	public boolean compararPunto(Coordenada origen, Punto destino){
		return false;
	}

	public Coordenada determinarCiudad(Coordenada c){
		//TODO obtener ciudad desde la BD
		return getCentro();
	}

	public void desplegarMapa(Coordenada ciudadCercana){
		setCentro(ciudadCercana);
		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = mapView.getContext().getResources().getDrawable(android.R.drawable.arrow_down_float);
		HelloItemizedOverlay itemizedoverlay = new HelloItemizedOverlay(drawable, mapView.getContext());
		OverlayItem overlayitem = new OverlayItem(centro, "Iquique","hola mundo!");
		itemizedoverlay.addOverlay(overlayitem);
		mapOverlays.add(itemizedoverlay);
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

	public void setMapView(MapView mapView) {
		this.mapView = mapView;
	}


}