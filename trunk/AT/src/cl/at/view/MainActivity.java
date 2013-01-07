package cl.at.view;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import cl.at.bussines.Ciudad;
import cl.at.bussines.Dispositivo;
import cl.at.bussines.GrupoFamiliar;
import cl.at.bussines.Usuario;
import cl.at.util.Comunicador;
import cl.at.util.Util;

import com.google.android.gcm.GCMRegistrar;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class MainActivity extends MapActivity {

	private static final String TAG = MainActivity.class.getName();
	private MyLocationOverlay mOverlayLocation;
	private MapView mapView;
	private ImageButton btnCentrar;
	private EditText editText;
	private Context context;
	private Comunicador com;
	private AlertDialog dialogGPS;
	private boolean traerUsuario = false;

	private Ciudad ciudad;
	private Usuario usuario;
	private Dispositivo dispositivo;
	private GrupoFamiliar gf;
	private boolean conservar = true;

	private static final int MNU_OPC1 = 1;
	private static final int SMNU_OPC11 = 11;
	private static final int SMNU_OPC12 = 12;

	private static final int MNU_OPC2 = 2;
	private static final int SMNU_OPC21 = 21;
	private static final int SMNU_OPC22 = 22;
	private static final int SMNU_OPC23 = 23;
	private static final int SMNU_OPC24 = 24;
	private static final int SMNU_OPC25 = 25;
	private static final int SMNU_OPC26 = 26;

	private static final int MNU_OPC3 = 3;
	private static final int SMNU_OPC31 = 31;
	private static final int SMNU_OPC32 = 32;
	private static final int SMNU_OPC34 = 34;
	private static final int SMNU_OPC35 = 35;

	private static final int MNU_OPC4 = 4;
	private static final int SMNU_OPC41 = 41;
	private static final int SMNU_OPC42 = 42;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		com = Comunicador.getInstancia();
		setContentView(R.layout.activity_main);
		GCMRegistrar.checkDevice(MainActivity.this);
		GCMRegistrar.checkManifest(MainActivity.this);
		context = getApplicationContext();
		if (com.getUsuario() != null) {
			usuario = com.getUsuario();
			// desuscribirDispositivo();
		} else if (Util.getPreferencia("usuario") != null) {
			traerUsuario = true;
		} else {
			Toast.makeText(context, "Ha ocurrido un error inesperado al iniciar la aplicacion", Toast.LENGTH_LONG).show();
			Util.reiniciarPreferencias(context);
			salir();
		}

		mapView = (MapView) findViewById(R.id.mapview);
		dialogGPS = validarGPS();
	}

	private AlertDialog validarGPS() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("GPS desactivado");
		String mensaje = "Hemos detectado que el GPS se encuentra actualmente desactivado.\n" + "La aplicacion necesita de el para ejecutarse correctamente.\n"
				+ "Presione \"Aceptar\" para ir a la configuracion y activarlo (Marque \"Utilizar satelites GPS\" o similar).\n"
				+ "Si selecciona \"Cancelar\" se cerrara la aplicacion";
		alert.setMessage(mensaje);
		alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				abrirConfigGPS();
			}
		});
		alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				salir();
			}
		});
		alert.setCancelable(false);
		return alert.create();
	}

	private void comenzar() {
		btnCentrar = (ImageButton) findViewById(R.id.ActivityMain_btnCentrar);
		mOverlayLocation = new MyLocationOverlay(mapView.getContext(), mapView);
		mOverlayLocation.enableMyLocation();
		mapView.getOverlays().add(mOverlayLocation);
		mOverlayLocation.runOnFirstFix(new Runnable() {
			public void run() {
				centrarEnMiPosicion();
				com.notifyPosition(mOverlayLocation.getMyLocation());
			}
		});

		btnCentrar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				centrarEnMiPosicion();
			}
		});
		new AsyncCargar().execute();

	}

	protected void centrarEnMiPosicion() {
		if (mOverlayLocation.getLastFix() != null) {
			mapView.getController().animateTo(mOverlayLocation.getMyLocation());
			while (mapView.getZoomLevel() < 15) {
				mapView.getController().zoomIn();
				SystemClock.sleep(200);
			}
		} else
			Toast.makeText(getApplicationContext(), "Espere a obtener la posicion...", Toast.LENGTH_LONG).show();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu smnu1 = menu.addSubMenu(Menu.NONE, MNU_OPC1, Menu.NONE, "Capas").setIcon(R.drawable.capas);
		int i = 0;
		if (gf != null) {
			smnu1.add(Menu.NONE, SMNU_OPC11, Menu.NONE, "Grupo familiar");
			smnu1.getItem(i).setCheckable(true);
			smnu1.getItem(i++).setChecked(Boolean.parseBoolean(Util.getPreferencia(Util.CAPA_GRUPO_FAMILIAR)));
		}
		smnu1.add(Menu.NONE, SMNU_OPC12, Menu.NONE, "Puntos de riesgo");
		smnu1.getItem(i).setCheckable(true);
		smnu1.getItem(i).setChecked(Boolean.parseBoolean(Util.getPreferencia(Util.CAPA_PUNTO_DE_RIESGO)));

		SubMenu smnu2 = menu.addSubMenu(Menu.NONE, MNU_OPC2, Menu.NONE, "Usuario").setIcon(R.drawable.user);
		smnu2.add(Menu.NONE, SMNU_OPC21, Menu.NONE, "Ingresar punto de riesgo");
		smnu2.add(Menu.NONE, SMNU_OPC22, Menu.NONE, "Actualizar datos");
		smnu2.add(Menu.NONE, SMNU_OPC23, Menu.NONE, "Ver invitaciones");
		smnu2.add(Menu.NONE, SMNU_OPC25, Menu.NONE, "Eliminar cuenta");
		smnu2.add(Menu.NONE, SMNU_OPC26, Menu.NONE, "Cerrar sesion");

		SubMenu smnu3 = menu.addSubMenu(Menu.NONE, MNU_OPC3, Menu.NONE, "Grupo familiar").setIcon(R.drawable.grupo_familiar);
		smnu3.add(Menu.NONE, SMNU_OPC24, Menu.NONE, "Crear grupo familiar");
		if (gf != null) {
			smnu3.add(Menu.NONE, SMNU_OPC31, Menu.NONE, "Invitar familiar");
			if (usuario.esLider())
				smnu3.add(Menu.NONE, SMNU_OPC32, Menu.NONE, "Definir punto de encuentro");
			smnu3.add(Menu.NONE, SMNU_OPC34, Menu.NONE, "Visualizar comentarios");
			smnu3.add(Menu.NONE, SMNU_OPC35, Menu.NONE, "Abandonar grupo");
		}

		SubMenu smnu4 = menu.addSubMenu(Menu.NONE, MNU_OPC4, Menu.NONE, "Configuracion").setIcon(R.drawable.ajustes);
		smnu4.add(Menu.NONE, SMNU_OPC41, Menu.NONE, "Cambiar intervalo de actualizacion");
		smnu4.add(Menu.NONE, SMNU_OPC42, Menu.NONE, "Actualizar posicion");
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 11:
			ciudad.mostrarCapas(Ciudad.CAPA_GRUPO_FAMILIAR); // Grupo Familiar
			Util.guardarEstadoCapaGrupoFamiliar(!item.isChecked());
			item.setChecked(!item.isChecked());
			return true;
		case 12:
			ciudad.mostrarCapas(Ciudad.CAPA_PUNTO_RIESGO); // Puntos de riesgo
			Util.guardarEstadoCapaPuntosDeRiesgo(!item.isChecked());
			item.setChecked(!item.isChecked());
			return true;
		case 21:
			startActivity(new Intent("at.INGRESAR_PUNTO_DE_RIESGO"));
			return true;
		case 22:
			startActivity(new Intent("at.MODIFICAR_USUARIO"));
			return true;
		case SMNU_OPC23:
			mostrarInvitaciones();
			return true;
		case 24:
			validarGrupoFamiliar();
			return true;
		case 25:
			confirmarEliminarCuenta();
			return true;
		case 26:// Cerrando sesion
			Util.reiniciarPreferencias(context);
			desuscribirDispositivo();
			salir();
			return true;
		case 31:
			startActivity(new Intent("at.INVITAR_FAMILIAR"));
			return true;
		case 32:
			startActivity(new Intent("at.DEFINIR_PUNTO_ENCUENTRO_MAP"));
			return true;
		case 33:
			startActivity(new Intent("at.PUBLICAR_COMENTARIO"));
			return true;
		case SMNU_OPC34:
			mostrarComentarios();
			return true;
		case 35:
			abandonarGrupo("Esta seguro que desea abandonar su grupo familiar?");
			return true;
		case 41:
			configGeolocalizacion();
			return true;
		case 42:
			ciudad.actualizarPosiciones();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void abandonarGrupo(String s) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Abandonar grupo familiar");
		alert.setMessage(s);
		alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				new AsyncAbandonar().execute();
			}
		});
		alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		final AlertDialog dialog = alert.create();
		dialog.show();
	}

	private void configGeolocalizacion() {
		LayoutInflater factory = LayoutInflater.from(this);
		final View viewComentario = factory.inflate(R.layout.ingresar_comentario_riesgo, null);
		editText = (EditText) viewComentario.findViewById(R.id.ingresar_comentario_riesgo);
		editText.setInputType(InputType.TYPE_CLASS_NUMBER);
		editText.setText(String.valueOf(Integer.parseInt(Util.getPreferencia("intervalo")) / 1000));
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Cambiar configuracion");
		alert.setMessage("Ingrese el intervalo de tiempo que desea actualizar su posicion (en seg):");
		alert.setView(viewComentario);
		alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if (editText.getText().toString().length() < 3) {
					Toast.makeText(getApplicationContext(), "El tiempo minimo debe ser de 100 segundos", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "Configuracion cambiada exitosamente", Toast.LENGTH_SHORT).show();
					Util.guardarIntervalo(String.valueOf(Integer.parseInt(editText.getText().toString()) * 1000));
				}
			}
		});

		alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {

			}

		});
		alert.create().show();
	}

	private void mostrarComentarios() {
		Intent intent = new Intent("at.LISTA_COMENTARIOS");
		startActivity(intent);
	}

	private void mostrarInvitaciones() {
		Intent intent = new Intent("at.LISTA_INVITACIONES");
		startActivityForResult(intent, 999);
		// ArrayList<Invitacion> invitaciones = usuario.getInvitaciones();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// Toast.makeText(getApplicationContext(), "onResume",
		// Toast.LENGTH_SHORT).show();
		if (!estadoGPS()) {
			dialogGPS.show();
		} else{
			if(dialogGPS.isShowing())
				dialogGPS.cancel();
			comenzar();
		}
		if (ciudad != null) {
			mOverlayLocation.enableMyLocation();
			ciudad.mostrarCapas();
		}
	}

	@Override
	protected void onPause() {
		if(mOverlayLocation!=null)
			mOverlayLocation.disableMyLocation();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
//		if(!conservar)
//			Process.killProcess(Process.myPid());
		Log.d(TAG, "onDestroy, y murio...");
		super.onDestroy();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	protected void registrarDispositivo() {
		final String regId = GCMRegistrar.getRegistrationId(MainActivity.this);
		if (regId.equals("")) {
			GCMRegistrar.register(MainActivity.this, "42760762845");
		} else {
			Log.v("GCMTest", "Ya registrado");
		}
	}

	protected void desuscribirDispositivo() {
		final String regId = GCMRegistrar.getRegistrationId(MainActivity.this);
		if (!regId.equals("")) {
			GCMRegistrar.unregister(MainActivity.this);
		} else {
			Log.v("GCMTest", "Ya des-registrado");
		}
	}

	public void confirmarEliminarCuenta() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Eliminar cuenta");
		alert.setMessage("Estas seguro que desea eliminar su cuenta?");
		alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if(gf == null)
					new AsyncDelete().execute();
				else 
					abandonarGrupo("Para eliminar su cuenta, antes debe abandonar su grupo familiar");
					
			}
		});
		alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		final AlertDialog dialog = alert.create();
		dialog.show();
	}

	public void validarGrupoFamiliar() {
		if(gf == null)
			startActivityForResult(new Intent("at.CREAR_GRUPO_FAMILIAR"), 999);
		else
			abandonarGrupo("Para crear un grupo familiar nuevo, primero debe abandonar su grupo familiar actual");
	}

	private boolean estadoGPS() {
		LocationManager servicio = (LocationManager) getSystemService(LOCATION_SERVICE);
		return servicio.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	public void abrirConfigGPS() {
		Intent actividad = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		startActivityForResult(actividad, 777);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 999 && resultCode == Activity.RESULT_OK) {
			this.recargar();
		}
	}

	private void recargar() {
		conservar = true;
		finish();
		startActivity(new Intent(MainActivity.this,MainActivity.class));
	}
	
	private void salir() {
		conservar = false;
		finish();
	}

	class AsyncDelete extends AsyncTask<String, String, String> {

		ProgressDialog pDialog = new ProgressDialog(MainActivity.this);

		protected void onPreExecute() {
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... params) {
			if (usuario.getGrupoFamiliar() != null) {
				usuario.setGrupoFamiliar(null);
			}
			try {
				usuario.eliminar();
				return "Cuenta eliminada!";
			} catch (Exception w) {
				return "No se ha podido eliminar la cuenta";
			}
		}

		protected void onPostExecute(String s) {
			desuscribirDispositivo();
			Util.reiniciarPreferencias(context);
			Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
			pDialog.dismiss();
			salir();
		}

	}

	class AsyncCargar extends AsyncTask<String, String, Boolean> {

		ProgressDialog pDialog = new ProgressDialog(MainActivity.this);

		protected void onPreExecute() {
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.setMessage("Cargando...");
			pDialog.show();
		}

		protected Boolean doInBackground(String... params){
			if (traerUsuario) {
				usuario = new Usuario((String) Util.getPreferencia("usuario"));
				if (!usuario.getExisteUsuario() || usuario.getPassword() == null
						|| !Util.encriptaEnMD5(usuario.getNombreUsuario() + usuario.getPassword()).equals(Util.getPreferencia("login"))) {
					Util.reiniciarPreferencias(context);
					return false;
				}
			}
			dispositivo = usuario != null ? usuario.getDispositivo() : new Dispositivo(null);
			gf = usuario != null ? usuario.getGrupoFamiliar() : null;
			ciudad = new Ciudad(mapView, dispositivo, gf != null ? gf.getPuntoEncuentro() : null);
			try {
				ciudad.obtenerCiudad();
			} catch (IOException e) {
				Log.e(TAG, "AsyncCargar.doInBackground, " + e.toString() + e.getCause());
			}
			return true;
		}

		protected void onPostExecute(Boolean result) {
			ciudad.actualizarPosiciones();
			if (result)
				pDialog.dismiss();
			else {
				Toast.makeText(context, "Ha ocurrido un error inesperado al iniciar la aplicacion", Toast.LENGTH_LONG).show();
				salir();
			}

			com.setUsuario(usuario);
			com.setCiudad(ciudad);
			com.setDispositivo(dispositivo);
			registrarDispositivo();
		}
	}

	class AsyncAbandonar extends AsyncTask<String, String, Boolean> {

		ProgressDialog pDialog = new ProgressDialog(MainActivity.this);

		protected void onPreExecute() {
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.setMessage("Abandonando grupo...");
			pDialog.show();
		}

		protected Boolean doInBackground(String... params) {
			return usuario.setGrupoFamiliar(null);
		}

		protected void onPostExecute(Boolean exito) {
			if (exito)
				MainActivity.this.recargar();
			Toast.makeText(getApplicationContext(), exito ? "Se ha abandonado el grupo" : "No se ha podido abandonar, intentelo mas tarde.", Toast.LENGTH_SHORT).show();
			pDialog.dismiss();
		}

	}
}