package cl.at.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class RouteSegmentOverlay extends Overlay {
	private GeoPoint locpoint;
	private Paint paint;
	private GeoPoint routePoints [];
	private int routeGrade[];
	private boolean routeIsActive;	
	private Point pold, pnew, pp;
	private int numberRoutePoints;
	
	// Constructor permitting the route array to be passed as an argument.
	public RouteSegmentOverlay(GeoPoint[] routePoints, int[] routeGrade) {
		this.routePoints = routePoints;
		this.routeGrade = routeGrade;
		numberRoutePoints  = routePoints.length;
		routeIsActive = true;
		// If first time, set initial location to start of route
		locpoint = routePoints[0];
		pold = new Point(0, 0);
		pnew = new Point(0,0);
		pp = new Point(0,0);
		paint = new Paint();
	}
	
	// Method to turn route display on and off
	public void setRouteView(boolean routeIsActive){
		this.routeIsActive = routeIsActive;
	}

	@Override
	public void draw(Canvas canvas, MapView mapview, boolean shadow) {
		super.draw(canvas, mapview, shadow);
		if(! routeIsActive) return;
		
		/* 
		The object mapview is a MapView.  The class MapView, documented at 
		http://code.google.com/android/add-ons/google-apis/reference/com/google/android/maps/MapView.html,
		has a method getProjection() that returns a Projection of the MapView.  Projection is an interface, docs at
		http://code.google.com/android/add-ons/google-apis/reference/com/google/android/maps/Projection.html,
		which has a method toPixels(GeoPoint in, android.graphics.Point out), which takes a GeoPoint (in) and outputs
		a Point (out) that contains the screen x and y coordinates (in pixels) corresponding to the GeoPoint. (with latitude
		and longitude specified in microdegrees).  Thus, after implementing mapview.getProjection().toPixels(gp, pp),
		where gp is a GeoPoint, pp.x and pp.y hold the corresponding screen coordinates for the current MapView.
		Note that this translation must be done each time draw is called, since the relationship between GeoPoints and
		x-y coordinates may have changed (for example, if the map were panned or zoomed since the last draw).
		 */
		
		mapview.getProjection().toPixels(locpoint, pp);       // Converts GeoPoint to screen pixels
		
		int xoff = 0;
		int yoff = 0;
		int oldx = pp.x;
		int oldy = pp.y;
		int newx = oldx + xoff;
		int newy = oldy + yoff;
		
		paint.setAntiAlias(true);

		// Draw route segment by segment, setting color and width of segment according to the slope
		// information returned from the server for the route.
		
		for(int i=0; i<numberRoutePoints-1; i++){
			switch(routeGrade[i]){
				case 1:
					paint.setARGB(100,255,0,0);
					paint.setStrokeWidth(3);
					break;
				case 2:
					paint.setARGB(100, 0, 255, 0);
					paint.setStrokeWidth(5);
					break;
				case 3:
					paint.setARGB(100, 0, 0, 255);
					paint.setStrokeWidth(7);
					break;
				case 4:
					paint.setARGB(90, 153, 102, 153);
					paint.setStrokeWidth(6);
					break;
			}
			
			// Find endpoints of this segment in pixels
			mapview.getProjection().toPixels(routePoints[i], pold);
			oldx = pold.x;
			oldy = pold.y;
			mapview.getProjection().toPixels(routePoints[i+1], pnew);
			newx = pnew.x;
			newy = pnew.y;
			
			// Draw the segment
			canvas.drawLine(oldx, oldy, newx, newy, paint);
		}
	      
	}

}
