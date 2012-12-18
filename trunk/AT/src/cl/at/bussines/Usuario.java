package cl.at.bussines;

import java.io.Serializable;
import java.util.ArrayList;

import cl.at.data.UsuarioSQL;


public class Usuario implements Serializable{

	protected String email;
	protected Boolean estadoDeLlegada;
	protected String nombreCompleto;
	protected String nombreUsuario;
	protected String password;
	protected ArrayList<Dispositivo> dispositivos;
	protected ArrayList<Comentario> comentarios;
	protected ArrayList<Invitacion> invitaciones;
	protected GrupoFamiliar grupoFamiliar;
	protected Boolean existeUsuario = false;
	
	//Constructores
	public Usuario(String nombre, String pass){
		this.nombreUsuario = nombre;
		this.password = pass;
		UsuarioSQL uSQL = new UsuarioSQL();
		//this.grupoFamiliar = uSQL.obtenerGrupoFamiliar(this);
		existeUsuario = uSQL.cargarUsuario(this);
	}
	
	public Usuario (String nombre){
		this.nombreUsuario = nombre;
		UsuarioSQL uSQL = new UsuarioSQL();
		existeUsuario = uSQL.cargarUsuarioIniciar(this);
	}
	
	public Usuario(){
		dispositivos = new ArrayList<Dispositivo>();
		comentarios = new ArrayList<Comentario>();
		invitaciones = new ArrayList<Invitacion>();
	}
	
	public void finalize() throws Throwable {

	}

	public Usuario(String nombreCompleto, String nombreUsuario, String pass, String email){
		this.setNombreCompleto(nombreCompleto);
		this.setNombreUsuario(nombreUsuario);
		this.setPassword(pass);
		this.setEmail(email);
	}//Fin contructores
	
	// Setters y getters
	public String getNombreUsuario() {
		return nombreUsuario;
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

	public String getNombreCompleto(){
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto){
		this.nombreCompleto=nombreCompleto;
	}

	public String getEmail(){
		return email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEstadoLlegada(boolean llego){
		this.estadoDeLlegada = llego;
	}
	
	public Boolean isEstadoLlegada(){
		return estadoDeLlegada;
	}
	
	public Boolean estaLlegada(){
		return estadoDeLlegada;
	}

	public ArrayList<Dispositivo> getDispositivos() {
		return dispositivos;
	}

	public void addDispositivo(Dispositivo dispositivo) {
		this.dispositivos.add(dispositivo);
	}
	
	//Fin setters y getters
	public void abandonarGrupoFamiliar(){

	}

	public void agregarUsuario(){

	}

	public Usuario buscar(String cadena){
		return null;
	}

	public void comentar(){
		
	}

	protected void definirPuntoEncuentro(){

	}

	public void eliminar(){

	}

	public Boolean esLider(){
		return false;
	}

	public Boolean existe(String nombreUsuario){
		return false;
	}

// 	OJO YA NO VA 
//	public Boolean existe(String nombre, String pass){
//		return false;
//	}

	public ArrayList<Comentario> getComentarios(){
		return comentarios;
	}

	public GrupoFamiliar getGrupoFamiliar(){
		return grupoFamiliar;
	}

	public void setGrupoFamiliar(GrupoFamiliar grupoFamiliar){
//		if(this.grupoFamiliar!=null){
//			this.grupoFamiliar = grupoFamiliar;
//		}
//		return this.grupoFamiliar!=null;
	}
	
	public ArrayList<Invitacion> getInvitaciones(){
		return invitaciones;
	}

	public void onInvitacionRecibida(Invitacion invitacion){
		
	}

	public void persistir() throws Exception{
		UsuarioSQL uSQL = new UsuarioSQL();
		if(!uSQL.persistir(this))
			throw new Exception("Error");
	}	

	public void vaciarInvitaciones(){
	}

	public void responderInvitacion(){
		
	}
	
}