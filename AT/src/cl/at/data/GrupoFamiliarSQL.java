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
		post = ConexionHttp.getConexion();
	}

	public Boolean cargarGrupoFamiliar(GrupoFamiliar grupoFamiliar, final Usuario usuario) {
		Log.e(TAG, "cargando grupo familiar...");
		try {
			Parametros postParametersToSend = new Parametros();
			postParametersToSend.add("nombre", usuario.getNombreUsuario());
			JSONArray jdata = null;
			jdata = post.getServerData(postParametersToSend, ConexionHttp.URL_CONNECT + "getGrupoFamiliar.php");
			if (jdata != null) {
				JSONObject jsonData = jdata.getJSONObject(0);
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

	
	public Boolean persistir(GrupoFamiliar grupoFamiliar) {
		try {
			Parametros postParametersToSend = new Parametros();
			postParametersToSend.add("nombreGrupo", grupoFamiliar.getNombre());
			postParametersToSend.add("nombreUsuarioLider", grupoFamiliar.getLider().getNombreUsuario());
			postParametersToSend.add("latitudPuntoEncuentro",grupoFamiliar.getPuntoDeEncuentro().getCoordenada().getLatitud().toString());
			postParametersToSend.add("longitudPuntoEncuentro",grupoFamiliar.getPuntoDeEncuentro().getCoordenada().getLongitud().toString());
			postParametersToSend.add("comentarioPuntoEncuentro", grupoFamiliar.getPuntoDeEncuentro().getReferencia());
			JSONArray jdata = null;
			jdata = post.getServerData(postParametersToSend, ConexionHttp.URL_CONNECT + "ingresarGrupoFamiliar.php");
			if (jdata != null) {
				grupoFamiliar.setId(jdata.getJSONObject(0).getInt(CAMPO_ID));
				return true;
			}
		} catch (Exception e) {
			Log.e(TAG, "persistir, " + e.toString());
		}
		return false;
	}
}
