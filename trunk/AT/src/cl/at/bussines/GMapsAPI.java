package cl.at.bussines;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import cl.at.data.CiudadSQL;
import cl.at.data.PuntoSQL;
import cl.at.view.MarkItemizedOverlay;
import cl.at.view.R;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class GMapsAPI{

	private static final String TAG = GMapsAPI.class.getName();
	private Float zoom;
	private GeoPoint centro;
	private transient MapView mapView;
	private transient MapController mapController;
	private List<Overlay> mapOverlays;
	private transient MyLocationOverlay mOverlayLocation;
//	public GMapsAPI(){
//
//	}
	
	public GMapsAPI(MapView m, Coordenada centro, float zoom){
		this.mapView = m;
//		this.mapView.setBuiltInZoomControls(true);
		this.centro = new GeoPoint((int)(centro.getLatitud()*1E6), (int)(centro.getLongitud()*1E6));
		this.zoom = zoom;
		this.mapController = mapView.getController();
		this.mapOverlays = null;
		
//		mapController.setCenter(this.centro);
		mapController.setZoom(this.zoom.intValue());
		
	}

	public GMapsAPI(MapView m, Coordenada centro){
		this(m,centro,15);
	}

	public GMapsAPI(MapView mapView) {
		//TODO determinarCiudad();
		this(mapView,new Coordenada(0.0, 0.0),15);
	}

	public void setZoom(Float zoom) {
		this.zoom = zoom;
	}
	
	public Float getZoom() {
		return zoom;
	}

	public void setCentro(Coordenada centro) {
		this.centro = new GeoPoint((int)(centro.getLatitud()*1E6),(int) (centro.getLongitud()*1E6));
		this.mapController.setCenter(this.centro);
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

	public void determinarCiudad(Ciudad ciudad) throws IOException{
		String nombreCiudad = null;
		Geocoder geoCoder = new Geocoder(this.mapView.getContext(), Locale.getDefault());
		List<Address> direccion = geoCoder.getFromLocation(ciudad.getDispositivo().getPosicion().getLatitud(), ciudad.getDispositivo().getPosicion().getLongitud(), 1);
		if(direccion.size() > 0)
			nombreCiudad = direccion.get(0).getLocality();
		CiudadSQL cSQL = new CiudadSQL();
		cSQL.cargarCiudad(ciudad, nombreCiudad);
		ciudad.setAreaInundacion(new PuntoSQL().cargarAreaInundacion(ciudad));
		dibujarPolilinea(ciudad.getAreaInundacion());
//		for(int i = 0; i < ciudad.getAreaInundacion().size(); i++){
//			dibujarPunto(ciudad.getAreaInundacion().get(i), 1);
//		}
		
		//TODO obtener ciudad desde la BD
//		return getCentro();
	}

	public void desplegarMapa(Coordenada coordenada){
		this.setCentro(coordenada);
		this.dibujarPosicion(coordenada, Punto.RADIO);
	}

	public void dibujarPoligono(ArrayList<Coordenada> dots){

	}

	public void dibujarPolilinea(ArrayList<Coordenada> areaInundacion){
//		Polyline polilinea = new Polyline();
	}

	public void dibujarPosicion(Coordenada c, float radio){

	}

//	public void dibujarPunto(Coordenada c, int color){
////		setCentro(c);
//		GeoPoint posicion = new GeoPoint((int)(c.getLatitud()*1E6), (int)(c.getLongitud()*1E6));
//		this.mapOverlays = mapView.getOverlays();
//		for(int i = 1; i < this.mapOverlays.size(); i++){
//			this.mapOverlays.remove(i);
//		}
//		Drawable drawable = mapView.getContext().getResources().getDrawable(color == 1? R.drawable.icono_persona_segura: R.drawable.icono_persona_riesgo);
//		MarkItemizedOverlay itemizedoverlay = new MarkItemizedOverlay(drawable, mapView.getContext());
//		OverlayItem overlayItem = new OverlayItem(posicion, , "Posicion: "+c.getLatitud().toString()+" / "+c.getLongitud().toString());
//		Log.i(TAG, "dibujo: "+posicion.getLatitudeE6()*1E6+" - "+posicion.getLongitudeE6()*1E6);
//		itemizedoverlay.addOverlay(overlayItem);
//		mapOverlays.add(itemizedoverlay);
//		this.mapView.postInvalidate();
//	}
	
	public void dibujarPunto(Usuario usuario){
//		setCentro(c);
		Coordenada coordenada = new Coordenada(usuario.getDispositivo().getPosicion().getLatitud(), usuario.getDispositivo().getPosicion().getLongitud());
		GeoPoint posicion = new GeoPoint((int)(coordenada.getLatitud()*1E6), (int)(coordenada.getLongitud()*1E6));
		Drawable drawable = mapView.getContext().getResources().getDrawable(usuario.getDispositivo().estaSeguro()? R.drawable.icono_persona_segura: R.drawable.icono_persona_riesgo);
		MarkItemizedOverlay itemizedoverlay = new MarkItemizedOverlay(drawable, mapView.getContext());
		OverlayItem overlayItem = new OverlayItem(posicion, usuario.getNombreUsuario(), "Posicion: "+coordenada.getLatitud().toString()+" / "+coordenada.getLongitud().toString());
		Log.i(TAG, "integrante: "+usuario.getNombreUsuario()+posicion.getLatitudeE6()*1E6+" - "+posicion.getLongitudeE6()*1E6);
		itemizedoverlay.addOverlay(overlayItem);
		mapOverlays.add(itemizedoverlay);
		this.mapView.postInvalidate();
	}
	
	public void borrarPuntos(){
		this.mapOverlays = mapView.getOverlays();
		for(int i = 1; i < this.mapOverlays.size(); i++){
			if(mapOverlays.get(i).getClass()==MarkItemizedOverlay.class)
				this.mapOverlays.remove(i);
		}
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