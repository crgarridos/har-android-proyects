package cl.at.view;

import java.util.ArrayList;

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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cl.at.bussines.GrupoFamiliar;
import cl.at.bussines.Invitacion;
import cl.at.bussines.Usuario;
import cl.at.util.Comunicador;

public class ListInvitacionesActivity extends Activity {

	ProgressDialog pDialog;

	private Usuario usuario;
	private ListView lv;
	private Button btn;
	private int posicionSeleccionada;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_de_invitaciones);

		usuario = Comunicador.getInstancia().getUsuario();

		lv = (ListView) findViewById(R.id.listaInvitacion_lista);
		btn = (Button) findViewById(R.id.listaInvitacion_btnEmpezar);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
				AlertDialog.Builder alert = new AlertDialog.Builder(ListInvitacionesActivity.this);
				alert.setTitle("Aceptar Invitacion");
				String nombre = usuario.getInvitaciones().get(position).getRemitente().getNombreUsuario();
				String mensaje = "Que desea hacer con la invitacin  de " + nombre + "?"
						+ "\nAl aceptar la invitacion se eliminaran las otras y ser�s incluido en el grupo familiar de " + nombre
						+ ".\nSi la rechaza, se eliminara la invitacion y ya no volvera a estar disponible.";
				alert.setMessage(mensaje);
				posicionSeleccionada = position;
				alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						new AsyncResponder().execute(usuario.getInvitaciones().get(position).getRemitente());
					}
				});
				alert.setNegativeButton("Rechazar", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						new AsyncDeleteInvitacion().execute(false);
					}
				});
				alert.setCancelable(true);
				final AlertDialog dialog = alert.create();
				dialog.show();
			}
		});

		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

		new AsyncInvitaciones().execute();
	}

	public void mostrarMensajeConfirmacion() {

	}

	class AsyncInvitaciones extends AsyncTask<String, String, ArrayList<Invitacion>> {

		ProgressDialog pDialog;

		protected void onPreExecute() {
			pDialog = new ProgressDialog(ListInvitacionesActivity.this);
			pDialog.setMessage("Cargando Invitaciones");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected ArrayList<Invitacion> doInBackground(String... params) {
			ArrayList<Invitacion> inv = usuario.getInvitaciones();
			for(int i = 0; i < inv.size(); i++)
				inv.get(i).getRemitente().getGrupoFamiliar();
			return inv;
		}

		protected void onPostExecute(ArrayList<Invitacion> invs) {
			if (invs != null)
				lv.setAdapter(new InvitacionAdapter(ListInvitacionesActivity.this, invs));
			else
				Toast.makeText(getApplicationContext(), "No hay invitaciones por el momento.", Toast.LENGTH_LONG).show();
			pDialog.dismiss();
		}
	}

	class AsyncResponder extends AsyncTask<Usuario, String, Boolean> {

		ProgressDialog pDialog;

		protected void onPreExecute() {
			pDialog = new ProgressDialog(ListInvitacionesActivity.this);
			pDialog.setMessage("Integrando al grupo familiar...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Boolean doInBackground(Usuario... u) {
			GrupoFamiliar gf = u[0].getGrupoFamiliar();
			return gf.addIntegrante(usuario);
		}

		protected void onPostExecute(Boolean est) {
			if (est) {
				new AsyncDeleteInvitacion().execute(true);
				Toast.makeText(getApplicationContext(), "El usuario ha sido a�adido al grupo exitosamente!", Toast.LENGTH_LONG).show();
			} else
				Toast.makeText(getApplicationContext(), "No se ha podido realizar la operacion, intentelo mas tarde", Toast.LENGTH_LONG).show();
			pDialog.dismiss();
		}
	}

	class AsyncDeleteInvitacion extends AsyncTask<Boolean, String, Boolean> {

		ProgressDialog pDialog;

		protected void onPreExecute() {
			pDialog = new ProgressDialog(ListInvitacionesActivity.this);
			pDialog.setMessage("Anulando invitacion(es)...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Boolean doInBackground(Boolean... todas) {
			if (todas[0])
				return usuario.vaciarInvitaciones();
			return usuario.rechazarInvitacion(posicionSeleccionada);
		}

		protected void onPostExecute(Boolean est) {
			if (est) {
				Toast.makeText(getApplicationContext(), "La operacion ha terminado exitosamente!", Toast.LENGTH_LONG).show();
			} else
				Toast.makeText(getApplicationContext(), "No se ha podido realizar la operacion, intentelo mas tarde", Toast.LENGTH_LONG).show();
			pDialog.dismiss();
			finish();
		}
	}

	class InvitacionAdapter extends BaseAdapter {

		protected Activity activity;
		protected ArrayList<Invitacion> items;

		public InvitacionAdapter(Activity activity, ArrayList<Invitacion> items) {
			this.activity = activity;
			this.items = items;
		}

		public InvitacionAdapter(Activity activity, Invitacion[] items) {
			this.activity = activity;
			ArrayList<Invitacion> tempItems = new ArrayList<Invitacion>();
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
				fila = inflater.inflate(R.layout.list_view_invitaciones, null);
			}

			Invitacion invitacion = items.get(position);

			TextView nombreFamilia = (TextView) fila.findViewById(R.id.listViewInvitacion_nombreFamilia);
			String nombreTmp = invitacion.getRemitente().getGrupoFamiliar().getNombre();
			nombreFamilia.setText((nombreTmp.toLowerCase().contains("familia") ? "" : "Familia ") + nombreTmp);

			TextView remitente = (TextView) fila.findViewById(R.id.listViewInvitacion_nombreRemitente);
			remitente.setText("Invitado por: " + invitacion.getRemitente().getNombreUsuario());

			TextView remitenteCompleto = (TextView) fila.findViewById(R.id.listViewInvitacion_nombreCompletoRemitente);
			remitenteCompleto.setText(invitacion.getRemitente().getNombreCompleto());

			TextView remitenteEmail = (TextView) fila.findViewById(R.id.listViewInvitacion_emailRemitente);
			remitenteEmail.setText(invitacion.getRemitente().getEmail());

			fila.setBackgroundColor(getItemViewType(position) == 1 ? Color.rgb(0xE6, 0xE6, 0xE6) : Color.rgb(0xF6, 0xF6, 0xF6));
			return fila;
		}

		@Override
		public int getItemViewType(int position) {
			return position % 2; // posicion par o impar
		}
	}
}
