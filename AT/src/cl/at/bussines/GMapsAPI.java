package cl.at.bussines;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;
import cl.at.data.CiudadSQL;
import cl.at.data.PuntoSQL;
import cl.at.view.MarkItemizedOverlay;
import cl.at.view.R;
import cl.at.view.RouteSegmentOverlay;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;

public class GMapsAPI {

	private static final String TAG = GMapsAPI.class.getName();
	private Float zoom;
	private GeoPoint centro;
	private MapView mapView;
	private MapController mapController;
	private List<Overlay> mapOverlays;
	private boolean dibujando = false;
	private RouteSegmentOverlay route;

	public GMapsAPI(MapView m, Coordenada centro, float zoom) {
		this.mapView = m;
		// this.mapView.setBuiltInZoomControls(true);
		this.centro = new GeoPoint((int) (centro.getLatitud() * 1E6), (int) (centro.getLongitud() * 1E6));
		this.zoom = zoom;
		this.mapController = mapView.getController();
		this.mapOverlays = null;

		// mapController.setCenter(this.centro);
		mapController.setZoom(this.zoom.intValue());

	}

	public GMapsAPI(MapView m, Coordenada centro) {
		this(m, centro, 15);
	}

	public GMapsAPI(MapView mapView) {
		// TODO determinarCiudad();
		this(mapView, new Coordenada(0.0, 0.0), 15);
	}

	public void setZoom(Float zoom) {
		this.zoom = zoom;
	}

	public Float getZoom() {
		return zoom;
	}

	public void setCentro(Coordenada centro) {
		this.centro = new GeoPoint((int) (centro.getLatitud() * 1E6), (int) (centro.getLongitud() * 1E6));
		this.mapController.setCenter(this.centro);
	}

	public Coordenada getCentro() {
		Coordenada c = new Coordenada((double) centro.getLatitudeE6() / 1E6, (double) centro.getLongitudeE6() / 1E6);
		return c;
	}

	public void finalize() throws Throwable {

	}

	
	public Float compararPunto(Coordenada origen, Coordenada destino) {
		Location l = new Location("mi punto");
		l.setLatitude(origen.getLatitud());
		l.setLongitude(origen.getLongitud());
		Location ld = new Location("punto de encuentro");
		ld.setLatitude(destino.getLatitud());
		ld.setLongitude(destino.getLongitud());
		Log.e(TAG, " "+ l.distanceTo(ld));
//		if (l.distanceTo(ld) <= 50000)
//			return true;
		return l.distanceTo(ld);
	}

	public void determinarCiudad(Ciudad ciudad) {
		String nombreCiudad = null;
		Geocoder geoCoder = new Geocoder(this.mapView.getContext(), Locale.getDefault());
		try {
			List<Address> direccion = geoCoder.getFromLocation(ciudad.getDispositivo().getPosicion().getLatitud(), ciudad.getDispositivo().getPosicion().getLongitud(), 1);
			if (direccion.size() > 0)
				nombreCiudad = direccion.get(0).getLocality();
		} catch (IOException e) {
			nombreCiudad = new CiudadSQL().getNombre(ciudad.getDispositivo().getPosicion().getLatitud(), ciudad.getDispositivo().getPosicion().getLongitud());
		}
		CiudadSQL cSQL = new CiudadSQL();
		cSQL.cargarCiudad(ciudad, nombreCiudad);
		ciudad.setAreaInundacion(new PuntoSQL().cargarAreaInundacion(ciudad));
		dibujarPolilinea(ciudad.getAreaInundacion());
		GrupoFamiliar grupoFamiliar = ciudad.getDispositivo().getUsuario().getGrupoFamiliar();
		if (grupoFamiliar != null) {
			ciudad.setPuntoEncuentro(grupoFamiliar.getPuntoEncuentro());
			dibujarPunto(ciudad.getPuntoEncuentro());
		}
		this.mapView.postInvalidate();
	}

	public void desplegarMapa(Coordenada coordenada) {
		this.setCentro(coordenada);
		this.dibujarPosicion(coordenada, Punto.RADIO);
	}

	public void dibujarPoligono(ArrayList<Coordenada> dots) {

	}

