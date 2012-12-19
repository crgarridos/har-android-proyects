package cl.at.bussines;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import cl.at.util.HelloItemizedOverlay;
import cl.at.view.R;

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
	private List<Overlay> mapOverlays;

//	public GMapsAPI(){
//
//	}
//	
	public GMapsAPI(MapView m, Coordenada centro, float zoom){
		this.mapView = m;
//		this.mapView.setBuiltInZoomControls(true);
		Log.i(TAG, "centro: "+Double.toString(centro.getLatitud())+" - "+Double.toString(centro.getLongitud()));
		this.centro = new GeoPoint((int)(centro.getLatitud()*1E6), (int)(centro.getLongitud()*1E6));
		this.zoom = zoom;
		this.mapController = mapView.getController();
		this.mapOverlays = null;
		mapController.setCenter(this.centro);
		mapController.setZoom(this.zoom.intValue());
	}

	public GMapsAPI(MapView m, Coordenada centro){
		this(m,centro,15);
	}

	public GMapsAPI(MapView mapView) {
		this(mapView,new Coordenada(0.0, 0.0),15);
	}

	public void setZoom(Float zoom) {
		this.zoom = zoom;
	}
	
	public Float getZoom() {
		return zoom;
	}

	public void setCentro(Coordenada centro) {
		Log.i(TAG, "Centro: "+centro.getLatitud()+" - "+centro.getLongitud());
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

	public void determinarCiudad(){
		//TODO obtener ciudad desde la BD
//		return getCentro();
	}

	public void desplegarMapa(Coordenada ciudadCercana){
		this.dibujarPosicion(ciudadCercana, Punto.RADIO);
	}

	public void dibujarPoligono(ArrayList<Coordenada> dots){

	}

	public void dibujarPolilinea(ArrayList<Coordenada> dots){

	}

	public void dibujarPosicion(Coordenada c, float radio){
		setCentro(c);
		this.mapOverlays = mapView.getOverlays();
		Drawable drawable = mapView.getContext().getResources().getDrawable(R.drawable.icono_persona);
		HelloItemizedOverlay itemizedoverlay = new HelloItemizedOverlay(drawable, mapView.getContext());
		
		OverlayItem overlayitem = new OverlayItem(centro, "Iquique","hola mundo!");
		Log.i(TAG, "dibujo: "+centro.getLatitudeE6()+" - "+centro.getLongitudeE6());
		itemizedoverlay.addOverlay(overlayitem);
		mapOverlays.add(itemizedoverlay);
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