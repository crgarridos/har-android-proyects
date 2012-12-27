package cl.at.bussines;

import java.util.ArrayList;

import cl.at.data.GrupoFamiliarSQL;
import cl.at.data.InvitacionSQL;
import cl.at.data.PuntoEncuentroSQL;
import cl.at.data.UsuarioSQL;

public class GrupoFamiliar {

	private Integer id;
	private String nombre;
	private ArrayList<Comentario> comentarios;
	private PuntoEncuentro puntoEncuentro;
	private ArrayList<Usuario> integrantes;
	private Lider lider;

	public GrupoFamiliar() {
		comentarios = new ArrayList<Comentario>();
		integrantes = new ArrayList<Usuario>();
	}

	public GrupoFamiliar(String nombre, PuntoEncuentro puntoEncuentro, Lider lider) throws Exception{
		lider.setGrupoFamiliar(this);
		comentarios = new ArrayList<Comentario>();
		integrantes = new ArrayList<Usuario>();
		this.setNombre(nombre);
		this.setPuntoEncuentro(null);
		this.setLider(lider);
	}

	// Setter y getter

	public GrupoFamiliar(Usuario usuario) {
		GrupoFamiliarSQL gfSQL = new GrupoFamiliarSQL();
		gfSQL.cargarGrupoFamiliar(this, usuario);
		//TODO ahora solo se cargara el nombre y id del grupo familiar cuando se inicie sesion
//		if (gfSQL.cargarGrupoFamiliar(this, usuario)) {
//			PuntoEncuentroSQL ptoEncuentro = new PuntoEncuentroSQL();
//			puntoEncuentro = ptoEncuentro.cargarPtoEncuentro(this);
//			UsuarioSQL uSQL = new UsuarioSQL();
//			lider = usuario.esLider ? new Lider(usuario) : uSQL.cargarLider(this);
//			integrantes = uSQL.cargarIntegrantes(this, usuario);
//			ComentarioSQL cSQL = new ComentarioSQL();
//			comentarios = cSQL.cargarComentarios(this);
//		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void addComentario(Comentario comentario) {
		this.comentarios.add(comentario);
	}

	public ArrayList<Comentario> getComentarios() {
		return this.comentarios;
	}

	public void setPuntoEncuentro(PuntoEncuentro puntoEncuentro) {
		this.puntoEncuentro = puntoEncuentro;
	}

	public PuntoEncuentro getPuntoEncuentro(){
		return puntoEncuentro;
	}

	public Boolean addIntegrante(Usuario usuario) {
		this.integrantes.add(usuario);
		return usuario.setGrupoFamiliar(this);
	}

	public ArrayList<Usuario> getIntegrantes() {
		if (integrantes == null || integrantes.size() == 0){
			integrantes= new UsuarioSQL().cargarIntegrantes(this);
			PuntoEncuentroSQL ptoEncuentro = new PuntoEncuentroSQL();
			puntoEncuentro = ptoEncuentro.cargarPtoEncuentro(this);
		}
		return integrantes;
	}

	public void eliminarIntegrante(Usuario usuario) {
		this.integrantes.remove(usuario);
	}

	private void setLider(Lider lider) {
		this.lider = lider;
	}

	public Lider getLider() {
		return lider;
	}

	public void finalize() throws Throwable {

	}

	public void eliminar() {

	}

	public void persistir() throws Exception{
			GrupoFamiliarSQL grupoFamiliarSQL = new GrupoFamiliarSQL();
			if(!grupoFamiliarSQL.persistir(this))
				throw new Exception("Error");
	}

	public void notificar() {
		// TODO Auto-generated method stub
		
	}

}