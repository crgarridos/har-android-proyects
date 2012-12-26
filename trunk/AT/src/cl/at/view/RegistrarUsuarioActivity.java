package cl.at.view;

import java.io.Serializable;

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

public class RegistrarUsuarioActivity extends Activity {

	private Button btnSgte;
	private EditText editTextNombreCompleto;
	private EditText editTextNombreUsuario;
	private EditText editTextEmail;
	private EditText editTextPass;
	private EditText editTextPass2;
	private ProgressDialog pDialog;
	public Usuario u;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registrar_usuario);
		btnSgte = (Button) findViewById(R.id.registrarUsuario_btnSgte);
		editTextNombreCompleto = (EditText) findViewById(R.id.registrarUsuario_editTextNombreCompleto);
		editTextNombreUsuario = (EditText) findViewById(R.id.registrarUsuario_editTextNombreUsuario);
		editTextEmail = (EditText) findViewById(R.id.registrarUsuario_editTextEmail);
		editTextPass = (EditText) findViewById(R.id.registrarUsuario_editTextPass);
		editTextPass2 = (EditText) findViewById(R.id.registrarUsuario_editTextPass2);

		btnSgte.setOnClickListener(new View.OnClickListener() {
			public void onClick(View w) {
				if (validarCoherencia(editTextNombreCompleto.getText().toString(), editTextEmail.getText().toString()))
					new asynclogin().execute();
			}
		});
	}

	private Boolean validarCoherencia(String nombreCompleto, String email) {
		return true;
		//
		// String nombreCompletoRegEx = "[a-z A-Z]{3,20}";
		// String emailRegEx = "([a-z._]{3,64})@([a-z._]{3,255}).(com|cl)";
		// String passRegEx = "[^^]{8,15}";
		// Matcher m =
		// Pattern.compile(nombreCompletoRegEx).matcher(nombreCompleto);
		// Matcher m1 = Pattern.compile(emailRegEx).matcher(email);
		// Matcher m2 =
		// Pattern.compile(passRegEx).matcher(this.editTextPass.getText().toString());
		//
		// boolean error =
		// !this.editTextPass.getText().toString().equals(this.editTextPass2.getText().toString());
		// error = !m.matches() || !m1.matches() || !m2.matches() || error;
		//
		// if (!m.matches())
		// Toast.makeText(getApplicationContext(), "Nombre no permitido",
		// Toast.LENGTH_SHORT).show();
		// else if (!m1.matches())
		// Toast.makeText(getApplicationContext(), "Email no permitido",
		// Toast.LENGTH_SHORT).show();
		// else if
		// (!this.editTextPass.getText().toString().equals(this.editTextPass2.getText().toString()))
		// Toast.makeText(getApplicationContext(),
		// "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
		// else if (!m2.matches())
		// Toast.makeText(getApplicationContext(), "Contraseña no permitida",
		// Toast.LENGTH_SHORT).show();
		//
		// return !error;
	}

	class asynclogin extends AsyncTask<String, String, String> {

		private boolean exito = false;
		
		protected void onPreExecute() {
			pDialog = new ProgressDialog(RegistrarUsuarioActivity.this);
			pDialog.setMessage("Comprobando datos....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... params) {
			SystemClock.sleep(500);
			u = new Usuario(editTextNombreUsuario.getText().toString());
			if (!u.getExisteUsuario()) {
				u.setNombreCompleto(editTextNombreCompleto.getText().toString());
				u.setPassword(editTextPass.getText().toString());
				u.setEmail(editTextEmail.getText().toString());
				u.setExisteUsuario(true);
				try {
					u.persistir();
					exito = true;
					return "Cuenta creada exitosamente!";
				} catch (Exception w) {
					return "No se ha podido crear la cuenta";
				}
			} else {
				return "Nombre de usuario ya existente en el sistema";
			}
		}

		protected void onPostExecute(String s) {
			Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
			if (exito) {
				Intent intent = new Intent("at.MAPA");
				intent.putExtra("usuario", (Serializable) u);
				startActivity(intent);
				setResult(Activity.RESULT_OK);
				finish();
			}
			pDialog.dismiss();// ocultamos progess dialog.
		}
	}

}
