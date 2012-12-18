package cl.at.view;

import java.io.Serializable;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.Toast;
import cl.at.bussines.Ciudad;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class MainActivity extends MapActivity {

	private MapView mapView;
	private ProgressDialog pDialog;
	private Ciudad ciudad;
	private static final int MNU_OPC1 = 1;
	private static final int SMNU_OPC11 = 11;
	private static final int SMNU_OPC12 = 12;

	private static final int MNU_OPC2 = 2;
	private static final int SMNU_OPC21 = 21;
	private static final int SMNU_OPC22 = 22;
	private static final int SMNU_OPC23 = 23;
	private static final int SMNU_OPC24 = 24;
	private static final int SMNU_OPC25 = 25;

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
		setContentView(R.layout.activity_main);
		mapView = (MapView) findViewById(R.id.mapview);
		ciudad = new Ciudad(mapView);
		new asynclogin().execute();
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
			return true;
		case 22:
			Intent intent = new Intent("modificarUsuario");
			//intent.putExtra("usuario",(Serializable) u);
			startActivity(intent);
			return true;
		case 23:
			return true;
		case 24:
			Intent i = new Intent("crearGrupoFamiliar");
			startActivity(i);
			return true;
		case 25:
			Toast.makeText(getApplicationContext(), "puntos de riesgo", Toast.LENGTH_SHORT).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	class asynclogin extends AsyncTask<String, String, String> {

		protected void onPreExecute() {
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Determinando ciudad....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... params) {
			ciudad.obtenerCiudad();
			SystemClock.sleep(300);
			// if(i == 0) return "ok";
			return "err";
		}

		protected void onPostExecute(String result) {
			// if(result == "ok")
			pDialog.dismiss();
		}
	}

}
