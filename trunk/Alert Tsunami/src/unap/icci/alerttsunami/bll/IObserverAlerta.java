package unap.icci.alerttsunami.bll;

public interface IObserverAlerta {

	public void onAlertaFinalizada();

	/**
	 * 
	 * @param alerta
	 */
	public void onAlertaRecibida(Alerta alerta);

}