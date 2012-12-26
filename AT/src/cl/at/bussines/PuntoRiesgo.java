package cl.at.bussines;

import cl.at.data.PuntoRiesgoSQL;

public class PuntoRiesgo extends Punto {

	private Integer categoria;
	private String descripcion;
	private Ciudad ciudad;

	public PuntoRiesgo(){

	}
	
	public PuntoRiesgo(Integer categoria, String descripcion, Ciudad ciudad, Coordenada coordenada){
		this.setCategoria(categoria);
		this.setDescripcion(descripcion);
		this.setCoordenada(coordenada);
		this.ciudad = ciudad;
		this.ciudad.ingresarPuntoRiesgo(this);
		
		
	}

	public void persistir() throws Exception {
		PuntoRiesgoSQL puntoRiesgoSQL = new PuntoRiesgoSQL();
		if (!puntoRiesgoSQL.persistir(this))
			throw new Exception("Error");
	}

	public void finalize() throws Throwable {
		super.finalize();
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
	
	public Ciudad getCiudad(){
		return ciudad;
	}
	
}