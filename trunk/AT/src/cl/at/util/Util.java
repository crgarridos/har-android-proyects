package cl.at.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import cl.at.bussines.Dispositivo;
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
	
	public static void setIntervalo(Integer intervalo, Context context) {
		SharedPreferences prefs = context.getSharedPreferences("Prefs", Context.MODE_PRIVATE);
		SharedPreferences.Editor editar = prefs.edit();
		editar.putString("intervalo", intervalo.toString());
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

	public static String getPreferencia(String preferencia) {
		return AlertTsunamiApplication.getAppContext().getSharedPreferences("Prefs", Context.MODE_PRIVATE).getString(preferencia, null);
	}
	
	public static void reiniciarPreferencias(Context context) {
		SharedPreferences prefs = context.getSharedPreferences("Prefs", Context.MODE_PRIVATE);
		SharedPreferences.Editor editar = prefs.edit();
		final String[] titlePrefs ={"usuario","login","intervalo","dispositivo"};
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

    /**
     * Metodo encargado de verificar si existe una conexion a internet
     * @param a Activity o contexto de donde se llama al metodo
     * @return retorna true si existe alguna conexion a internet, Wifi o Datos
     */
    public final static boolean verificarInternet(Activity a) { 
        //Variables de estado
        boolean hasConnectedWifi = false; 
        boolean hasConnectedMobile = false;
        //Instanciamos un objeto de conexion y verificamos si en wifi o datos hay conexion 
        ConnectivityManager cm = (ConnectivityManager) a.getSystemService(Context.CONNECTIVITY_SERVICE); 
        NetworkInfo[] netInfo = cm.getAllNetworkInfo(); 
        //Recorremos la informacion de la red en busqueda de una conexion
        for (NetworkInfo ni : netInfo) { 
            //Si el tipo de red es Wifi
            if (ni.getTypeName().equalsIgnoreCase("wifi")){
                //Si wifi esta conectado
                if (ni.isConnected()){
                    hasConnectedWifi = true;
                }                    
            }
            //Si el tipo de red es mobile (datos)
            if (ni.getTypeName().equalsIgnoreCase("mobile")){
                //si datos o mobile esta conectado
                if (ni.isConnected()){
                    hasConnectedMobile = true;
                }    
            }
        }
        return hasConnectedWifi || hasConnectedMobile; 
    }

	
    public static void guardar(Dispositivo dispositivo) {
		SharedPreferences prefs = AlertTsunamiApplication.getAppContext().getSharedPreferences("Prefs", Context.MODE_PRIVATE);
		SharedPreferences.Editor editar = prefs.edit();
		editar.putString("dispositivo", dispositivo.getId().toString());
		editar.commit();
		
	}
}
