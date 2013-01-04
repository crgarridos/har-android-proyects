package cl.at.bussines;

import java.io.IOException;
import java.util.ArrayList;

import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import cl.at.data.DispositivoSQL;
import cl.at.data.PuntoRiesgoSQL;
import cl.at.util.AlertTsunamiApplication;

import com.google.android.maps.MapView;

public class Ciudad {

	private static final int CAPA_GRUPO_FAMILIAR = 0;
	private static final int CAPA_PUNTO_RIESGO = 1;
	private static final String TAG = Ciudad.class.getName();
	private Integer id;
	private Dispositivo dispositivo;
	private Coordenada coordenada;
	private Punto puntoSeguridad;
	private ArrayList<Coordenada> areaInundacion;
	private ArrayList<PuntoRiesgo> puntosRiesgo;
	private PuntoEncuentro puntoEncuentro;
	private GMapsAPI gMapsAPI;
	private LocationListener locListener;
	private boolean ejecutando = false;
	private Boolean capaGrupoFamiliarVisible = true;
	private Boolean capaPuntoRiesgoVisible = true;
	
	public Ciudad(MapView mapView, Dispositivo disp, PuntoEncuentro ptoEncuentro) {
		dispositivo = disp;

		puntoSeguridad = new Punto();// TODO Calcular punto seguridad???
		areaInundacion = new ArrayList<Coordenada>();// cargar desde la bd
		puntosRiesgo = new ArrayList<PuntoRiesgo>();// cargar desde la bd
		puntoEncuentro = ptoEncuentro;
		gMapsAPI = new GMapsAPI(mapView);
		// gMapsAPI.setCentro(disp.getPosicion());
		locListener = new LocationListener() {
	    	public void onLocationChanged(Location location) {
	    		Log.i(TAG, "Localizaciofn: "+location.getLatitude()+" - "+location.getLongitude());
	    		dispositivo.setPosicion(new Coordenada(location.getLatitude(), location.getLongitude()));
	    		actualizarPosiciones();
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

	public Ciudad() {
		// Usado para la alerta Don't touch it!!
	}

	public Dispositivo getDispositivo(){
		return dispositivo;
	}

	public void setDispositivo(Dispositivo dispositivo) {
		this.dispositivo = dispositivo;
	}

	public Coordenada getCoordenada() {
		return coordenada;
	}

	public void setCoordenada(Coordenada coordenada) {
		this.coordenada = coordenada;
	}

	public Punto getPuntoSeguridad() {
		return puntoSeguridad;
	}

	public void setPuntoSeguridad(Punto puntoSeguridad) {
		this.puntoSeguridad = puntoSeguridad;
	}

	public ArrayList<Coordenada> getAreaInundacion() {
		return areaInundacion;
	}

	public void setAreaInundacion(ArrayList<Coordenada> areaInundacion) {
		this.areaInundacion = areaInundacion;
	}

	public ArrayList<PuntoRiesgo> getPuntosRiesgo() {
		if(this.puntosRiesgo == null || this.puntosRiesgo.size() == 0){
			PuntoRiesgoSQL pSQL = new PuntoRiesgoSQL();
			pSQL.cargarPuntosRiesgo(this);
		}
		return puntosRiesgo;
	}

	public void setPuntosRiesgo(ArrayList<PuntoRiesgo> puntosRiesgo) {
		this.puntosRiesgo = puntosRiesgo;
	}
	
	public PuntoEncuentro getPuntoEncuentro(){
//		if(this.puntoEncuentro == null){
//			PuntoEncuentroSQL pSQL = new PuntoEncuentroSQL();
//			pSQL.cargarPuntosRiesgo(this);
//		}
		return this.puntoEncuentro;
	}

	public void setPuntoEncuentro(PuntoEncuentro puntoEncuentro) {
		this.puntoEncuentro = puntoEncuentro;
	}

	public void definirPuntoEncuentro() {
		try {
			this.obtenerCiudad();
			if (this.getDispositivo().getUsuario().getGrupoFamiliar() != null) {
				// TODO CDU14 - Visualizar punto de encuentro
			}
		} catch (Exception e) {
			Log.d(TAG,"Error");
		}
	}

	public Punto determinarPuntoSeguridad(Coordenada c){
		return puntoSeguridad;
	}

	public void obtenerCiudad() throws IOException {
		Coordenada c = dispositivo.getPosicion();
		gMapsAPI.determinarCiudad(this);
		// gMapsAPI.setCentro(c);
		gMapsAPI.desplegarMapa(c);
	}

	public LocationListener getLocationListener() {
		return this.locListener;
	}

	public PuntoEncuentro ingresar(String referencia, Coordenada coordenada) {
		if (!this.gMapsAPI.estaEnElMedio(coordenada, this.areaInundacion)) {
			PuntoEncuentro puntoEncuentro = new PuntoEncuentro(coordenada);
			puntoEncuentro.setReferencia(referencia);
			return puntoEncuentro;
		} else {
			return null;
		}
	}

	public void ingresarPuntoRiesgo(PuntoRiesgo puntoRiesgo) {
		puntosRiesgo.add(puntoRiesgo);
	}
	

	public void visualizarMapa() {
		
	}	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void actualizarPosiciones(){
		if(!ejecutando ){
			ejecutando = true;
			Log.i(TAG, "dibujando...");
			new AsyncMapa().execute();
		}
	}
	
	class AsyncMapa extends AsyncTask<String, String, String> {

		GrupoFamiliar grupoFamiliar;
		PuntoEncuentro puntoEncuentro;
		ArrayList<Usuario> integrantes;
		
		@Override
		protected void onPreExecute() {
//			gMapsAPI.invalidate();
			Toast.makeText(AlertTsunamiApplication.getAppContext(), "Actualizando...", Toast.LENGTH_LONG).show();
		}
		
		@Override
		protected String doInBackground(String... params) {
			try{
				grupoFamiliar = dispositivo.getUsuario().getGrupoFamiliar();
				puntoEncuentro = null;
				dispositivo.actualizarPosicion();
				if(grupoFamiliar != null){
					puntoEncuentro = grupoFamiliar.getPuntoEncuentro();
					integrantes = grupoFamiliar.getIntegrantes();
					if(gMapsAPI.compararPunto(dispositivo.getPosicion(), puntoEncuentro.getCoordenada()) < 5000){
						int intentos = 0;
						while(!dispositivo.getUsuario().setEstadoLlegada(true)&&intentos++ < 5);
					}
				}
				puntosRiesgo = getPuntosRiesgo();
				DispositivoSQL dSQL = new DispositivoSQL();
				dSQL.actualizarPosicion(dispositivo);
			}catch(Exception e){
				Log.e(TAG, "Error en dibujando "+e);
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dispositivo.inicializar(Ciudad.this.getLocationListener());
			gMapsAPI.borrarPuntos(Ciudad.this);
			gMapsAPI.dibujarPunto(puntosRiesgo);
			if(grupoFamiliar != null){
				gMapsAPI.dibujarPunto(puntoEncuentro);
				gMapsAPI.dibujarPunto(integrantes, dispositivo.getUsuario());
			}
			gMapsAPI.getCoordenadaMasCercana(Ciudad.this);
			Ciudad.this.ejecutando = false;
			Log.i(TAG, "terminando de dibujar...");
		}
	}

	public void mostrarCapas(int capa) {
		try {
			if(capa == CAPA_GRUPO_FAMILIAR)
				capaGrupoFamiliarVisible = !capaGrupoFamiliarVisible;
			if(capa == CAPA_PUNTO_RIESGO)
				capaPuntoRiesgoVisible = !capaPuntoRiesgoVisible;
			GrupoFamiliar grupoFamiliar = dispositivo.getUsuario().getGrupoFamiliar();
			gMapsAPI.borrarPuntos(Ciudad.this);
			if (grupoFamiliar != null) {
				if (capaGrupoFamiliarVisible)
					gMapsAPI.dibujarPunto(grupoFamiliar.getIntegrantes(), dispositivo.getUsuario());
				gMapsAPI.dibujarPunto(puntoEncuentro);
			}
			gMapsAPI.dibujarPolilinea(areaInundacion);
			if (capaPuntoRiesgoVisible)
				gMapsAPI.dibujarPunto(puntosRiesgo);
			gMapsAPI.refresh();
		} catch (Exception e) {
			Log.e(TAG, "Error en dibujando " + e);
		}
	}

	public void setEstadoRiesgo(Boolean estadoRiesgo) {
		this.getDispositivo().setEstadoDeRiesgo(estadoRiesgo);
	}

	
	public boolean ejecutando() {
		return ejecutando;
	}
}