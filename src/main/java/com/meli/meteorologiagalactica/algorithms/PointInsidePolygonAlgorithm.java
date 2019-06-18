package com.meli.meteorologiagalactica.algorithms;

import java.util.List;

import com.meli.meteorologiagalactica.Position;

/**
 * Interfaz para implementar algoritmo que determine si un punto está dentro de un poligono.
 * @author lposca
 *
 */
public interface PointInsidePolygonAlgorithm {

	/**
	 * Determina si un punto  está dentro de un poligono
	 * @param P punto a determinar su pertenencia
	 * @param points  puntos que forman el poligono
	 * @return true si el punto está dentro, false caso contrario
	 */
	public boolean inside(Position P, List<Position> points);
}
