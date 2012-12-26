package cl.at.data;

import org.json.JSONArray;

import android.util.Log;
import cl.at.bussines.PuntoRiesgo;
import cl.at.util.Parametros;


public class PuntoRiesgoSQL {

	private static final String TAG = PuntoRiesgoSQL.class.getName();
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

}
