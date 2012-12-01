package unap.icci.alerttsunami.bll;

import java.util.ArrayList;

public class Usuario {

	private String email;
	private Boolean estadoDeLlegada;
	private String nombreCompleto;
	private String nombreUsuario;
	private String password;
	private ArrayList<Dispositivo> dispositivos;
	private ArrayList<Comentario> comentarios;
	private ArrayList<Invitacion> invitaciones;
	private GrupoFamiliar grupoFamiliar;
	
	//Constructores
	public Usuario(){
		dispositivos = new ArrayList<Dispositivo>();
		comentarios = new ArrayList<>();
		invitaciones = new ArrayList<>();
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

	public Boolean existe(String nombre, String pass){
		return false;
	}

	public ArrayList<Comentario> getComentarios(){
		return comentarios;
	}

	public GrupoFamiliar getGrupoFamiliar(){
		return grupoFamiliar;
	}

	public ArrayList<Invitacion> getInvitaciones(){
		return invitaciones;
	}

	public void onInvitacionRecibida(Invitacion invitacion){
		
	}

	public void persistir(){

	}

	public void vaciarInvitaciones(){
	}

}