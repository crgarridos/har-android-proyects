package cl.at.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import cl.at.bussines.Coordenada;
import cl.at.bussines.GrupoFamiliar;
import cl.at.bussines.PuntoEncuentro;
import cl.at.bussines.Usuario;
import cl.at.util.Parametros;

public class PuntoEncuentroSQL {
	private static final String TAG = PuntoEncuentroSQL.class.getName();
	private static final String CAMPO_ID = "ID_PUNTO";
	private static final String CAMPO_FK_FAMILIA = "ID_FAMILIAR";
	private static final String CAMPO_FK_CIUDAD = "ID_CIUDAD";
	private static final String CAMPO_LATITUD = "LATITUD_PUNTO";
	private static final String CAMPO_LONGITUD = "LONGITUD_PUNTO";
	private static final String CAMPO_REFERENCIA = "REFERENCIA_ENCUENTRO";

	ConexionHttp post;

	public PuntoEncuentroSQL() {
		post = ConexionHttp.getConexion();
	}

	public PuntoEncuentro cargarPtoEncuentro(final GrupoFamiliar grupoFamiliar) {
		try {
			Parametros postParametersToSend = new Parametros();
			postParametersToSend.add("id", grupoFamiliar.getId().toString());
			JSONArray jdata = null;
			jdata = post.getServerData(postParametersToSend, ConexionHttp.URL_CONNECT + "getPtoEncuentro.php");
			if (jdata != null) {
				JSONObject jsonData = jdata.getJSONObject(0);
				PuntoEncuentro p = new PuntoEncuentro(grupoFamiliar);
				p.setReferencia(jsonData.getString(CAMPO_REFERENCIA));
				Double lat = Double.parseDouble(jsonData.getString(CAMPO_LATITUD));
				Double lon = Double.parseDouble(jsonData.getString(CAMPO_LONGITUD));
				Coordenada c = new Coordenada(lat, lon);
				p.setCoordenada(c);
				return p;
			}
		} catch (Exception e) {
			Log.e(TAG, "cargarPtoEncuentro, " + e.toString());
		}
		return null;
	}
	
	public Boolean persistir(PuntoEncuentro puntoEncuentro) {
		try {
			Parametros postParametersToSend = new Parametros();
			postParametersToSend.add("idGrupo", puntoEncuentro.getGrupoFamiliar().getId().toString());
			postParametersToSend.add("descripcion", puntoEncuentro.getReferencia());
			postParametersToSend.add("latitud", puntoEncuentro.getCoordenada().getLatitud().toString());
			postParametersToSend.add("longitud", puntoEncuentro.getCoordenada().getLongitud().toString());
			JSONArray jdata = null;
			jdata = post.getServerData(postParametersToSend, ConexionHttp.URL_CONNECT + "actualizarPuntoEncuentro.php");
			if (jdata != null) {
				return true;
			}
		} catch (Exception e) {
			Log.e(TAG, "persistir, " + e.toString());
		}
		return false;
	}

}
