package cl.at.view;

import java.util.ArrayList;

import cl.at.bussines.GrupoFamiliar;
import cl.at.bussines.Comentario;
import cl.at.bussines.Usuario;
import cl.at.util.Comunicador;
import cl.at.util.Util;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListComentariosActivity extends Activity {

	private final String TAG = ListComentariosActivity.class.getName();

	Usuario usuario;
	ArrayList<Comentario> Comentarios;
	ListView lv;
	TextView seleccionado;
	Button btn;
	EditText contenido;
	int posicionSeleccionada;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_de_comentarios);

		usuario = Comunicador.getInstancia().getUsuario();

		lv = (ListView) findViewById(R.id.listaComentario_lista);
		btn = (Button) findViewById(R.id.listaComentario_btn);
		contenido = (EditText) findViewById(R.id.listaComentario_nuevoComentario);

		// lv.setOnItemClickListener(new OnItemClickListener() {
		// public void onItemClick(AdapterView<?> parent, View view, final int
		// position, final long id) {
		// AlertDialog.Builder alert = new
		// AlertDialog.Builder(ListComentariosActivity.this);
		// alert.setTitle("Aceptar Comentario");
		// String nombre =
		// usuario.getComentarios().get(position).getRemitente().getNombreUsuario();
		// String mensaje = "¿Que desea hacer con la invitación  de " + nombre +
		// "?"
		// +
		// "\nAl aceptar la Comentario se eliminaran las otras y serás incluido en el grupo familiar de "
		// + nombre
		// +
		// ".\nSi la rechaza, se eliminara la Comentario y ya no volvera a estar disponible.";
		// alert.setMessage(mensaje);
		// posicionSeleccionada = position;
		// alert.setPositiveButton("Aceptar", new
		// DialogInterface.OnClickListener() {
		// public void onClick(DialogInterface dialog, int which) {
		// new
		// AsyncResponder().execute(usuario.getComentarios().get(position).getRemitente().getGrupoFamiliar());
		// }
		// });
		// alert.setNegativeButton("Rechazar", new
		// DialogInterface.OnClickListener() {
		// public void onClick(DialogInterface dialog, int which) {
		// new AsyncDeleteComentario().execute(false);
		// }
		// });
		// alert.setCancelable(true);
		// final AlertDialog dialog = alert.create();
		// dialog.show();
		// }
		// });

		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				publicarComentario();
			}
		});

		new AsyncComentarios().execute();
	}

	protected void publicarComentario() {
		new AsyncComentar().execute();
	}

	public void mostrarMensajeConfirmacion() {

	}

	class AsyncComentarios extends AsyncTask<String, String, ArrayList<Comentario>> {

		ProgressDialog pDialog;

		protected void onPreExecute() {
			pDialog = new ProgressDialog(ListComentariosActivity.this);
			pDialog.setMessage("Cargando Comentarios...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected ArrayList<Comentario> doInBackground(String... params) {
			return usuario.getGrupoFamiliar().getComentarios();
		}

		protected void onPostExecute(ArrayList<Comentario> coms) {
			if (coms != null)
				lv.setAdapter(new ComentarioAdapter(ListComentariosActivity.this, coms));
			else
				Toast.makeText(getApplicationContext(), "No hay Comentarios por el momento.", Toast.LENGTH_LONG).show();
			pDialog.dismiss();
		}
	}

	class AsyncComentar extends AsyncTask<String, String, Boolean> {

		ProgressDialog pDialog;

		protected void onPreExecute() {
			if (contenido.getText().toString().length() > 120)
				Toast.makeText(getApplicationContext(), "El contenido debe ser menor a 120 caracteres", Toast.LENGTH_SHORT).show();
			else {
				pDialog = new ProgressDialog(ListComentariosActivity.this);
				pDialog.setMessage("Integrando al grupo familiar...");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(false);
				pDialog.show();
			}
		}

		@Override
		protected Boolean doInBackground(String... c) {
			GrupoFamiliar gf = usuario.getGrupoFamiliar();
			Comentario comentario = new Comentario(contenido.getText().toString(), usuario);
			return gf.addComentario(comentario);
		}

		protected void onPostExecute(Boolean est) {
			if (est) 
				Toast.makeText(getApplicationContext(), "El comentario ha sido añadido al grupo exitosamente!", Toast.LENGTH_LONG).show();
			else
				Toast.makeText(getApplicationContext(), "No se ha podido realizar la operacion, intentelo mas tarde", Toast.LENGTH_LONG).show();
			pDialog.dismiss();
			finish();
		}
	}

	class ComentarioAdapter extends BaseAdapter {

		protected Activity activity;
		protected ArrayList<Comentario> items;

		public ComentarioAdapter(Activity activity, ArrayList<Comentario> items) {
			this.activity = activity;
			this.items = items;
		}

		public ComentarioAdapter(Activity activity, Comentario[] items) {
			this.activity = activity;
			ArrayList<Comentario> tempItems = new ArrayList<Comentario>();
			for (int i = 0; i < items.length; i++)
				tempItems.add(items[i]);
			this.items = tempItems;
		}

		public int getCount() {
			return items.size();
		}

		public Object getItem(int position) {
			return items.get(position);
		}

		public long getItemId(int position) {
			return 1;
			// return items.get(position).getId();
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View fila = convertView;
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				fila = inflater.inflate(R.layout.list_view_comentarios, null);
			}

			Comentario comentario = items.get(position);

			TextView remitente = (TextView) fila.findViewById(R.id.listViewComentario_nombreRemitente);
			remitente.setText("Enviado por: " + comentario.getUsuario().getNombreUsuario());

			TextView contenido = (TextView) fila.findViewById(R.id.listViewComentario_contenido);
			contenido.setText(comentario.getContenido());

			TextView hora = (TextView) fila.findViewById(R.id.listViewComentario_instante);
			hora.setText(Util.HourToString(comentario.getFecha()) + " " + Util.DateToString(comentario.getFecha()));

			fila.setBackgroundColor(getItemViewType(position) == 1 ? Color.rgb(0xE6, 0xE6, 0xE6) : Color.rgb(0xF6, 0xF6, 0xF6));
			return fila;
		}

		@Override
		public int getItemViewType(int position) {
			return position % 2; // posicion par o impar
		}
	}
}
