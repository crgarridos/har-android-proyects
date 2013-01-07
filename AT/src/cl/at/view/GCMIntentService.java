package cl.at.view;

import java.util.Date;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import cl.at.bussines.Alerta;
import cl.at.bussines.Dispositivo;
import cl.at.util.AlertTsunamiApplication;
import cl.at.util.Comunicador;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {

	private Alerta alerta;
	private Dispositivo dispositivo;
	private Comunicador com;


	public GCMIntentService() {
		super("42760762845");
		dispositivo = new Dispositivo();
		alerta = new Alerta(dispositivo);
		com = Comunicador.getInstancia();
	}

	@Override
	protected void onError(Context context, String errorId) {
		Log.d("GCMTest", "REGISTRATION: Error -> " + errorId);
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		String msg = intent.getExtras().getString("message");
		String tipo = intent.getExtras().getString("tipo");
		Date fecha = new Date();// Util.StringDBToDate(intent.getExtras().getString("fecha"));
		Log.d("GCMTest", "Mensaje: " + msg);
		if (tipo.equalsIgnoreCase("alerta")) {
			Integer categoria = 1;// intent.getExtras().getInt("categoria");
			alerta.consultaSHOA(fecha, categoria, msg);
		} else if (tipo.equalsIgnoreCase("invitacion") || tipo.equalsIgnoreCase("comentario") || tipo.equalsIgnoreCase("puntoEncuentro")) {
			context = AlertTsunamiApplication.getAppContext();
			NotificationManager notManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			int icono = R.drawable.ic_launcher;
			CharSequence titulo = "Notificacion";
			Intent notIntent = new Intent("at.MAPA");
			notIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
			if(tipo.equalsIgnoreCase("invitacion")){
				icono = R.drawable.grupo_familiar;
				titulo = "Invitacion recibida";
				notIntent.setAction("at.LISTA_INVITACIONES");
			}
			else if(tipo.equalsIgnoreCase("comentario")){
//				icono = R.drawable.grupo_familiar;
				titulo = "Nuevo Comentario";
				notIntent.setAction("at.LISTA_COMENTARIOS");
			}
			else if(tipo.equalsIgnoreCase("puntoEncuentro")){
//				icono = R.drawable.grupo_familiar;
				titulo = "Cambio en el punto de encuentro";
				dispositivo.getUsuario().setEstadoLlegada(false);
			}
			long hora = System.currentTimeMillis();
			Notification notif = new Notification(icono, titulo, hora);
			notif.defaults = Notification.DEFAULT_ALL;
			PendingIntent contIntent = PendingIntent.getActivity(context, 0, notIntent, 0);
			notif.setLatestEventInfo(context, titulo, msg, contIntent);
			notif.flags |= Notification.FLAG_AUTO_CANCEL;
			notManager.notify(1, notif);
		}
	}

	@Override
	protected void onRegistered(Context context, String regId) {
		Log.d("GCMTest", "REGISTRATION: Registrado OK.");
		dispositivo = com.getDispositivo();
		// this.regId = regId;
		new AsyncRegister().execute(regId);
	}

	@Override
	protected void onUnregistered(Context context, String regId) {
		Log.d("GCMTest", "REGISTRATION: Desregistrado OK.");
		dispositivo = com.getDispositivo();
		new AsyncRegister().execute("");
	}

	class AsyncRegister extends AsyncTask<String, String, Boolean> {

		@Override
		protected Boolean doInBackground(String... regId) {
			dispositivo.registrarEnGCM(regId[0]);
			return true;
		}

	}
}
