package cl.at.view;

import chichi.mapa.R;
import cl.at.bussines.Usuario;
import cl.at.util.*;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class IniciarSesionActivity extends Activity {

	private Button btnSgte;
	private EditText editTextNombre;
	private EditText editTextPass;
	private ProgressDialog pDialog;
	int respuesta;
	public Usuario u;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.iniciar_sesion);
		btnSgte = (Button) findViewById(R.id.iniciarSesion_btnSgte);
		editTextNombre = (EditText) findViewById(R.id.iniciarSesion_editTextNombre);
		editTextPass = (EditText) findViewById(R.id.iniciarSesion_editTextPass);

		btnSgte.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (validarCoherencia()) {
					u = new Usuario(editTextNombre.getText().toString(),
							editTextPass.getText().toString());
					new asynclogin().execute();
				}

			}
		});
	}

	private Boolean validarCoherencia() {
		// Validar la coherencia
		return true;
	}

	class asynclogin extends AsyncTask<String, String, String> {

		protected void onPreExecute() {
			pDialog = new ProgressDialog(IniciarSesionActivity.this);
			pDialog.setMessage("Autentificando....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... params) {
			if (u.getExisteUsuario()) {
				return "ok";
			} else {
				return "err";
			}
		}

		protected void onPostExecute(String result) {
			pDialog.dismiss();// ocultamos progess dialog.
			if (result == "ok") {
				Toast.makeText(getApplicationContext(),
						"Bienvenido" + u.getNombreCompleto(),
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(),
						"Error en los datos ingresados", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

}
