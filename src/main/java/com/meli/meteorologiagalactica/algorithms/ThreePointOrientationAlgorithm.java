package com.meli.meteorologiagalactica.algorithms;

import java.util.List;

import com.meli.meteorologiagalactica.Position;
/**
 * Algoritmo que determina un punto dentro de un triángulo mediante la orientación que forman sus puntos.<br>
 * Esta implementación es solo para triángulos.
 * @author lposca
 *
 */
public class ThreePointOrientationAlgorithm implements PointInsidePolygonAlgorithm {

	private double areaT(Position A1, Position A2, Position A3) {
		// área del triángulo A1A2A3, es > 0 si esta orientado positivamente,else < 0
		return (A1.getX() - A3.getX()) * (A2.getY() - A3.getY()) - (A1.getY() - A3.getY()) * (A2.getX() - A3.getX());

	}//

	public boolean inside(Position P, List<Position> points) {

		// Decide si un punto P está dentro del triángulo orientado
		// A1A2A3
		boolean inside = false;
		Position A1 = points.get(0);
		Position A2 = points.get(1);
		Position A3 = points.get(2);

		if (areaT(A1, A2, A3) >= 0) {
			inside = areaT(A1, A2, P) >= 0 && areaT(A2, A3, P) >= 0 && areaT(A3, A1, P) >= 0;

		} else {
			inside = areaT(A1, A2, P) <= 0 && areaT(A2, A3, P) <= 0 && areaT(A3, A1, P) <= 0;

		}
		return inside;
	}
}
