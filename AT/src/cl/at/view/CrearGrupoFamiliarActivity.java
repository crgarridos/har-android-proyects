package cl.at.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import cl.at.bussines.Usuario;

import com.google.android.maps.MapActivity;

public class CrearGrupoFamiliarActivity extends MapActivity{

	private EditText editTextNombreGrupo;
//	private MapView mapView;
	private ImageView imgStatusPtoEncuentro;
	private Button btnInvitar;
	private Button btnPtoEncuentro;
	
	private Usuario usuario = new Usuario("crusier","1234");
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crear_grupo_familiar);
		editTextNombreGrupo = (EditText)findViewById(R.id.crearGrupoFamiliar_nombreGrupo);
//		mapView = (MapView)findViewById(R.id.crearGrupoFamiliar_mapview);
//		mapView.setBuiltInZoomControls(true);
		btnInvitar = (Button)findViewById(R.id.crearGrupoFamiliar_btnInvitar);
		btnPtoEncuentro = (Button)findViewById(R.id.crearGrupoFamiliar_btnDefinirPtoEncuentro);
		imgStatusPtoEncuentro = (ImageView)findViewById(R.id.crearGrupoFamiliar_imageEstadoPtoEncuentro);
		
		btnPtoEncuentro.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				imgStatusPtoEncuentro.setImageResource(R.drawable.ticket);
				Toast.makeText(getApplicationContext(), "hola", 2000).show();
				
			}
		});
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
}
