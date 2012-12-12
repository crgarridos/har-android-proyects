package Datos;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import Negocio.Usuario;
import android.util.Log;
import clasesauxiliares.Httppostaux;

public class UsuarioSQL {

	private static final String TAG = "UsuarioSQL";
	String URL_connect = "http://acinfo.unap.cl/jvega/Alerttsunami/getPersona.php";
	Httppostaux post;

	public UsuarioSQL() {
		post = new Httppostaux();
	}

	public Boolean cargarUsuario(Usuario u) {
		try {
			ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
			postparameters2send.add(new BasicNameValuePair("nombre", u.getNombreUsuario()));
			postparameters2send.add(new BasicNameValuePair("pass", u.getPassword()));
			JSONArray jdata = post.getserverdata(postparameters2send, URL_connect);
			JSONObject json_data;
			try {
				Log.d(TAG, "cargarUsuario() Entro en el try");
				if (jdata != null) {
					json_data = jdata.getJSONObject(0); // Se lee la respuesta
					Log.d(TAG, json_data.toString());
					u.setNombreCompleto(json_data.getString("NOMBRE_COMPLETO_USUARIO"));
					// u.setLatitud(Double.parseDouble(json_data.getString("Latitud")));
					u.setEmail(json_data.getString("EMAIL"));
					return true;
				}
			} catch (Exception e) {
			}
		} catch (Exception e) {
		}
		return false;
	}
}
