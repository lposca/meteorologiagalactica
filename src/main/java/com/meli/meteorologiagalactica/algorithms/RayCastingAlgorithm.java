package com.meli.meteorologiagalactica.algorithms;

import java.util.List;

import com.meli.meteorologiagalactica.Position;

/**
 * Implementación del algoritmo RayCasting.
 * @author lposca
 *
 */
public class RayCastingAlgorithm implements PointInsidePolygonAlgorithm {

	@Override
	public boolean inside(Position p, List<Position> points) {

		Position[] polygon = points.toArray(new Position[points.size()]);

		double minX = polygon[0].getX();
		double maxX = polygon[0].getX();
		double minY = polygon[0].getY();
		double maxY = polygon[0].getY();
		for (int i = 1; i < polygon.length; i++) {
			Position q = polygon[i];
			minX = Math.min(q.getX(), minX);
			maxX = Math.max(q.getX(), maxX);
			minY = Math.min(q.getY(), minY);
			maxY = Math.max(q.getY(), maxY);
		}

		if (p.getX() < minX || p.getX() > maxX || p.getY() < minY || p.getY() > maxY) {
			return false;
		}

		boolean inside = false;
		for (int i = 0, j = polygon.length - 1; i < polygon.length; j = i++) {
			if ((polygon[i].getY() > p.getY()) != (polygon[j].getY() > p.getY())
					&& p.getX() < (polygon[j].getX() - polygon[i].getX()) * (p.getY() - polygon[i].getY())
							/ (polygon[j].getY() - polygon[i].getY()) + polygon[i].getX()) {
				inside = !inside;
			}
		}

		return inside;
	}
}
