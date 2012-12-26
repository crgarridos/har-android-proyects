package cl.at.view;

import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import cl.at.bussines.Alerta;
import cl.at.bussines.Dispositivo;
import cl.at.util.Comunicador;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {

	private Alerta alerta;
	private Dispositivo dispositivo;
	private Comunicador com;
//	private String regId;

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
		Toast.makeText(getApplicationContext(), "lalalala", Toast.LENGTH_LONG).show();
		String msg = intent.getExtras().getString("message");
		Date fecha = new Date();// Util.StringDBToDate(intent.getExtras().getString("fecha"));
		Integer categoria = 1;// intent.getExtras().getInt("categoria");
		Log.d("GCMTest", "Mensaje: " + msg);
		alerta.consultaSHOA(fecha, categoria, msg);
		// mostrarNotificacion(context, msg);
	}

	@Override
	protected void onRegistered(Context context, String regId) {
		Log.d("GCMTest", "REGISTRATION: Registrado OK.");
		dispositivo = com.getDispositivo();
//		this.regId = regId;
		new AsyncRegister().execute(regId);
	}

	@Override
	protected void onUnregistered(Context context, String regId) {
		Log.d("GCMTest", "REGISTRATION: Desregistrado OK.");
	}

	class AsyncRegister extends AsyncTask<String, String, Boolean> {

		@Override
		protected Boolean doInBackground(String... regId) {
			dispositivo.registrarEnGCM(regId[0]);
			return true;
		}

	}
}
