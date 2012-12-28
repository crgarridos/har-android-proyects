package cl.at.view;

import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cl.at.bussines.Ciudad;
import cl.at.bussines.Coordenada;
import cl.at.bussines.PuntoRiesgo;
import cl.at.bussines.Usuario;
import cl.at.util.Comunicador;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class IngresarPuntoDeRiesgoActivity extends MapActivity {

	private AlertDialog dialog;
	private AlertDialog dialog1;
	private MapView mapView;
	private Button btnRojo;
	private Button btnNaranjo;
	private Button btnAmarillo;
	private EditText editTextComentario;
	private ProgressDialog pDialog;

	private Coordenada coordenada;
	private Usuario usuario;
	private Ciudad ciudad;
	
	private String comentario;
	private int color;

	private Comunicador com;

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	class MapOverlay extends com.google.android.maps.Overlay {
		@Override
		public boolean onTap(GeoPoint p, MapView mapView) {
			Double latitud = ((double) p.getLatitudeE6()) / 1000000;
			Double longitud = ((double) p.getLongitudeE6()) / 1000000;
			coordenada = new Coordenada(latitud, longitud);
			dialog.show();
			return false;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ingresar_punto_riesgo);

		com = Comunicador.getInstancia();
		usuario = com.getUsuario();
		ciudad = com.getCiudad();

		mapView = (MapView) findViewById(R.id.ingresar_punto_de_riesgo_mapView);
		this.setTitle("Seleccione un punto de riesgo en el mapa");

		MapOverlay mapOverlay = new MapOverlay();
		List<Overlay> listOfOverlays = mapView.getOverlays();
		listOfOverlays.clear();
		listOfOverlays.add(mapOverlay);

		LayoutInflater factory = LayoutInflater.from(this);
		final View viewComentario = factory.inflate(R.layout.ingresar_comentario_riesgo, null);
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Descripcion");
		alert.setMessage("Ingrese una descripcion del punto de riesgo");
		alert.setView(viewComentario);
		editTextComentario = (EditText) viewComentario.findViewById(R.id.ingresar_comentario_riesgo);
		editTextComentario.setInputType(InputType.TYPE_CLASS_NUMBER);
		LayoutInflater factory1 = LayoutInflater.from(this);
		final View viewCategoria = factory1.inflate(R.layout.ingresar_categoria_riesgo, null);
		AlertDialog.Builder alert1 = new AlertDialog.Builder(this);
		alert1.setTitle("Categoria");
		alert1.setMessage("Seleccione una categoria del punto de riesgo");
		alert1.setView(viewCategoria);
		btnRojo = (Button) viewCategoria.findViewById(R.id.ingresarCategoria_btnRojo);
		btnNaranjo = (Button) viewCategoria.findViewById(R.id.ingresarCategoria_btnNaranjo);
		btnAmarillo = (Button) viewCategoria.findViewById(R.id.ingresarCategoria_btnAmarillo);

		alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				comentario = editTextComentario.getText().toString();
				if(comentario.length() > 2 && comentario.length() < 201)
					dialog1.show();
				else
					Toast.makeText(getApplicationContext(), "La descripcion debe tener entre 3 y 200 caracteres", Toast.LENGTH_SHORT).show();
			}
		});

		alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		
		alert1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				new IngresarPuntoRiesgoAsync().execute();
			}
		});

		alert1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		btnRojo.setBackgroundColor(Color.DKGRAY);
		btnNaranjo.setBackgroundColor(Color.DKGRAY);
		btnAmarillo.setBackgroundColor(Color.DKGRAY);
		
		btnRojo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				color = 1;
				btnRojo.setBackgroundColor(Color.LTGRAY);
				btnNaranjo.setBackgroundColor(Color.DKGRAY);
				btnAmarillo.setBackgroundColor(Color.DKGRAY);
			}
		});
		
		btnNaranjo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				color = 2;
				btnRojo.setBackgroundColor(Color.DKGRAY);
				btnNaranjo.setBackgroundColor(Color.LTGRAY);
				btnAmarillo.setBackgroundColor(Color.DKGRAY);
			}
		});
		
		btnAmarillo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				color = 3;
				btnRojo.setBackgroundColor(Color.DKGRAY);
				btnNaranjo.setBackgroundColor(Color.DKGRAY);
				btnAmarillo.setBackgroundColor(Color.LTGRAY);
			}
		});
		
		dialog = alert.create();
		dialog1 = alert1.create();
	}

	public void ingresarPuntoRiesgo() {
		ciudad.definirPuntoEncuentro();
	}

	class IngresarPuntoRiesgoAsync extends AsyncTask<String, String, String> {

		Boolean exito = false;
		
		protected void onPreExecute() {
			pDialog = new ProgressDialog(IngresarPuntoDeRiesgoActivity.this);
			pDialog.setMessage("Ingresando punto de riesgo....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... params) {

			try {
				PuntoRiesgo p = new PuntoRiesgo(color, editTextComentario.getText().toString(), ciudad, coordenada);
				p.persistir();
				exito = true;
				return "Punto de riesgo creado exitosamente!";
			} catch (Exception e) {
				Log.d("Error", e.toString());
				return "Ha ocurrido un error";
			}
		}

		protected void onPostExecute(String s) {
			pDialog.dismiss();
			Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
			if (exito){
				finish();
			}
		}
	}

}
