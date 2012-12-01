package unap.icci.alerttsunami.bll;

public interface IObserverAlerta {

	public void onAlertaFinalizada();

	public void onAlertaRecibida(Alerta alerta);

}