	public void dibujarPolilinea(ArrayList<Coordenada> area) {
		boolean routeIsDisplayed = false;
		int numberRoutePoints;
		GeoPoint routePoints[];
		int routeGrade[];
		if (!routeIsDisplayed) {
			routeIsDisplayed = true;
		} else {
			if (route != null)
				route.setRouteView(false);
			route = null;
			routeIsDisplayed = false;
			mapView.postInvalidate();
		}
		int pointCounter = -1;
		int lat = -1;
		int lon = -1;
		int grade = -1;
		numberRoutePoints = area.size() - 2;//se le restan los extrenmos del area de inundacion
		routePoints = new GeoPoint[numberRoutePoints];
		routeGrade = new int[numberRoutePoints];
		for (int i = 1; i < area.size() - 1; i++) {
			String latStg = area.get(i).getLatitud().toString().replace(".", "") + "000";
			String lonStg = area.get(i).getLongitud().toString().replace(".", "") + "000";
			lat = Integer.valueOf(latStg.substring(0, 9));
			lon = Integer.valueOf(lonStg.substring(0, 9));
			grade = 1;
			pointCounter++;
			routePoints[pointCounter] = new GeoPoint(lat, lon);
			routeGrade[pointCounter] = grade;
			// Log.i(TAG, "   trackpoint=" + pointCounter + " latitude=" + lat +
			// " longitude=" + lon + " grade=" + grade);
		}
		if (route != null)
			return;
		route = new RouteSegmentOverlay(routePoints, routeGrade);
		mapOverlays = mapView.getOverlays();
		mapOverlays.add(route);
	}

	public void dibujarPosicion(Coordenada c, float radio) {

	}

	public void dibujarPunto(ArrayList<PuntoRiesgo> puntosRiesgo) {
		try {
			ArrayList<PuntoRiesgo> puntosRiesgoAlto = new ArrayList<PuntoRiesgo>();
			ArrayList<PuntoRiesgo> puntosRiesgoMedio = new ArrayList<PuntoRiesgo>();
			ArrayList<PuntoRiesgo> puntosRiesgoBajo = new ArrayList<PuntoRiesgo>();
			
			for(int i = 0; i < puntosRiesgo.size(); i++){
				if(puntosRiesgo.get(i).getCategoria() == 1)
					puntosRiesgoAlto.add(puntosRiesgo.get(i));
				else if(puntosRiesgo.get(i).getCategoria() == 2)
					puntosRiesgoMedio.add(puntosRiesgo.get(i));
				else puntosRiesgoBajo.add(puntosRiesgo.get(i));
			}
			
			// Dibujamos puntos de riesgo alto
			Drawable drawable = mapView.getContext().getResources().getDrawable(R.drawable.punto_riesgo_alto);
			MarkItemizedOverlay itemizedoverlay = new MarkItemizedOverlay(drawable, mapView.getContext());
			for (int i = 0; i < puntosRiesgoAlto.size(); i++) {
				itemizedoverlay.addPuntoRiesgo(puntosRiesgoAlto.get(i));
			}
			itemizedoverlay.grabar();
			mapView.getOverlays().add(itemizedoverlay);
			
			//Dibujamos puntos de riesgo medio
			drawable = mapView.getContext().getResources().getDrawable(R.drawable.punto_riesgo_medio);
			itemizedoverlay = new MarkItemizedOverlay(drawable, mapView.getContext());
			for (int i = 0; i < puntosRiesgoMedio.size(); i++) {
				itemizedoverlay.addPuntoRiesgo(puntosRiesgoMedio.get(i));
			}
			itemizedoverlay.grabar();
			mapView.getOverlays().add(itemizedoverlay);
			
			//Dibujamos puntos de riesgo bajo
			drawable = mapView.getContext().getResources().getDrawable(R.drawable.punto_riesgo_bajo);
			itemizedoverlay = new MarkItemizedOverlay(drawable, mapView.getContext());
			for (int i = 0; i < puntosRiesgoBajo.size(); i++) {
				itemizedoverlay.addPuntoRiesgo(puntosRiesgoMedio.get(i));
			}
			itemizedoverlay.grabar();
			mapView.getOverlays().add(itemizedoverlay);
			
			
		} catch (Exception e) {
			Log.e(TAG, "dibujarPuntoIntegrantes," + e.toString() + " " + e.getCause());
		}
	}

