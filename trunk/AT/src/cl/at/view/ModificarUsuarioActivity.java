package cl.at.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cl.at.bussines.Usuario;
import cl.at.util.Comunicador;
import cl.at.util.Util;

public class ModificarUsuarioActivity extends Activity {

	private Button btnSgte;
	private EditText editTextNombreCompleto;
	private EditText editTextEmail;
	private EditText editTextPassActual;
	private EditText editTextPassNueva;
	private EditText editTextPassNueva2;
	private ProgressDialog pDialog;
	private Context context;

	private Usuario u;
	
	private Comunicador com;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		com = Comunicador.getInstancia();
		setContentView(R.layout.modificar_usuario);
		context = getApplicationContext();
		btnSgte = (Button) findViewById(R.id.modificarUsuario_btnSgte);
		editTextNombreCompleto = (EditText) findViewById(R.id.modificarUsuario_editTextNombreCompleto);
		editTextEmail = (EditText) findViewById(R.id.modificarUsuario_editTextEmail);
		editTextPassActual = (EditText) findViewById(R.id.modificarUsuario_editTextPassActual);
		editTextPassNueva = (EditText) findViewById(R.id.modificarUsuario_editTextPassNueva);
		editTextPassNueva2 = (EditText) findViewById(R.id.modificarUsuario_editTextPassNueva2);
		
		u = com.getUsuario();
		editTextNombreCompleto.setText(u.getNombreCompleto());
		editTextEmail.setText(u.getEmail());


		btnSgte.setOnClickListener(new View.OnClickListener() {
			public void onClick(View w) {
				if (validarCoherencia(editTextNombreCompleto.getText().toString(), editTextEmail.getText().toString()))
					new AsyncModificar().execute();
			}
		});
	}

	private Boolean validarCoherencia(String nombreCompleto, String email) {
		String nombreCompletoRegEx = "[a-z A-Z]{3,20}";
		String emailRegEx = "([a-z._]{3,64})@([a-z._]{3,255}).(com|cl)";
		String passRegEx = "[a-zA-Z_.0-9-*]{4,15}";
		Pattern p = Pattern.compile(nombreCompletoRegEx);
		Pattern p1 = Pattern.compile(emailRegEx);
		Pattern p2 = Pattern.compile(passRegEx);
		Matcher m = p.matcher(nombreCompleto);
		Matcher m1 = p1.matcher(email);
		Matcher m2 = p2.matcher(this.editTextPassNueva.getText().toString());
		if (!m.matches()) {
			Toast.makeText(getApplicationContext(), "Nombre no permitido, debe tener un maximo de 20", Toast.LENGTH_SHORT).show();
			return false;
		} else {
			if (!m1.matches()) {
				Toast.makeText(getApplicationContext(), "Email no permitido", Toast.LENGTH_SHORT).show();
				return false;
			} else if (!this.editTextPassNueva.getText().toString().equals(this.editTextPassNueva2.getText().toString())) {
				Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
				return false;
			} else if (!m2.matches()) {
				Toast.makeText(getApplicationContext(), "Contraseña no permitida", Toast.LENGTH_SHORT).show();
				return false;
			} else if (!u.getPassword().equals(this.editTextPassActual.getText().toString())) {
				Toast.makeText(getApplicationContext(), "Contraseña actual incorrecta", Toast.LENGTH_SHORT).show();
				return false;
			}
			return true;
		}
	}

	class AsyncModificar extends AsyncTask<String, String, String> {

		protected void onPreExecute() {
			pDialog = new ProgressDialog(ModificarUsuarioActivity.this);
			pDialog.setMessage("Comprobando datos....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... params) {
			u.setNombreCompleto(editTextNombreCompleto.getText().toString());
			u.setEmail(editTextEmail.getText().toString());
			u.setPassword(editTextPassNueva.getText().toString());
			try {
				u.persistir();
				return "Datos modificados exitosamente!";
			} catch (Exception w) {
				return "No se han podido modificar los datos";
			}
		}

		protected void onPostExecute(String s) {
			Util.guardarUsuario(u, context);
			Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
			pDialog.dismiss();// ocultamos progess dialog.
		}
	}

}
