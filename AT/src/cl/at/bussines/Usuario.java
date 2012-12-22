package cl.at.bussines;

import java.io.Serializable;
import java.util.ArrayList;

import cl.at.data.UsuarioSQL;
import cl.at.util.Util;

public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2640096247226986181L;
	protected String email;
	protected Boolean estadoDeLlegada;
	protected String nombreCompleto;
	protected String nombreUsuario;
	protected String password;
	protected Dispositivo dispositivo;
	protected ArrayList<Comentario> comentarios;
	protected ArrayList<Invitacion> invitaciones;
	protected GrupoFamiliar grupoFamiliar;
	protected Boolean existeUsuario = false;
	protected Boolean externo = true;

	// Constructores
	public Usuario(String nombre, String pass) {
		this(nombre, null, pass, null);
		UsuarioSQL uSQL = new UsuarioSQL();
		existeUsuario = uSQL.cargarUsuario(this);
		grupoFamiliar = new GrupoFamiliar(this);
		externo = false;
	}

	public Usuario(String nombre) {
		this(nombre, null);
	}

	public Usuario(String nombre, boolean externo) {
		this(nombre, null, null, null);
		this.externo = externo;
		UsuarioSQL uSQL = new UsuarioSQL();
		existeUsuario = uSQL.cargarUsuario(this);
		if(!externo) 
			grupoFamiliar = new GrupoFamiliar(this);
	}

	public Usuario() {
		if(externo)
			dispositivo = new Dispositivo(this);// TODO ,externo); debe cargar de la bd
		comentarios = new ArrayList<Comentario>();
		invitaciones = new ArrayList<Invitacion>();
	}

	protected Usuario(String nombreUsuario, String nombreCompleto, String password, String email) {
		this();
		this.nombreUsuario = nombreUsuario;
		this.nombreCompleto = nombreCompleto;
		this.password = password;
		this.email = email;
	}
	
	protected Usuario(Usuario usuario) {
		this.nombreUsuario = usuario.getNombreUsuario();
		this.nombreCompleto = usuario.getNombreCompleto();
		this.password = usuario.getPassword();
		this.email = usuario.getEmail();
		this.dispositivo = usuario.getDispositivo();
		this.comentarios = usuario.getComentarios();
		this.invitaciones = usuario.getInvitaciones();
	}// Fin contructores

	public void finalize() throws Throwable {

	}

	// Setters y getters
	public String getNombreUsuario() {
		return nombreUsuario.toLowerCase();
	}

	public Boolean getExisteUsuario() {
		return existeUsuario;
	}

	public void setExisteUsuario(Boolean existeUsuario) {
		this.existeUsuario = existeUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEstadoLlegada(boolean llego) {
		this.estadoDeLlegada = llego;
	}

	public Boolean isEstadoLlegada() {
		return estadoDeLlegada;
	}

	public Boolean estaLlegada() {
		return estadoDeLlegada;
	}

	public Dispositivo getDispositivo() {
		return dispositivo;
	}
	
	// public void addDispositivo(Dispositivo dispositivo) {
	// this.dispositivos.add(dispositivo);
	// }

	// Fin setters y getters
	public void abandonarGrupoFamiliar() {

	}

	public void agregarUsuario() {

	}

	public Usuario buscar(String cadena) {
		return null;
	}

	public void comentar() {

	}

	protected void definirPuntoEncuentro() {

	}

	public void eliminar() {

	}

	public Boolean esLider() {
		return false;// TODO implementar
	}

	public Boolean existe(String nombreUsuario) {
		return false;
	}

	// OJO YA NO VA
	// public Boolean existe(String nombre, String pass){
	// return false;
	// }

	public ArrayList<Comentario> getComentarios() {
		return comentarios;
	}

	public GrupoFamiliar getGrupoFamiliar() {
		return grupoFamiliar;
	}

	public void setGrupoFamiliar(GrupoFamiliar grupoFamiliar) {
		// if(this.grupoFamiliar!=null){
		// this.grupoFamiliar = grupoFamiliar;
		// }
		// return this.grupoFamiliar!=null;
	}

	public ArrayList<Invitacion> getInvitaciones() {
		return invitaciones;
	}

	public void onInvitacionRecibida(Invitacion invitacion) {

	}

	public void persistir() throws Exception {
		UsuarioSQL uSQL = new UsuarioSQL();
		if (!uSQL.persistir(this))
			throw new Exception("Error");
	}

	public void vaciarInvitaciones() {
	}

	public void responderInvitacion() {

	}

	public Boolean esExterno() {
		return externo;
	}
}