package cl.at.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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

	public static String getPreferencia(String preferencia,Context context) {
		return context.getSharedPreferences("Prefs", Context.MODE_PRIVATE).getString(preferencia, null);
	}
	
	public static void reiniciarPreferencias(Context context) {
		SharedPreferences prefs = context.getSharedPreferences("Prefs", Context.MODE_PRIVATE);
		SharedPreferences.Editor editar = prefs.edit();
		final String[] titlePrefs ={"usuario","login"};
		for(int i=0; i<titlePrefs.length; i++)
			if(prefs.getString(titlePrefs[i],null)!=null)
				editar.remove(titlePrefs[i]);
		editar.commit();
	}

	public static Date StringDBToDate(String date){
        try{
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
        }catch(ParseException ex){
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, "Utils.StringToHour: ", ex);
            return null;
        }
    }   	
	
	public static Date StringToDate(String date){
        try{
            return new SimpleDateFormat("dd/MM/yyyy").parse(date);
        }catch(ParseException ex){
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, "Utils.StringToHour: ", ex);
            return null;
        }
    }   

    public static String DateToString(Date date){
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    public static String HourToString(Date date){
        return new SimpleDateFormat("HH:mm").format(date);
    }

    public static Date StringToHour(String date){
        try {
            return new SimpleDateFormat("HH:mm").parse(date);
        } catch (ParseException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, "Utils.StringToHour: ", ex);
            return null;
        }
    }
}
