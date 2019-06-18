package com.meli.meteorologiagalactica;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.meli.meteorologiagalactica.algorithms.PointInsidePolygonAlgorithm;
import com.meli.meteorologiagalactica.algorithms.PolygonPerimeterAlgorithm;
/**
 * Clase utilitaria para conversión de números, redondeo y operaciones geométricas como
 * determinar si un punto está dentro de un triángulo.
 * @author lposca
 *
 */
@Component
public class Utils {

	@Autowired
	private PointInsidePolygonAlgorithm pointInsidePolygonAlgorithm;

	@Autowired
	private PolygonPerimeterAlgorithm perimeterAlgorithm;

	/**
	 * Redondea un double con los decimales pasados por parámetro
	 * @param doubleValue  valor a redondear
	 * @param decimals  decimales del redondeo
	 * @return nuevo double redondeado
	 */
	public static double roundDouble(double doubleValue, int decimals) {
		return bd(doubleValue, decimals).doubleValue();
	}
	
	/**
	 * Conversión de double a BigDecimal con redondeo.
	 * @param doubleValue  valor a transfomar a BigDecimal
	 * @param decimals decimales de redondeo
	 * @return  BigDecimal convertido
	 */
	public static BigDecimal bd(double doubleValue, int decimals) {
		return new BigDecimal(String.valueOf(doubleValue)).setScale(decimals, RoundingMode.HALF_UP);
	}

	/**
	 * Calcula la pendiente entre la recta que pasa por 2 puntos.
	 * 
	 * @param p1
	 *            punto 1 de la recta
	 * @param p2
	 *            punto 2 de la recta
	 * @return pendiente de la recta
	 */
	public static double getSlope(Position p1, Position p2) {

		double y1 = p1.getY();
		double y2 = p2.getY();

		double x1 = p1.getX();
		double x2 = p2.getX();

		if (y1 == 0.0 && y2 == 0.0) {
			return 0.0;
		}
		if (x1 == 0.0 && x2 == 0.0) {
			return Double.POSITIVE_INFINITY;
		}

		return bd(y1, 6).subtract(bd(y2, 6)).divide(bd(x1, 6).subtract(bd(x2, 6)), 2, RoundingMode.HALF_UP)
				.doubleValue();

	}

	/**
	 * Calcula la alineación entre planetas y luego con el sol.<br>
	 * La alineación se calcula si la recta que pasa por los puntos, posee la misma
	 * pendiente; en el caso que posean la misa pendiente se calcula luego la
	 * pendiente con la posición del sol. Con estos cálculos podemos determinar si
	 * hubo condiciones optimas o sequia.
	 * 
	 * @param positions
	 *            puntos de las posiciones de los planetas
	 * @return {@link AstronomicalObjectAlignment} indicando si hubo condiciones
	 *         optimas o sequia
	 */
	public static AstronomicalObjectAlignment calculateAstronomicalObjectAlignment(List<Position> positions) {

		boolean firstSlope = true;
		Position p1 = positions.get(0), p2 = null;

		Double lastSlope = null;
		for (int i = 1; i < positions.size(); i++) {
			p2 = positions.get(i);
			Double slope = Utils.getSlope(p1, p2);

			if (firstSlope) {
				firstSlope = false;

			} else {
				if (!lastSlope.equals(slope)) {
					return AstronomicalObjectAlignment.NONE;
				}
			}
			lastSlope = slope;
		}

		AstronomicalObjectAlignment astronomicalObjectAlignment = new AstronomicalObjectAlignment();
		astronomicalObjectAlignment.setOptimalConditions(true);
		double sunSlope = getSlope(p2, Position.sunPosition);
		if (lastSlope.equals(sunSlope)) {
			astronomicalObjectAlignment.setOptimalConditions(false);
			astronomicalObjectAlignment.setDrought(true);

		}

		return astronomicalObjectAlignment;
	}

	/**
	 * Determina si la posición de los planetas corresponde a un período de lluvia.<br>
	 * El cálculo de la posición del sol dentro del triángulo que forman los planetas se determina
	 * con el algoritmo dentro de {@link PointInsidePolygonAlgorithm}.  <br><br>
	 * En el caso que hubiese lluvia, se determina el perímetro del triángulo con el algoritmo definido dentro de
	 * {@link PolygonPerimeterAlgorithm}
	 * @param P posición del punto a determinar dentro del triángulo
	 * @param positions posiciones de los planetas que forman el triángulo
	 * @return  {@link Rain} determinando si llueve y su máximo perímetro
	 */
	public Rain rains(Position P, List<Position> positions) {
		boolean inside = pointInsidePolygonAlgorithm.inside(P, positions);

		Rain rain = new Rain();

		if (inside) {
			rain.setRain(true);
			rain.setPerimeter(perimeterAlgorithm.perimeter(positions));
		}

		return rain;
	}

}
