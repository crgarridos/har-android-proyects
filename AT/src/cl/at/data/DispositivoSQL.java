package cl.at.data;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import cl.at.bussines.Coordenada;
import cl.at.bussines.Dispositivo;
import cl.at.bussines.Usuario;
import cl.at.util.Parametros;

public class DispositivoSQL {

	private static final String TAG = DispositivoSQL.class.getName();
	private static final String CAMPO_ID = "ID_DISPOSITIVO";
//	private static final String CAMPO_NOMBRE_USUARIO = "NOMBRE_USUARIO";
	private static final String CAMPO_COD_GCM = "COD_GCM";
	private static final String CAMPO_ULTIMA_LATITUD = "ULTIMA_LATITUD_DISPOSITIVO";
	private static final String CAMPO_ULTIMA_LONGITUD = "ULTIMA_LONGITUD_DISPOSITIVO";
	private static final String CAMPO_SEGURO = "SEGURO";
	
	ConexionHttp post;

	public DispositivoSQL() {
		post = ConexionHttp.getConexion();
	}

	public Boolean getUltimaPosicion(Dispositivo dispositivo) {
		try {
			Parametros postParametersToSend = new Parametros();
			postParametersToSend.add("id", dispositivo.getId().toString());
			JSONArray jdata = null;
			jdata = post.getServerData(postParametersToSend, ConexionHttp.URL_CONNECT + "cargarDispositivo.php");
			if (jdata != null) {
				JSONObject json_data = jdata.getJSONObject(0);
				try {
					dispositivo.setPosicion(new Coordenada(json_data.getDouble(CAMPO_ULTIMA_LATITUD), json_data.getDouble(CAMPO_ULTIMA_LONGITUD)));
					dispositivo.estadoRiesgo(json_data.getInt(CAMPO_SEGURO) == 1);
					return true;
				} catch (Exception e) {
					Log.e(TAG, "getUltimaPosicion, " + e.toString());
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "getUltimaPosicion, " + e.toString());
		}
		return false;
	}
	
	public Boolean actualizarPosicion(Dispositivo dispositivo) {
		try {
			Parametros postParametersToSend = new Parametros();
			Usuario usuario = dispositivo.getUsuario();
			postParametersToSend.add("nombreUsuario", usuario!=null ? usuario.getNombreUsuario() : null);
			postParametersToSend.add("latitudDispositivo", dispositivo.getPosicion().getLatitud().toString());
			postParametersToSend.add("longitudDispositivo", dispositivo.getPosicion().getLongitud().toString());
			postParametersToSend.add("seguro", dispositivo.getEstadoDeRiesgo().toString());
			JSONArray jdata = post.getServerData(postParametersToSend, ConexionHttp.URL_CONNECT + "actualizarPosicion.php");
			if (jdata != null) {
				return true;
			}
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		return false;
	}
	
	public Boolean persistir(Dispositivo dispositivo){
		try {
			Parametros postParametersToSend = new Parametros();
			Usuario usuario = dispositivo.getUsuario();
			postParametersToSend.add("usuario", usuario!=null ? usuario.getNombreUsuario() : null);
			postParametersToSend.add("latitud", dispositivo.getPosicion().getLatitud().toString());
			postParametersToSend.add("longitud", dispositivo.getPosicion().getLongitud().toString());
			JSONArray jdata = null;
			jdata = post.getServerData(postParametersToSend, ConexionHttp.URL_CONNECT + "ingresarDispositivo.php");
			if (jdata != null) {
				JSONObject json_data = jdata.getJSONObject(0);
				dispositivo.setId(json_data.getInt(CAMPO_ID));
				return true;
			}
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		return false;		
	}


	public Boolean registrarEnGCM(Dispositivo dispositivo) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("dispositivo", dispositivo.getId().toString()));
		params.add(new BasicNameValuePair("regId", dispositivo.getRegGCM()));
		JSONArray jdata = post.getServerData(params, ConexionHttp.URL_CONNECT + "registrarGCM.php");
		try {
//			JSONObject jsonData = jdata.getJSONObject(0);
			Log.d(TAG, "Registro WS: OK.");
		} catch (Exception e) {
			Log.e(TAG, "Registro WS: NOK. " + e.getCause() + " || " + e.getMessage());
		}
		return jdata!= null;
	}

	public Boolean cargarDispositivo(Dispositivo dispositivo) {
		try {
			Parametros postParametersToSend = new Parametros();
			if(dispositivo.getId() != null)
				postParametersToSend.add("id", dispositivo.getId().toString());
			else postParametersToSend.add("nombreUsuario", dispositivo.getUsuario().getNombreUsuario());
			JSONArray jdata = post.getServerData(postParametersToSend, ConexionHttp.URL_CONNECT + "cargarDispositivo.php");
			if (jdata != null) {
				JSONObject jsonData = jdata.getJSONObject(0);
				dispositivo.setId(jsonData.getInt(CAMPO_ID));
				Coordenada coordenada = new Coordenada(jsonData.getDouble(CAMPO_ULTIMA_LATITUD), jsonData.getDouble(CAMPO_ULTIMA_LONGITUD));
				dispositivo.setPosicion(coordenada);
				dispositivo.setRegGCM(jsonData.getString(CAMPO_COD_GCM));
				dispositivo.estadoRiesgo(jsonData.getInt(CAMPO_SEGURO) == 1);
				return true;
			}
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		return false;		
	}
}