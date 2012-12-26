package cl.at.bussines;

import java.util.ArrayList;

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
import cl.at.data.DispositivoSQL;
import cl.at.util.AlertTsunamiApplication;
import cl.at.util.Util;
import cl.at.view.R;

public class Dispositivo {

	private static final String TAG = Dispositivo.class.getName();
	private Integer id;
	private Boolean estadoDeRiesgo;
	private Usuario usuario;
	private Coordenada posicion;
	private Integer intervalo;
	private LocationManager locManager;
	private Location location;
	private Context context;
	private String regGCM;

	public Dispositivo(Usuario usuario) {
		DispositivoSQL dSQL = new DispositivoSQL();
		this.estadoDeRiesgo = false;// TODO Calcular la posicion e indicar si
									// hay estado de riesgo
		this.context = AlertTsunamiApplication.getAppContext();
		this.intervalo = Util.getPreferencia("intervalo") 
				!= null ? Integer.parseInt(Util.getPreferencia("intervalo")) : 20000;// TODO cambiar tiempo default
		setUsuario(usuario);
		if(!usuario.esExterno()){
			this.locManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
			if(this.locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)!=null){
				this.location = this.locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				setPosicion(new Coordenada(location.getLatitude(), location.getLongitude()));
			}
			else new DispositivoSQL().getUltimaPosicion(this.getUsuario(), this);
		} else {
			dSQL.getUltimaPosicion(this.getUsuario(), this);
		}
//		if(Util.getPreferencia("usuario")!=null)
			if(Util.getPreferencia("dispositivo")==null){
				dSQL.persistir(this);
				Util.guardar(this);
			}
			else{
				this.id = Integer.parseInt(Util.getPreferencia("dispositivo"));
				dSQL.cargarDispositivo(this);
			}
		
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
		Integer i = 1;// TODO porque en 1????
		ArrayList<Usuario> grupoFamiliar = new ArrayList<Usuario>();
		grupoFamiliar = getUsuario().getGrupoFamiliar().getIntegrantes();
		try {
			this.location = this.locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			setPosicion(new Coordenada(this.location.getLatitude(), this.location.getLongitude()));
			// this.posicion.setLatitud(this.location.getLatitude());
			// this.posicion.setLongitud(this.location.getLongitude());
			DispositivoSQL dSQL = new DispositivoSQL();
			while (i < grupoFamiliar.size()) {
				dSQL.getUltimaPosicion(grupoFamiliar.get(i), grupoFamiliar.get(i).getDispositivo());
				i++;
			}
		} catch (Exception e) {
			DispositivoSQL dSQL = new DispositivoSQL();
			dSQL.getUltimaPosicion(this.getUsuario(), this);
		}
		if (!estaSeguro()) {
			// TODO comprobar estado de dispositivo
		}
		DispositivoSQL dSQL = new DispositivoSQL();
		dSQL.actualizarPosicion(this);
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
		this.locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, this.intervalo, 0, locListener);
	}

	public void onAlertaRecibida(Alerta a) {
		// ciudad.visualizarMapa();
		// Obtenemos una referencia al servicio de notificaciones
		String ns = Context.NOTIFICATION_SERVICE;
		context = AlertTsunamiApplication.getAppContext();
		NotificationManager notManager = (NotificationManager) context.getSystemService(ns);

		// Configuramos la notificaci�n
		int icono = android.R.drawable.stat_sys_warning;
		CharSequence textoEstado = "Alerta de Tsunami";
		long hora = System.currentTimeMillis();
		Context contexto = context.getApplicationContext();

		Notification notif = new Notification(icono, textoEstado, hora);
		// notif.defaults = Notification.DEFAULT_ALL;
		// long[] vibrate = {100,100,200,300};
		// notif.vibrate = vibrate;
		notif.sound = Uri.parse("android.resource://" + contexto.getPackageName() + "/" + R.raw.alerta);
		notif.audioStreamType = AudioManager.STREAM_NOTIFICATION;
		notif.defaults = Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE;
		// Configuramos el Intent

		CharSequence titulo = "Alerta de Tsunami";
		CharSequence descripcion = a.getDescripcion();
		Intent notIntent = new Intent("at.MAPA");// contexto,
													// GCMIntentService.class);
		PendingIntent contIntent = PendingIntent.getActivity(contexto, 0, notIntent, 0);
		notif.setLatestEventInfo(contexto, titulo, descripcion, contIntent);
		// AutoCancel: cuando se pulsa la notificai�n �sta desaparece
		notif.flags |= Notification.FLAG_AUTO_CANCEL;
		// Enviar notificaci�n
		notManager.notify(1, notif);
	}

	public void registrarEnGCM(String regId) {
		this.setRegGCM(regId);
		new DispositivoSQL().registrarEnGCM(this);
	}

}