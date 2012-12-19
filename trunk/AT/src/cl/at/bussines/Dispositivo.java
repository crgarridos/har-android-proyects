package cl.at.bussines;

public class Dispositivo {

	private Boolean estadoDeRiesgo;
//	private Ciudad ciudad;
	private Usuario usuario;
	private Coordenada posicion;
	private Integer intervalo;

	public Dispositivo(Usuario usuario){
		
		this.estadoDeRiesgo = false;//TODO Calcular la posicion e indicar si hay estado de riesgo
		this.intervalo = 3600000;//? en milisegundos??
//		setCiudad(ciudad);
		setUsuario(usuario);
		//actualizar posicion//setPosicion(null);//TODO calcular
	}
	
	//get estado de riesgo
	public Boolean getEstadoDeRiesgo(){
		return this.estadoDeRiesgo;
	}
	
	//get y set ciudad

	//get y set usuario
	public Usuario getUsuario(){
		return usuario;
	}
	
	public void setUsuario(Usuario usuario){
		this.usuario = usuario;
	}
	
	//get y set posici�n
	public Coordenada getPosicion(){
		return posicion;
	}
	
	public void setPosicion(Coordenada posicion){
		this.posicion = posicion;
	}

	//get y set intervalo en que se demora en actualizar la posici�n del dispositivo
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
	public void actualizarPosicion(Coordenada c){
	}

	public Boolean estaSeguro(){
		return estadoDeRiesgo; //podria tener un setter para que la ciudad lo haga cambiar de estado, asi evitarnos la relacion.
								// creo que para eso estaba :S, la relacion...
//		if(this.posicion == this.ciudad.getPuntoSeguridad().coordenada){
//			return true;
//		}
//		else
//			return false;
	}
}