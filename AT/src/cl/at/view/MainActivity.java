package cl.at.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import cl.at.bussines.Ciudad;
import cl.at.data.ConexionHttp;
import cl.at.util.HelloItemizedOverlay;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;


public class MainActivity extends MapActivity {

	private MapController m;
	private MapView mapView;
	String URL_connect="http://acinfo.unap.cl/jvega/Alerttsunami/ciudad.php";
	ConexionHttp post;
	private ProgressDialog pDialog;
	private Ciudad ciudad;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mapView = (MapView) findViewById(R.id.mapview);
		ciudad = new Ciudad(mapView);
		new asynclogin().execute();
	}
	
	public int obtenerCiudad(){
//		try {				
//			ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();
//			postparameters2send.add(new BasicNameValuePair("Latitud","-20.213889"));
//			postparameters2send.add(new BasicNameValuePair("Longitud","-70.152500"));
//			JSONArray jdata=post.getServerData(postparameters2send, URL_connect);
//	    	JSONObject json_data;
//			try{
//				json_data = jdata.getJSONObject(0);
////				c.setNombre("Iquique");
////				c.setLatitud(-20.213839);
////				c.setLongitud(-70.152500);
////				c.setNombre(json_data.getString("Nombre"));
////				c.setLatitud(Double.parseDouble(json_data.getString("Latitud")));
////				c.setLongitud(Double.parseDouble(json_data.getString("Longitud")));
//				return 0;
//			}catch(Exception e) {return -1;}
//		}catch(Exception e) {return -1;}
		return -1;
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	 class asynclogin extends AsyncTask< String, String, String > {

	        protected void onPreExecute() {
	            pDialog = new ProgressDialog(MainActivity.this);
	            pDialog.setMessage("Determinando ciudad....");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(false);
	            pDialog.show();
	        }
	 
			protected String doInBackground(String... params){ 
				ciudad.obtenerCiudad();
				SystemClock.sleep(300);
//				if(i == 0) return "ok";
				return "err";
			}

	        protected void onPostExecute(String result) {
//	        	if(result == "ok")
	        	pDialog.dismiss();
	        }
		}
	
}
