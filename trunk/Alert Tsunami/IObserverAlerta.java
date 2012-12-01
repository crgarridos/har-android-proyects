public interface IObserverAlerta {

	public void onAlertaFinalizada();

	/**
	 * 
	 * @param alerta
	 */
	public void onAlertaRecibida(Alerta alerta);

}