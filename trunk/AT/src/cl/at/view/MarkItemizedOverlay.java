package cl.at.view;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import cl.at.bussines.Coordenada;
import cl.at.bussines.Punto;
import cl.at.bussines.PuntoEncuentro;
import cl.at.bussines.PuntoRiesgo;
import cl.at.bussines.Usuario;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MarkItemizedOverlay extends ItemizedOverlay<OverlayItem>{

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	
	public MarkItemizedOverlay(Drawable defaultMarker) {
		  super(boundCenterBottom(defaultMarker));
	}

	public MarkItemizedOverlay(Drawable defaultMarker, Context context) {
		  super(boundCenterBottom(defaultMarker));
		  mContext = context;
	}
	
	public void addPunto(Coordenada coordenada){
		GeoPoint posicion = new GeoPoint((int)(coordenada.getLatitud()*1E6), (int)(coordenada.getLongitud()*1E6));
		OverlayItem overlayItem = new OverlayItem(posicion, "Punto", "Posicion: "+coordenada.getLatitud()+" / "+coordenada.getLongitud());
		addOverlay(overlayItem);
	}
	
	public void addIntegrante(Usuario usuario){
		GeoPoint posicion = new GeoPoint((int)(usuario.getDispositivo().getPosicion().getLatitud()*1E6), (int)(usuario.getDispositivo().getPosicion().getLongitud()*1E6));
		OverlayItem overlayItem = new OverlayItem(posicion, usuario.getNombreUsuario(), "Posicion: "+usuario.getDispositivo().getPosicion().getLatitud()+" / "+usuario.getDispositivo().getPosicion().getLongitud());
		addOverlay(overlayItem);
	}
	
	public void addPuntoEncuentro(PuntoEncuentro puntoEncuentro){
		GeoPoint posicion = new GeoPoint((int)(puntoEncuentro.getCoordenada().getLatitud()*1E6), (int)(puntoEncuentro.getCoordenada().getLongitud()*1E6));
		OverlayItem overlayItem = new OverlayItem(posicion, "Punto de encuentro", puntoEncuentro.getReferencia());
		addOverlay(overlayItem);
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
//	    populate();
	}
	
	public void grabar(){
		populate();
	}

	@Override
	public int size() {
	  return mOverlays.size();
	}
	
	@Override
	protected boolean onTap(int index) {
	  OverlayItem item = mOverlays.get(index);
	  AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
	  dialog.setTitle(item.getTitle());
	  dialog.setMessage(item.getSnippet());
	  dialog.show();
	  return true;
	}

	public void addPuntoRiesgo(PuntoRiesgo puntoRiesgo) {
		GeoPoint posicion = new GeoPoint((int)(puntoRiesgo.getCoordenada().getLatitud()*1E6), (int)(puntoRiesgo.getCoordenada().getLongitud()*1E6));
		OverlayItem overlayItem = new OverlayItem(posicion, "Punto de riesgo", puntoRiesgo.getDescripcion());
		addOverlay(overlayItem);
	}
	
	public void addPuntoSeguro(Punto puntoSeguro, float distancia) {
		GeoPoint posicion = new GeoPoint((int)(puntoSeguro.getCoordenada().getLatitud()*1E6), (int)(puntoSeguro.getCoordenada().getLongitud()*1E6));
		OverlayItem overlayItem = new OverlayItem(posicion, "Punto de seguro", "Distancia: "+distancia+" metros");
		addOverlay(overlayItem);
	}
	
}
