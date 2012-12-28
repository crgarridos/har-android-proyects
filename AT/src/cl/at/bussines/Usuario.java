package cl.at.bussines;

import java.util.ArrayList;

import android.util.Log;
import cl.at.data.InvitacionSQL;
import cl.at.data.UsuarioSQL;

public class Usuario {

	private static final String TAG = Usuario.class.getName();
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
	protected Boolean externo = false;
	protected Boolean esLider = false;

	// Constructores
	public Usuario(String nombre, String pass) {
		this(nombre, null, pass, null);
		UsuarioSQL uSQL = new UsuarioSQL();
		existeUsuario = uSQL.cargarUsuario(this);
	}

	public Usuario(String nombre){
		this(nombre,null);
	}
	
	public Usuario(String nombre, int num) {
		this.nombreUsuario = nombre;
		UsuarioSQL uSQL = new UsuarioSQL();
		existeUsuario = uSQL.cargarUsuario(this);
	}

	public Usuario(String nombreUsuario, boolean externo) {
		this.nombreUsuario = nombreUsuario;
		if (!externo) {
			UsuarioSQL uSQL = new UsuarioSQL();
			existeUsuario = uSQL.cargarUsuario(this);
		} else
			this.externo = externo;
		dispositivo = new Dispositivo(this);
	}

	public Usuario() {
		// if(externo)
		// dispositivo = new Dispositivo(this);// TODO ,externo); debe cargar de
		// la bd
		// comentarios = new ArrayList<Comentario>();
		// invitaciones = new ArrayList<Invitacion>();
	}

	protected Usuario(String nombreUsuario, String nombreCompleto, String password, String email) {
		this.nombreUsuario = nombreUsuario;
		this.nombreCompleto = nombreCompleto;
		this.email = email;
		this.password = password;
		dispositivo = new Dispositivo(this);
//			comentarios = new ArrayList<Comentario>();
//			invitaciones = new ArrayList<Invitacion>();
	}

	protected Usuario(Usuario usuario){
		this.nombreUsuario = usuario.getNombreUsuario();
		this.nombreCompleto = usuario.getNombreCompleto();
		this.password = usuario.getPassword();
		this.email = usuario.getEmail();
		this.dispositivo = usuario.getDispositivo();
		this.comentarios = usuario.getComentarios();
		this.invitaciones = usuario.getInvitaciones();
		this.existeUsuario = usuario.getExisteUsuario();
		this.externo = usuario.getExterno();
	}// Fin contructores
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

	public Boolean setEstadoLlegada(boolean llego) {
		Comentario comentario = new Comentario(this.nombreUsuario+" ha llegado al punto de encuentro", this);
		try{
			comentario.persistir();
			this.estadoDeLlegada = llego;
			return true;
		}catch(Exception e){
			Log.e(TAG, "setEstadoLlegada"+e.toString());
			return false;
		}
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

	public Boolean getExterno() {
		return this.externo;
	}

	public void changeExterno(Boolean externo) {
		this.externo = externo;
	}

	// public void addDispositivo(Dispositivo dispositivo) {
	// this.dispositivos.add(dispositivo);
	// }

	// Fin setters y getters
	public void abandonarGrupoFamiliar() {
	}

	public void agregarUsuario() {

	}

	public Usuario buscar(String cadenaBusqueda) throws Exception {
		try {
			UsuarioSQL uSQL = new UsuarioSQL();
			if (uSQL.buscar(this, cadenaBusqueda))
				return this;
			return null;
		} catch (Exception e) {
			Log.e(TAG, "buscar, "+e.toString());
			return null;
		}
	}

	public void comentar() {

	}

	protected void definirPuntoEncuentro() {

	}

	public void eliminar() throws Exception {
		UsuarioSQL uSQL = new UsuarioSQL();
		if (!uSQL.eliminar(this))
			throw new Exception("Error");
	}

	public void esLider(int esLider) {
		this.esLider = (esLider == 1);
	}


	public Boolean existe(String nombreUsuario) {
		return false;
	}

	public ArrayList<Comentario> getComentarios() {
		//TODO cargar de la bd
		return comentarios;
	}

	public GrupoFamiliar getGrupoFamiliar() {
		if(grupoFamiliar == null){
			grupoFamiliar = new GrupoFamiliar(this);
			if(grupoFamiliar.getId()==null)
					grupoFamiliar = null;
		}
 		return grupoFamiliar;
	}

	public Boolean setGrupoFamiliar(GrupoFamiliar grupoFamiliar) {
		this.grupoFamiliar = grupoFamiliar;
		return new UsuarioSQL().enlazarGrupoFamiliar(this,grupoFamiliar);
	}

	public ArrayList<Invitacion> getInvitaciones() {
		if (invitaciones == null || invitaciones.size() == 0)
			invitaciones = new InvitacionSQL().cargarInvitaciones(this);
		return invitaciones;
	}

	public void onInvitacionRecibida(Invitacion invitacion) {

	}

	public void persistir() throws Exception {
		UsuarioSQL uSQL = new UsuarioSQL();
		if (!uSQL.persistir(this))
			throw new Exception("Error");
	}

	public Boolean vaciarInvitaciones() {
		Boolean exito = new InvitacionSQL().eliminarTodas(this);
		if(exito)
			this.invitaciones = new ArrayList<Invitacion>();
		return exito;
	}

	public void responderInvitacion() {

	}

	public Boolean esExterno() {
		return externo;
	}

	public boolean getEsLider() {
		return this.esLider;
	}
	
	public void setEsLider(Boolean esLider) {
		this.esLider = esLider;
	}

	public Boolean rechazarInvitacion(int posicionSeleccionada) {
		try{
			Invitacion inv = invitaciones.get(posicionSeleccionada);
			Boolean exito = new InvitacionSQL().eliminarInvitacion(inv);
			if(exito)
				this.invitaciones.remove(posicionSeleccionada);
			return exito;
		}
		catch(Exception ex){
			Log.e(TAG, "rechazarInvitacion, "+ex.toString()+" "+ex.getCause());
		}
		return false;
	}
}