package cl.at.data;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import cl.at.bussines.GrupoFamiliar;
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
				JSONObject json_data = jdata.getJSONObject(0); // Se lee la respuesta
				try {
					u.setNombreCompleto(u.getNombreCompleto()==null?json_data.getString("NOMBRE_COMPLETO_USUARIO"):u.getNombreCompleto());
					u.setEmail(u.getEmail()==null?json_data.getString("EMAIL"):u.getEmail());
					u.setPassword(u.getPassword()==null?json_data.getString("PASS_USUARIO"):u.getPassword());
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
	
//	public Boolean cargarUsuarioIniciar(Usuario u) {
//		try {
//			Parametros postParametersToSend = new Parametros();
//			postParametersToSend.add("nombre", u.getNombreUsuario());
//			JSONArray jdata = null;
//			jdata = post.getServerData(postParametersToSend, URL_connect + "getPersona.php");
//			if (jdata != null) {
//				try {
//					JSONObject json_data = jdata.getJSONObject(0); // Se lee la respuesta
//					if(json_data.getString("NOMBRE_COMPLETO_USUARIO") != null);
//					u.setNombreCompleto(json_data.getString("NOMBRE_COMPLETO_USUARIO"));
//					u.setEmail(json_data.getString("EMAIL"));
//					u.setPassword(json_data.getString("PASS_USUARIO"));
//					//TODO la pass deberia validarla es servidor, recuendenlo xD
//					return true;
//				} catch (Exception e) {
//					Log.e(TAG, "cargarUsuario, " + e.toString());
//				}
//			}
//		} catch (Exception e) {
//			Log.e(TAG, "cargarUsuarioIniciar, " + e.toString());
//		}
//		return false;
//	}

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

	public GrupoFamiliar obtenerGrupoFamiliar(Usuario usuario) {
		
		return null;
	}
}
