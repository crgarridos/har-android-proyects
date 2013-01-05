package cl.at.util;

import com.google.android.maps.GeoPoint;

import cl.at.bussines.Alerta;
import cl.at.bussines.Ciudad;
import cl.at.bussines.Dispositivo;
import cl.at.bussines.Usuario;

public class Comunicador {
	private static Comunicador comunicador;

	private Usuario usuario;
	private Dispositivo dispositivo;
	private Alerta alerta;
	private Ciudad ciudad;
	private GeoPoint posicion;

	private Comunicador() {
	}

	public static Comunicador getInstancia() {
		if (comunicador == null)
			comunicador = new Comunicador();
		return comunicador;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Dispositivo getDispositivo() {
		return dispositivo;
	}

	public void setDispositivo(Dispositivo dispositivo) {
		this.dispositivo = dispositivo;
	}

	public Alerta getAlerta() {
		return alerta;
	}

	public void setAlerta(Alerta alerta) {
		this.alerta = alerta;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}
	
	public GeoPoint getPosition() {
		return posicion;
	}
	
	public void notifyPosition(GeoPoint posicion) {
		this.posicion = posicion;
	}

}