	public void dibujarPunto(ArrayList<Usuario> integrantes, Usuario usuario) {
		try {
			ArrayList<Usuario> integrantesSeguros = new ArrayList<Usuario>();
			ArrayList<Usuario> integrantesRiesgo = new ArrayList<Usuario>();
			
			//Evitamos que se dibuje el usuario interno
			for(int i = 0; i < integrantes.size(); i++){
				if(!usuario.getNombreUsuario().equalsIgnoreCase(integrantes.get(i).getNombreUsuario())){
					if(integrantes.get(i).getDispositivo().getEstadoDeRiesgo())
						integrantesRiesgo.add(integrantes.get(i));
					else
						integrantesSeguros.add(integrantes.get(i));
				}
			}

			// Dibujamos integrantes en riesgo
			Drawable drawable = mapView.getContext().getResources().getDrawable(R.drawable.icono_persona_riesgo);
			MarkItemizedOverlay itemizedoverlay = new MarkItemizedOverlay(drawable, mapView.getContext());
			for (int i = 0; i < integrantesRiesgo.size(); i++) {
				itemizedoverlay.addIntegrante(integrantesRiesgo.get(i));
			}
			itemizedoverlay.grabar();
			mapView.getOverlays().add(itemizedoverlay);

			// Dibujamos integrantes seguros
			drawable = mapView.getContext().getResources().getDrawable(R.drawable.icono_persona_segura);
			itemizedoverlay = new MarkItemizedOverlay(drawable, mapView.getContext());
			for (int i = 0; i < integrantesSeguros.size(); i++) {
				itemizedoverlay.addIntegrante(integrantesSeguros.get(i));
			}
			itemizedoverlay.grabar();
			mapView.getOverlays().add(itemizedoverlay);
		} catch (Exception e) {
			Log.e(TAG, "dibujarPuntoIntegrantes," + e.toString() + " " + e.getCause());
		}
	}

	public void borrarPuntos(Ciudad ciudad) {
		try {
			this.mapOverlays = mapView.getOverlays();
			List<Overlay> temp = new ArrayList<Overlay>(this.mapOverlays);
			this.mapOverlays.clear();
			for (Overlay ov : temp) {
				if (ov.getClass() == MyLocationOverlay.class || ov.getClass() == RouteSegmentOverlay.class){
					this.mapOverlays.add(ov);
				}				
			}
		} catch (Exception e) {
			Log.e(TAG, "borrarPuntos," + e.toString() + " " + e.getCause());
		}
	}

	public void dibujarPunto(PuntoEncuentro puntoEncuentro) {
		try {
			Drawable drawable = mapView.getContext().getResources().getDrawable(R.drawable.punto_encuentro);
			MarkItemizedOverlay itemizedoverlay = new MarkItemizedOverlay(drawable, mapView.getContext());
			itemizedoverlay.addPuntoEncuentro(puntoEncuentro);
			itemizedoverlay.grabar();
			mapView.getOverlays().add(itemizedoverlay);
		} catch (Exception e) {
			Log.e(TAG, "dibujarPuntoEncuentro," + e.toString() + " " + e.getCause());
		}
	}
	
	public void dibujarPunto(Punto puntoSeguro, float distancia){
		try {
			Drawable drawable = mapView.getContext().getResources().getDrawable(R.drawable.punto_seguro);
			MarkItemizedOverlay itemizedoverlay = new MarkItemizedOverlay(drawable, mapView.getContext());
			itemizedoverlay.addPuntoSeguro(puntoSeguro, distancia);
			itemizedoverlay.grabar();
			mapView.getOverlays().add(itemizedoverlay);
		} catch (Exception e) {
			Log.e(TAG, "dibujarPuntoEncuentro," + e.toString() + " " + e.getCause());
		}
	}

	public boolean estaEnElMedio(Coordenada c, ArrayList<Coordenada> zona) {
		return false;
	}

