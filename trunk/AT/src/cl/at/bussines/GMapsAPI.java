package cl.at.bussines;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;
import cl.at.util.HelloItemizedOverlay;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class GMapsAPI {

	private Float zoom;
	private GeoPoint centro;
	private MapView mapView;
	private MapController mapController;

//	public GMapsAPI(){
//
//	}
//	
	public GMapsAPI(MapView m, Coordenada centro, float zoom){
		this.mapView = m;
		this.mapView.setBuiltInZoomControls(true);
		this.centro = new GeoPoint((int)(centro.getLatitud()*1E6),(int) (centro.getLongitud()*1E6));
		this.zoom = zoom;
		this.mapController = mapView.getController();
		mapController.setCenter(this.centro);
		mapController.setZoom(this.zoom.intValue());
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
		this.centro = new GeoPoint((int)(centro.getLatitud()*1E6),(int) (centro.getLongitud()*1E6));
		mapController.setCenter(this.centro);

	}
	
	public Coordenada getCentro() {
		Coordenada c = new Coordenada((double) centro.getLatitudeE6()/1E6,(double) centro.getLatitudeE6()/1E6);
		return c;
	}

	public void finalize() throws Throwable {

	}

	public boolean compararPunto(Coordenada origen, Punto destino){
		return false;
	}

	public Coordenada determinarCiudad(Coordenada c){
		return new Coordenada(-20.213839,-70.152500);
	}

	public void desplegarMapa(Coordenada ciudadCercana){
		setCentro(ciudadCercana);
		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = mapView.getContext().getResources().getDrawable(
				android.R.drawable.btn_star);
		HelloItemizedOverlay itemizedoverlay = new HelloItemizedOverlay(
				drawable, mapView.getContext());
		// GeoPoint point = new GeoPoint(19240000,-99120000);
		OverlayItem overlayitem = new OverlayItem(centro, "Iquique",
				"hola mundo!");
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