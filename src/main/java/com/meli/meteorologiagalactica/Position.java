package com.meli.meteorologiagalactica;
/**
 * Representa la posición de un planeta en el plano cartesiano, en el formato (x,y).<br>
 * Cada {@link AstronomicalObject}  representa su posición mediante(x,y).
 * @author lposca
 *
 */
public class Position {
	
	/**
	 * Posición del sol
	 */
	public static Position sunPosition = new Position(0.0, 0.0);
	
	/**
	 *  Posición del planeta sobre el eje X cartesiano 
	 */
	private double x;
	
	/**
	 *  Posición del planeta sobre el eje Y cartesiano 
	 */
	private double y;

	/**
	 * Constructor indicando posiciones en x,y
	 * @param x
	 * @param y
	 */
	public Position(double x, double y) {
		this.x =Utils.roundDouble(x, 6);
		this.y = Utils.roundDouble(y, 6);;
	}

	/**
	 * Posición sobre x del planeta en el plano cartesiano
	 * @return posición sobre x
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Setea la posición sobre x del planeta en el plano cartesiano
	 * @param x posición sobre x
	 */
	public void setX(double x) {
		this.x = x;
	}
	/**
	 * Posición sobre y del planeta en el plano cartesiano
	 * @return posición sobre y
	 */
	public double getY() {
		return y;
	}

	/**
	 * Setea la posición sobre y del planeta en el plano cartesiano
	 * @param y posición sobre y
	 */
	public void setY(double y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + "]";
	}

}
