package cl.at.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import cl.at.bussines.Ciudad;
import cl.at.bussines.Coordenada;
import cl.at.bussines.Dispositivo;
import cl.at.bussines.GrupoFamiliar;
import cl.at.bussines.Punto;
import cl.at.bussines.Usuario;
import cl.at.util.Parametros;

public class CiudadSQL {
	
	private static final String TAG = CiudadSQL.class.getName();
	private static final String CAMPO_ID_CIUDAD = "ID_CIUDAD";
	private static final String CAMPO_LATITUD_CENTRO_CIUDAD = "LATITUD_CENTRO_CIUDAD";
	private static final String CAMPO_LONGITUD_CENTRO_CIUDAD = "LONGITUD_CENTRO_CIUDAD";
	private static final String CAMPO_NOMBRE_CIUDAD = "NOMBRE_CIUDAD";
	
	ConexionHttp post;

	public CiudadSQL() {
		post = new ConexionHttp();
	}

	public Boolean cargarCiudad(Ciudad ciudad, String nombreCiudad) {
		try {
			Parametros postParametersToSend = new Parametros();
			postParametersToSend.add("nombreCiudad", nombreCiudad);
			JSONArray jdata = null;
			jdata = post.getServerData(postParametersToSend, ConexionHttp.URL_CONNECT + "getCiudad.php");
			if (jdata != null) {
				JSONObject json_data = jdata.getJSONObject(0); // Se lee la respuesta
				try {
					ciudad.setId(json_data.getInt(CAMPO_ID_CIUDAD));
					ciudad.setCoordenada(new Coordenada(json_data.getDouble(CAMPO_LATITUD_CENTRO_CIUDAD), json_data.getDouble(CAMPO_LONGITUD_CENTRO_CIUDAD)));
					return true;
				} catch (Exception e) {
					Log.e(TAG, e.toString());
				}
			}
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		return false;
	}
}
