package cl.at.data;

import org.json.JSONArray;

import android.util.Log;
import cl.at.bussines.Invitacion;
import cl.at.util.Parametros;

public class InvitacionSQL {

	private static final String TAG = UsuarioSQL.class.getName();
	ConexionHttp post;
	
	public InvitacionSQL() {
		post = ConexionHttp.getConexion();
	}
	
	public Boolean persistir(Invitacion i){
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

}
