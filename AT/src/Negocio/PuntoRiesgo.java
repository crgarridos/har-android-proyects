package Negocio;

public class PuntoRiesgo extends Punto {

	private Integer categoria;
	private String descripcion;

	public PuntoRiesgo(){

	}
	
	public PuntoRiesgo(Integer categoria, String descripcion){
		this.setCategoria(categoria);
		this.setDescripcion(descripcion);
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
	
}