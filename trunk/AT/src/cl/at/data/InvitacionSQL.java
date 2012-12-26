package cl.at.data;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import cl.at.bussines.Comentario;
import cl.at.bussines.Invitacion;
import cl.at.bussines.Usuario;
import cl.at.util.Parametros;
import cl.at.util.Util;

public class InvitacionSQL {

	private static final String TAG = UsuarioSQL.class.getName();
	private static final String CAMPO_INVITADO = "NOMBRE_INVITADO";
	private static final String CAMPO_REMITENTE = "NOMBRE_REMITENTE";
	private static final String CAMPO_FECHA = "INSTANTE_INVITACION";
	ConexionHttp post;

	public InvitacionSQL() {
		post = ConexionHttp.getConexion();
	}

	public Boolean persistir(Invitacion i) {
		try {
			Parametros postParametersToSend = new Parametros();
			postParametersToSend.add("nombreUsuarioInvitado", i.getInvitado().getNombreUsuario());
			postParametersToSend.add("nombreUsuarioRemitente", i.getRemitente().getNombreUsuario());
			JSONArray jdata = null;
			jdata = post.getServerData(postParametersToSend, ConexionHttp.URL_CONNECT + "ingresarInvitacion.php");
			if (jdata != null) {
				return true;
			}
		} catch (Exception e) {
			Log.e(TAG, "persistir, " + e.toString());
		}
		return false;
	}

	public ArrayList<Invitacion> cargarInvitaciones(Usuario usuario) {
		try {
			Parametros postParametersToSend = new Parametros();
			postParametersToSend.add("usuario", usuario.getNombreUsuario());
			// postParametersToSend.add("remitente", "true");//Solo con fines de depuracion
			JSONArray jdata = null;
			jdata = post.getServerData(postParametersToSend, ConexionHttp.URL_CONNECT + "getInvitaciones.php");
			if (jdata != null) {
				ArrayList<Invitacion> invitaciones = new ArrayList<Invitacion>();
				for (int i = 0; i < jdata.length(); i++) {
					JSONObject jsonData = jdata.getJSONObject(i);
					Usuario invitado = usuario;// new
												// Usuario(jsonData.getString(CAMPO_INVITADO),true);
					Usuario remitente = new Usuario(jsonData.getString(CAMPO_REMITENTE));//TODO externo y crear getGrupo y weas a peticion
					Date fecha = Util.StringDBToDate(jsonData.getString(CAMPO_FECHA));
					Invitacion inv = new Invitacion(invitado, remitente, fecha);
					invitaciones.add(inv);
				}
				return invitaciones;
			}
		} catch (Exception e) {
			Log.e(TAG, "cargarInvitaciones, " + e.toString());
		}
		return null;
	}

	public Boolean eliminarTodas(Usuario usuario) {
		try {
			Parametros postParametersToSend = new Parametros();
			postParametersToSend.add("invitado", usuario.getNombreUsuario());
			JSONArray jdata = post.getServerData(postParametersToSend, ConexionHttp.URL_CONNECT + "eliminarInvitaciones.php");
			return jdata != null;
		} catch (Exception e) {
			Log.e(TAG, "eliminarTodas, " +e.toString()+" "+e.getCause());
		}
		return false;
	}

	public Boolean eliminarInvitacion(Invitacion inv) {
		try {
			Parametros postParametersToSend = new Parametros();
			postParametersToSend.add("remitente", inv.getRemitente().getNombreUsuario());
			postParametersToSend.add("invitado", inv.getInvitado().getNombreUsuario());
			JSONArray jdata = post.getServerData(postParametersToSend, ConexionHttp.URL_CONNECT + "eliminarInvitaciones.php");
			return jdata != null;
		} catch (Exception e) {
			Log.e(TAG, "eliminarInvitacion, " +e.toString()+" "+e.getCause());
		}
		return false;
	}

}
