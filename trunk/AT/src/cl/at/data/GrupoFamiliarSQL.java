package cl.at.data;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import cl.at.bussines.GrupoFamiliar;
import cl.at.bussines.Usuario;
import cl.at.util.Parametros;

public class GrupoFamiliarSQL {

	private static final String TAG = GrupoFamiliarSQL.class.getName();
	private static final String CAMPO_NOMBRE = "NOMBRE_FAMILIAR";
	private static final String CAMPO_ID = "ID_FAMILIAR";
	
	ConexionHttp post;
	
	public GrupoFamiliarSQL() {
		post = new ConexionHttp();
	}

	public boolean cargarGrupoFamiliar(GrupoFamiliar grupoFamiliar, final Usuario usuario) {
		try {
			Parametros postParametersToSend = new Parametros();
			postParametersToSend.add("nombre", usuario.getNombreUsuario());
			JSONArray jdata = null;
			jdata = post.getServerData(postParametersToSend, ConexionHttp.URL_CONNECT + "getGrupoFamiliar.php");
			if (jdata != null) {
				JSONObject jsonData = jdata.getJSONObject(0); // Se lee la respuesta
				try {
					grupoFamiliar.setId(Integer.parseInt(jsonData.getString(CAMPO_ID)));
					grupoFamiliar.setNombre(jsonData.getString(CAMPO_NOMBRE));
					return true;
				} catch (Exception e) {
					Log.e(TAG, "cargarGrupoFamiliar, " + e.toString());
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "cargarGrupoFamiliar, " + e.toString());
		}
		return false;
	}

}
