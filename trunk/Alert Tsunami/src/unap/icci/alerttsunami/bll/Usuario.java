package unap.icci.alerttsunami.bll;

import java.util.ArrayList;

public class Usuario {

	private String email;
	private Boolean estadoDeLlegada;
	private String nombre;
	private String nombreUsuario;
	private String password;
	public ArrayList<Dispositivo> dispositivos;
	public ArrayList<Comentario> comentarios;
	public ArrayList<Invitacion> invitaciones;
	public GrupoFamiliar grupoFamiliar;

	public Usuario(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param nombreCompleto
	 * @param nombreUsuario
	 * @param pass
	 * @param email
	 */
	public Usuario(String nombreCompleto, String nombreUsuario, String pass, String email){

	}

	public void abandonarGrupoFamiliar(){

	}

	public void agregarUsuario(){

	}

	/**
	 * 
	 * @param cadena
	 */
	public Usuario buscar(String cadena){
		return null;
	}

	public void comentar(){

	}

	protected void definirPuntoEncuentro(){

	}

	public void eliminar(){

	}

	public boolean esLider(){
		return false;
	}

	public boolean estaLlegada(){
		return false;
	}

	/**
	 * 
	 * @param nombreUsuario
	 */
	public boolean existe(String nombreUsuario){
		return false;
	}

	/**
	 * 
	 * @param nombre
	 * @param pass
	 */
	public boolean existe(String nombre, String pass){
		return false;
	}

	public ArrayList<Comentario> getComentarios(){
		return null;
	}

	public String getEmail(){
		return "";
	}

	public GrupoFamiliar getGrupoFamiliar(){
		return null;
	}

	public ArrayList<Invitacion> getInvitaciones(){
		return null;
	}

	public String getNombreCompleto(){
		return "";
	}

	/**
	 * 
	 * @param invitacion
	 */
	public void onInvitacionRecibida(Invitacion invitacion){

	}

	public void persistir(){

	}

	/**
	 * 
	 * @param email
	 */
	public void setEmail(String email){

	}

	/**
	 * 
	 * @param llego
	 */
	public void setEstadoLlegada(boolean llego){

	}

	/**
	 * 
	 * @param nombreUsuario
	 */
	public void setNombreCompleto(String nombreUsuario){

	}

	/**
	 * 
	 * @param email
	 */
	public void setPass(String email){

	}

	public void vaciarInvitaciones(){

	}

}