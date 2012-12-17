package cl.at.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BienvenidaActivity extends Activity {

	private Button btnRegistrar;
	private Button btnIniciarSesion;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bienvenida);
		btnRegistrar = (Button) findViewById(R.id.bienvenida_btnRegistrar);
		btnIniciarSesion = (Button) findViewById(R.id.bienvenida_btnIniciarSesion);
		
		btnRegistrar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent("registrarUsuario");	
				startActivity(i);
			}
		});
		
		btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent("iniciarSesion");	
				startActivity(i);
			}
		});
	}
}
