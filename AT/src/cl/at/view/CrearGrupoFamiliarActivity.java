package cl.at.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.maps.MapActivity;

public class CrearGrupoFamiliarActivity extends MapActivity {

	private EditText editTextNombreGrupo;
	private Button btnSgte;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crear_grupo_familiar);
		editTextNombreGrupo = (EditText) findViewById(R.id.crearGrupoFamiliar_editTextNombreGrupoFamiliar);
		btnSgte = (Button) findViewById(R.id.crearGrupoFamiliar_btnSgte);
		btnSgte.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (validarCoherencia()) {
					Intent i = new Intent("at.DEFINIR_PUNTO_ENCUENTRO_MAP");
					i.putExtra("nombreGrupo", editTextNombreGrupo.getText().toString());
					startActivityForResult(i,777);
				}
			}
		});
	}

	private Boolean validarCoherencia() {
		String nombreGrupoFamiliarRegEx = "[a-z A-Z]{3,20}";
		Pattern p = Pattern.compile(nombreGrupoFamiliarRegEx);
		Matcher m = p.matcher(editTextNombreGrupo.getText().toString());
		if (!m.matches()) {
			Toast.makeText(getApplicationContext(), "El nombre del grupo debe tener entre 3 y 20 caracteres", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if ((resultCode != Activity.RESULT_CANCELED) && (requestCode == 777)) {
			finish();
		}
	}
	

}
