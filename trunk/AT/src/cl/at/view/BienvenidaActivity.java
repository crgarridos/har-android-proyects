package cl.at.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import cl.at.util.Util;

public class BienvenidaActivity extends Activity {

	private Button btnRegistrar;
	private Button btnIniciarSesion;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!Util.hayInternet(this)) {
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("No hay conexion a internet");
			alert.setMessage("Debe haber una conexion a internet para el uso correcto de la aplicacion, pruebe activando la conexion de datos (Puede causar gastos adicionales), o conectandose a una red wi-fi.");

			alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});
			alert.create().show();
		} 
		else {
			if (Util.getPreferencia("usuario") != null) {
				startActivity(new Intent("at.MAPA"));
				finish();
			}
			else {
				Util.guardarIntervalo("600000");
				Util.guardarEstadoCapaGrupoFamiliar(true);
				Util.guardarEstadoCapaPuntosDeRiesgo(true);
			}
			setContentView(R.layout.bienvenida);
			btnRegistrar = (Button) findViewById(R.id.bienvenida_btnRegistrar);
			btnIniciarSesion = (Button) findViewById(R.id.bienvenida_btnIniciarSesion);

			btnRegistrar.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent("at.REGISTRAR_USUARIO");
					startActivityForResult(i, 777);
				}
			});

			btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent("at.INICIAR_SESION");
					startActivityForResult(i, 777);
				}
			});
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if ((resultCode != Activity.RESULT_CANCELED) && (requestCode == 777)) {
			finish();
		}
	}
}
