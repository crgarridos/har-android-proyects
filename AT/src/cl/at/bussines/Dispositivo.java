package cl.at.bussines;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import cl.at.data.DispositivoSQL;
import cl.at.util.Util;
import cl.at.view.AlertTsunamiApplication;

public class Dispositivo implements Serializable{
	
	private static final String TAG = Dispositivo.class.getName();
	private Boolean estadoDeRiesgo;
	private Usuario usuario;
	private Coordenada posicion;
	private Integer intervalo;
	private LocationManager locManager;
	private Location location;
	private Context context;
	

	public Dispositivo(Usuario usuario){
		this.estadoDeRiesgo = false;//TODO Calcular la posicion e indicar si hay estado de riesgo
		this.context = AlertTsunamiApplication.getAppContext();
		this.intervalo = Util.getPreferencia("intervalo", context)!= null?Integer.parseInt(Util.getPreferencia("intervalo", context)):20000;//TODO cambiar tiempo default
		setUsuario(usuario);
		if(!usuario.esExterno()){
			this.locManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
			this.location = (Location)(this.locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)!=null?this.locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER):new DispositivoSQL().getUltimaPosicion(this.getUsuario(), this));
			if((Location)this.locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)!=null)
				setPosicion(new Coordenada(location.getLatitude(), location.getLongitude()));
		}
		else{
			new DispositivoSQL().getUltimaPosicion(this.getUsuario(), this);
		}
//		actualizarPosicion();
	}
	
	//get estado de riesgo
	public Boolean getEstadoDeRiesgo(){
		return this.estadoDeRiesgo;
	}
	
	//get y set usuario
	public Usuario getUsuario(){
		return usuario;
	}
	
	public void setUsuario(Usuario usuario){
		this.usuario = usuario;
	}
	
	//get y set posición
	public Coordenada getPosicion(){
		return posicion;
	}
	
	public void setPosicion(Coordenada posicion){
		this.posicion = posicion;
	}

	//get y set intervalo en que se demora en actualizar la posiciï¿½n del dispositivo
	public Integer getIntervalo(){
		return intervalo;
	}
	
	public void setIntervalo(Integer intervalo){
		this.intervalo = intervalo;
	}
	
	//destructor
	public void finalize() throws Throwable {

	}
	
	//otros
	public void actualizarPosicion(){
		Integer i = 1;
		ArrayList<Usuario> grupoFamiliar = new ArrayList<Usuario>();
		grupoFamiliar = getUsuario().getGrupoFamiliar().getIntegrantes();
		try{
			this.location = this.locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			setPosicion(new Coordenada(this.location.getLatitude(), this.location.getLongitude()));
//			this.posicion.setLatitud(this.location.getLatitude());
//			this.posicion.setLongitud(this.location.getLongitude());
			DispositivoSQL dSQL = new DispositivoSQL();
			while(i < grupoFamiliar.size()){
				dSQL.getUltimaPosicion(grupoFamiliar.get(i), grupoFamiliar.get(i).getDispositivo());
				i++;
			}
		}catch(Exception e){
			DispositivoSQL dSQL = new DispositivoSQL();
			dSQL.getUltimaPosicion(this.getUsuario(), this);
			}
		if(!estaSeguro()){
			//TODO comprobar estado de dispositivo
		}
	}

	public Boolean estaSeguro(){
		return estadoDeRiesgo;
		//podria tener un setter para que la ciudad lo haga cambiar de estado, asi evitarnos la relacion.
								// creo que para eso estaba :S, la relacion...
//		if(this.posicion == this.ciudad.getPuntoSeguridad().coordenada){
//			return true;
//		}
//		else
//			return false;
	}

	public void inicializar(LocationListener locListener) {
		 this.locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, this.intervalo, 0, locListener);
	}
}