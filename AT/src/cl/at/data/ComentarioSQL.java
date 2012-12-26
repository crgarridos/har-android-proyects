package cl.at.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import cl.at.bussines.Comentario;
import cl.at.bussines.GrupoFamiliar;
import cl.at.bussines.Usuario;
import cl.at.util.Parametros;
import cl.at.util.Util;

public class ComentarioSQL {

	private static final String TAG = ComentarioSQL.class.getName();
	private static final String CAMPO_ID = "ID_COMENTARIO";
	private static final String CAMPO_FK_FAMILIA = "ID_FAMILIAR";
	private static final String CAMPO_FK_NOMBRE_USUARIO = "NOMBRE_USUARIO";
	private static final String CAMPO_CONTENIDO = "CONTENIDO_COMENTARIO";
	private static final String CAMPO_FECHA = "FECHA_COMENTARIO";

	ConexionHttp post;

	public ComentarioSQL() {
		post = ConexionHttp.getConexion();
	}

	public ArrayList<Comentario> cargarComentarios(final GrupoFamiliar grupoFamiliar) {
		try {
			Parametros postParametersToSend = new Parametros();
			postParametersToSend.add("id", grupoFamiliar.getId().toString());
			JSONArray jdata = null;
			jdata = post.getServerData(postParametersToSend, ConexionHttp.URL_CONNECT + "getComentarios.php");
			if (jdata != null) {
				ArrayList<Comentario> comentarios = new ArrayList<Comentario>();
				for (int i = 0; i < jdata.length(); i++) {
					JSONObject jsonData = jdata.getJSONObject(i);
					Comentario com = new Comentario(new Usuario(jsonData.getString(CAMPO_FK_NOMBRE_USUARIO),true));
					com.setContenido(jsonData.getString(CAMPO_CONTENIDO));
					com.setFecha(Util.StringDBToDate(jsonData.getString(CAMPO_FECHA)));
					comentarios.add(com);
				}
				return comentarios;
			}
		} catch (Exception e) {
			Log.e(TAG, "cargarComentarios, " + e.toString());
		}
		return null;
	}

}
