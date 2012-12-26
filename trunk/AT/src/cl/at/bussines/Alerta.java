package cl.at.bussines;

import java.util.Date;

public class Alerta {

	private Integer categoria;
	private String descripcion;
	private Date fecha;
	public Dispositivo	dispositivo;

	public Alerta() {
	}

	public Alerta(Integer categoria, String descripcion, Dispositivo dispositivo){
		setCategoria(categoria);
		setDescripcion(descripcion);
		this.fecha = new Date();
		this.dispositivo = dispositivo;
	}
	
	public Alerta(Dispositivo dispositivo) {
		this.dispositivo=dispositivo;
	}

	public Integer getCategoria() {
		return categoria;
	}
	public void setCategoria(Integer categoria) {
		this.categoria = categoria;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public void consultaSHOA(Date fecha,Integer categoria,String descripcion){
		setFecha(fecha);
		setCategoria(categoria);
		setDescripcion(descripcion);
		notificar();
	}

	public void notificar(){
		dispositivo.onAlertaRecibida(this);
	}
}