package cl.at.data;

import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import cl.at.bussines.GrupoFamiliar;
import cl.at.bussines.Lider;
import cl.at.bussines.Usuario;
import cl.at.util.Parametros;

public class UsuarioSQL {

	private static final String TAG = UsuarioSQL.class.getName();
	private static final String CAMPO_NOMBRE_COMPLETO = "NOMBRE_COMPLETO_USUARIO";
	private static final String CAMPO_EMAIL = "EMAIL";
	private static final String CAMPO_PASS = "PASS_USUARIO";
	private static final String CAMPO_NOMBRE_USUARIO = "NOMBRE_USUARIO";
	private static final String CAMPO_ES_LIDER = "ES_LIDER";

	ConexionHttp post;

	public UsuarioSQL() {
		post = ConexionHttp.getConexion();
	}

	public Boolean cargarUsuario(Usuario u) {
		Log.i(TAG, "cargando usuario...");
		try {
			Parametros postParametersToSend = new Parametros();
			postParametersToSend.add("nombre", u.getNombreUsuario());
			postParametersToSend.add("pass", u.getPassword());
			JSONArray jdata = null;
			jdata = post.getServerData(postParametersToSend, ConexionHttp.URL_CONNECT + "getPersona.php");
			if (jdata != null) {
				JSONObject json_data = jdata.getJSONObject(0); // Se lee la
																// respuesta
				try {
					u.setNombreCompleto(u.getNombreCompleto() == null ? json_data.getString(CAMPO_NOMBRE_COMPLETO) : u.getNombreCompleto());
					u.setEmail(u.getEmail() == null ? json_data.getString(CAMPO_EMAIL) : u.getEmail());
					if (!u.getExterno())
						u.setPassword(u.getPassword() == null ? json_data.getString(CAMPO_PASS) : u.getPassword());
					u.esLider(json_data.getInt(CAMPO_ES_LIDER));
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

	public Boolean persistir(Usuario u) {
		try {
			Parametros postParametersToSend = new Parametros();
			postParametersToSend.add("nombreUsuario", u.getNombreUsuario());
			postParametersToSend.add("nombreCompleto", u.getNombreCompleto());
			postParametersToSend.add("pass", u.getPassword());
			postParametersToSend.add("email", u.getEmail());
			JSONArray jdata = null;
			jdata = post.getServerData(postParametersToSend, ConexionHttp.URL_CONNECT + "ingresarUsuario.php");
			if (jdata != null) {
				return true;
			}
		} catch (Exception e) {
			Log.e(TAG, "persistir, " + e.toString());
		}
		return false;
	}

	public Boolean eliminar(Usuario u) {
		try {
			Parametros postParametersToSend = new Parametros();
			postParametersToSend.add("nombreUsuario", u.getNombreUsuario());
			JSONArray jdata = null;
			jdata = post.getServerData(postParametersToSend, ConexionHttp.URL_CONNECT + "eliminarUsuario.php");
			if (jdata != null) {
				return true;
			}
		} catch (Exception e) {
			Log.e(TAG, "eliminar, " + e.toString());
		}
		return false;
	}

	public GrupoFamiliar obtenerGrupoFamiliar(final Usuario usuario) {

		return null;
	}

	public ArrayList<Usuario> cargarIntegrantes(final GrupoFamiliar grupoFamiliar) {
		Log.i(TAG, "Cargando integrantes...");
		try {
			Parametros postParametersToSend = new Parametros();
			postParametersToSend.add("id", grupoFamiliar.getId().toString());
			JSONArray jdata = null;
			jdata = post.getServerData(postParametersToSend, ConexionHttp.URL_CONNECT + "getIntegrantes.php");
			if (jdata != null) {
				ArrayList<Usuario> integrantes = new ArrayList<Usuario>();
				for (int i = 0; i < jdata.length(); i++) {
					try {
						JSONObject jsonData = jdata.getJSONObject(i);

						// TODO if(jsonData.getInt(CAMPO_ES_LIDER) != 1){
						Usuario u = new Usuario(jsonData.getString(CAMPO_NOMBRE_USUARIO), true);
						u.setEmail(jsonData.getString(CAMPO_EMAIL));
						u.setNombreCompleto(jsonData.getString(CAMPO_NOMBRE_COMPLETO));
						integrantes.add(u);
						// }
					} catch (Exception e) {
						Log.e(TAG, "cargarIntegrantes, " + e.toString());
					}
				}
				return integrantes;
			}
		} catch (Exception e) {
			Log.e(TAG, "cargarIntegrantes, " + e.toString());
		}
		return null;
	}

	public Lider cargarLider(GrupoFamiliar grupoFamiliar) {
		try {
			Parametros postParametersToSend = new Parametros();
			postParametersToSend.add("id", grupoFamiliar.getId().toString());
			JSONArray jdata = null;
			jdata = post.getServerData(postParametersToSend, ConexionHttp.URL_CONNECT + "getLider.php");
			if (jdata != null) {
				try {
					JSONObject jsonData = jdata.getJSONObject(0);
					Lider lider = new Lider();
					lider.setEmail(jsonData.getString(CAMPO_EMAIL));
					lider.setNombreCompleto(jsonData.getString(CAMPO_NOMBRE_COMPLETO));
					lider.setNombreUsuario(jsonData.getString(CAMPO_NOMBRE_USUARIO));
					return lider;
				} catch (Exception e) {
					Log.e(TAG, "cargarIntegrantes, " + e.toString());
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "cargarIntegrantes, " + e.toString());
		}
		return null;
	}

	public Boolean buscar(Usuario usuario, String cadenaBusqueda) {
		try {
			Parametros postParametersToSend = new Parametros();
			postParametersToSend.add("cadenaBusqueda", cadenaBusqueda);
			JSONArray jdata = null;
			jdata = post.getServerData(postParametersToSend, ConexionHttp.URL_CONNECT + "buscarUsuario.php");
			if (jdata != null) {
				JSONObject json_data = jdata.getJSONObject(0);
				usuario.setNombreUsuario(json_data.getString(CAMPO_NOMBRE_USUARIO));
				usuario.setNombreCompleto(json_data.getString(CAMPO_NOMBRE_COMPLETO));
				usuario.setEmail(json_data.getString(CAMPO_EMAIL));
				if (!json_data.isNull("ID_FAMILIAR"))
					usuario.setGrupoFamiliar(new GrupoFamiliar());
				return true;
			}
		} catch (Exception e) {
			Log.e(TAG, "buscar, " + e.toString());
		}
		return false;
	}

	public Boolean enlazarGrupoFamiliar(Usuario usuario, GrupoFamiliar grupoFamiliar) {
		try {
			Parametros postParametersToSend = new Parametros();
			postParametersToSend.add("usuario", usuario.getNombreUsuario());
			postParametersToSend.add("grupo_familiar", grupoFamiliar.getId().toString());
			JSONArray jdata = null;
			jdata = post.getServerData(postParametersToSend, ConexionHttp.URL_CONNECT + "enlazarGrupoFamiliar.php");
			if (jdata != null) {
				return true;
			}
		} catch (Exception e) {
			Log.e(TAG, "persistir, " + e.toString());
		}
		return false;
	}
}
