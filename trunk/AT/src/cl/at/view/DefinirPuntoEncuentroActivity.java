package cl.at.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cl.at.bussines.Coordenada;
import cl.at.bussines.GrupoFamiliar;
import cl.at.bussines.Lider;
import cl.at.bussines.PuntoEncuentro;
import cl.at.bussines.Usuario;
import cl.at.util.Util;

public class DefinirPuntoEncuentroActivity extends Activity {

	private EditText editTextDescripcion;
	private Button btnSgte;

	private ProgressDialog pDialog;

	private PuntoEncuentro puntoEncuentro;
	private Coordenada coordenada;
	private Usuario usuario;
	private Lider lider;

	private String nombreGrupo;
	private Double latitud;
	private Double longitud;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.definir_punto_encuentro);
		editTextDescripcion = (EditText) findViewById(R.id.definirPuntoEncuentro_editTextDescripcion);
		btnSgte = (Button) findViewById(R.id.definirPuntoEncuentro_btnSgte);

		Bundle bundle = getIntent().getExtras();
		latitud = bundle.getDouble("latitud");
		longitud = bundle.getDouble("longitud");
		nombreGrupo = bundle.getString("nombreGrupo");
		if (getIntent() != null && getIntent().getExtras() != null)
			usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		// TODO Dato creado localmente, revisar CDU
		coordenada = new Coordenada(latitud, longitud);
		puntoEncuentro = new PuntoEncuentro(coordenada);
		puntoEncuentro.setReferencia(editTextDescripcion.getText().toString());
		//
		lider = new Lider(usuario);

		btnSgte.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (validarCoherencia()) {
					new crearGrupoAsync().execute();
				}
			}
		});
	}
	
	protected Boolean validarCoherencia() {
		String descripcionRegEx = "[a-z A-Z]{3,30}";
		Pattern p = Pattern.compile(descripcionRegEx);
		Matcher m = p.matcher(editTextDescripcion.getText().toString());
		if (!m.matches()) {
			Toast.makeText(getApplicationContext(), "La descripcion debe tener entre 3 y 30 caracteres", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	class crearGrupoAsync extends AsyncTask<String, String, String> {

		Boolean exito = false;

		protected void onPreExecute() {
			pDialog = new ProgressDialog(DefinirPuntoEncuentroActivity.this);
			pDialog.setMessage("Creando grupo familiar....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... params) {
			try {
				GrupoFamiliar grupoFamiliar = new GrupoFamiliar(nombreGrupo, puntoEncuentro, lider);
				exito = true;
				return "Grupo familiar creado!";
			} catch (Exception w) {
				return "Ha ocurrido un error al crear el grupo familiar";
			}
		}

		protected void onPostExecute(String s) {
			Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
			if (exito) {
				Intent i = new Intent("at.INVITAR_FAMILIAR");
				startActivityForResult(i,777);
				setResult(Activity.RESULT_OK);
				finish();
			}
			pDialog.dismiss();// ocultamos progess dialog.
		}
	}

}
