package cl.at.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import cl.at.bussines.Ciudad;
import cl.at.bussines.Coordenada;
import cl.at.bussines.GMapsAPI;
import cl.at.bussines.GrupoFamiliar;
import cl.at.bussines.Lider;
import cl.at.bussines.PuntoEncuentro;
import cl.at.bussines.Usuario;
import cl.at.util.Comunicador;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class DefinirPuntoEncuentroMapActivity extends MapActivity {

	private AlertDialog dialog;
	private MapView mapView;
	private EditText editTextComentario;
	private ProgressDialog pDialog;

	private String nombreGrupo;

	private Coordenada coordenada;
	private Usuario usuario;
	private Ciudad ciudad;
	private GrupoFamiliar grupoFamiliar;

	private Comunicador com;
	private Lider lider;

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	class MapOverlay extends com.google.android.maps.Overlay {
		@Override
		public boolean onTap(GeoPoint p, MapView mapView) {
			Double latitud = ((double) p.getLatitudeE6()) / 1000000;
			Double longitud = ((double) p.getLongitudeE6()) / 1000000;
			coordenada = new Coordenada(latitud, longitud);
			if (!ciudad.estaDentro(coordenada))
				dialog.show();
			else
				Toast.makeText(getApplicationContext(), "Debe ingresar el punto de encuentro en la zona segura", Toast.LENGTH_SHORT).show();
			return false;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.definir_punto_encuentro_map);

		com = Comunicador.getInstancia();
		usuario = com.getUsuario();
		grupoFamiliar = com.getUsuario().getGrupoFamiliar();
		ciudad = com.getCiudad();

		this.setTitle("Seleccione un punto de encuentro");
		mapView = (MapView) findViewById(R.id.definir_punto_encuentro_mapView);

		GMapsAPI gMapsAPI = new GMapsAPI(mapView);
		mapView.getOverlays().add(new MapOverlay());
		gMapsAPI.dibujarPolilinea(ciudad.getAreaInundacion());

		Bundle bundle = getIntent().getExtras();
		if (grupoFamiliar == null && bundle != null && bundle.containsKey("nombreGrupo"))
			nombreGrupo = bundle.getString("nombreGrupo");
		else
			gMapsAPI.dibujarPunto(ciudad.getPuntoEncuentro());

		prepararDialogoDescripcion();
	}

	private void prepararDialogoDescripcion() {
		LayoutInflater factory = LayoutInflater.from(this);
		final View viewComentario = factory.inflate(R.layout.ingresar_comentario, null);
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Descripcion");
		alert.setMessage("Ingrese una descripcion del punto de encuentro familiar");
		alert.setView(viewComentario);
		editTextComentario = (EditText) viewComentario.findViewById(R.id.ingresar_comentario);
		
		alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				new definirPuntoEncuentroAsync().execute();
			}
		});

		alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		dialog = alert.create();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if ((resultCode != Activity.RESULT_CANCELED) && (requestCode == 777)) {
			setResult(Activity.RESULT_OK);
			finish();
		}
	}

	public void definirPuntoEncuentro() {
		ciudad.definirPuntoEncuentro();
	}

	class definirPuntoEncuentroAsync extends AsyncTask<String, String, String> {

		Boolean existe;
		Boolean exito = false;
		Boolean salir = false;

		protected void onPreExecute() {
			existe = true;
			pDialog = new ProgressDialog(DefinirPuntoEncuentroMapActivity.this);
			if (grupoFamiliar == null)
				pDialog.setMessage("Creando grupo familiar....");
			else
				pDialog.setMessage("Definiendo punto de encuentro ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... params) {
			try {
				if (grupoFamiliar == null) {
					lider = new Lider(usuario);
					usuario.setEsLider(true);
					grupoFamiliar = new GrupoFamiliar(nombreGrupo, null, lider);
					existe = false;
				}
				//TODO definirPuntoEncuentro(); bugeao :B se hace todo afuera parece, bastaria con eliminarlo
				Log.d("dispositivo", "" + ciudad.getDispositivo());
				PuntoEncuentro puntoEncuentro = ciudad.ingresar(editTextComentario.getText().toString(), coordenada);
				if (puntoEncuentro != null) {
					if (!existe){
						puntoEncuentro.setGrupoFamiliar(grupoFamiliar);
						grupoFamiliar.setPuntoEncuentro(puntoEncuentro);
						grupoFamiliar.persistir();
						com.getUsuario().setGrupoFamiliar(grupoFamiliar);
						exito = true;
						return "Grupo familiar creado exitosamente";
					} else
						puntoEncuentro.setGrupoFamiliar(grupoFamiliar);
					ciudad.setPuntoEncuentro(puntoEncuentro);
					grupoFamiliar.setPuntoEncuentro(puntoEncuentro);
					puntoEncuentro.persistir();
					salir = true;
					return "Punto de encuentro definido exitosamente";
				} else
					return "El punto de encuentro debe estar dentro de la zona de seguridad";
			} catch (Exception w) {
				Log.d("Error:", w.toString());
				return "Ha ocurrido un error";
			}
		}

		protected void onPostExecute(String s) {
			Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
			if (salir)
				finish();
			if (exito) {
				startActivityForResult(new Intent("at.INVITAR_FAMILIAR"), 777);
				setResult(Activity.RESULT_OK);
				finish();
			}
			pDialog.dismiss();// ocultamos progess dialog.
		}
	}

}
