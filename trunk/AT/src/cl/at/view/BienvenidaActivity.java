package cl.at.view;

import cl.at.util.Util;
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
		
		if(Util.getPreferencia("usuario")!=null){
			startActivity(new Intent("at.MAPA"));
			finish();
		}
		
		setContentView(R.layout.bienvenida);
		btnRegistrar = (Button) findViewById(R.id.bienvenida_btnRegistrar);
		btnIniciarSesion = (Button) findViewById(R.id.bienvenida_btnIniciarSesion);
		
		btnRegistrar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent("at.REGISTRAR_USUARIO");	
				startActivityForResult(i,777);
			}
		});
		
		btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent("at.INICIAR_SESION");
				startActivityForResult(i,777);
			}
		});

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if ((resultCode != Activity.RESULT_CANCELED) && (requestCode == 777)) {
			finish();
		}
	}
}