	public ArrayList<Coordenada> getCoordenadaMasCercana(Coordenada origen, ArrayList<Coordenada> polilinea) {
		
		Float distancia = null;
		Float distanciaPunto = 50000f;
		Coordenada punto1 = null;
		Coordenada punto2 = null;
		Coordenada punto3 = null;
		int indicePunto=0;
		for(int i = 0; i<polilinea.size(); i++){
			distancia = compararPunto(origen, polilinea.get(i));
			if(distancia < distanciaPunto){
				distanciaPunto = distancia;
				punto2 = polilinea.get(i);
				indicePunto = i;
			}
		}
		punto1 = polilinea.get(indicePunto-1);//pto anterior al pto de la cota mas cercano
		punto3 = polilinea.get(indicePunto+1);//pto posterior al pto de la cota mas cercano
		
		//Calculando primer pto seguro
		Double dx = punto2.getLongitud() - punto1.getLongitud();
		Double dy = punto2.getLatitud() - punto1.getLatitud();
		Float dm = (float) (dy / dx);
		Float fm = (-1 / dm);
		Double x = (origen.getLatitud() - fm * origen.getLongitud() + dm * punto1.getLongitud() - punto1.getLatitud()) / (dm - fm);
		Double y = dm * (x - punto1.getLongitud()) + punto1.getLatitud();
		Coordenada puntoSeguro1 = new Coordenada(y, x);
		
		//Calculando segundo pto seguro
		dx = punto3.getLongitud() - punto2.getLongitud();
		dy = punto3.getLatitud() - punto2.getLatitud();
		dm = (float) (dy / dx);
		fm = (-1 / dm);
		x = (origen.getLatitud() - fm * origen.getLongitud() + dm * punto2.getLongitud() - punto2.getLatitud()) / (dm - fm);
		y = dm * (x - punto2.getLongitud()) + punto2.getLatitud();
		Coordenada puntoSeguro2 = new Coordenada(y, x);
		
		//Calculamos el pto seguro mas seguro
		Float distanciaPunto1 = compararPunto(origen, puntoSeguro1);
		Float distanciaPunto2 = compararPunto(origen, puntoSeguro2);
			
		
		if(distanciaPunto1 < distanciaPunto2 && 
				puntoSeguro1.getLongitud() < punto1.getLongitud() && puntoSeguro1.getLongitud() > punto2.getLongitud() &&
					puntoSeguro1.getLatitud() > punto1.getLatitud() && puntoSeguro1.getLatitud() < punto2.getLatitud())
			dibujarPunto(new Punto(puntoSeguro1), distanciaPunto1);
		else if(puntoSeguro2.getLongitud() < punto3.getLongitud() && puntoSeguro2.getLongitud() > punto3.getLongitud() &&
					puntoSeguro2.getLatitud() > punto3.getLatitud() && puntoSeguro2.getLatitud() < punto3.getLatitud())
			dibujarPunto(new Punto(puntoSeguro2), distanciaPunto2);
		else dibujarPunto(new Punto(punto2), distanciaPunto);
		
		
				
//		if(origen.getLatitud()<punto1.getLatitud() && origen.getLatitud()<punto2.getLatitud()){//SUR
//			
//			orientacion = SUR;
//		}
//		else if(origen.getLatitud()>punto1.getLatitud() && origen.getLatitud()>punto2.getLatitud()){//NORTE
//			orientacion = NORTE;
//		}
//		else if(origen.getLongitud()<punto1.getLongitud() && origen.getLongitud()<punto2.getLongitud()){//OESTE
//			orientacion = OESTE;
//		}
//		else if(origen.getLongitud()>punto1.getLongitud() && origen.getLongitud()>punto2.getLongitud()){//ESTE
//			orientacion = ESTE;
//		}

//		Log.i(TAG, "Coordenada: ("+x+" , "+y+")");
//		Coordenada puntoSeguro = new Coordenada(y, x);
//		dibujarPunto(new PuntoEncuentro(puntoSeguro));
		return null;
	}

	public void setMapView(MapView mapView) {
		this.mapView = mapView;
	}

	public void refresh() {
		mapView.postInvalidate();
	}

}