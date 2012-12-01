package unap.icci.alerttsunami.bll;

import java.util.Date;

public class Alerta {

	private Integer categoria;
	private String descripcion;
	private Date fecha;
	public IObserverAlerta iObserverAlerta;

	//constructor
	public Alerta(Integer categoria, String descripcion, IObserverAlerta iObserverAlerta){
		setCategoria(categoria);
		setDescripcion(descripcion);
		this.fecha = new Date();
		setiObserverAlerta(iObserverAlerta);
	}
	
	//get y set categoria
	public Integer getCategoria() {
		return categoria;
	}
	
	public void setCategoria(Integer categoria){

	}
	
	//get y set descripción
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String desc){

	}
	
	//get y set fecha
	public Date getFecha() {
		return fecha;
	}
	
	//get y set Interface Observer Alerta
	public void setiObserverAlerta(IObserverAlerta iObserverAlerta) {
		this.iObserverAlerta = iObserverAlerta;
	}
	
	public IObserverAlerta getiObserverAlerta() {
		return iObserverAlerta;
	}
		
	//destructor
	public void finalize() throws Throwable {

	}

	//otros
	public void addAlertaListener(IObserverAlerta ia){

	}

	public void consultarSHOA(){

	}

	public void notificar(){

	}
}