package com.meli.meteorologiagalactica;

/**
 * Representa un período de lluvia.<br>
 * Contiene el perímetro del triángulo que forman los planetas.
 * @author lposca
 *
 */
public class Rain {

	private boolean rain = false;
	private double perimeter = 0;
	
	public Rain() {
		
	}
	
	/**
	 * Perímetro del triángulo que forman los planetas
	 * @return perímetro
	 */
	public double getPerimeter() {
		return perimeter;
	}
	/**
	 * Determina si es un periodo de lluvia
	 * @return
	 */
	public boolean isRain() {
		return rain;
	}
	
	/**
	 * Setter para determinar periodo de lluvia
	 * @param llueve
	 */
	public void setRain(boolean llueve) {
		this.rain = llueve;
	}
	
	/**
	 * Setter para perímetro del triángulo que forman los planetas
	 * @param perimetro
	 */
	public void setPerimeter(double perimetro) {
		this.perimeter = perimetro;
	}
}
