package cl.at.util;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

@SuppressWarnings("serial")
public class Parametros extends ArrayList<NameValuePair>{
	
	public void add(String nombre, String valor){
		this.add(new BasicNameValuePair(nombre,valor));
	}
}
