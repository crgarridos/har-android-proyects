package cl.at.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import cl.at.bussines.Ciudad;
import cl.at.bussines.Coordenada;
import cl.at.bussines.Punto;
import cl.at.bussines.Usuario;
import cl.at.util.Parametros;

public class PuntoSQL {
	
	private static final String TAG = PuntoSQL.class.getName();
	private static final String CAMPO_ID_PUNTO = "ID_PUNTO";
	private static final String CAMPO_ID_CIUDAD = "ID_CIUDAD";
	private static final String CAMPO_LATITUD_PUNTO = "LATITUD_PUNTO";
	private static final String CAMPO_LONGITUD_PUNTO = "LONGITUD_PUNTO";

	ConexionHttp post;

	public PuntoSQL() {
		post = new ConexionHttp();
	}
	
	public ArrayList<Coordenada> cargarAreaInundacion(final Ciudad ciudad) {
		try {
			Parametros postParametersToSend = new Parametros();
			postParametersToSend.add("id", ciudad.getId().toString());
			JSONArray jdata = null;
			jdata = post.getServerData(postParametersToSend, ConexionHttp.URL_CONNECT + "getAreaInundacion.php");
			if (jdata != null) {
				ArrayList<Coordenada> areaInundacion = new ArrayList<Coordenada>();
				for (int i = 0; i < jdata.length(); i++) {
					try {
						JSONObject jsonData = jdata.getJSONObject(i);
						Coordenada coordenada = new Coordenada(jsonData.getDouble(CAMPO_LATITUD_PUNTO), jsonData.getDouble(CAMPO_LONGITUD_PUNTO));
						areaInundacion.add(coordenada);
					} catch (Exception e) {
						Log.e(TAG, e.toString());
					}
				}
				return areaInundacion;
			}
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		return null;
	}
}
