package cl.at.data;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import cl.at.bussines.Usuario;
import cl.at.util.Parametros;

public class UsuarioSQL {

	private static final String TAG = UsuarioSQL.class.getName();
	String URL_connect = "http://acinfo.unap.cl/jvega/Alerttsunami/";
	ConexionHttp post;

	public UsuarioSQL() {
		post = new ConexionHttp();
	}

	public Boolean cargarUsuario(Usuario u) {
		try {
			Parametros postParametersToSend = new Parametros();
			postParametersToSend.add("nombre", u.getNombreUsuario());
			postParametersToSend.add("pass", u.getPassword());
			JSONArray jdata = null;
			jdata = post.getServerData(postParametersToSend, URL_connect + "getPersona.php");
			if (jdata != null) {
				try {
					JSONObject json_data = jdata.getJSONObject(0); // Se lee la respuesta
					u.setNombreCompleto(json_data.getString("NOMBRE_COMPLETO_USUARIO"));
					u.setEmail(json_data.getString("EMAIL"));
					return true;
				} catch (Exception e) {
					Log.e(TAG, "cargarUsuario, " + e.toString());
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "cargarUsuario, " + e.toString());
		}
		return false;
	}
	
	public Boolean cargarUsuarioIniciar(Usuario u) {
		try {
			Parametros postParametersToSend = new Parametros();
			postParametersToSend.add("nombre", u.getNombreUsuario());
			JSONArray jdata = null;
			jdata = post.getServerData(postParametersToSend, URL_connect + "getPersonaIniciar.php");
			if (jdata != null) {
				try {
					JSONObject json_data = jdata.getJSONObject(0); // Se lee la respuesta
					if(json_data.getString("NOMBRE_COMPLETO_USUARIO") != null);
						return true;
				} catch (Exception e) {
					Log.e(TAG, "cargarUsuario, " + e.toString());
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "cargarUsuarioIniciar, " + e.toString());
		}
		return false;
	}

	public Boolean persistir(Usuario u){
		try {
			Parametros postParametersToSend = new Parametros();
			postParametersToSend.add("nombreUsuario", u.getNombreUsuario());
			postParametersToSend.add("nombreCompleto", u.getNombreCompleto());
			postParametersToSend.add("pass", u.getPassword());
			postParametersToSend.add("email", u.getEmail());
			JSONArray jdata = null;
			jdata = post.getServerData(postParametersToSend, URL_connect + "ingresarUsuario.php");
			if (jdata != null) {
				return true;
			}
		} catch (Exception e) {
			Log.e(TAG, "persistir, " + e.toString());
		}
		return false;
	}
}
