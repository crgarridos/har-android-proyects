package unap.icci.alerttsunami.bll;

import java.util.Date;

/**
 * @author Alert Tsunami
 * @version 1.0
 * @created 01-dic-2012 12:59:35
 */
public class Alerta {

	private Integer categoria;
	private String descripcion;
	private Date fecha;
	public IObserverAlerta iObserverAlerta;

	public Alerta(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param ia
	 */
	public void addAlertaListener(IObserverAlerta ia){

	}

	public void consultarSHOA(){

	}

	public void notificar(){

	}

	/**
	 * 
	 * @param categoria
	 */
	public void setCategoria(Integer categoria){

	}

	/**
	 * 
	 * @param desc
	 */
	public void setDescripcion(String desc){

	}

	/**
	 * 
	 * @param fechya
	 */
	public void setFecha(Date fecha){

	}

}