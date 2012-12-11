package Negocio;

public interface IObserverAlerta {

	public void onAlertaFinalizada();

	public void onAlertaRecibida(Alerta alerta);

}