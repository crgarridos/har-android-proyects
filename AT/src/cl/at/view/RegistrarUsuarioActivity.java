package cl.at.view;

import cl.at.bussines.Usuario;
import cl.at.view.IniciarSesionActivity.asynclogin;
import android.accounts.Account;
import android.accounts.AccountManager;
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
import cl.at.bussines.*;
import java.util.regex.*;

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
		String nombreCompletoRegEx = "[a-z A-Z]{3,20}";
		String emailRegEx = "([a-z._]{3,64})@([a-z._]{3,255}).(com|cl)";
		String passRegEx = "[a-zA-Z_.0-9-*]{8,15}";
		Pattern p = Pattern.compile(nombreCompletoRegEx);
		Pattern p1 = Pattern.compile(emailRegEx);
		Pattern p2 = Pattern.compile(passRegEx);
		Matcher m = p.matcher(nombreCompleto);
		Matcher m1 = p1.matcher(email);
		Matcher m2 = p2.matcher(this.editTextPass.getText().toString());
		if (!m.matches()) {
			Toast.makeText(getApplicationContext(), "Nombre no permitido", Toast.LENGTH_SHORT).show();
			return false;
		} else {
			if (!m1.matches()) {
				Toast.makeText(getApplicationContext(), "Email no permitido", Toast.LENGTH_SHORT).show();
				return false;
			} else {
				if(!this.editTextPass.getText().toString().equals(this.editTextPass2.getText().toString())){
					Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
					return false;
				}else if (!m2.matches()){
					Toast.makeText(getApplicationContext(), "Contraseña no permitida", Toast.LENGTH_SHORT).show();
					return false;
				}
			}
			return true;
		}
	}

	class asynclogin extends AsyncTask<String, String, Boolean> {

		protected void onPreExecute() {
			pDialog = new ProgressDialog(RegistrarUsuarioActivity.this);
			pDialog.setMessage("Comprobando datos....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected Boolean doInBackground(String... params) {
			SystemClock.sleep(500);
			u = new Usuario(editTextNombreUsuario.getText().toString());
			return u.getExisteUsuario();
		}

		protected void onPostExecute(Boolean e) {
			pDialog.dismiss();// ocultamos progess dialog.
			if (!e) {
				u.setNombreCompleto(editTextNombreCompleto.getText().toString());
				u.setPassword(editTextPass.getText().toString());
				u.setEmail(editTextEmail.getText().toString());
				u.setExisteUsuario(true);
				try {
					u.persistir();
					Toast.makeText(getApplicationContext(), "¡Cuenta creada exitosamente!", Toast.LENGTH_SHORT).show();
				} catch (Exception w) {
					Toast.makeText(getApplicationContext(), "No se ha podido crear la cuenta", Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(getApplicationContext(), "Nombre de usuario ya existente en el sistema", Toast.LENGTH_SHORT).show();
			}
		}
	}

}
