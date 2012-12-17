package cl.at.view;

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
import cl.at.bussines.Usuario;

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
					new asynclogin().execute();
				}

			}
		});
	}

	private Boolean validarCoherencia() {
		// Validar la coherencia
		return true;
	}

	class asynclogin extends AsyncTask<String, String, Boolean> {

		protected void onPreExecute() {
			pDialog = new ProgressDialog(IniciarSesionActivity.this);
			pDialog.setMessage("Autentificando....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected Boolean doInBackground(String... params) {
			SystemClock.sleep(500);
			u = new Usuario(editTextNombre.getText().toString(), editTextPass.getText().toString());
			return u.getExisteUsuario();
		}

		protected void onPostExecute(Boolean existe) {
			pDialog.dismiss();// ocultamos progess dialog.
			if (existe) {
				Toast.makeText(getApplicationContext(), "Bienvenido " + u.getNombreCompleto(), Toast.LENGTH_SHORT).show();
				Intent intent = new Intent("mapa");
				startActivity(intent);
			} else {
				Toast.makeText(getApplicationContext(), "Error en los datos ingresados", Toast.LENGTH_SHORT).show();
			}
		}
	}

}
