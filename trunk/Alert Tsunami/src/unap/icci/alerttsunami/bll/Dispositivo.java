package unap.icci.alerttsunami.bll;

public class Dispositivo {

	private Boolean estadoDeRiesgo;
	private Ciudad ciudad;
	private Usuario usuario;
	private Coordenada posicion;
	private Integer intervalo;

	public Dispositivo(Ciudad ciudad, Usuario usuario, Coordenada posicion){
		this.estadoDeRiesgo = null;
		setCiudad(ciudad);
		setUsuario(usuario);
		setPosicion(posicion);
	}
	
	//get estado de riesgo
	public Boolean getEstadoDeRiesgo(){
		return this.estadoDeRiesgo;
	}
	
	//get y set ciudad
	public Ciudad getCiudad(){
		return ciudad;
	}
	
	public void setCiudad(Ciudad ciudad){
		this.ciudad = ciudad;
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

	//get y set intervalo en que se demora en actualizar la posición del dispositivo
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

}