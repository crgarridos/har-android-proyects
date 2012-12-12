package cl.at.data;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import cl.at.bussines.Usuario;

public class UsuarioSQL {

	private static final String TAG = UsuarioSQL.class.getName();
	String URL_connect = "http://acinfo.unap.cl/jvega/Alerttsunami/getPersona.php";
	ConexionHttp post;

	public UsuarioSQL() {
		post = new ConexionHttp();
	}

	public Boolean cargarUsuario(Usuario u) {
		try {
			ArrayList<NameValuePair> postParametersToSend = new ArrayList<NameValuePair>();
			postParametersToSend.add(new BasicNameValuePair("nombre", u.getNombreUsuario()));
			postParametersToSend.add(new BasicNameValuePair("pass", u.getPassword()));
			JSONArray jdata = null;
			jdata = post.getServerData(postParametersToSend, URL_connect);
			if (jdata != null) {
				try {
					JSONObject json_data = jdata.getJSONObject(0); // Se lee la respuesta
					Log.d(TAG, json_data.toString());
					u.setNombreCompleto(json_data.getString("NOMBRE_COMPLETO_USUARIO"));
					// u.setLatitud(Double.parseDouble(json_data.getString("Latitud")));
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
}
