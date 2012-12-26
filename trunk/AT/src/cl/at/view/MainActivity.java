package cl.at.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import cl.at.bussines.Ciudad;
import cl.at.bussines.Dispositivo;
import cl.at.bussines.GrupoFamiliar;
import cl.at.bussines.Invitacion;
import cl.at.bussines.Usuario;
import cl.at.util.Comunicador;
import cl.at.util.Util;

import com.google.android.gcm.GCMRegistrar;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class MainActivity extends MapActivity {

//	private static final String TAG = MainActivity.class.getName();
	private MyLocationOverlay mOverlayLocation;
	private MapView mapView;
	private ImageButton btnCentrar;
	private ProgressDialog pDialog;
	private Context context;
	private Comunicador com;
	private boolean traerUsuario = false;

	private Ciudad ciudad;
	private Usuario usuario;
	private Dispositivo dispositivo;
	private GrupoFamiliar gf;

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
	private static final int SMNU_OPC33 = 33;
	private static final int SMNU_OPC34 = 34;
	private static final int SMNU_OPC35 = 35;

	private static final int MNU_OPC4 = 4;
	private static final int SMNU_OPC41 = 41;
	private static final int SMNU_OPC42 = 42;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		com = Comunicador.getInstancia();
		setContentView(R.layout.activity_main);
		context = getApplicationContext();
		if (com.getUsuario() != null) {
			usuario = com.getUsuario();
		} else if (Util.getPreferencia("usuario") != null) {
			traerUsuario = true;
		} else {
			Toast.makeText(context, "Ha ocurrido un error inesperado al iniciar la aplicacion", Toast.LENGTH_LONG).show();
			Util.reiniciarPreferencias(context);
			finish();
		}
		mapView = (MapView) findViewById(R.id.mapview);	
		btnCentrar = (ImageButton) findViewById(R.id.ActivityMain_btnCentrar);
		mOverlayLocation = new MyLocationOverlay(mapView.getContext(), mapView);
		mOverlayLocation.enableMyLocation();
		mapView.getOverlays().add(mOverlayLocation);
//		mapView.getController().setCenter(mOverlayLocation.getMyLocation());
		mOverlayLocation.runOnFirstFix(new Runnable() {
			public void run() {
				centrarEnMiPosicion();
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
		mapView.getController().animateTo(mOverlayLocation.getMyLocation());
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu smnu1 = menu.addSubMenu(Menu.NONE, MNU_OPC1, Menu.NONE, "Capas").setIcon(R.drawable.capas);
		smnu1.add(Menu.NONE, SMNU_OPC11, Menu.NONE, "Grupo familiar");
		smnu1.add(Menu.NONE, SMNU_OPC12, Menu.NONE, "Puntos de riesgo");

		SubMenu smnu2 = menu.addSubMenu(Menu.NONE, MNU_OPC2, Menu.NONE, "Usuario").setIcon(R.drawable.user);
		smnu2.add(Menu.NONE, SMNU_OPC21, Menu.NONE, "Ingresar punto de riesgo");
		smnu2.add(Menu.NONE, SMNU_OPC22, Menu.NONE, "Actualizar datos");
		smnu2.add(Menu.NONE, SMNU_OPC23, Menu.NONE, "Ver invitaciones");
		smnu2.add(Menu.NONE, SMNU_OPC24, Menu.NONE, "Crear grupo familiar");
		smnu2.add(Menu.NONE, SMNU_OPC25, Menu.NONE, "Eliminar cuenta");
		smnu2.add(Menu.NONE, SMNU_OPC26, Menu.NONE, "Cerrar sesion");

		SubMenu smnu3 = menu.addSubMenu(Menu.NONE, MNU_OPC3, Menu.NONE, "Grupo familiar").setIcon(R.drawable.grupo_familiar);
		smnu3.add(Menu.NONE, SMNU_OPC31, Menu.NONE, "Invitar familiar");
		smnu3.add(Menu.NONE, SMNU_OPC32, Menu.NONE, "Definir punto de encuentro");
		smnu3.add(Menu.NONE, SMNU_OPC33, Menu.NONE, "Publicar comentario");
		smnu3.add(Menu.NONE, SMNU_OPC34, Menu.NONE, "Visualizar comentarios");
		smnu3.add(Menu.NONE, SMNU_OPC35, Menu.NONE, "Abandonar grupo");

		SubMenu smnu4 = menu.addSubMenu(Menu.NONE, MNU_OPC4, Menu.NONE, "Ajustes").setIcon(R.drawable.ajustes);
		smnu4.add(Menu.NONE, SMNU_OPC41, Menu.NONE, "Configurar geolocalización");
		smnu4.add(Menu.NONE, SMNU_OPC42, Menu.NONE, "Actualizar posición");
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 11:
			Toast.makeText(getApplicationContext(), "grupo familiar", Toast.LENGTH_SHORT).show();
			return true;
		case 12:
			Toast.makeText(getApplicationContext(), "puntos de riesgo", Toast.LENGTH_SHORT).show();
			return true;
		case 21:
			startActivity(new Intent("at.INGRESAR_PUNTO_DE_RIESGO"));
			return true;
		case 22:
			Intent intent = new Intent("at.MODIFICAR_USUARIO");
			startActivity(intent);
			return true;
		case SMNU_OPC23:
			mostrarInvitaciones();
			return true;
		case 24:
			validarGrupoFamiliar();
			return true;
		case 25:
			mostrarMensajeConfirmacion();
			return true;
		case 26:
			Util.reiniciarPreferencias(context);
			finish();
			return true;
		case 31:
			Intent intent2 = new Intent("at.INVITAR_FAMILIAR");
			startActivity(intent2);
			return true;
		case 32:
			Intent intent1 = new Intent("at.DEFINIR_PUNTO_ENCUENTRO_MAP");
			intent1.putExtra("usuario", (Serializable) usuario);
			startActivity(intent1);
			return true;
		case 33:
			startActivity(new Intent("at.PUBLICAR_COMENTARIO"));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void mostrarInvitaciones() {
		Intent intent = new Intent("at.LISTA_INVITACIONES");
		startActivity(intent);
//		ArrayList<Invitacion> invitaciones = usuario.getInvitaciones();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	protected void registrarDispositivo(){
        final String regId = GCMRegistrar.getRegistrationId(MainActivity.this);
        if (regId.equals("")) {
        	GCMRegistrar.register(MainActivity.this, "42760762845"); //Sender ID
        } else {
        	Log.v("GCMTest", "Ya registrado");
        }
	}

	public void mostrarMensajeConfirmacion() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Eliminar cuenta");
		alert.setMessage("Está seguro que desea eliminar su cuenta?");
		alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				new AsyncDelete().execute();
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
		if (usuario.getGrupoFamiliar() == null) {
			Intent i = new Intent("at.CREAR_GRUPO_FAMILIAR");
			startActivity(i);
		} else
			Toast.makeText(getApplicationContext(), "Ya pertenece a un grupo familiar", Toast.LENGTH_SHORT).show();
	}

	class AsyncDelete extends AsyncTask<String, String, String> {

		protected void onPreExecute() {
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... params) {
			SystemClock.sleep(300);
			if (usuario.getGrupoFamiliar() != null) {
				// TODO Abandonar grupo familiar CDU - 07
			}
			try {
				usuario.eliminar();
				return "Cuenta eliminada!";
			} catch (Exception w) {
				return "No se ha podido eliminar la cuenta";
			}
		}

		protected void onPostExecute(String s) {
			Util.reiniciarPreferencias(context);
			Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
			pDialog.dismiss();// ocultamos progess dialog.
			finish();

		}

	}

	class AsyncCargar extends AsyncTask<String, String, Boolean> {

		protected void onPreExecute() {
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected Boolean doInBackground(String... params) {
			if (traerUsuario) {
				pDialog.setMessage("Cargando...");
				usuario = new Usuario((String) Util.getPreferencia("usuario"));
				if (!usuario.getExisteUsuario() || usuario.getPassword() == null
						|| !Util.encriptaEnMD5(usuario.getNombreUsuario() + usuario.getPassword()).equals(Util.getPreferencia("login"))) {
					Util.reiniciarPreferencias(context);
					return false;
					// TODO se debe cambiar el encriptado de login en
					// preferences cuando el usuario cambie la contrasena
					// (Util.guardarUsuario(usuario)), se entiende? : se se se
				}
			}
			dispositivo = usuario != null ? usuario.getDispositivo() : new Dispositivo(null);
			gf = usuario != null ? usuario.getGrupoFamiliar() : null;
			ciudad = new Ciudad(mapView, dispositivo, gf != null ? gf.getPuntoEncuentro() : null);
			try {
				ciudad.obtenerCiudad();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dispositivo.actualizarPosicion();
			// SystemClock.sleep(300);
			return true;
		}

		protected void onPostExecute(Boolean result) {
			if (result)
				pDialog.dismiss();
			else {
				Toast.makeText(context, "Ha ocurrido un error inesperado al iniciar la aplicacion", Toast.LENGTH_LONG).show();
				finish();
			}
			dispositivo.inicializar(ciudad.getLocationListener());
			com.setUsuario(usuario);
			com.setCiudad(ciudad);
			com.setDispositivo(dispositivo);
			registrarDispositivo();
		}
	}
}