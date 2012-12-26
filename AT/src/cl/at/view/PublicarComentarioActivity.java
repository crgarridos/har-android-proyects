package cl.at.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cl.at.bussines.Comentario;
import cl.at.bussines.Usuario;
import cl.at.util.Comunicador;
import cl.at.util.Util;

public class PublicarComentarioActivity extends Activity {

	private Comunicador com;
	private Usuario usuario;
	private Button btnSgte;
	private EditText editTextComentario;
	private ProgressDialog pDialog;
	private Comentario comentario;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.publicar_comentario);
		com = Comunicador.getInstancia();
		usuario = com.getUsuario();

		btnSgte = (Button) findViewById(R.id.publicarComentario_btnSgte);
		editTextComentario = (EditText) findViewById(R.id.publicarComentario_editTextComentario);

		btnSgte.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (usuario.getGrupoFamiliar() != null) {
					if (editTextComentario.getText().toString().length() < 121 && editTextComentario.getText().toString().length() > 3) {
						comentario = new Comentario(editTextComentario.getText().toString(), usuario);
						usuario.getGrupoFamiliar().addComentario(comentario);
						new AsyncPublicarComentario().execute();
					} else {
						Toast.makeText(getApplicationContext(), "El comentario debe tener entre 4 y 120 caracteres", Toast.LENGTH_SHORT).show();
					}
					
				} else {
					Toast.makeText(getApplicationContext(), "No puede publicar comentarios porque no pertenece a un grupo familiar", Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	class AsyncPublicarComentario extends AsyncTask<String, String, String> {

		protected void onPreExecute() {
			pDialog = new ProgressDialog(PublicarComentarioActivity.this);
			pDialog.setMessage("Ingresando comentario");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... params) {
			try {
				comentario.persistir();
				return "Comentario ingresado exitosamente!";
			} catch (Exception w) {
				return "Ha ocurrido un error";
			}
		}

		protected void onPostExecute(String s) {
			pDialog.dismiss();
			Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
			
		}
	}




}
