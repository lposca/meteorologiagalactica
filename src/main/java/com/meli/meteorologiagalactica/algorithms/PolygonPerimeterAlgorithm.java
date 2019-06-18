package com.meli.meteorologiagalactica.algorithms;

import java.util.List;

import com.meli.meteorologiagalactica.Position;

/**
 * Interfaz para implementar algoritmo que determine el perímetro de un poligono
 * @author lposca
 *
 */
public interface PolygonPerimeterAlgorithm {

	/**
	 * Determina el perímetro de un poligono
	 * @param points  puntos que forman el poligono
	 * @return  perimetro del poligono
	 */
	public double perimeter( List<Position> points);
}
