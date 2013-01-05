package cl.at.bussines;

import java.util.ArrayList;

import com.google.android.maps.GeoPoint;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.Uri;
import android.util.Log;
import cl.at.data.DispositivoSQL;
import cl.at.util.AlertTsunamiApplication;
import cl.at.util.Comunicador;
import cl.at.util.Util;
import cl.at.view.R;


public class Dispositivo {

	private static final String TAG = Dispositivo.class.getName();
	private Integer id;
	private Boolean estadoDeRiesgo = true;
	private Usuario usuario;
	private Coordenada posicion;
	private Integer intervalo;
	private LocationManager locManager;
	private Location location;
	private Context context;
	private String regGCM;
	private boolean suscrito = false;

	public Dispositivo(Usuario usuario) {
		DispositivoSQL dSQL = new DispositivoSQL();
		this.context = AlertTsunamiApplication.getAppContext();
		setUsuario(usuario);
		if(!usuario.esExterno()){
			this.intervalo = Util.getPreferencia("intervalo") != null ? Integer.parseInt(Util.getPreferencia("intervalo")) : 15000;
			this.locManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
			this.location = this.locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if(this.location!=null){
				setPosicion(new Coordenada(location.getLatitude(), location.getLongitude()));
				dSQL.persistir(this);
				Util.guardar(this);
			}
			else dSQL.cargarDispositivo(this);
		}
		else dSQL.cargarDispositivo(this);
	}

	public Dispositivo() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getEstadoDeRiesgo() {
		return this.estadoDeRiesgo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Coordenada getPosicion() {
		return posicion;
	}
	
	public void setPosicion(Coordenada posicion) {
		this.posicion = posicion;
	}

	public Integer getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(Integer intervalo) {
		this.intervalo = intervalo;
	}

	public String getRegGCM() {
		return regGCM;
	}

	public void setRegGCM(String regGCM) {
		this.regGCM = regGCM;
	}

	public void actualizarPosicion() {
		DispositivoSQL dSQL = new DispositivoSQL();
		this.location = this.locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if(this.location!=null)
			setPosicion(new Coordenada(this.location.getLatitude(), this.location.getLongitude()));
		else if(getOverlayPosition() != null) {
			GeoPoint posicion = getOverlayPosition();
			setPosicion(new Coordenada((double)(posicion.getLatitudeE6() /1E6), (double)(posicion.getLongitudeE6() /1E6)));
		}
		else dSQL.getUltimaPosicion(this);
		dSQL.actualizarPosicion(this);
		if (!estaSeguro()) {
			// TODO comprobar estado de dispositivoa
			
		}
	}

	private GeoPoint getOverlayPosition() {
		return Comunicador.getInstancia().getPosition();
	}

	public Boolean estaSeguro() {
		return estadoDeRiesgo;
		// podria tener un setter para que la ciudad lo haga cambiar de estado,
		// asi evitarnos la relacion.
		// creo que para eso estaba :S, la relacion...
		// if(this.posicion == this.ciudad.getPuntoSeguridad().coordenada){
		// return true;
		// }
		// else
		// return false;
	}

	public void inicializar(LocationListener locListener) {
		if(!suscrito){
			this.locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000/*this.intervalo*/, 0, locListener);
			suscrito = true;
		}
	}

	public void onAlertaRecibida(Alerta a) {
		// Obtenemos una referencia al servicio de notificaciones
		String ns = Context.NOTIFICATION_SERVICE;
		context = AlertTsunamiApplication.getAppContext();
		NotificationManager notManager = (NotificationManager) context.getSystemService(ns);

		int icono = android.R.drawable.stat_sys_warning;
		CharSequence textoEstado = "Alerta de Tsunami";
		long hora = System.currentTimeMillis();
		Context contexto = context.getApplicationContext();

		Notification notif = new Notification(icono, textoEstado, hora);
		// notif.defaults = Notification.DEFAULT_ALL;
		// long[] vibrate = {100,100,200,300};
		// notif.vibrate = vibrate;
		notif.sound = Uri.parse("android.resource://" + contexto.getPackageName() + "/" + R.raw.alerta);
		notif.audioStreamType = AudioManager.MODE_RINGTONE;
		notif.defaults = Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE;
		// Configuramos el Intent

		CharSequence titulo = "Alerta de Tsunami";
		CharSequence descripcion = a.getDescripcion();
		Intent notIntent = new Intent("at.MAPA");
		notIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent contIntent = PendingIntent.getActivity(contexto, 0, notIntent, 0);
		notif.setLatestEventInfo(contexto, titulo, descripcion, contIntent);
		notif.flags |= Notification.FLAG_AUTO_CANCEL;
		notManager.notify(1, notif);
	}

	public void registrarEnGCM(String regId) {
		this.setRegGCM(regId);
		new DispositivoSQL().registrarEnGCM(this);
	}

	public void setEstadoDeRiesgo(Boolean estadoRiesgo) {
		if(this.estadoDeRiesgo && !estadoRiesgo) {
			Comentario comentario = new Comentario(this.usuario.getNombreUsuario()+" se encuentra en zona de seguridad", this.usuario);
			try{
				comentario.persistir();
			}catch(Exception e){
				Log.e(TAG, "setEstadoDeRiesgo, "+e.toString());
			}
		}
		this.estadoDeRiesgo = estadoRiesgo;
	}

	public void estadoRiesgo(boolean b) {
		this.estadoDeRiesgo = b;
		
	}

}