package cl.at.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cl.at.bussines.Invitacion;
import cl.at.bussines.Usuario;
import cl.at.util.Comunicador;

public class InvitarFamiliar extends Activity {

	private EditText editTextCadenaBusqueda;
	private TextView textViewNombreUsuario;
	private TextView textViewNombreCompleto;
	private TextView textViewEmail;
	private Button btnSalir;
	private Button btnInvitarFamiliar;
	private Button btnBuscar;

	private Usuario u;
	private Usuario thisUsuario;

	private Comunicador com;
	private ProgressDialog pDialog;

	private Boolean exito = false;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.invitar_familiar);

		com = Comunicador.getInstancia();
		thisUsuario = com.getUsuario();

		editTextCadenaBusqueda = (EditText) findViewById(R.id.invitarFamiliar_editTextCadenaBusqueda);
		textViewNombreUsuario = (TextView) findViewById(R.id.invitarFamiliar_textViewNombreUsuario);
		textViewNombreCompleto = (TextView) findViewById(R.id.invitarFamiliar_textViewNombreCompleto);
		textViewEmail = (TextView) findViewById(R.id.invitarFamiliar_textViewEmail);
		btnSalir = (Button) findViewById(R.id.invitarFamiliar_btnSalir);
		btnInvitarFamiliar = (Button) findViewById(R.id.invitarFamiliar_btnInvitarFamiliar);
		btnBuscar = (Button) findViewById(R.id.invitarFamiliar_btnBuscar);

		btnSalir.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		btnInvitarFamiliar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (exito)
					new InvitarFamiliarAsync().execute();
			}
		});

		btnBuscar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new buscarUsuarioAsync().execute();
				// TODO CDU05 - Buscar usuario
			}
		});
	}

	class buscarUsuarioAsync extends AsyncTask<String, String, String> {

		Usuario usuario;

		protected void onPreExecute() {
			pDialog = new ProgressDialog(InvitarFamiliar.this);
			pDialog.setMessage("Buscando usuario ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			textViewNombreUsuario.setText("");
			textViewNombreCompleto.setText("");
			textViewEmail.setText("");
			pDialog.show();
		}

		protected String doInBackground(String... params) {
			try {
				usuario = new Usuario();
				u = usuario.buscar(editTextCadenaBusqueda.getText().toString());
				if (u != null) {
					if (!u.getNombreUsuario().equals(thisUsuario.getNombreUsuario())) {
						exito = true;
						return "existe";
					} else {
						exito = false;
						return "No se puede invitar a si mismo";
					}
				} else {
					exito = false;
					return "No se encontra el usuario con el dato ingresado";
				}
			} catch (Exception w) {
				Log.d("Error:", w.toString());
				exito = false;
				return "Ha ocurrido un error al buscar al usuario";
			}
		}

		protected void onPostExecute(String s) {
			if (!s.equals("existe"))
				Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
			if (exito) {
				textViewNombreUsuario.setText("Nombre usuario: " + u.getNombreUsuario());
				textViewNombreCompleto.setText("Nombre completo: " + u.getNombreCompleto());
				textViewEmail.setText("Email: " + u.getEmail());
				btnInvitarFamiliar.setVisibility(0);
			} else {
				btnInvitarFamiliar.setVisibility(8);
			}
			pDialog.dismiss();// ocultamos progess dialog.
		}
	}

	class InvitarFamiliarAsync extends AsyncTask<String, String, String> {

		Boolean exito = false;
		Usuario usuario;

		protected void onPreExecute() {
			pDialog = new ProgressDialog(InvitarFamiliar.this);
			pDialog.setMessage("Invitado usuario al grupo familiar ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... params) {
			try {
				if (u.getGrupoFamiliar() == null) {
					Invitacion invitacion = new Invitacion(u, thisUsuario);
					invitacion.persistir();
					exito = true;
					return "El usuario fue invitado exitosamente!";
				} else {
					return "El usuario ya pertenece a un grupo familiar";
				}
			} catch (Exception w) {
				Log.d("Error:", w.toString());
				return "Ha ocurrido un error al invitar al usuario";
			}
		}

		protected void onPostExecute(String s) {
			Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
			pDialog.dismiss();// ocultamos progess dialog.
			if (exito)
				finish();
		}
	}

}
