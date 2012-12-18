package cl.at.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.content.SharedPreferences;
import cl.at.bussines.Usuario;

public abstract class Util {

	private static final char[] CONSTS_HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static void guardarUsuario(Usuario u, Context context) {
		SharedPreferences prefs = context.getSharedPreferences("Prefs", Context.MODE_PRIVATE);
		SharedPreferences.Editor editar = prefs.edit();
		editar.putString("usuario", u.getNombreUsuario());
		editar.putString("login", encriptaEnMD5(u.getNombreUsuario()+u.getPassword()));
		editar.commit();
	}

	public static String encriptaEnMD5(String stringAEncriptar) {
		try {
			MessageDigest msgd = MessageDigest.getInstance("MD5");
			byte[] bytes = msgd.digest(stringAEncriptar.getBytes());
			StringBuilder strbCadenaMD5 = new StringBuilder(2 * bytes.length);
			for (int i = 0; i < bytes.length; i++) {
				int bajo = (int) (bytes[i] & 0x0f);
				int alto = (int) ((bytes[i] & 0xf0) >> 4);
				strbCadenaMD5.append(CONSTS_HEX[alto]);
				strbCadenaMD5.append(CONSTS_HEX[bajo]);
			}
			return strbCadenaMD5.toString();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

}
