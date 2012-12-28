package cl.at.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import cl.at.bussines.Ciudad;
import cl.at.bussines.Coordenada;
import cl.at.bussines.PuntoRiesgo;
import cl.at.util.Parametros;


public class PuntoRiesgoSQL {

	private static final String TAG = PuntoRiesgoSQL.class.getName();
	private static final String CAMPO_ID_PUNTO = "ID_PUNTO";
	private static final String CAMPO_ID_CIUDAD = "ID_CIUDAD";
	private static final String CAMPO_LATITUD_PUNTO = "LATITUD_PUNTO";
	private static final String CAMPO_LONGITUD_PUNTO = "LONGITUD_PUNTO";
	private static final String CAMPO_COMENTARIO_RIESGO = "COMENTARIO_RIESGO";
	private static final String CAMPO_CATEGORIA_RIESGO = "CATEGORIA_RIESGO";
	ConexionHttp post;
	
	public PuntoRiesgoSQL() {
		post = ConexionHttp.getConexion();
	}
	
	public Boolean persistir(PuntoRiesgo puntoRiesgo) {
		try {
			Integer a = 1;
			Parametros postParametersToSend = new Parametros();
			postParametersToSend.add("categoria", puntoRiesgo.getCategoria().toString());
			postParametersToSend.add("latitudPuntoEncuentro",puntoRiesgo.getCoordenada().getLatitud().toString());
			postParametersToSend.add("longitudPuntoEncuentro",puntoRiesgo.getCoordenada().getLongitud().toString());
			postParametersToSend.add("descripcion", puntoRiesgo.getDescripcion().toString());
			postParametersToSend.add("ciudad", a.toString());
			JSONArray jdata = null;
			jdata = post.getServerData(postParametersToSend, ConexionHttp.URL_CONNECT + "ingresarPuntoRiesgo.php");
			if (jdata != null) {
				return true;
			}
		} catch (Exception e) {
			Log.e(TAG, "persistir, " + e.toString());
		}
		return false;
	}

	public Boolean cargarPuntosRiesgo(Ciudad ciudad) {
		try{
			Parametros postParametersToSend = new Parametros();
			postParametersToSend.add("ciudad", ciudad.getId().toString());
			JSONArray jdata = null;
			jdata = post.getServerData(postParametersToSend, ConexionHttp.URL_CONNECT + "getPuntosRiesgo.php");
			if (jdata != null) {
				ArrayList<PuntoRiesgo> puntoRiesgos = new ArrayList<PuntoRiesgo>();
				for (int i = 0; i < jdata.length(); i++) {
					JSONObject jsonData = jdata.getJSONObject(i);
					PuntoRiesgo punto= new PuntoRiesgo();
					punto.setCategoria(jsonData.getInt(CAMPO_CATEGORIA_RIESGO));
					punto.setCoordenada(new Coordenada(jsonData.getDouble(CAMPO_LATITUD_PUNTO), jsonData.getDouble(CAMPO_LONGITUD_PUNTO)));
					punto.setDescripcion(jsonData.getString(CAMPO_COMENTARIO_RIESGO));
					puntoRiesgos.add(punto);
				}
				ciudad.setPuntosRiesgo(puntoRiesgos);
				return true;
			}
		}catch (Exception e) {
			Log.e(TAG, "cargarPuntosRiesgo, " + e.toString());
		}
		return false;
	}

}
