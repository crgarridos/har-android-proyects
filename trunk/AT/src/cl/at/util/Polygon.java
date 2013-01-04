package cl.at.util;

import java.util.List;

import cl.at.bussines.Coordenada;

/**
 * Minimum Polygon class for Android.
 */
public class Polygon {
	// Polygon coodinates.
	private double[] polyY;

	double[] polyX;

	// Number of sides in the polygon.
	private int polySides;

	/**
	 * Default constructor.
	 * 
	 * @param px
	 *            Polygon y coods.
	 * @param py
	 *            Polygon x coods.
	 * @param ps
	 *            Polygon sides count.
	 */
	public Polygon(double[] px, double[] py, int ps) {
		polyX = px;
		polyY = py;
		polySides = ps;
	}

	public Polygon(List<Coordenada> poly) {

		polySides = poly.size() + 1;
		polyX = new double[polySides];
		polyY = new double[polySides];
		for (int i = 0; i < polySides; i++) {
			polyX[i] = poly.get(i % (polySides - 1)).getLongitud();
			polyY[i] = poly.get(i % (polySides - 1)).getLatitud();
		}
	}

	/**
	 * Checks if the Polygon contains a point.
	 * 
	 * @see "http://alienryderflex.com/polygon/"
	 * @param x
	 *            Point horizontal pos.
	 * @param y
	 *            Point vertical pos.
	 * @return Point is in Poly flag.
	 */
	
	public boolean contains(double x, double y) {

		boolean oddTransitions = false;
		for (int i = 0, j = polySides - 1; i < polySides; j = i++) {
			if ((polyY[i] < y && polyY[j] >= y) || (polyY[j] < y && polyY[i] >= y)) {
				if (polyX[i] + (y - polyY[i]) / (polyY[j] - polyY[i]) * (polyX[j] - polyX[i]) < x) {
					oddTransitions = !oddTransitions;
				}
			}
		}
		return oddTransitions;
	}
}
