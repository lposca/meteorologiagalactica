package com.meli.meteorologiagalactica.algorithms;

import java.util.List;

import com.meli.meteorologiagalactica.Position;
import com.meli.meteorologiagalactica.Utils;
/**
 * Implementación de calculo de perímetro de un triángulo, sumando sus lados. <br>
 * 
 * @author lposca
 *
 */
public class TrianglePerimeterAlgorithm  implements PolygonPerimeterAlgorithm{

	public double perimeter( List<Position> points)
	{
		Position a1 = points.get(0);
		Position a2 = points.get(1);
		Position a3 = points.get(2);
				
		return perimeter(a1, a2) +perimeter(a1, a3) +perimeter(a2, a3); 
	}

	
	private double perimeter(Position a1, Position a2) {
		
		 double hypot = Math.hypot(Utils.bd(a1.getX(), 2).subtract(Utils.bd(a2.getX(), 2)).doubleValue(), Utils.bd(a1.getY(), 2).subtract(Utils.bd(a2.getY(), 2)).doubleValue());
		 return Utils.roundDouble(hypot, 4);
	}
}
