package cl.at.data;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import cl.at.bussines.Coordenada;
import cl.at.bussines.Dispositivo;
import cl.at.bussines.Usuario;
import cl.at.util.Parametros;

public class DispositivoSQL {

	private static final String TAG = DispositivoSQL.class.getName();
//	private static final String CAMPO_NOMBRE_USUARIO = "NOMBRE_USUARIO";
	private static final String CAMPO_ULTIMA_LATITUD = "ULTIMA_LATITUD_DISPOSITIVO";
	private static final String CAMPO_ULTIMA_LONGITUD = "ULTIMA_LONGITUD_DISPOSITIVO";
	
	ConexionHttp post;

	public DispositivoSQL() {
		post = new ConexionHttp();
	}

	public Boolean getUltimaPosicion(Usuario usuario, Dispositivo dispositivo) {
		try {
			Parametros postParametersToSend = new Parametros();
			postParametersToSend.add("nombreUsuario", usuario.getNombreUsuario());
			JSONArray jdata = null;
			jdata = post.getServerData(postParametersToSend, ConexionHttp.URL_CONNECT + "getUltimaPosicion.php");
			if (jdata != null) {
				JSONObject json_data = jdata.getJSONObject(0); // Se lee la respuesta
				try {
					Coordenada coordenada = new Coordenada(json_data.getDouble(CAMPO_ULTIMA_LATITUD), json_data.getDouble(CAMPO_ULTIMA_LONGITUD));
					dispositivo.setPosicion(coordenada);
					return true;
				} catch (Exception e) {
					Log.e(TAG, "cargarDispositivo, " + e.toString());
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "cargarDispositivo, " + e.toString());
		}
		return false;
	}
	
	public Boolean actualizarPosicion(Dispositivo dispositivo) {
		try {
			Parametros postParametersToSend = new Parametros();
			postParametersToSend.add("nombreUsuario", dispositivo.getUsuario().getNombreUsuario());
			postParametersToSend.add("latitudDispositivo", dispositivo.getPosicion().getLatitud().toString());
			postParametersToSend.add("longitudDispositivo", dispositivo.getPosicion().getLongitud().toString());
			JSONArray jdata = null;
			jdata = post.getServerData(postParametersToSend, ConexionHttp.URL_CONNECT + "actualizarPosicion.php");
			if (jdata != null) {
				return true;
			}
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		return false;
	}
	
}
